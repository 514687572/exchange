package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.CoinSavingsMapper;
import com.cmd.exchange.common.model.CoinSavings;
import com.cmd.exchange.common.model.CoinSavingsRecord;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.github.pagehelper.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class CoinSavingsService {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private CoinSavingsMapper coinSavingsMapper;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private CoinSavingsService me;

    // 存取钱单位，必须是这个的倍数
    public static final BigDecimal SavingUnit = new BigDecimal(1);

    /**
     * 存储或取出用户金额
     *
     * @param userId            用户id
     * @param coinName          币种
     * @param changeBalance     可用币变化数，可以是负数或0
     * @param changeWithdrawing 冻结币变化数，可以是负数或0
     * @param reason            变化原因，在UserBillReason总定义
     * @param logComment        账单日志
     */
    @Transactional
    public void changeCoinSavings(int userId, String coinName, BigDecimal changeBalance, BigDecimal changeWithdrawing, String reason, String logComment) {
        int changeAvailable = changeBalance.compareTo(BigDecimal.ZERO);
        int changeFreeze = changeWithdrawing.compareTo(BigDecimal.ZERO);
        if (changeAvailable == 0 && changeFreeze == 0) {
            return;
        }
        // 调整精度
        if (changeBalance.scale() > 8) changeBalance = changeBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        if (changeWithdrawing.scale() > 8) changeWithdrawing = changeWithdrawing.setScale(8, BigDecimal.ROUND_HALF_UP);
        int changeCount = coinSavingsMapper.changeCoinSavings(userId, coinName, changeBalance, changeWithdrawing);
        if (changeCount == 0) {
            if (coinSavingsMapper.getCoinSavingsByUserIdAndCoinName(userId, coinName) == null) {
                // 没有钱包，手工创建一个新的钱包
                CoinSavings coin = new CoinSavings();
                coin.setCoinName(coinName);
                coin.setUserId(userId);
                coin.setBalance(BigDecimal.ZERO);
                coin.setWithdrawing(BigDecimal.ZERO);
                coinSavingsMapper.add(coin);
            } else {
                Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
            }
            // 重新修改钱包
            changeCount = coinSavingsMapper.changeCoinSavings(userId, coinName, changeBalance, changeWithdrawing);
        }
        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);

        // 增加修改日志
        if (changeAvailable != 0) {
            //coinSavingsMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_AVAILABLE, reason, changeBalance, logComment);
            log.info("UserSavingBill:userId=" + userId + ",coinName=" + coinName + ",changeBalance=" + changeBalance
                    + ",reason=" + reason + ",comment=" + logComment);
        }
        // 增加修改日志
        if (changeFreeze != 0) {
            //coinSavingsMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_FREEZE, reason, changeWithdrawing, logComment);
            log.info("UserSavingBill:userId=" + userId + ",coinName=" + coinName + ",changeWithdrawing=" + changeWithdrawing
                    + ",reason=" + reason + ",comment=" + logComment);
        }
    }

    /**
     * 存钱，规定是10000的倍数
     *
     * @param userId
     * @param coinName
     * @param savingBalance
     */
    @Transactional
    public void savingCoin(int userId, String coinName, BigDecimal savingBalance) {
        Assert.check(savingBalance.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_SAVING_COUNT_INVALID);
        BigDecimal toSaving = savingBalance.divide(SavingUnit, 8, RoundingMode.HALF_UP).multiply(SavingUnit);
        Assert.check(savingBalance.compareTo(toSaving) != 0, ErrorCode.ERR_SAVING_COUNT_INVALID);
        userCoinService.changeUserCoin(userId, coinName, toSaving.negate(), BigDecimal.ZERO, UserBillReason.SAVING_ADD, "");
        // 预计到达时间，明天早晨8点
        Date wantArrivalTime = new Date(1000L * (DateUtil.getDayBeginTimestamp(System.currentTimeMillis()) + 3600 * (24 + 8)));
        // 增加记录
        CoinSavingsRecord record = new CoinSavingsRecord();
        record.setUserId(userId).setAmount(toSaving).setCoinName(coinName).setType(CoinSavingsRecord.TYPE_SAVE).setWantArrivalTime(wantArrivalTime);
        coinSavingsMapper.addRecord(record);
        //changeCoinSavings(userId, coinName, toSaving, BigDecimal.ZERO, UserBillReason.SAVING_ADD, "");
    }

    /**
     * 取钱，规定是10000的倍数
     *
     * @param userId
     * @param coinName
     * @param withdrawing
     */
    @Transactional
    public void withdrawingCoin(int userId, String coinName, BigDecimal withdrawing) {
        Assert.check(withdrawing.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_SAVING_COUNT_INVALID);
        BigDecimal toWithdrawing = withdrawing.divide(SavingUnit, 8, RoundingMode.HALF_UP).multiply(SavingUnit);
        Assert.check(withdrawing.compareTo(toWithdrawing) != 0, ErrorCode.ERR_SAVING_COUNT_INVALID);
        changeCoinSavings(userId, coinName, toWithdrawing.negate(), BigDecimal.ZERO, UserBillReason.SAVING_SUB, "");
        //userCoinService.changeUserCoin(userId, coinName, toWithdrawing, BigDecimal.ZERO, UserBillReason.SAVING_SUB, "");
        // 预计到达时间，明天早晨8点
        Date wantArrivalTime = new Date(1000L * (DateUtil.getDayBeginTimestamp(System.currentTimeMillis()) + 3600 * (24 + 8)));
        // 增加记录
        CoinSavingsRecord record = new CoinSavingsRecord();
        record.setUserId(userId).setAmount(toWithdrawing).setCoinName(coinName).setType(CoinSavingsRecord.TYPE_GET).setWantArrivalTime(wantArrivalTime);
        coinSavingsMapper.addRecord(record);
    }

    public CoinSavings getCoinSavings(int userId, String userCoin) {
        return coinSavingsMapper.getCoinSavingsByUserIdAndCoinName(userId, userCoin);
    }

    public BigDecimal getTotalCoinSavings(String userCoin) {
        return coinSavingsMapper.getTotalCoinSavings(userCoin);
    }

    public Page<CoinSavings> getCoinSavings(Integer userId, String coinName, int pageNo, int pageSize) {
        return coinSavingsMapper.getCoinSavings(userId, coinName, new RowBounds(pageNo, pageSize));
    }

    @Transactional
    public void arrivalRecord(CoinSavingsRecord record) {
        record = coinSavingsMapper.lockCoinSavingsRecord(record.getId());
        if (record.getStatus() != CoinSavingsRecord.STATUS_NO_ARRIVAL) {
            log.error("record of id:" + record.getId() + " status wrong." + record);
        }
        if (record.getType() == CoinSavingsRecord.TYPE_GET) {
            // 增加用户余额
            userCoinService.changeUserCoin(record.getUserId(), record.getCoinName(), record.getAmount(), BigDecimal.ZERO, UserBillReason.SAVING_SUB, "");
        } else if (record.getType() == CoinSavingsRecord.TYPE_SAVE) {
            changeCoinSavings(record.getUserId(), record.getCoinName(), record.getAmount(), BigDecimal.ZERO, UserBillReason.SAVING_ADD, "");
        } else {
            log.error("record of id:" + record.getId() + " type wrong." + record);
            return;
        }
        coinSavingsMapper.setRecordArrival(record.getId());
    }

    // 将所有需要到账的钱进行到账
    public void checkArrivalRecords() {
        BigInteger lastId = BigInteger.ZERO;
        Date now = new Date();
        while (true) {
            CoinSavingsRecord record = coinSavingsMapper.getNextWantArrivalRecord(now, lastId);
            if (record == null) return;
            lastId = record.getId();
            log.info("now arrival Record " + record);
            try {
                me.arrivalRecord(record);
            } catch (Exception ex) {
                log.error("arrival Record", ex);
            }
        }
    }
}
