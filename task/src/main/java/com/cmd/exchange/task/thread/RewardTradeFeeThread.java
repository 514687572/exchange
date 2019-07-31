package com.cmd.exchange.task.thread;

import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.MatchTransactionService;
import com.cmd.exchange.service.RewardTradeFeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 将手续费转换为平台币返还给用户
 */
@Component
public class RewardTradeFeeThread {
    private Log log = LogFactory.getLog(this.getClass());
    // 第一次等待时间，30秒
    private final int firstWaitTime = 1000 * 30;
    // 平时等待时间，2秒
    @Value("${task.trade.match-making-sleep:2000}")
    private int defaultWaitTime = 2000;

    @Autowired
    private RewardTradeFeeService rewardTradeFeeService;

    @Autowired
    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("init RewardTradeFeeThread");
        RewardTradeFeeThread task = this;
        new Thread("RewardTradeFeeThread") {
            public void run() {
                task.run();
            }
        }.start();
    }

    public void run() {
        int waitTime = firstWaitTime;
        log.warn("Begin RewardTradeFeeThread thread");
        while (true) {
            try {
                Thread.sleep(waitTime);
                rewardTeamFee();
                waitTime = defaultWaitTime;
            } catch (Throwable th) {
                log.error("", th);
            }
        }
    }

    // 返回  旧方法暂时不用
    public void rewardTradeFee() {
        log.debug("Begin rewardTradeFee");
        // 开始一次交易撮合
        rewardTradeFeeService.rewardNewTradeFees();
        log.debug("End rewardTradeFee");
    }

    // 返回  团队奖励
    public void rewardTeamFee() {
        log.debug("Begin rewardTeamFee");
        // 开始一次交易撮合
        rewardTradeFeeService.rewardTeamFees();
        log.debug("End rewardTeamFee");
    }

}
