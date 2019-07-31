package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.enums.ApplicationOrderStatus;
import com.cmd.exchange.common.model.OtcOrder;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.OtcService;
import com.cmd.exchange.service.ServerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * C2C对过期的交易进行取消
 */
@Component
public class CloudTradeTask {
    private static Log log = LogFactory.getLog(CloudTradeTask.class);
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private OtcService otcService;

    /**
     * 超时取消订单
     */
    @Scheduled(fixedDelay = 60000)
    public void cancelOrderExpiredTask() {
        log.info("cancelOrderExpiredTask start");
        try {
            boolean bFind = false;
            do {
                List<OtcOrder> list = otcService.getApplicationOrderExpire("", ApplicationOrderStatus.ACCEPTED.getCode());
                bFind = (list != null && list.size() != 0) ? true : false;
                if (bFind) {
                    for (OtcOrder order : list) {
                        otcService.cancelOrder(order.getDepositUserId(), order.getId(), "取消订单(系统超时)");
                    }
                }
            } while (bFind);
        } catch (Exception e) {
            log.error("cancelOrderExpiredTask throw exception", e);
        }
    }

    /**
     * 超时冻结订单
     */
    @Scheduled(fixedDelay = 70000)
    public void frozenOrderExpiredTask() {
        log.info("frozenOrderExpiredTask start");
        try {
            boolean bFind = false;
            do {
                List<OtcOrder> list = otcService.getApplicationOrderExpire("", ApplicationOrderStatus.PAID.getCode());
                bFind = (list != null && list.size() != 0) ? true : false;
                if (bFind) {
                    for (OtcOrder order : list) {
                        otcService.frozenOrder(order, "冻结申诉订单(系统超时)");
                    }
                }
            } while (bFind);
        } catch (Exception e) {
            log.error("frozenOrderExpiredTask throw exception", e);
        }
    }

}
