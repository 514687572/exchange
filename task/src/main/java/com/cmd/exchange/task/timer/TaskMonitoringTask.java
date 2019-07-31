package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ServerType;
import com.cmd.exchange.common.utils.Network;
import com.cmd.exchange.service.MonitorCacheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 用于监控api服务器
 */
@Component
public class TaskMonitoringTask {
    private static Log log = LogFactory.getLog(TaskMonitoringTask.class);


    // 第一次等待时间，5秒
    private final int firstWaitTime = 1000 * 5;
    // 平时等待时间，60秒
    private final int defaultWaitTime = 1000 * 10;

    @Autowired
    private MonitorCacheService monitorCacheService;

    private Thread gatherThread;

    private String localIp;

    public TaskMonitoringTask() {
        this.localIp = Network.getIpAddress();
    }

    @Autowired
    private RedisTemplate<String, String> redTemplate;


    @PostConstruct
    public void init() {
        log.info("init service monitor");
        TaskMonitoringTask task = this;
        new Thread("service monitor") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;

        while (true) {
            try {
                Thread.sleep(waitTime);
                serviceMonitor();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 开始监控
    public void serviceMonitor() {

        log.info("Begin serviceMonitor");
        boolean bool = true;
        //10秒一次
        try {
            if (this.localIp == null || this.localIp.length() == 0) {
                this.log.error("Cannot get local ip, cannot do work");

            }
            // InetAddress address = InetAddress.getLocalHost();
            log.info("current service ip address is {}" + this.localIp);
            this.redTemplate.opsForValue().set(ServerType.TASK_SERVER + this.localIp, "OK", 10L, TimeUnit.SECONDS);
            // monitorCacheService.setApiObjectByStringKey(address.getHostAddress(), bool, 10L);
            System.out.println(this.localIp);
            log.info("End serviceMonitor");
        } catch (Exception e) {
            log.error("excption is {}", e);
        }


    }
}
