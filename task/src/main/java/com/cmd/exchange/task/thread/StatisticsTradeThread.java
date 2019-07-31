package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.TradeStatService;
import com.cmd.exchange.task.timer.StatisticsTradeTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

// 单独的线程更新24小时成交数据
@Component
public class StatisticsTradeThread {
    private static Log log = LogFactory.getLog(StatisticsTradeTask.class);
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private TradeService tradeService;

    @Value("${thread.renew_open_trade.time:1000}")
    private long updateOpenTradeTime = 1000;

    @PostConstruct
    public void init() {
        log.info("init StatisticsTradeThread");
        StatisticsTradeThread me = this;
        new Thread("statLast24HourTrade") {
            public void run() {
                me.statLast24HourTrade();
            }
        }.start();
        new Thread("updateOpenTradeList") {
            public void run() {
                me.updateOpenTradeList();
            }
        }.start();
    }

    private void fixMilSecond(long start, long milSecond) {
        long now = System.currentTimeMillis();
        try {
            long time = now - start;
            if (time < milSecond) {
                Thread.sleep(milSecond - time);
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }


    /**
     * 统计24小时内的实时数据
     */
    public void statLast24HourTrade() {
        while (true) {
            long start = System.currentTimeMillis();
            try {
                if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                    log.debug("not trade master server, ignore work");
                } else {
                    tradeStatService.statLast24HourTrade();
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
            fixMilSecond(start, 1000);
        }
    }

    public void updateOpenTradeList() {
        while (true) {
            long start = System.currentTimeMillis();
            try {
                if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                    log.debug("not trade master server, ignore work");
                } else {
                    tradeService.updateOpenTradeList();
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
            fixMilSecond(start, updateOpenTradeTime);
        }
    }
}
