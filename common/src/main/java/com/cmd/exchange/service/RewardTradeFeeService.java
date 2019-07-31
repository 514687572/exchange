package com.cmd.exchange.service;

import com.cmd.exchange.common.componet.DayCountLimit;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.TradeBonusLogMapper;
import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.mapper.UserBillMapper;
import com.cmd.exchange.common.mapper.UserMapper;
import com.cmd.exchange.common.model.TradeFeeReturnDetail;
import com.cmd.exchange.common.model.TradeLog;
import com.cmd.exchange.common.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

// 交易费用返还（挖矿）
@Service
public class RewardTradeFeeService {
    private static Logger logger = LoggerFactory.getLogger(RewardTradeFeeService.class);
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    TradeLogMapper tradeLogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TradeBonusLogMapper tradeBonusLogMapper;
    @Autowired
    ConfigService configService;
    @Autowired
    UserBillMapper userBillMapper;

    @Autowired
    RewardTradeFeeService me;   // 自己，用来启用事务的

    // 用于计算用户的交易次数
    private DayCountLimit dayCountLimit = new DayCountLimit();

    // 上次删除的时间
    private long lastRemoveTime = 0;

    // 获取平台币针对USDT的价格
    private BigDecimal getPlatformCoinPriceOfUsdt() {
        BigDecimal price = tradeLogMapper.getLatestPrice(configService.getPlatformCoinName(), "USDT");
        if (price != null) return price;
        return BigDecimal.ZERO;
    }

    // 交易即挖矿，对新的交易进行返还
    public void rewardNewTradeFees() {
        BigInteger lastRewardLogId = configService.getConfigValue(ConfigKey.LAST_MINE_TRADE_ID, new BigInteger("-1"));
        if (lastRewardLogId.compareTo(BigInteger.ZERO) < 0) {
            // 第一次直接设置为最后一个交易id，以前的不进行
            logger.info("lastRewardLogId is not set");
            TradeLog tradeLog = tradeLogMapper.getLastTrade();
            if (tradeLog != null) {
                configService.setConfigValue(ConfigKey.LAST_MINE_TRADE_ID, tradeLog.getId().toString());
            }
            return;
        }
        BigDecimal uksPrice = getPlatformCoinPriceOfUsdt();
        if(uksPrice.compareTo(BigDecimal.ZERO) == 0) {
            logger.warn("没有平台币对usdt的交易记录, ignore work");
            return;
        }
        logger.info("1 UKS(" + configService.getPlatformCoinName() + ") = " + uksPrice + " USDT" + ",lastRewardLogId=" + lastRewardLogId);
        BigDecimal recUserRate = configService.getConfigValue(ConfigKey.TRADE_FEE_REC_USER, BigDecimal.ZERO, true);
        BigDecimal rec2UserRate = configService.getConfigValue(ConfigKey.TRADE_FEE_REC_REC_USER, BigDecimal.ZERO, true);
        int sameTradeRewardMaxTimes = configService.getConfigValue(ConfigKey.TRADE_SAME_REWARD_MAX_TIMES, 5);
        logger.debug("rewardNewTradeFees,recUserRate=" + recUserRate + ",rec2UserRate=" + rec2UserRate + ",sameTradeRewardMaxTimes=" + sameTradeRewardMaxTimes);
        this.dayCountLimit.setDayLimitCount(sameTradeRewardMaxTimes);
        if (this.lastRemoveTime < System.currentTimeMillis() - 1000L * 3600 * 24) {
            logger.info("removeOldStats ");
            try {
                this.dayCountLimit.removeOldStats();
            } catch (Exception ex) {
                logger.error("", ex);
            }
            this.lastRemoveTime = System.currentTimeMillis();
        }
        while (true) {
            TradeLog tradeLog = tradeLogMapper.getNextUsdtFeeTradeLog(lastRewardLogId);
            if (tradeLog == null) break;
            logger.info("begin reward trade " + tradeLog);
            // 查看该交易是否超过当天奖励限制
            String tradeIdentity = tradeLog.getCoinName() + "/" + tradeLog.getSettlementCurrency() + "-" + tradeLog.getBuyUserId() + "/" + tradeLog.getSellUserId();
            if (this.dayCountLimit.isDayCountFinishUp(tradeIdentity)) {
                logger.info("trade of " + tradeIdentity + " is DayCountFinishUp, can not reward");
                lastRewardLogId = tradeLog.getId();
                continue;
            }
            // 获取用户，推荐用户，二级推荐用户
            User user = userMapper.getUserByUserId(tradeLog.getSellUserId());
            if (user == null) {
                lastRewardLogId = tradeLog.getId();
                logger.error("can not find user of id:" + tradeLog.getSellUserId());
                continue;
            }

            User recUser = null;
            if (user.getReferrer() != null && user.getReferrer() != 0) {
                recUser = userMapper.getUserByUserId(user.getReferrer());
            }

            User rec2User = null;
            if (recUser != null && recUser.getReferrer() != null && user.getReferrer() != 0) {
                rec2User = userMapper.getUserByUserId(recUser.getReferrer());
            }
            me.rewardTradeFee(tradeLog, user, recUser, rec2User, recUserRate, rec2UserRate, uksPrice);
            // 增加统计次数
            this.dayCountLimit.addDayCount(tradeIdentity);

            lastRewardLogId = tradeLog.getId();
        }
    }

