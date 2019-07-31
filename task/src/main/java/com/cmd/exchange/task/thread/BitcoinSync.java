package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.BitcoinService;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.MatchTransactionService;
import com.cmd.exchange.service.ServerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 同步比特币
 */
@Component
public class BitcoinSync {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，4秒
    private final int firstWaitTime = 1000 * 4;
    // 平时等待时间，120秒
    private final int defaultWaitTime = 1000 * 120;

    @Autowired
    private BitcoinService bitcoinService;
    @Autowired
    private ServerTaskService serverTaskService;

    @PostConstruct
    public void init() {
        log.info("init BitcoinSync");
        BitcoinSync task = this;
        new Thread("BitcoinSync") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin sync bitcoin thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                syncBitcoin();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 开始同步
    public void syncBitcoin() {
        if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
            log.debug("is not bonus task master, not work");
            return;
        }
        log.debug("Begin sync bitcoin New Transactions");
        bitcoinService.syncNewTransactions();
        log.debug("End sync bitcoin New Transactions");
    }
}
