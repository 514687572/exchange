package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.model.SendCoin;
import com.cmd.exchange.service.SendCoinService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * C2C对过期的交易进行取消
 */
@Component
public class TransferTask {
    private static Log log = LogFactory.getLog(TransferTask.class);

    @Autowired
    private SendCoinService sendCoinService;

    /**
     * 对转账区块确认数确认,2分钟
     */
    //@Scheduled(fixedDelay=60000)
    public void nodeConfirmTransferTask() {
        log.info("nodeConfirmTransferTask start");
        try {
            int id = 0;
            do {
                List<SendCoin> list = sendCoinService.getTransferUnconfirm(id);
                if (list == null || list.size() == 0)
                    break;

                for (SendCoin sendCoin : list) {
                    id = sendCoin.getId();
                    sendCoinService.nodeConfirm(sendCoin.getId());
                    log.info(sendCoin.toString());
                }
            } while (true);
        } catch (Exception e) {
            log.error("nodeConfirmTransferTask throw exception", e);
        }
    }
}