    @Transactional
    public void rewardTradeFee(TradeLog tradeLog, User user, User recUser, User rec2User, BigDecimal recUserRate, BigDecimal rec2UserRate, BigDecimal uksPrice) {
        logger.info("rewardTradeFee " + tradeLog + ",user=" + user + ",recUser=" + recUser + ",rec2User=" + rec2User);
        configService.setConfigValue(ConfigKey.LAST_MINE_TRADE_ID, tradeLog.getId().toString());
        TradeFeeReturnDetail detail = new TradeFeeReturnDetail();
        detail.setTradeLogId(tradeLog.getId());
        detail.setRetTime(new Date());
        detail.setRecUserRate(recUserRate);
        detail.setRec2UserRate(rec2UserRate);
        detail.setBuyRetCoin(tradeLog.getCoinName());
        detail.setSellRetCoin(configService.getPlatformCoinName());

        BigDecimal userUks = tradeLog.getSellFeeCurrency().divide(uksPrice, 8, RoundingMode.FLOOR);
        if (userUks.compareTo(BigDecimal.ZERO) > 0) {
            if (userUks.compareTo(BigDecimal.ZERO) > 0) {
                userCoinService.changeUserCoin(user.getId(), configService.getPlatformCoinName(), userUks, BigDecimal.ZERO, UserBillReason.TRADE_BONUS,
                        "logId=" + tradeLog.getId() + ",price=" + uksPrice);
            }
        }
        if (recUser != null) {
            BigDecimal recUserUks = tradeLog.getSellFeeCurrency().multiply(recUserRate).divide(uksPrice, 8, RoundingMode.FLOOR);
            if (recUserUks.compareTo(BigDecimal.ZERO) > 0) {
                userCoinService.changeUserCoin(recUser.getId(), configService.getPlatformCoinName(), recUserUks, BigDecimal.ZERO, UserBillReason.TRADE_BONUS,
                        "rec1 reword, logId=" + tradeLog.getId() + ",price=" + uksPrice + ",rate=" + recUserRate);
            }
            detail.setSellRecRet(recUserUks);
            detail.setSellRecUserId(recUser.getId());
        }
        if (rec2User != null) {
            BigDecimal rec2UserUks = tradeLog.getSellFeeCurrency().multiply(rec2UserRate).divide(uksPrice, 8, RoundingMode.FLOOR);
            if (rec2UserUks.compareTo(BigDecimal.ZERO) > 0) {
                userCoinService.changeUserCoin(rec2User.getId(), configService.getPlatformCoinName(), rec2UserUks, BigDecimal.ZERO, UserBillReason.TRADE_BONUS,
                        "rec2 reword, logId=" + tradeLog.getId() + ",price=" + uksPrice + ",rate=" + rec2UserRate);
            }
            detail.setSellRec2Ret(rec2UserUks);
            detail.setSellRec2UserId(rec2User.getId());
        }
        // 写日志
        tradeLogMapper.addTradeFeeReturnDetail(detail);
    }

    @Transactional
    public void rewardTeamFee(TradeLog tradeLog, User user, User recUser, User rec2User, BigDecimal recUserRate, BigDecimal rec2UserRate) {
        logger.info("rewardTradeFee " + tradeLog + ",user=" + user + ",recUser=" + recUser + ",rec2User=" + rec2User);
        configService.setConfigValue(ConfigKey.LAST_MINE_TRADE_ID, tradeLog.getId().toString());
        TradeFeeReturnDetail detail = new TradeFeeReturnDetail();
        detail.setTradeLogId(tradeLog.getId());
        detail.setRetTime(new Date());
        detail.setRecUserRate(recUserRate);
        detail.setRec2UserRate(rec2UserRate);
        detail.setBuyRetCoin(tradeLog.getCoinName());
        detail.setSellRetCoin(tradeLog.getSettlementCurrency());
        detail.setUserId(user.getId());

        if (recUser != null) {
            BigDecimal recUserEtex = tradeLog.getSellFeeCurrency().multiply(recUserRate);
            if (recUserEtex.compareTo(BigDecimal.ZERO) > 0) {
                userCoinService.changeUserCoin(recUser.getId(), configService.getMarketBaseCoinName(), recUserEtex, BigDecimal.ZERO, UserBillReason.TRADE_BONUS,
                        "rec1 reword, logId=" + tradeLog.getId() + ",sellRecRet=" + recUserEtex + ",rate=" + recUserRate);
            }
            detail.setSellRecRet(recUserEtex);
            detail.setSellRecUserId(recUser.getId());
        }
        if (rec2User != null) {
            BigDecimal rec2UserEtex = tradeLog.getSellFeeCurrency().multiply(rec2UserRate);
            if (rec2UserEtex.compareTo(BigDecimal.ZERO) > 0) {
                userCoinService.changeUserCoin(rec2User.getId(), configService.getMarketBaseCoinName(), rec2UserEtex, BigDecimal.ZERO, UserBillReason.TRADE_BONUS,
                        "rec2 reword, logId=" + tradeLog.getId() + ",SellRec2Ret=" + rec2UserEtex + ",rate=" + rec2UserRate);
            }
            detail.setSellRec2Ret(rec2UserEtex);
            detail.setSellRec2UserId(rec2User.getId());
        }
        // 写日志
        tradeLogMapper.addTradeFeeReturnDetail(detail);
    }

