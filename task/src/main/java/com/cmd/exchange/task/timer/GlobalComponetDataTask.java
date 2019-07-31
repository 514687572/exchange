package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.componet.GlobalDataComponet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GlobalComponetDataTask {

    private static Log log = LogFactory.getLog(GlobalComponetDataTask.class);

    @Autowired
    private GlobalDataComponet globalDataComponet;

    private Thread gatherThread;


    @Scheduled(cron = "0 0/1 * * * ?")
    public synchronized void getGlobalData() {

        log.info("globaldata----start-------");
        log.info("globaldata----start-------");
        log.info("globaldata----start-------");
        log.info("globaldata----start-------");
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("feel coin ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("getFeeCoin") {
            public void run() {
                globalDataComponet.initConValueMap();
            }
        };
        gatherThread.start();
    }


}
