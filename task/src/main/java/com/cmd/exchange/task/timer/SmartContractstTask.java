package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.CoinSavingsService;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.ShareOutBonusService;
import com.cmd.exchange.service.UserContractstService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 智能合约订单每日返还，用于延迟到达（银行的T+1）操作
 */
@Component
public class SmartContractstTask {
    private static Log log = LogFactory.getLog(SmartContractstTask.class);
    @Autowired
    private UserContractstService userContractstService;
    @Autowired
    private ShareOutBonusService shareOutBonusService;
    @Autowired
    private ServerTaskService serverTaskService;

    private Thread smartContractstThread;

    @Scheduled(cron = "0 0 1 * * ?")
    public synchronized void smartContractstTimer() {
        if (smartContractstThread != null && smartContractstThread.isAlive()) {
            log.error("smartContractstThread is alive, cannot start new work");
            return;
        }
        smartContractstThread = new Thread("smartContractstThread") {
            public void run() {
                doSmartContractst();
            }
        };
        smartContractstThread.start();
    }

    public synchronized void doSmartContractst() {
        log.info("begin smartContractstThread timer");
        if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SMART_CONTRACTST)) {
            log.debug("smartContractstThread, not master server,ignore");
            return;
        }


        try {
            userContractstService.rtvDayContract();
        } catch (Exception ex) {
            log.error("智能合约返还异常", ex);
        }

        try {

            shareOutBonusService.smartNodeYesterdayProfit();

        } catch (Exception ex) {
            log.error("智能合约分润异常", ex);
        }

    }
}
