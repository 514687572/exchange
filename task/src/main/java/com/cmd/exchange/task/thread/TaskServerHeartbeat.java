package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.service.ServerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用户更新服务器的心跳
 */
@Component
public class TaskServerHeartbeat {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，2秒
    private final int firstWaitTime = 1000 * 2;
    // 平时等待时间，3分钟
    private final int defaultWaitTime = 1000 * 180;

    @Autowired
    private ServerTaskService serverTaskService;

    @PostConstruct
    public void init() {
        log.info("init TaskServerHeartbeat");
        TaskServerHeartbeat task = this;
        new Thread("TaskServerHeartbeat") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin heart beat thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                heartBeat();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    public void heartBeat() {
        log.debug("heart beat");
        this.serverTaskService.heartbeat(ConfigKey.TASK_NAME_TRADE_MATCH);
        this.serverTaskService.heartbeat(ConfigKey.TASK_NAME_SHARE_OUT_BONUS);
    }
}
