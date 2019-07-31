package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.TransferStatus;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.CoinMapper;
import com.cmd.exchange.common.mapper.ConfigMapper;
import com.cmd.exchange.common.mapper.TransferMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.Config;
import com.cmd.exchange.common.model.ReceivedCoin;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 转账服务，包括转账和接受转账，主要维护表t_send_coin，t_received_coin
 */
@Service
public class TransferService {
    @Autowired
    private UserCoinService userCoinService;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private CoinMapper coinMapper;

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 用户从外部区块链收到数字货币
     *
     * @param userId         用户id
     * @param coinName       币种名称
     * @param address        用户地址
     * @param txId           交易id
     * @param receivedAmount 收到原始金额(包括费用)
     * @param fee            需要扣除的费用
     * @param txTime         交易时间（区块链上记录的）
     */
    @Transactional
    public void addUserReceivedCoin(int userId, String coinName, String address, String txId, BigDecimal receivedAmount,
                                    BigDecimal fee, int txTime) {
        Coin coin = coinMapper.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        int count = transferMapper.addReceivedCoin(userId, address, coinName, txId, receivedAmount, fee, txTime, TransferStatus.REV_STATUS_SUCCESS);
        if (count == 0) {
            throw new RuntimeException("addReceivedCoin failed,userId=" + userId + ",address=" + address);
        }
        // 判断是否放入转入冻结里面
        if (coin.getReceivedFreeze() != null && coin.getReceivedFreeze() != 0) {

            //放入转入冻结规则
            // boolean flse = changeUserReceivedFreezeCoinRule(userId, coinName, receivedAmount, fee, UserBillReason.BC_RECEIVED_COIN);
            //if(!flse)
            //{
            // 放入转入冻结里面
            userCoinService.changeUserReceivedFreezeCoin(userId, coinName, receivedAmount.subtract(fee), UserBillReason.BC_RECEIVED_COIN, "");
            //}

        } else {
            userCoinService.changeUserCoin(userId, coinName, receivedAmount.subtract(fee), UserCoinService.ZERO, UserBillReason.BC_RECEIVED_COIN, "");
        }
    }

    /**
     * 放入转入冻结规则
     *
     * @param userId         用户id
     * @param coinName       币种名称
     * @param receivedAmount 收到原始金额(包括费用)
     * @param fee            需要扣除的费用
     * @param reason         备注
     * @return
     */
    public boolean changeUserReceivedFreezeCoinRule(int userId, String coinName, BigDecimal receivedAmount, BigDecimal fee, String reason, String comment) {

        Boolean rtv = Boolean.TRUE;
        //判断是否开启
        Config synFreezeRuleCoinIs = configMapper.getConfigByName(ConfigKey.SYN_FREEZE_RULE_COIN_IS);
        if (null == synFreezeRuleCoinIs || synFreezeRuleCoinIs.getConfValue().equals("0")) {
            return false;
        }
        if (getConfigByName(coinName, ConfigKey.SYN_FREEZE_RULE_COIN_NAME)) return false;


        Config synFreezeRuleThreshold = configMapper.getConfigByName(ConfigKey.SYN_FREEZE_RULE_THRESHOLD);
        if (null == synFreezeRuleThreshold || null == synFreezeRuleThreshold.getConfValue() || synFreezeRuleThreshold.getConfValue().compareTo("0") <= 0) {
            return false;
        }
        //取出百分比
        BigDecimal synFreezeRuleThresholdValue = new BigDecimal(synFreezeRuleThreshold.getConfValue());

        //扣除费用
        //BigDecimal change = receivedAmount.subtract(fee);
        //不扣手续费
        BigDecimal change = receivedAmount;
        //转入冻结，金额乘以百分比
        BigDecimal receiveFreezeBalance = change.multiply(synFreezeRuleThresholdValue);

        // 放入转入冻结里面
        userCoinService.changeUserReceivedFreezeCoin(userId, coinName, receiveFreezeBalance, reason, comment);

        //可用资产,金额减转入冻结金额，得出可用金额比例
        BigDecimal availableBalance = change.subtract(receiveFreezeBalance);

        //放入活动金额
        userCoinService.changeUserCoin(userId, coinName, availableBalance, UserCoinService.ZERO, reason, comment);

        return rtv;
    }

    public boolean getConfigByName(String coinName, String configByName) {
        //同步指定币种名称,数组存储
        Config synFreezeRuleCoinName = configMapper.getConfigByName(configByName);
        if (null == synFreezeRuleCoinName || null == synFreezeRuleCoinName.getConfValue()) {
            return true;
        }
        String[] coinNames = synFreezeRuleCoinName.getConfValue().split(",");

        if (null == coinNames || coinNames.length == 0) {
            return true;
        }
        boolean isCoin = true;
        for (String s : coinNames) {
            if (s.equals(coinName)) {
                isCoin = false;
                break;
            }
        }

        if (isCoin) {
            return true;
        }
        return false;
    }

    /**
     * 判断交易是否存在
     *
     * @param coinName 币种名称
     * @param txId     交易id
     * @return 存在返回true
     */
    public boolean isTransactionExists(String coinName, String txId) {
        ReceivedCoin revCoin = transferMapper.getReceivedCoinId(coinName, txId);
        return revCoin != null;
    }

    /**
     * 统计从外部转账进来的币种
     *
     * @return
     */
    public List<ReceivedCoin> getTotalReceiveFromExternal() {
        List<ReceivedCoin> rcs = transferMapper.getTotalReceiveFromExternal();
        addOtherCoin(rcs);
        return rcs;
    }

    /**
     * 统计某个用户从外部转账进来的币种
     *
     * @return
     */
    public List<ReceivedCoin> getUserTodayReceiveFromExternal(int userId) {
        List<ReceivedCoin> rcs = transferMapper.getUserTotalReceiveFromExternal(userId);
        return rcs;
    }

    /**
     * 统计今天从外部转账进来的币种
     *
     * @return
     */
    public List<ReceivedCoin> getTodayReceiveFromExternal() {
        int begin = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        int end = begin + 3600 * 24;
        List<ReceivedCoin> rcs = transferMapper.getTotalReceiveFromExternalWithTime(begin, end);

        addOtherCoin(rcs);
        return rcs;
    }

    private void addOtherCoin(List<ReceivedCoin> ls) {
        List<Coin> coins = coinMapper.getCoin();
        for (Coin coin : coins) {
            boolean find = false;
            for (ReceivedCoin rc : ls) {
                if (coin.getName().equalsIgnoreCase(rc.getCoinName())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                ReceivedCoin tmp = new ReceivedCoin();
                tmp.setAmount(BigDecimal.ZERO);
                tmp.setCoinName(coin.getName());
                ls.add(tmp);
            }
        }
    }

    /**
     * 获取下一个对应币种的外部同步id
     */
    public ReceivedCoin getNextReceiveFromExternal(int lastId, String coinName) {
        return transferMapper.getNextReceiveFromExternal(lastId, coinName);
    }
}
