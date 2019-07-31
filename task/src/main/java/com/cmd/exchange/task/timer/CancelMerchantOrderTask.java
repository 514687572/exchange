package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.model.Trade;
import com.cmd.exchange.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 对过期的交易进行取消
 */
@Component
public class CancelMerchantOrderTask {
    private static Log log = LogFactory.getLog(CancelMerchantOrderTask.class);
    @Autowired
    private UserMerchantOrderService userMerchantOrderService;
    @Autowired
    private SuperMerchantOrderService superMerchantOrderService;

    /**
     * 每2分钟检查一次
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void cancelExpiredTradesTask() {
        try {
            userMerchantOrderService.cancelTimeoutOrders();
            superMerchantOrderService.cancelTimeoutOrders();
        } catch (Exception ex) {
            log.error("cancelExpiredTrades throw exception", ex);
        }
    }

}
