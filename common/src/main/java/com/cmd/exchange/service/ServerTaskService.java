package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.ConfigMapper;
import com.cmd.exchange.common.mapper.TaskServerMapper;
import com.cmd.exchange.common.model.Config;
import com.cmd.exchange.common.utils.Network;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务器的主备和后台工作任务的主备信息维护
 */
@Service
public class ServerTaskService {
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private TaskServerMapper taskServerMapper;
    private Log log = LogFactory.getLog(this.getClass());

    // 自己的地址
    private String localIp;

    public ServerTaskService() {
        this.localIp = Network.getIpAddress();
    }

    /**
     * 开始一个任务，这个任务同时只能一个任务在工作，并且距离上次执行至少间隔一段时间
     * 调用这个函数成功后会同时设置本次开始的时间
     * 注意，本次调用必须在事务中，否则会抛出异常
     *
     * @param taskName    任务名称
     * @param minInterval 任务最少间隔，一般来说，如果期望间隔是4分钟，这里可以设置为1分钟，1/2，这样，不要设置为完全一样的，完全一样可能导致任务间隔时间不到导致无法执行
     * @return 上次执行的时间
     */
    public int lockAndBeginTask(String taskName, int minInterval) {
        Config conf = this.configMapper.getConfigByNameForUpdate(taskName);
        if (conf == null) {
            conf = new Config();
            conf.setConfName(taskName);
            conf.setConfValue("0");
            conf.setComment("Task");
            this.configMapper.insertConfig(conf);
        }
        // 重新锁定
        conf = this.configMapper.getConfigByNameForUpdate(taskName);
        int lastTime = Integer.parseInt(conf.getConfValue());
        this.configMapper.updateConfigValue(taskName, Integer.toString((int) (System.currentTimeMillis() / 1000)));
        return lastTime;
    }

    /**
     * 开始一个任务，这个任务同时只能一个任务在工作，并且距离上次执行至少间隔一段时间
     * 调用这个函数成功后会同时设置本次开始的时间
     * 注意，本次调用必须在事务中，否则会抛出异常
     *
     * @param taskName 任务名称
     * @return 上次执行的时间
     */
    public int lockAndBeginTask(String taskName) {
        return lockAndBeginTask(taskName, 0);
    }

    /**
     * 判断本机器是否是主服务器
     * 每个工作都可以有一个主服务器
     * 对于主服务器，该接口会直接更新主服务器心跳时间
     *
     * @param taskName 工作名称
     * @return 是否是主服务器
     */
    public boolean isMasterServer(String taskName) {
        if (StringUtils.isBlank(this.localIp)) {
            this.log.error("Cannot get local ip, cannot do work");
            return false;
        }
        int updateCount = this.taskServerMapper.updateTaskMasterServer(taskName, this.localIp);
        return updateCount > 0;
    }

    /**
     * 更新服务器的心跳时间
     *
     * @param taskName 任务名称
     * @return 如果变成主服务器或是主服务器，返回true，否则返回false
     */
    public boolean heartbeat(String taskName) {
        return this.isMasterServer(taskName);
    }
}
