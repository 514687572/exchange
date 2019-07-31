package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.*;
import com.cmd.exchange.common.enums.SmsCaptchaType;
import com.cmd.exchange.common.enums.TransferType;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.*;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinanceService {

    private static final int TIMEOUT = 3 * 1000;
    @Autowired
    private SendCoinMapper sendCoinMapper;
    @Autowired
    private ReceivedCoinMapper receivedCoinMapper;
    @Autowired
    private UserCoinMapper userCoinMapper;
    @Autowired
    private UserBillMapper userBillMapper;
    @Autowired
    private SendCoinService sendCoinService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinConfigMapper coinConfigMapper;
    @Autowired
    private ExchangeRateMapper exchangeRateMapper;
    @Autowired
    GoogleAuthService googleAuthService;
    @Autowired
    TransferService transferService;
    @Autowired
    private UserAmountTransferredIntoLogMapper userAmountTransferredIntoLogMapper;
    @Autowired
    private UserCoinService userCoinService;

    @Autowired
    private RedisLockService redisLockService;

    @Autowired
    private MallUserDeductionIntegralRecordMapper mallUserDeductionIntegralRecordMapper;

    @Autowired
    private ConfigService configService;


    public Page<CoinTransferVo> transferHistory(CoinTransferReqVO reqVO) {
        if (reqVO.getType() == TransferType.SEND) {
            Page<CoinTransferVo> voList = sendCoinMapper.getTransferList(reqVO, new RowBounds(reqVO.getPageNo(), reqVO.getPageSize()));
            for (CoinTransferVo vo : voList) {
                //还需判断是内部还是外部
                if (vo.getTxid() == null) {
                    vo.setInnerTransfer(false);
                } else {
                    if (vo.getTxid().toLowerCase().startsWith("inner-")) {
                        vo.setInnerTransfer(true);
                    }
                }
                vo.setType(TransferType.SEND);
            }
            return voList;
        }

        if (reqVO.getType() == TransferType.RECEIVED) {
            log.info("recived start time :--------------------------------------" + reqVO.getStartTime());

            log.info("recived end time :" + reqVO.getEndTime());
            log.info("recived start time :" + reqVO.getStartTime());
            log.info("recived end time :" + reqVO.getEndTime());
            log.info("recived end time :--------------------------------------" + reqVO.getEndTime());
            if (reqVO.getStartTime() != null) {

                Date startDate = DateUtil.getDate(reqVO.getStartTime());
                reqVO.setStartTime(startDate.getTime() / 1000 + "");
            }
            if (reqVO.getEndTime() != null) {
                Date endDate = DateUtil.getDate(reqVO.getEndTime());
                reqVO.setEndTime(endDate.getTime() / 1000 + "");
            }

            Page<CoinTransferVo> voList = receivedCoinMapper.getTransferList(reqVO, new RowBounds(reqVO.getPageNo(), reqVO.getPageSize()));
            for (CoinTransferVo v : voList) {
                UserGroupConfig userGroupConfig = userService.getUserGroupConfigById(v.getUserId());
                v.setGroupName(userGroupConfig.getGroupName());
                v.setTransferTime(new Date(v.getReceivedTime() * 1000l));
                if (v.getTxid() == null) {
                    v.setInnerTransfer(false);
                } else {
                    if (v.getTxid().toLowerCase().startsWith("inner-")) {
                        v.setInnerTransfer(true);
                    }
                }
                v.setType(TransferType.RECEIVED);
            }

            return voList;
        }

        return new Page<>();
    }

    //获取钱包余额
    public UserCoinVO getBalanceByCoinName(int userId, String coinName) {
        Assert.check(coinMapper.getCoinByName(coinName) == null, ErrorCode.ERR_NOT_SUPPORT_COIN);
        return userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
    }

    //获取转账出去余额
    public Page<SendCoin> getTransfer(int userId, String coinName, Integer status, Integer pageNo, Integer pageSize) {
        return sendCoinMapper.getTransfer(userId, coinName, status, new RowBounds(pageNo, pageSize));
    }

    // 检查转账参数是否正确
    private void checkTransfer(int userId, String coinName, BigDecimal amount, String comment, String validCode, String payPassword) {
        //检查验证码
        User user = userService.getUserByUserId(userId);
        String mobile = user.getMobile();
        String email = user.getEmail();
        if (!smsService.checkCaptcha(user.getAreaCode(), user.getMobile(), SmsCaptchaType.TRANSFER_OUT.getValue(), validCode)
                && !smsService.checkCaptcha(user.getAreaCode(), user.getEmail(), SmsCaptchaType.TRANSFER_OUT.getValue(), validCode)) {
            Assert.check(true, ErrorCode.ERR_USER_SMSCODE_ERROR);
        }

        Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        //转账检查
        CoinConfig coinConfig = coinConfigMapper.getCoinConfigByName(coinName);
        if (coinConfig != null) {
            Assert.check(amount.doubleValue() < coinConfig.getTransferMinAmount().doubleValue(), ErrorCode.ERR_TRANSFER_AMOUNT_TO_LOW);
            Assert.check(amount.doubleValue() > coinConfig.getTransferMaxAmount().doubleValue(), ErrorCode.ERR_TRANSFER_AMOUNT_TO_HIGH);
        }

        Coin coin = coinMapper.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_NOT_SUPPORT_COIN);
        Assert.check(coin.getStatus() != 0, ErrorCode.ERR_NOT_SUPPORT_COIN);

        //检查密码
        Assert.check(StringUtils.isBlank(user.getPayPassword()), ErrorCode.ERR_USER_PAY_PASSWORD_NOT_FOUND);
        Assert.check(!EncryptionUtil.check(payPassword, user.getPayPassword(), user.getPaySalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);
        //校验google验证码
        googleAuthService.checkGoogleAuthSecond(userId, user.getAreaCode(), mobile != null ? mobile : email, SmsCaptchaType.TRANSFER_OUT.getValue());
    }

    //转账出去
    @Transactional
    public void transfer(int userId, String coinName, String toAddress, BigDecimal amount, String comment, String validCode, String payPassword) {
        checkTransfer(userId, coinName, amount, comment, validCode, payPassword);

        sendCoinService.transferCoin(userId, coinName, toAddress, amount.doubleValue(), comment);
    }

    public void transferReceivedFreeze(int fromUserId, String coinName, int toUserId, BigDecimal amount, String comment, String validCode, String payPassword) {
        String value = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_SWITCH, "true");
        if("false".equalsIgnoreCase(value)){
            Assert.check(true, 500, "该功能暂未开放！");
        }
        checkTransfer(fromUserId, coinName, amount, comment, validCode, payPassword);
        sendCoinService.transferFreezeCoin(coinName, fromUserId, toUserId, amount);
    }

    public List<UserCoinVO> getBalanceByUserId(Integer userId) {
        List<UserCoinVO> userCoinList = userCoinMapper.getUserCoinByUserId(userId);

        //user coin表中可能没有所有的coin，需要再查询coin表来获取全部的币种信息
        List<String> coinNameList = userCoinList.stream().map(u -> u.getCoinName()).collect(Collectors.toList());

        List<UserCoinVO> coinConfigList = userCoinMapper.getCoinConfigList(coinNameList);
        userCoinList.addAll(coinConfigList);

        // 删除所有禁用的币
        List<Coin> disableCoins = coinMapper.getCoinListByStatus(CoinStatus.DISABLE.getValue());
        List<String> disableNameList = disableCoins.stream().map(u -> u.getName().toUpperCase()).collect(Collectors.toList());
        userCoinList.removeIf(coin -> disableNameList.contains(coin.getCoinName().toUpperCase()));

        userCoinList.sort(new Comparator<UserCoinVO>() {
            @Override
            public int compare(UserCoinVO o1, UserCoinVO o2) {
                return o1.getCoinName().compareTo(o2.getCoinName());
            }
        });
        userCoinList.forEach(data -> data.setImage(coinMapper.getCoinsImage(data.getCoinName())));
        return userCoinList;
    }

    /**
     * 获取用户折算成指定币后的总资产数
     *
     * @param userId
     * @param coinName
     * @return
     */
    public BigDecimal getUserTotalAssetConvertTo(int userId, String coinName) {
        List<UserCoinVO> coins = userCoinMapper.getUserCoinByUserId(userId);
        boolean find;
        BigDecimal totalAvalable = BigDecimal.ZERO;
        // 获取所有某个币种的兑换率
        List<ExchangeRate> rates = exchangeRateMapper.getCoinExchangeRate(coinName);
        for (UserCoinVO coin : coins) {
            if (coin.getCoinName().equalsIgnoreCase(coinName)) {
                coin.setAvailableConvert(coin.getAvailableBalance());
                coin.setFreezeConvert(coin.getFreezeBalance());
                totalAvalable = totalAvalable.add(coin.getAvailableBalance());
                continue;
            }
            // 先找有直接结算的
            find = false;
            for (ExchangeRate rate : rates) {
                if (rate.getCoinName().equalsIgnoreCase(coin.getCoinName())) {
                    coin.setAvailableConvert(coin.getAvailableBalance().multiply(rate.getPrice()));
                    coin.setFreezeConvert(coin.getFreezeBalance().multiply(rate.getPrice()));
                    totalAvalable = totalAvalable.add(coin.getAvailableConvert());
                    find = true;
                    break;
                }
            }
            if (find) continue;
            // 到反过来结算
            for (ExchangeRate rate : rates) {
                if (rate.getSettlementCurrency().equalsIgnoreCase(coin.getCoinName()) && rate.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    coin.setAvailableConvert(coin.getAvailableBalance().divide(rate.getPrice(), 8, RoundingMode.HALF_UP));
                    coin.setFreezeConvert(coin.getFreezeBalance().divide(rate.getPrice(), 8, RoundingMode.HALF_UP));
                    totalAvalable = totalAvalable.add(coin.getAvailableConvert());
                    break;
                }
            }
        }
        return totalAvalable;
    }

    public Page<UserBillVO> getBillList(Integer userId, String coinName, Integer pageNo, Integer pageSize) {
        //return userBillMapper.getBillListByUser(userId, coinName, new RowBounds(pageNo, pageSize));
        return userBillMapper.getUserBills(userId, null, coinName, null, null, new RowBounds(pageNo, pageSize));
    }

    //管理后台全栈流水查询(有用户类型)
    public Page<UserBillVO> getUserBillsByTime(Integer userId, String coinName, String startTime, String endTime, String groupType, Integer subType, String reason, String realName, Integer pageNo, Integer pageSize) {
        return userBillMapper.getUserBillsByTime(userId, null, startTime, endTime, coinName, groupType, subType, reason, realName, new RowBounds(pageNo, pageSize));
    }

    //管理后台全栈流水查询
    public Page<UserBillVO> getUserBillsPage(Integer userId, String coinName, String startTime, String endTime, Integer subType, String reason, Integer pageNo, Integer pageSize) {
        Page<UserBillVO> pageList = userBillMapper.getUserBillsPage(userId, null, startTime, endTime, coinName, subType, reason, new RowBounds(pageNo, pageSize));
        if (pageList != null && pageList.getResult().size() > 0) {
            List<UserBillVO> userBillVOList = pageList.getResult();
            //获取用户的组名
            for (int i = 0; i < userBillVOList.size(); i++) {
                UserBillVO userBillVO = userBillVOList.get(i);
                UserGroupConfig userGroupConfig = userService.getUserGroupConfigById(userBillVO.getUserId());
                userBillVO.setGroupName(userGroupConfig.getGroupName());
                userBillVO.setGroupType(userGroupConfig.getGroupType());
                User user = userService.getUserByUserId(userBillVO.getUserId());
                userBillVO.setRealName(user.getRealName());
                pageList.set(i, userBillVO);
            }
        }
        return pageList;
    }


    public CoinConfig getTransferConfig(String coinName) {
        return coinConfigMapper.getCoinConfigByName(coinName);
    }

    public Page<UserBill> getUserBillsByReasons(Integer userId, List<String> types, String coinName, String beginTime, String endTime,Integer pageNo, Integer pageSize) {
        return userBillMapper.getUserBillReward(userId, types != null ? types.toArray(new String[0]) : null, coinName, beginTime, endTime,new RowBounds(pageNo, pageSize));
    }

    public Page<UserBillVO> getUserBills(Integer userId, String type, String coinName, Integer pageNo, Integer pageSize) {
        return userBillMapper.getUserBills(userId, type, coinName, null, null, new RowBounds(pageNo, pageSize));
    }

    public Page<UserBillVO> getUserBills5(Integer userId, Integer subType, String reason, String coinName, Integer pageNo, Integer pageSize) {
        return userBillMapper.getUserBills5(userId, subType, reason, coinName, null, null, new RowBounds(pageNo, pageSize));
    }

    public Page<UserCoinInfoVO> getUserCoinInfo(Integer userId, String coinName, String groupType, String realName, String column, String order, Integer pageNo, Integer pageSize) {
        return userCoinMapper.getUserCoinInfo(userId, coinName, groupType, realName, column, "desc".equalsIgnoreCase(order), new RowBounds(pageNo, pageSize));
    }

    public BigDecimal getUserCoinAmountByCoinName(String coinName, String groupType) {
        return userCoinMapper.getUserCoinAmountByCoinName(coinName, groupType);
    }

    public List<UserCoinInfoVO> getUserCoinInfoAll(Integer userId, String coinName, String groupType, String realName, String column, String order) {
        return userCoinMapper.getUserCoinInfoAll(userId, coinName, groupType, realName, column, "desc".equalsIgnoreCase(order));
    }

    /**
     * 外部用户转入指定账户余额，加锁
     *
     * @param coinName      币种名称
     * @param mobile        手机号
     * @param changeBalance 转入金额
     * @param sign          签名
     * @param changeTime    转入时间
     */
    @Transactional
    public void changeUserCoinBalance(String coinName, String mobile, BigDecimal changeBalance, String sign, Integer changeTime) {

        long time = System.currentTimeMillis() + TIMEOUT;

        Assert.check(StringUtils.isEmpty(coinName) || StringUtils.isEmpty(coinName) || changeBalance.compareTo(BigDecimal.ZERO) != 1, ErrorCode.ERR_PARAM_ERROR);

        //  加锁
        Assert.check(!redisLockService.lock(RedisKey.EXTERNAL_USERCHANGE + mobile, String.valueOf(time)), ErrorCode.ERR_USER_REPETITIVE_OPERATION);


        User userByMobile = userService.getUserByMobile(mobile);

        Assert.check(null == userByMobile, ErrorCode.ERR_USER_NOT_EXIST);

        UserCoinVO userCoinVO = userCoinMapper.lockUserCoinByUserIdAndCoinName(userByMobile.getId(), coinName);

        Assert.check(null == userCoinVO, ErrorCode.ERR_COIN_NAME_IS_EXISTED);

        //转入记录
        UserAmountTransferredIntoLog amountTransferredIntoLog = new UserAmountTransferredIntoLog();
        amountTransferredIntoLog.setAmountTransferredInto(changeBalance);
        amountTransferredIntoLog.setCoinName(coinName);
        amountTransferredIntoLog.setMobile(mobile);
        amountTransferredIntoLog.setSign(sign);
        amountTransferredIntoLog.setUserId(userByMobile.getId());
        amountTransferredIntoLog.setChangeTime(changeTime);
        int isAdd = userAmountTransferredIntoLogMapper.add(amountTransferredIntoLog);

        Assert.check(0 >= isAdd, ErrorCode.ERR_USER_AMOUNT_TRANSFERRED_INTO);


        boolean b = transferService.changeUserReceivedFreezeCoinRule(userByMobile.getId(), coinName, changeBalance, null, UserBillReason.USER_AMOUNT_TRANSFERRED_INTO, UserBillComment.USER_AMOUNT_TRANSFERRED_INTO_COMMENT);

        if (!b) {
            userCoinService.changeUserReceivedFreezeCoin(userByMobile.getId(), coinName, changeBalance, UserBillReason.USER_AMOUNT_TRANSFERRED_INTO, UserBillComment.USER_AMOUNT_TRANSFERRED_INTO_COMMENT);
        }
        //  解锁
        redisLockService.unlock(RedisKey.EXTERNAL_USERCHANGE + mobile, String.valueOf(time));
    }

    /**
     * 对接商城积分扣除业务，加锁
     *
     * @param coinName      币种名称
     * @param mobile        手机号
     * @param changeBalance 扣除金额
     * @param sign          签名
     * @param changeTime    扣除时间
     * @param orderNum      订单号
     */
    @Transactional
    public void userDeductionIntegral(String coinName, String mobile, BigDecimal changeBalance, String sign, Integer changeTime, String orderNum) {

        Assert.check(StringUtils.isEmpty(coinName) || StringUtils.isEmpty(coinName) || StringUtils.isEmpty(orderNum) || changeBalance.compareTo(BigDecimal.ZERO) != 1, ErrorCode.ERR_PARAM_ERROR);

        //long time = System.currentTimeMillis()+TIMEOUT;
        //加锁
        // Assert.check(!redisLockService.lock(RedisKey.EXTERNAL_DEDUCTIONINTEGRAL+orderNum,String.valueOf(time)),ErrorCode.ERR_USER_REPETITIVE_OPERATION);

       /* MallUserDeductionIntegralRecord byOrderNum = mallUserDeductionIntegralRecordMapper.getByOrderNum(orderNum);

        Assert.check(null != byOrderNum,ErrorCode.ERR_SUCCESS);
        */
        User userByMobile = userService.getUserByMobile(mobile);

        Assert.check(null == userByMobile, ErrorCode.ERR_USER_NOT_EXIST);

        UserCoinVO userCoinVO = userCoinMapper.lockUserCoinByUserIdAndCoinName(userByMobile.getId(), coinName);

        Assert.check(null == userCoinVO, ErrorCode.ERR_COIN_NAME_IS_EXISTED);

        Assert.check(userCoinVO.getAvailableBalance().compareTo(changeBalance) == -1, ErrorCode.ERR_BALANCE_INSUFFICIENT);

        if (changeBalance.scale() > 8) changeBalance = changeBalance.setScale(8, BigDecimal.ROUND_HALF_UP);

        int changeCount = userCoinMapper.changeUserDeductionIntegral(userByMobile.getId(), coinName, changeBalance);

        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);


        userBillMapper.insertUserBill(userByMobile.getId(), coinName, UserBillType.SUB_TYPE_AVAILABLE, UserBillReason.USER_DEDUCTION_INTEGRAL, changeBalance, "");

        //插入记录
        MallUserDeductionIntegralRecord mallUserDeductionIntegralRecord = new MallUserDeductionIntegralRecord();
        mallUserDeductionIntegralRecord.setChangeTime(changeTime);
        mallUserDeductionIntegralRecord.setCoinName(coinName);
        mallUserDeductionIntegralRecord.setDeductionIntegral(changeBalance);
        mallUserDeductionIntegralRecord.setMobile(mobile);
        mallUserDeductionIntegralRecord.setOrderNum(orderNum);
        mallUserDeductionIntegralRecord.setSign(sign);
        mallUserDeductionIntegralRecord.setUserId(userByMobile.getId());
        mallUserDeductionIntegralRecordMapper.add(mallUserDeductionIntegralRecord);

        //解锁
        //  redisLockService.unlock(RedisKey.EXTERNAL_DEDUCTIONINTEGRAL+orderNum,String.valueOf(time));

    }

}
