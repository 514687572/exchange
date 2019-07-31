package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.model.Trade;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.ServerTaskService;
import com.cmd.exchange.service.TradeService;
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
public class CancelTradeTask {
    private static Log log = LogFactory.getLog(CancelTradeTask.class);
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TradeService tradeService;
    // 默认没有配置的时候交易的有效期，默认3天没有交易完成将会被取消
    private static final int tradeValidTime = 3600 * 24 * 3;
    // 每次最多取消的交易个数，暂时定为100个，避免时间太长
    private static final int maxCancelPerTime = 100;

    /**
     * 每小时取消一次过期交易
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void cancelExpiredTradesTask() {
        try {
            int count;
            do {
                count = cancelExpiredTrades();
            } while (count >= maxCancelPerTime);
        } catch (Exception ex) {
            log.error("cancelExpiredTrades throw exception", ex);
        }
    }

    @Transactional
    public int cancelExpiredTrades() {
        try {
            serverTaskService.lockAndBeginTask(ConfigKey.LAST_CANCEL_TRADE_TASK, 1800);
        } catch (Exception ex) {
            log.info("lockAndBeginTask failed," + ex.toString());
        }
        int validTime = configService.getConfigValue(ConfigKey.TRADE_VALID_TIME, tradeValidTime);
        int timeInvalid = (int) (System.currentTimeMillis() / 1000) - validTime;
        log.info("begin cancel expired trades, invalid time=" + timeInvalid);
        // 获取超时的未完成的交易
        List<Integer> tradeIds = tradeService.getExpiredTradeIds(timeInvalid, maxCancelPerTime);
        if (tradeIds == null) {
            log.info("end cancel expired trades, cancel count=0");
            return 0;
        }
        for (Integer id : tradeIds) {
            try {
                Trade trade = tradeService.getTradeById(id);
                tradeService.cancelTrade(trade.getUserId(), id, UserBillReason.TRADE_BG_CANCEL);
            } catch (Exception ex) {
                log.error("cancelTrade failed,trade id=" + id, ex);
            }
        }

        log.info("end cancel expired trades, cancel count=" + tradeIds.size());
        return tradeIds.size();
    }
}
