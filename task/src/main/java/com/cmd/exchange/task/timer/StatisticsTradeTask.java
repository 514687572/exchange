package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.TradeStatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StatisticsTradeTask {
    private static Log log = LogFactory.getLog(StatisticsTradeTask.class);
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private MarketService marketService;

    @Value("${trade.stat.kline.interval:2000}")
    private int statKlineInterval = 2000;  // k线统计周期
    private Thread statKlineThread;


    @PostConstruct
    public void init() {
        log.info("init StatKline");
        new Thread("StatKline") {
            public void run() {
                statKlineRealTime();
            }
        }.start();
    }

    public void statKlineRealTime() {
        while (true) {
            try {
                Thread.sleep(statKlineInterval);
                if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                    log.debug("not trade master server, statKlineRealTime ignore work");
                    continue;
                }
                for (Market m : marketService.getAllMarkets()) {
                    if (m.getClosed() == MarketStatus.SHOW) {
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 1);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 5);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 15);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 30);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 60);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 240);
                        tradeStatService.updateNewTradeStats(m.getCoinName(), m.getSettlementCurrency(), 1440);
                    }
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    /**
     * 统计每段时间的交易情况，每分钟统计一次
     */
    @Scheduled(cron = "12 * * * * ?")
    public void statTrade() {
        try {
            if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                log.debug("not trade master server, ignore work");
                return;
            }
            tradeStatService.statOneMinuteTrades();
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    private void statTradeCycle(int cycle) {
        try {
            if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                log.debug("not trade master server, ignore work");
                return;
            }
            tradeStatService.statCycleTrades(cycle);
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 统计每段时间的交易情况，每5分钟统计一次
     */
    @Scheduled(cron = "30 0/5 * * * ?")
    public void statTrade5() {
        statTradeCycle(5);
    }

    /**
     * 统计每段时间的交易情况，每15分钟统计一次
     */
    @Scheduled(cron = "0 2,17,32,47 * * * ?")
    public void statTrade15() {
        statTradeCycle(15);
    }

    /**
     * 统计每段时间的交易情况，每30分钟统计一次
     */
    @Scheduled(cron = "0 3,33 * * * ?")
    public void statTrade30() {
        statTradeCycle(30);
    }

    /**
     * 统计每段时间的交易情况，每60分钟统计一次
     */
    @Scheduled(cron = "0 5 * * * ?")
    public void statTrade60() {
        statTradeCycle(60);
    }

    /**
     * 统计每段时间的交易情况，每240分钟统计一次
     */
    @Scheduled(cron = "0 7 0,4,8,12,16,20 * * ?")
    public void statTrade240() {
        log.info("statTrade240 begin");
        statTradeCycle(240);
        log.info("statTrade240 end");
    }

    /**
     * 统计每段时间的交易情况，每天统计一次
     */
    @Scheduled(cron = "0 20 0 * * ?")
    public void statTradeDay() {
        statTradeCycle(1440);
        log.info("stat day trade end, now stat 5 day stat");
        statTradeCycle(1440 * 5);
        log.info("stat 5 day trade end, now stat 7 day stat");
        statTradeCycle(1440 * 7);
        log.info("stat 7 day stat end");
    }

    /**
     * 统计每段时间的交易情况，每月统计一次
     */
    @Scheduled(cron = "0 30 2 1 * ?")
    public void statTradeMonth() {
        log.info("statTradeMonth");
        statTradeCycle(44640);
        log.info("statTradeMonth end");
    }

    /**
     * 统计24小时内的实时数据
     */
    /*@Scheduled(fixedDelayString = "1000")
    public void statLast24HourTrade() {
        try {
            if(!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                log.debug("statLast24HourTrade,not trade master server, ignore work");
                return;
            }
            tradeStatService.statLast24HourTrade();
        }catch (Exception ex) {
            log.error("", ex);
        }
    }*/

    /*@Scheduled(fixedDelayString = "1000")
    public void updateOpenTradeList() {
        try {
            if(!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                log.debug("updateOpenTradeList,not trade master server, ignore work");
                return;
            }
            tradeService.updateOpenTradeList();
        }catch (Exception ex) {
            log.error("", ex);
        }
    }*/
}

