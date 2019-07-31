package com.cmd.exchange.admin.task;

import com.cmd.exchange.admin.service.HighFrequencyService;
import com.cmd.exchange.service.ServerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HighFrequencyThread {

    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，6秒
    private final int firstWaitTime = 1000 * 2;
    // 平时等待时间，60秒
    private final int defaultWaitTime = 1000 * 5;

    @Autowired
    private HighFrequencyService highFrequencyService;

    @Autowired
    private ServerTaskService serverTaskService;

    //@PostConstruct
    public void init() {
        log.info("init HighFrequency");
        HighFrequencyThread task = this;
        new Thread("HighFrequency") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin HighFrequency  thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                highFrequency();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 开始高频监控
    public void highFrequency() {

        log.debug("Begin HighFrequency etc New Transactions");
        highFrequencyService.searchTarde();
        log.debug("End HighFrequency etc New Transactions");
    }
}
