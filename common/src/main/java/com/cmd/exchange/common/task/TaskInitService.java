package com.cmd.exchange.common.task;

import com.cmd.exchange.common.mapper.CronConfigMapper;
import com.cmd.exchange.common.model.CronConfig;
import com.cmd.exchange.common.task.TaskSchedulerFactory;
import com.cmd.exchange.common.task.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

import static org.quartz.CronExpression.isValidExpression;

/**
 * 定时任务初始化服务类
 */
@Component
@Slf4j
public class TaskInitService {

    @Resource
    private TaskSchedulerFactory schedulerFactory;

    @Resource
    private CronConfigMapper cronConfigMapper;

    /*
     * 获取定时任务列表
     * */
    public Iterable<CronConfig> getCronJobsList() {
        return cronConfigMapper.getCronConfigList();
    }

    /**
     * 初始化
     */
    public void init() {
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (scheduler == null) {
            log.error("初始化定时任务组件失败，Scheduler is null...");
            return;
        }

        // 初始化基于cron时间配置的任务列表
        try {
            initCronJobs(scheduler);
            scheduler.start();
        } catch (Exception e) {
            log.error("init cron tasks error," + e.getMessage(), e);
        }
        log.info("TaskInitService::init......");
    }

    /**
     * 初始化任务（基于cron触发器）
     */
    private void initCronJobs(Scheduler scheduler) throws Exception {
        Iterable<CronConfig> jobList = getCronJobsList();
        if (jobList != null) {
            for (CronConfig job : jobList) {
                scheduleCronJob(job, scheduler);
            }
        }
    }

    /**
     * 安排任务(基于cron触发器)
     */
    private void scheduleCronJob(CronConfig job, Scheduler scheduler) {
        if (job != null && StringUtils.isNotBlank(job.getCronName()) && StringUtils.isNotBlank(job.getCronClassName())
                && StringUtils.isNotBlank(job.getCronExpression()) && scheduler != null) {
            if (job.getStatus().intValue() == 0) {
                return;
            }

            try {
                JobKey jobKey = TaskUtils.genCronJobKey(job);

                if (!scheduler.checkExists(jobKey)) {
                    // This job doesn't exist, then add it to scheduler.
                    log.info("Add new cron job to scheduler, jobName = " + job.getCronName());
                    this.newJobAndNewCronTrigger(job, scheduler, jobKey);
                } else {
                    log.info("Update cron job to scheduler, jobName = " + job.getCronName());
                    this.updateCronTriggerOfJob(job, scheduler, jobKey);
                }
            } catch (Exception e) {
                log.error("ScheduleCronJob is error," + e.getMessage(), e);
            }
        } else {
            log.error("Method scheduleCronJob arguments are invalid.");
        }
    }

    /**
     * 新建job和trigger到scheduler(基于cron触发器)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void newJobAndNewCronTrigger(CronConfig job, Scheduler scheduler, JobKey jobKey)
            throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);

        String cronExpr = job.getCronExpression();
        if (!isValidExpression(cronExpr)) {
            return;
        }

        // get a Class object by string class name of job;
        Class jobClass = Class.forName(job.getCronClassName().trim());
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(job.getCronName()).usingJobData("jobid", job.getId()).build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr).withMisfireHandlingInstructionDoNothing()).build();

        scheduler.scheduleJob(jobDetail, trigger);
        log.info("newJobAndNewCronTrigger:" + job.getId());
    }

    /**
     * 更新job的trigger(基于cron触发器)
     */
    private void updateCronTriggerOfJob(CronConfig job, Scheduler scheduler, JobKey jobKey) throws SchedulerException {
        TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);
        String cronExpr = job.getCronExpression().trim();

        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        for (int i = 0; triggers != null && i < triggers.size(); i++) {
            Trigger trigger = triggers.get(i);
            TriggerKey curTriggerKey = trigger.getKey();

            if (TaskUtils.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
                if (trigger instanceof CronTrigger && cronExpr.equalsIgnoreCase(((CronTrigger) trigger).getCronExpression())) {
                    // Don't need to do anything.
                } else {
                    if (isValidExpression(job.getCronExpression())) {
                        // Cron expression is valid, build a new trigger and
                        // replace the old one.
                        CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr).withMisfireHandlingInstructionDoNothing())
                                .build();
                        scheduler.rescheduleJob(curTriggerKey, newTrigger);
                    }
                }
            } else {
                // different trigger key ,The trigger key is illegal, unschedule
                // this trigger
                scheduler.unscheduleJob(curTriggerKey);
            }
        }
        log.info("updateCronTriggerOfJob:" + job.getId());
    }

}
