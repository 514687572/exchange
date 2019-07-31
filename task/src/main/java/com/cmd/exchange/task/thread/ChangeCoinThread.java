package com.cmd.exchange.task.thread;

import com.cmd.exchange.service.ChangeCoinService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 同步比特币
 */
@Component
public class ChangeCoinThread {
    private Log log = LogFactory.getLog(this.getClass());
    // 平时等待时间，5分钟
    @Value("${task.change_coin.time:300000}")
    private final int defaultWaitTime = 300000;
    // 删除时间超过指定时间的已经完成的记录
    @Value("${task.change_coin.del_time:30}")
    private final long delTime = 1000L * 3600 * 24 * 30;

    @Autowired
    private ChangeCoinService changeCoinService;

    @PostConstruct
    public void init() {
        log.info("init ChangeCoinThread");
        ChangeCoinThread task = this;
        new Thread("ChangeCoin") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = defaultWaitTime;
        log.warn("Begin sync bitcoin thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                changeCoinService.doAllChangeCoin(new Date());
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void delOldChangeCoin() {
        try {
            Date date = new Date(System.currentTimeMillis() - this.delTime);
            log.info("Begin delOldChangeCoin,date=" + date);
            int count = changeCoinService.delOldChangeCoin(date);
            log.info("end delOldChangeCoin,date=" + date + ",count=" + count);
        } catch (Throwable th) {
            log.error("", th);
        }
    }
}
