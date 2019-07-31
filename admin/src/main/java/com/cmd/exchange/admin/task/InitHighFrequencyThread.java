package com.cmd.exchange.admin.task;

import com.cmd.exchange.admin.service.HighFrequencyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitHighFrequencyThread {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，2秒
    private final int firstWaitTime = 1000 * 2;
    // 平时等待时间，一小时
    private final int defaultWaitTime = 1000 * 60 * 60;

    @Autowired
    private HighFrequencyService highFrequencyService;

    // @PostConstruct
    public void init() {
        log.info("init InitHighFrequencyThread");
        InitHighFrequencyThread task = this;
        new Thread("InitHighFrequencyThread") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin InitHighFrequencyThread  thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                initHighFrequencyKey();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 开始高频监控
    public void initHighFrequencyKey() {

        log.debug("Begin InitHighFrequencyThread etc New Transactions");
        highFrequencyService.initHighFrequency();
        log.debug("End InitHighFrequencyThread etc New Transactions");
    }

}
