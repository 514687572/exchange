package com.cmd.exchange.task;

import com.cmd.exchange.common.model.User;
import com.cmd.exchange.service.InvestDetailService;
import com.cmd.exchange.service.TradeStatService;
import com.cmd.exchange.service.TransferService;
import com.cmd.exchange.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.cmd.exchange.common.mapper")
@ComponentScan(value = {"com.cmd.exchange.service", "com.cmd.exchange.common", "com.cmd.exchange"})
public class TaskApplicationTests {
    private static Log log = LogFactory.getLog(TaskApplicationTests.class);
    @Autowired
    UserService userService;
    @Autowired
    TransferService transferService;
    @Autowired
    private InvestDetailService investDetailService;
    @Autowired
    private TradeStatService tradeStatService;

    /*@Test
    public void contextLoads() {
       *//* //User user =userService.getUserByUserId(1);
        //    System.out.println(user.getUserName());
        boolean b = transferService.changeUserReceivedFreezeCoinRule(100, "UKS", new BigDecimal(100.00000000000), new BigDecimal(0.5), "test", "");
        System.out.println(b);*//*
    }*/

    @Test
    public void myTest() {
        try {
            //查询过去一天的所有投资记录，按userId分组，计算出每个人过去一天的投资总额
//            investDetailService.countInvestStaticIncome();
//            investDetailService.countInvestDynamicIncome();
//            log.info("=======汇率====="+investDetailService.getLatestPrice());
            log.info("=======汇率====="+tradeStatService.getLast24HourTradeStat("eth", "usdt").getLatestPrice());
        } catch (Exception ex) {
            log.error("myTest throw exception", ex);
        }
    }

}