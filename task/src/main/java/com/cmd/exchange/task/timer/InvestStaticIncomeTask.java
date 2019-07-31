package com.cmd.exchange.task.timer;

import com.cmd.exchange.service.InvestDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每日24点根据用户投资总额返币给用户
 */
@Component
public class InvestStaticIncomeTask {
    private static Log log = LogFactory.getLog(InvestStaticIncomeTask.class);
    @Autowired
    private InvestDetailService investDetailService;

    /**
     * 每天0点返投资静态收益一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void investStaticIncomeTimer() {
        try {
            //查询过去一天的所有投资记录，按userId分组，计算出每个人过去一天的投资总额
            investDetailService.countInvestStaticIncome();
        } catch (Exception ex) {
            log.error("countInvestStaticIncome throw exception", ex);
        }
    }
}
