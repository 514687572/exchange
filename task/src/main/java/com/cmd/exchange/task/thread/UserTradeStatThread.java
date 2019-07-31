package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.UserTradeStatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserTradeStatThread {
    private Log log = LogFactory.getLog(this.getClass());
    // 平时等待时间，10秒
    @Value("${task.user_trade_stat.interval:5000}")
    private final int defaultWaitTime = 1000 * 5;
    @Autowired
    private UserTradeStatService userTradeStatService;
    @Autowired
    private ServerTaskService serverTaskService;

    @PostConstruct
    public void init() {
        log.info("init UserTradeStatThread");
        new Thread("UserTradeStat") {
            public void run() {
                startRun();
            }
        }.start();
    }

    public void startRun() {
        log.info("Start Run");
        while (true) {
            try {
                Thread.sleep(this.defaultWaitTime);
                if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_SHARE_OUT_BONUS)) {
                    log.debug("is not bonus task master, not work");
                    continue;
                }
                userTradeStatService.statNewTradesToUserTradeStat();
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }
}
