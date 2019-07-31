package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.SmsTemplate;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.SendCoinStatus;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class SendCoinService {
    private static final Logger logger = LoggerFactory.getLogger(SendCoinService.class);

    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private UserCoinMapper userCoinMapper;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private SendCoinMapper sendCoinMapper;
    @Autowired
    CoinConfigMapper coinConfigMapper;
    @Autowired
    private TransferMapper transferMapper;
    @Autowired
    AdapterService adapterService;
    @Autowired
    SmsService smsService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ConfigService configService;

    @Transactional
    public void transferCoin(int userId, String coinName, String address, double amount, String comment) {
        Coin coin = coinMapper.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_RECORD_NOT_EXIST);

        UserCoinVO userCoin = userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
        Assert.check(userCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);

        UserCoin toUserCoin = userCoinMapper.getUserCoinByAddressAndCoinName(address, coinName);
        if (toUserCoin != null) {
            //内部转账
            this.transfer(userId, coinName, address, amount, comment);
        } else {
            //转账到外部，提现
            this.transferOut(userId, coinName, address, amount, comment);
        }
    }

    //内部转账
    private void transfer(int userId, String coinName, String toAddress, double amount, String comment) {
        Assert.check(amount <= 0, ErrorCode.ERR_PARAM_ERROR);

        CoinConfig config = coinConfigMapper.getCoinConfigByName(coinName);
        Assert.check(config == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        UserCoin toUserCoin = userCoinService.getUserCoinByCoinNameAndAddress(coinName, toAddress);
        Assert.check(toUserCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);

        //冻结金额
        BigDecimal feeAmount = BigDecimal.ZERO;
        if (config.getTransferFeeSelect().intValue() == 0) {
            feeAmount = new BigDecimal(amount).multiply(config.getTransferFeeRate());
        } else if (config.getTransferFeeSelect().intValue() == 1) {
            feeAmount = config.getTransferFeeStatic();
            Assert.check(amount <= feeAmount.doubleValue(), ErrorCode.ERR_TRANSFER_AMOUNT_TO_LOW);
        }


        //转出方减少金额
        userCoinService.changeUserCoin(userId, coinName, BigDecimal.valueOf(amount).multiply(new BigDecimal(-1)), BigDecimal.ZERO, UserBillReason.TRANSFER, "转账出账");
        //转入方增加金额
        userCoinService.changeUserCoin(toUserCoin.getUserId(), coinName, BigDecimal.valueOf(amount).subtract(feeAmount), BigDecimal.ZERO, UserBillReason.TRANSFER, "转账入账");
        //添加记录
        String txid = "inner-" + System.currentTimeMillis() + "-" + userId + "-" + (new Random()).nextInt();
        sendCoinMapper.add(new SendCoin().setUserId(userId).setCoinName(coinName).setAddress(toAddress).setAmount(BigDecimal.valueOf(amount)).setFee(feeAmount)
                .setTxid(txid).setReceivedUserId(toUserCoin.getUserId()).setStatus(SendCoinStatus.PASSED));
        //添加收款记录
        transferMapper.addReceivedCoin(toUserCoin.getUserId(), toAddress, coinName, txid, BigDecimal.valueOf(amount).subtract(feeAmount), feeAmount, Long.valueOf(System.currentTimeMillis() / 1000l).intValue(), SendCoinStatus.PASSED.getValue());
    }

    /**
     * 把一个人的冻结余额转到另外一个人
     *
     * @param coinName   币种名称
     * @param fromUserId 转出人
     * @param toUserId   收款人
     * @param amount     金额大小
     */
    @Transactional
    public void transferFreezeCoin(String coinName, int fromUserId, int toUserId, BigDecimal amount) {
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);
        userCoinService.changeUserReceivedFreezeCoin(fromUserId, coinName, amount.negate(), UserBillReason.TRANSFER, "转出冻结金");
        // 冻结金扣除手续费 固定usdt作为手续费
        BigDecimal feeUsdt = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_FIXED, BigDecimal.ZERO, false);
        userCoinService.changeUserCoin(fromUserId, "USDT", feeUsdt.negate(), BigDecimal.ZERO, UserBillReason.LOCK_TRANSFER_FEE, "锁仓转账手续费");

