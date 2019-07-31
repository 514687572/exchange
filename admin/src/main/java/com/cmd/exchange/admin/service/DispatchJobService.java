package com.cmd.exchange.admin.service;

import com.cmd.exchange.common.task.TaskInitService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class DispatchJobService {
    private static Log log = LogFactory.getLog(DispatchJobService.class);

    @Autowired
    TaskInitService taskInitService;

    @PostConstruct
    public void initTask() {
        //启动定时任务
        log.info("DispatchJobService init task......");
        taskInitService.init();
    }
}
