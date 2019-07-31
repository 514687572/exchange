package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.EthService;
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
public class EthSync {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，6秒
    private final int firstWaitTime = 1000 * 6;
    // 平时等待时间，60秒
    private final int defaultWaitTime = 1000 * 120;

    @Autowired
    private EthService ethService;
    @Autowired
    private ServerTaskService serverTaskService;

    @PostConstruct
    public void init() {
        log.info("init EthSync");
        EthSync task = this;
        new Thread("EthSync") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin sync eth thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                syncEth();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 开始同步
    public void syncEth() {
        if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
            log.debug("is not bonus task master, not work");
            return;
        }
        log.debug("Begin sync eth New Transactions");
        ethService.syncNewTransactions();
        log.debug("End sync eth New Transactions");
    }
}
