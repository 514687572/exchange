package com.cmd.exchange.task.controller;

import com.cmd.exchange.task.thread.TransactionMatchMaking;
import com.cmd.exchange.task.timer.*;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 用来对任务进行调试的接口
 */
@RestController
@RequestMapping("task")
public class TaskController implements ApplicationContextAware {
    @Autowired
    private StatisticsTradeTask statisticsTradeTask;
    @Autowired
    private ShareOutBonusTask shareOutBonusTask;
    @Autowired
    private GatherEthTask gatherEthTask;
    @Autowired
    private GatherUsdtTask gatherUsdtTask;
    @Autowired
    private CancelTradeTask cancelTradeTask;
    @Autowired
    private TransactionMatchMaking transactionMatchMaking;

    private ApplicationContext applicationContext;

    @Autowired
    private SmartContractstTask smartContractstTask;
    @Autowired
    private InvestStaticIncomeTask investStaticIncomeTask;
    @Autowired
    private InvestDynamicIncomeTask investDynamicIncomeTask;

    private void checkInvoke() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userIp = request.getRemoteAddr();
        if (!userIp.equals("127.0.0.1") && !userIp.equals("0:0:0:0:0:0:0:1")) {
            throw new RuntimeException("Can only invoke in localhost:" + userIp);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /*@RequestMapping("mining")
    public String calYesterdayMiningIncome() {
        checkInvoke();
        shareOutBonusTask.calYesterdayCurHourMiningIncomeTimer();
        return "Success";
    }*/

    @RequestMapping("share-out-bonus")
    public String calYesterdayShareOutBonus() {
        checkInvoke();
        shareOutBonusTask.calShareOutBonus();
        return "Success";
    }
    @RequestMapping("smart-contractst-timer")
    public String smartContractstTimer() {
        checkInvoke();
        smartContractstTask.smartContractstTimer();
        return "Success";
    }

    @RequestMapping("invest-static-income-timer")
    public String investStaticIncomeTimer() {
        checkInvoke();
        investStaticIncomeTask.investStaticIncomeTimer();
        return "Success";
    }
    @RequestMapping("invest-dynamic-income-timer")
    public String investDynamicIncomeTimer() {
        checkInvoke();
        investDynamicIncomeTask.investDynamicIncomeTimer();
        return "Success";
    }
    /*@RequestMapping("award")
    public String award() {
        checkInvoke();
        shareOutBonusTask.awardPrizesTimer();
        return "Success";
    }*/

    @RequestMapping("gather-eth")
    public String gatherEth() {
        checkInvoke();
        gatherEthTask.gatherEthTimer();
        return "Success";
    }

    @RequestMapping("gather-usdt")
    public String gatherUsdt() {
        checkInvoke();
//        gatherUsdtTask.gatherUsdtTimer();
        gatherUsdtTask.gatherUsdtThread();
        return "Success";
    }

    @RequestMapping("cancel-trade")
    public String cancelTrade() {
        checkInvoke();
        cancelTradeTask.cancelExpiredTradesTask();
        return "Success";
    }

    @RequestMapping("dump-trade-mem")
    public String dumpTradeMatchMemory() {
        checkInvoke();
        return transactionMatchMaking.dumpMemory();
    }

    @RequestMapping("invoke")
    public String invoke(@ApiParam("交易类型") @RequestParam(required = true) String className,
                         @ApiParam("交易类型") @RequestParam(required = true) String methodName) throws Exception {
        checkInvoke();
        Class theClass = Class.forName(className);
        Object obj = this.applicationContext.getBean(theClass);
        Method method = theClass.getMethod(methodName);
        Class retType = method.getReturnType();
        if (retType == String.class) {
            return (String) method.invoke(obj);
        }
        if (retType == void.class) {
            method.invoke(obj);
            return "success";
        }
        return ReflectionToStringBuilder.toString(method.invoke(obj));
    }

}
