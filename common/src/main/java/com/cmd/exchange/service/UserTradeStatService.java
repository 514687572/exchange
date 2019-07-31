package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.mapper.UserTradeStatMapper;
import com.cmd.exchange.common.model.TradeLog;
import com.cmd.exchange.common.model.UserTradeStat;
import com.cmd.exchange.common.utils.Assert;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserTradeStatService {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserTradeStatMapper userTradeStatMapper;
    @Autowired
    private UserTradeStatService me;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TradeLogMapper tradeLogMapper;

    /**
     * 将一个交易统计到用户交易信息表
     *
     * @param tradeLog 交易日志
     */
    @Transactional
    public void statOneTradeToUserTradeStat(TradeLog tradeLog) {
        // 先查看是否有记录，有就用原有记录，没有添加新记录
        // 买入人
        boolean updateBuyLastId = true;
        UserTradeStat buyUserTradeStat = userTradeStatMapper.lockUserTradeStat(tradeLog.getBuyUserId(), tradeLog.getCoinName(), tradeLog.getSettlementCurrency());
        if (buyUserTradeStat == null) {
            buyUserTradeStat = new UserTradeStat();
            buyUserTradeStat.setUserId(tradeLog.getBuyUserId()).setCoinName(tradeLog.getCoinName()).setSettlementCurrency(tradeLog.getSettlementCurrency())
                    .setCoinBuyTotal(tradeLog.getAmount()).setSettlementBuyTotal(tradeLog.getPrice().multiply(tradeLog.getAmount()))
                    .setCoinFee(tradeLog.getBuyFeeCoin()).setFirstTradeLogId(tradeLog.getId()).setLastTradeLogId(tradeLog.getId());
            buyUserTradeStat.setHoldCoin(buyUserTradeStat.getCoinBuyTotal().subtract(buyUserTradeStat.getCoinFee()).subtract(buyUserTradeStat.getCoinSellTotal()));
            buyUserTradeStat.setSettlementCostTotal(buyUserTradeStat.getSettlementBuyTotal().subtract(buyUserTradeStat.getSettlementSellTotal()).add(buyUserTradeStat.getSettlementFee()));
            userTradeStatMapper.addUserTradeStat(buyUserTradeStat);
        } else {
            if (buyUserTradeStat.getLastTradeLogId().compareTo(tradeLog.getId()) < 0) {
                buyUserTradeStat.setCoinBuyTotal(buyUserTradeStat.getCoinBuyTotal().add(tradeLog.getAmount()));
                buyUserTradeStat.setSettlementBuyTotal(buyUserTradeStat.getSettlementBuyTotal().add(tradeLog.getPrice().multiply(tradeLog.getAmount())));
                buyUserTradeStat.setCoinFee(buyUserTradeStat.getCoinFee().add(tradeLog.getBuyFeeCoin()));
                buyUserTradeStat.setLastTradeLogId(tradeLog.getId());
                buyUserTradeStat.setHoldCoin(buyUserTradeStat.getCoinBuyTotal().subtract(buyUserTradeStat.getCoinFee()).subtract(buyUserTradeStat.getCoinSellTotal()));
                buyUserTradeStat.setSettlementCostTotal(buyUserTradeStat.getSettlementBuyTotal().subtract(buyUserTradeStat.getSettlementSellTotal()).add(buyUserTradeStat.getSettlementFee()));
                int count = userTradeStatMapper.updateUserTradeStatCoin(buyUserTradeStat);
                Assert.check(count != 1, ErrorCode.ERR_RECORD_UPDATE, "updateUserTradeStatCoin(buy) failed:" + ReflectionToStringBuilder.toString(buyUserTradeStat));
            } else {
                log.error("ignore trade because of id, tradeLogId=" + tradeLog.getId() + ",lastStatTradeLogId=" + buyUserTradeStat.getLastTradeLogId());
                updateBuyLastId = false;
            }
        }

        // 卖出人
        UserTradeStat sellUserTradeStat = userTradeStatMapper.lockUserTradeStat(tradeLog.getSellUserId(), tradeLog.getCoinName(), tradeLog.getSettlementCurrency());
        if (sellUserTradeStat == null) {
            sellUserTradeStat = new UserTradeStat();
            sellUserTradeStat.setUserId(tradeLog.getSellUserId()).setCoinName(tradeLog.getCoinName()).setSettlementCurrency(tradeLog.getSettlementCurrency())
                    .setCoinSellTotal(tradeLog.getAmount()).setSettlementSellTotal(tradeLog.getPrice().multiply(tradeLog.getAmount()))
                    .setSettlementFee(tradeLog.getSellFeeCurrency()).setFirstTradeLogId(tradeLog.getId()).setLastTradeLogId(tradeLog.getId());
            sellUserTradeStat.setHoldCoin(sellUserTradeStat.getCoinBuyTotal().subtract(sellUserTradeStat.getCoinFee()).subtract(sellUserTradeStat.getCoinSellTotal()));
            sellUserTradeStat.setSettlementCostTotal(sellUserTradeStat.getSettlementBuyTotal().subtract(sellUserTradeStat.getSettlementSellTotal()).add(sellUserTradeStat.getSettlementFee()));
            userTradeStatMapper.addUserTradeStat(sellUserTradeStat);
        } else {
            if (sellUserTradeStat.getLastTradeLogId().compareTo(tradeLog.getId()) < 0
                    // 买卖是同一个人，并且更新了买单，那么卖单肯定也要更新
                    || (updateBuyLastId && tradeLog.getSellUserId().equals(tradeLog.getBuyUserId()))) {
                sellUserTradeStat.setCoinSellTotal(sellUserTradeStat.getCoinSellTotal().add(tradeLog.getAmount()));
                sellUserTradeStat.setSettlementSellTotal(sellUserTradeStat.getSettlementSellTotal().add(tradeLog.getPrice().multiply(tradeLog.getAmount())));
                sellUserTradeStat.setSettlementFee(sellUserTradeStat.getSettlementFee().add(tradeLog.getSellFeeCurrency()));
                sellUserTradeStat.setLastTradeLogId(tradeLog.getId());
                sellUserTradeStat.setHoldCoin(sellUserTradeStat.getCoinBuyTotal().subtract(sellUserTradeStat.getCoinFee()).subtract(sellUserTradeStat.getCoinSellTotal()));
                sellUserTradeStat.setSettlementCostTotal(sellUserTradeStat.getSettlementBuyTotal().subtract(sellUserTradeStat.getSettlementSellTotal()).add(sellUserTradeStat.getSettlementFee()));
                int count = userTradeStatMapper.updateUserTradeStatCoin(sellUserTradeStat);
                Assert.check(count != 1, ErrorCode.ERR_RECORD_UPDATE, "updateUserTradeStatCoin(sell) failed:" + ReflectionToStringBuilder.toString(sellUserTradeStat));
            } else {
                log.error("ignore trade because of id, tradeLogId=" + tradeLog.getId() + ",lastStatTradeLogId=" + sellUserTradeStat.getLastTradeLogId());
            }
        }
        configService.setConfigValue(ConfigKey.USER_TRADE_STAT_LAST_LOG_ID, tradeLog.getId().toString());
    }

    /**
     * 将新的成交记录统计到用户统计里面
     */
    public void statNewTradesToUserTradeStat() {
        BigInteger lastLogId = configService.getConfigValue(ConfigKey.USER_TRADE_STAT_LAST_LOG_ID, BigInteger.ZERO);
        log.info("begin statNewTradesToUserTradeStat,lastLogId=" + lastLogId);
        int count = 0;
        while (true) {
            TradeLog tradeLog = tradeLogMapper.getNextTradeLog(lastLogId);
            if (tradeLog == null) break;
            me.statOneTradeToUserTradeStat(tradeLog);
            lastLogId = tradeLog.getId();
            count++;
        }
        BigInteger nowLast = configService.getConfigValue(ConfigKey.USER_TRADE_STAT_LAST_LOG_ID, BigInteger.ZERO);
        log.info("end statNewTradesToUserTradeStat,lastLogId=" + lastLogId + ",count=" + count + ",nowLast=" + nowLast);
    }

    // 排序，0：用户id升序，1 持仓成本降序，2 持仓成本升序  3：coinName 升序，4：settlementCurrency升序
    public Page<UserTradeStat> searchUserTradeStat(Integer userId, String coinName, String settlementCurrency, Integer orderType, int pageNo, int pageSize) {
        return userTradeStatMapper.searchUserTradeStat(userId, coinName, settlementCurrency, orderType, new RowBounds(pageNo, pageSize));
    }

    // 统计当前用户的交易盈亏信息
    // 排序 1 持仓成本降序，2 持仓成本升序 3 盈利总额降序 4 盈利总额升序
    public Page<UserTradeStat> statCurrentUserTrade(String coinName, String settlementCurrency, Integer userId, Integer orderType, int pageNo, int pageSize, String column, String sort) {
        BigDecimal price = tradeLogMapper.getLatestPrice(coinName, settlementCurrency);
        Assert.check(price == null, ErrorCode.ERR_RECORD_NOT_EXIST, "找不到最新成交价，无法分析盈亏情况");
        return userTradeStatMapper.statUserTradeWithPrice(coinName, settlementCurrency, userId, price, orderType, column, sort, new RowBounds(pageNo, pageSize));
    }
}
