package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 分红奖励
 */
@Component
public class ShareOutBonusTask {
    private static Log log = LogFactory.getLog(ShareOutBonusTask.class);
    @Autowired
    private ShareOutBonusService shareOutBonusService;
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private ConfigService configService;

    private Thread calYesterdayMiningIncomeThread;
    private Thread calYesterdayShareOutBonusThread;


    @Scheduled(cron = "0 30 1 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public synchronized void calShareOutBonusTimer() {
        if (calYesterdayShareOutBonusThread != null && calYesterdayShareOutBonusThread.isAlive()) {
            log.error("calYesterdayShareOutBonusThread is alive, cannot start new work");
            return;
        }
        calYesterdayShareOutBonusThread = new Thread("calShareOutBonus") {
            public void run() {
                calShareOutBonus();
            }
        };
        calYesterdayShareOutBonusThread.start();
    }

    public synchronized void calShareOutBonus() {
        log.info("begin calShareOutBonus timer");
        try {
            if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
                log.debug("calYesterdayShareOutBonus, not master server,ignore");
                return;
            }

            // 超级节点奖励
            //shareOutBonusService.statSuperNodeYesterdayProfit();
            shareOutBonusService.statNodeYesterdayProfit();
        } catch (Exception ex) {
            log.error("", ex);
        }

        log.info("begin updateUserMachineProfit timer");
        try {
            if(!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
                log.debug("updateUserMachineProfit, not master server,ignore");
                return;
            }
            // 用户矿机收益
            shareOutBonusService.updateUserMachineProfit();
        }catch(Exception ex) {
            log.error("", ex);
        }

        // 统计用户收益
        try {
            shareOutBonusService.updateUserDayProfit();
        } catch (Exception ex) {
            log.error("", ex);
        }

    }
}
