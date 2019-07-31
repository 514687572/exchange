package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.CoinSavingsService;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.ShareOutBonusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 从分润宝存钱/取钱任务，用于延迟到达（银行的T+1）操作
 */
@Component
public class CoinSavingTask {
    private static Log log = LogFactory.getLog(CoinSavingTask.class);
    @Autowired
    private CoinSavingsService coinSavingsService;
    @Autowired
    private ServerTaskService serverTaskService;

    private Thread coinArrivalThread;

    @Scheduled(cron = "0 0/5 * * * ?")
    public synchronized void coinArrivalTimer() {
        if (coinArrivalThread != null && coinArrivalThread.isAlive()) {
            log.error("coinArrivalThread is alive, cannot start new work");
            return;
        }
        coinArrivalThread = new Thread("coinArrival") {
            public void run() {
                doCoinArrival();
            }
        };
        coinArrivalThread.start();
    }

    public synchronized void doCoinArrival() {
        log.info("begin coinArrivalTimer timer");
        try {
            if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
                log.debug("coinArrivalTimer, not master server,ignore");
                return;
            }

            coinSavingsService.checkArrivalRecords();
        } catch (Exception ex) {
            log.error("", ex);
        }

    }
}
