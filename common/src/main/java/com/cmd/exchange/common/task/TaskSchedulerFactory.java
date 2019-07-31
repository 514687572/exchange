package com.cmd.exchange.common.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * 调度器工厂类，应当在Spring中将该类配置为单例
 */
@Component
@Slf4j
public class TaskSchedulerFactory {

    private volatile Scheduler scheduler;

    /**
     * 获得scheduler实例
     */
    public Scheduler getScheduler() {
        Scheduler s = scheduler;
        if (s == null) {
            synchronized (this) {
                s = scheduler;
                if (s == null) {
                    // 双重检查
                    try {
                        SchedulerFactory sf = new StdSchedulerFactory();
                        s = scheduler = sf.getScheduler();
                    } catch (Exception e) {
                        log.error("Get scheduler error :" + e.getMessage(), e);
                    }
                }
            }
        }

        return s;
    }
}
