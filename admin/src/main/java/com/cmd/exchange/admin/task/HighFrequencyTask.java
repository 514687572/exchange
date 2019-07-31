package com.cmd.exchange.admin.task;

import com.cmd.exchange.admin.service.HighFrequencyService;
import com.cmd.exchange.admin.service.componet.NoWarningUserComponet;
import com.cmd.exchange.service.TimeMonitoringConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 此处用于监控高频交易
 */
@Component
public class HighFrequencyTask {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private TimeMonitoringConfigService timeMonitoringConfigService;
    @Autowired
    private HighFrequencyService highFrequencyService;
    @Autowired
    private NoWarningUserComponet noWarningUserComponet;

    private Thread gatherThread;


    /**
     * 项目启动后一个小时执行一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void initHighFrequencyKey() {
        log.info("init start HighFrequency  key 初始化高频交易缓存k start");
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("init start HighFrequency  key ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("HighFrequencyInit and initNoWarningUser") {
            public void run() {
                noWarningUserComponet.initNoWarningUser();
                highFrequencyService.initHighFrequency();
            }
        };
        gatherThread.start();

        log.info("init finished HighFrequency  key 初始化高频交易缓存 finished");

    }

    //在进行高频交易定时监控
    @Scheduled(cron = "0/5 * *  * * ? ")   //每10秒执行一次
    public void searchTrade() {
        log.info("search trade start 定时查询高频交易信息");
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("search trade start ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("SearchTrade") {
            public void run() {
                highFrequencyService.searchTarde();
            }
        };
        gatherThread.start();

        log.info("search trade finished");
    }

}
