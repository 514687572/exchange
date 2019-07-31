package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.utils.Language;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.MatchTransactionService;
import com.cmd.exchange.service.ServerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 交易撮合
 */
@Component
public class TransactionMatchMaking {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，30秒
    private final int firstWaitTime = 1000 * 30;
    // 平时等待时间，2秒
    @Value("${task.trade.match-making-sleep:1000}")
    private int defaultWaitTime = 1000;

    @Autowired
    private MatchTransactionService matchTransactionService;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("init TransactionMatchMaking");
        TransactionMatchMaking task = this;
        new Thread("TransactionMatchMaking") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin Transaction Match Making thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                matchMaking();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 交易撮合
    public void matchMaking() {
        log.debug("Begin Transaction Match Making");
        // 开始一次交易撮合
        matchTransactionService.matchNewTrades();
        log.debug("End Transaction Match Making");
    }

    public String dumpMemory() {
        return matchTransactionService.dumpMemory();
    }
}