    public void logDayCountLimit() {
        HashMap<String, DayCountLimit.DayCount> stats = this.dayCountLimit.getAllStats();
        stats.forEach((String key, DayCountLimit.DayCount count) -> {
            logger.info("key:" + key + ",id:" + count.identity + ",time:" + count.statTime + ",count:" + count.count);
        });
    }


    public void rewardTeamFees() {
        BigInteger lastRewardLogId = configService.getConfigValue(ConfigKey.LAST_MINE_TRADE_ID, new BigInteger("-1"));
        //logger.info("最后一次撮合id为： ->{}", lastRewardLogId);
        if (lastRewardLogId.compareTo(BigInteger.ZERO) < 0) {
            // 第一次直接设置为最后一个交易id，以前的不进行
            logger.info("lastRewardLogId is not set");
            TradeLog tradeLog = tradeLogMapper.getLastTrade();
            if (tradeLog != null) {
                configService.setConfigValue(ConfigKey.LAST_MINE_TRADE_ID, tradeLog.getId().toString());
            }
            return;
        }

        /*BigDecimal etexprice = getPlatformCoinPriceOfUsdt();
        if (etexprice.compareTo(BigDecimal.ZERO) == 0) {
            logger.warn("1 ETEx = 0 USDT, ignore work");
            return;
        }
        logger.info("1 ETEx(" + configService.getPlatformCoinName() + ") = " + etexprice + " USDT" + ",lastRewardLogId=" + lastRewardLogId);
        */
        BigDecimal recUserRate = configService.getConfigValue(ConfigKey.TRADE_FEE_REC_USER, BigDecimal.ZERO, false);
        BigDecimal rec2UserRate = configService.getConfigValue(ConfigKey.TRADE_FEE_REC_REC_USER, BigDecimal.ZERO, false);
        /*int sameTradeRewardMaxTimes = configService.getConfigValue(ConfigKey.TRADE_SAME_REWARD_MAX_TIMES, 5);
        //logger.debug("rewardNewTradeFees,recUserRate=" + recUserRate + ",rec2UserRate=" + rec2UserRate + ",sameTradeRewardMaxTimes=" + sameTradeRewardMaxTimes);
        //this.dayCountLimit.setDayLimitCount(sameTradeRewardMaxTimes);
        if(this.lastRemoveTime < System.currentTimeMillis() - 1000L * 3600 * 24) {
            logger.info("removeOldStats ");
            try{
                this.dayCountLimit.removeOldStats();
            } catch (Exception ex) {
                logger.error("", ex);
            }
            this.lastRemoveTime = System.currentTimeMillis();
        }*/
        while (true) {
            TradeLog tradeLog = tradeLogMapper.getNextUsdtFeeTradeLog(lastRewardLogId);
            if (tradeLog == null) break;
            logger.info("begin reward trade " + tradeLog);
            /* 查看该交易是否超过当天奖励限制
            String tradeIdentity = tradeLog.getCoinName() + "/" + tradeLog.getSettlementCurrency() + "-" + tradeLog.getBuyUserId() + "/" + tradeLog.getSellUserId();
            if(this.dayCountLimit.isDayCountFinishUp(tradeIdentity)) {
                logger.info("trade of " + tradeIdentity + " is DayCountFinishUp, can not reward");
                lastRewardLogId = tradeLog.getId();
                continue;
            }*/
            // 获取用户，推荐用户，二级推荐用户
            User user = userMapper.getUserByUserId(tradeLog.getSellUserId());
            if (user == null) {
                lastRewardLogId = tradeLog.getId();
                logger.error("can not find user of id:" + tradeLog.getSellUserId());
                continue;
            }

            User recUser = null;
            if (user.getReferrer() != null && user.getReferrer() != 0) {
                recUser = userMapper.getUserByUserId(user.getReferrer());
            }

            User rec2User = null;
            if (recUser != null && recUser.getReferrer() != null && user.getReferrer() != 0) {
                rec2User = userMapper.getUserByUserId(recUser.getReferrer());
            }
            me.rewardTeamFee(tradeLog, user, recUser, rec2User, recUserRate, rec2UserRate);
            // 增加统计次数
            //this.dayCountLimit.addDayCount(tradeIdentity);

            lastRewardLogId = tradeLog.getId();
        }
    }
}