//        BigDecimal fee = BigDecimal.ZERO;
//        int select = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_TRANSFERFEESELECT, 1);
//        if (select == 0) {
//            fee = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_FIXED,  BigDecimal.ZERO, false);
//        } else if (select == 1) {
//            BigDecimal configValue = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_RATIO, BigDecimal.ZERO, false);
//            fee = amount.multiply(configValue);
//        }
//        logger.info("转账冻结金的币种为: " + coinName + ",手续费为：" + fee);
        userCoinService.changeUserReceivedFreezeCoin(toUserId, coinName, amount, UserBillReason.TRANSFER, "内部转入冻结金");
    }

    //外部转账
    private void transferOut(int userId, String coinName, String toAddress, double amount, String comment) {
        Assert.check(amount <= 0, ErrorCode.ERR_PARAM_ERROR);

        Assert.check(!adapterService.isAddressValid(coinName, toAddress), ErrorCode.ERR_INVALID_ADDRESS);

        CoinConfig config = coinConfigMapper.getCoinConfigByName(coinName);
        Assert.check(config == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        //冻结金额
        BigDecimal feeAmount = BigDecimal.ZERO;
        if (config.getTransferFeeSelect().intValue() == 0) {
            feeAmount = new BigDecimal(amount).multiply(config.getTransferFeeRate());
        } else if (config.getTransferFeeSelect().intValue() == 1) {
            feeAmount = config.getTransferFeeStatic();
            Assert.check(amount <= feeAmount.doubleValue(), ErrorCode.ERR_TRANSFER_AMOUNT_TO_LOW);
        }


        userCoinService.changeUserCoin(userId, coinName, new BigDecimal(amount).multiply(new BigDecimal(-1)), new BigDecimal(amount), UserBillReason.FREEZE, "提现冻结");
        sendCoinMapper.add(new SendCoin().setUserId(userId).setCoinName(coinName).setAmount(new BigDecimal(amount)).setFee(feeAmount)
                .setStatus(SendCoinStatus.APPLYING).setAddress(toAddress));
    }

    //第一次审核失败
    @Transactional
    public void transferCheckFail(int id, int userId) {
        SendCoin sendCoin = sendCoinMapper.lockTransferById(id);
        Assert.check(sendCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(sendCoin.getStatus() != SendCoinStatus.APPLYING, ErrorCode.ERR_RECORD_DATA_ERROR);
        //Assert.check(sendCoin.getUserId()!=userId, ErrorCode.ERR_RECORD_DATA_ERROR);

        //BigDecimal freezeAmount = sendCoin.getAmount().add(sendCoin.getFee());
        userCoinService.changeUserCoin(sendCoin.getUserId(), sendCoin.getCoinName(), sendCoin.getAmount(), sendCoin.getAmount().multiply(new BigDecimal(-1)),
                UserBillReason.UNFREEZE, "提现冻结失败返回");

        sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.FAILED.getValue(), null);
    }

    //第一次审核通过
    @Transactional
    public void transferCheckPass(int id, int userId) {
        SendCoin sendCoin = sendCoinMapper.lockTransferById(id);
        Assert.check(sendCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(sendCoin.getStatus() != SendCoinStatus.APPLYING, ErrorCode.ERR_RECORD_DATA_ERROR);
        //Assert.check(sendCoin.getUserId()!=userId, ErrorCode.ERR_RECORD_DATA_ERROR);

        // sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.PASSED.getValue(), null);
        sendCoinMapper.updateTransferSecondStatusById(id, SendCoinStatus.PASSED.getValue(), null);
        // BigDecimal amount = sendCoin.getAmount();
        // BigDecimal toAmount = sendCoin.getAmount().subtract(sendCoin.getFee());

//        //冻结扣除，审核通过
        //userCoinService.firstChangeUserCoin(sendCoin.getUserId(), sendCoin.getCoinName(), BigDecimal.ZERO, amount.negate(), UserBillReason.WITHDRAW, "提现冻结成功扣除");
//        sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.PASSED.getValue(), null);
//
//        //区块链主账户转账
//        String txid = adapterService.sendToAddress(sendCoin.getUserId(), sendCoin.getCoinName(), sendCoin.getAddress(), toAmount.doubleValue());
//        Assert.check(txid==null || txid.length()<=0, ErrorCode.ERR_TRANSFER_FAIL);
//
//        try {
//            sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.PASSED.getValue(), txid);
//            User user = userMapper.getUserByUserId(sendCoin.getUserId());
//            if (user!=null) {
//                smsService.sendSms(user.getAreaCode(), user.getMobile()==null?user.getEmail():user.getMobile(), SmsTemplate.transferCheckPass(toAmount.doubleValue(), sendCoin.getCoinName()));
//            }
//        } catch (Exception ex) {
//            logger.error("", ex);
//        }
    }


    //第二次审核失败
    @Transactional
    public void transferSecondCheckFail(int id, int userId) {
        SendCoin sendCoin = sendCoinMapper.lockTransferById(id);
        Assert.check(sendCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(sendCoin.getStatus() != SendCoinStatus.APPLYING, ErrorCode.ERR_RECORD_DATA_ERROR);
        userCoinService.changeUserCoin(sendCoin.getUserId(), sendCoin.getCoinName(), sendCoin.getAmount(), sendCoin.getAmount().multiply(new BigDecimal(-1)),
                UserBillReason.UNFREEZE, "提现冻结失败返回");
        sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.FAILED.getValue(), null);
    }

    //第二次审核通过
    @Transactional
    public void transferSecondCheckPass(int id, int userId) {
        SendCoin sendCoin = sendCoinMapper.lockTransferById(id);
        Assert.check(sendCoin == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(sendCoin.getStatus() != SendCoinStatus.APPLYING, ErrorCode.ERR_RECORD_DATA_ERROR);
        //Assert.check(sendCoin.getUserId()!=userId, ErrorCode.ERR_RECORD_DATA_ERROR);
        if (sendCoin.getStatus() == SendCoinStatus.APPLYING && sendCoin.getSecondStatus().equals("1")) {

            BigDecimal amount = sendCoin.getAmount();
            BigDecimal toAmount = sendCoin.getAmount().subtract(sendCoin.getFee());

            //冻结扣除，审核通过
            userCoinService.changeUserCoin(sendCoin.getUserId(), sendCoin.getCoinName(), BigDecimal.ZERO, amount.negate(), UserBillReason.WITHDRAW, "提现冻结成功扣除");

            //区块链主账户转账
            String txid = adapterService.sendToAddress(sendCoin.getUserId(), sendCoin.getCoinName(), sendCoin.getAddress(), toAmount.doubleValue());
            Assert.check(txid == null || txid.length() <= 0, ErrorCode.ERR_TRANSFER_FAIL);
            try {
                User user = userMapper.getUserByUserId(sendCoin.getUserId());
                if (user != null) {
                    smsService.sendSms(user.getAreaCode(), user.getMobile() == null ? user.getEmail() : user.getMobile(), SmsTemplate.transferCheckPass(toAmount.doubleValue(), sendCoin.getCoinName()));
                }
                sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.PASSED.getValue(), txid);
            } catch (Exception ex) {
                logger.error("", ex);
            }
        } else {
            Assert.check(true, ErrorCode.ERR_TRANSFER_FAIL);
            logger.info("illeage request---");
        }
    }


    //获取列表
    public Page<SendCoin> getTransferList(Integer userId, String coinName, SendCoinStatus status, int pageNo, int pageSize) {
        return sendCoinMapper.getTransfer(userId, coinName, status != SendCoinStatus.ALL ? status.getValue() : null, new RowBounds(pageNo, pageSize));
    }

    //第一次获取的审核列表
    public Page<SendCoin> getTransferListByTime(Integer userId, String coinName, SendCoinStatus status, String startTime, String endTime, String groupType, int pageNo, int pageSize) {
        return sendCoinMapper.getTransferByTime(userId, coinName, status != SendCoinStatus.ALL ? status.getValue() : null, startTime, endTime, groupType, new RowBounds(pageNo, pageSize));
    }

    //第二次获取的审核列表
    public Page<SendCoin> getSecondTransferListByTime(Integer userId, String coinName, String startTime, String endTime, String groupType, int pageNo, int pageSize) {
        return sendCoinMapper.getSecondTransferByTime(userId, coinName, startTime, endTime, groupType, new RowBounds(pageNo, pageSize));
    }

    public List<SendCoin> statUserTotalSendSuccessCoins(int userId) {
        return sendCoinMapper.statUserTotalSendSuccessCoins(userId);
    }

    public List<SendCoin> statTotalSendSuccessCoins() {
        return sendCoinMapper.statTotalSendSuccessCoins();
    }

    //获取节点未确认的列表
    public List<SendCoin> getTransferUnconfirm(Integer id) {
        return sendCoinMapper.getTransferUnconfirm(id);
    }

    @Transactional
    public void nodeConfirm(int id) {
        SendCoin sendCoin = sendCoinMapper.lockTransferById(id);
        CoinConfig coinConfig = coinConfigMapper.getCoinConfigByName(sendCoin.getCoinName());
        Assert.check(coinConfig == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        int confirm = adapterService.getTxConfirmCount(sendCoin.getCoinName(), sendCoin.getTxid());
        if (coinConfig.getNodeConfirmCount() == null) {
            logger.error("NodeConfirmCount error!!!");
        }
        if (confirm >= coinConfig.getNodeConfirmCount().intValue()) {
            sendCoinMapper.updateTransferStatusById(id, SendCoinStatus.CONFIRM.getValue(), null);
        }
    }
}
