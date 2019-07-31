package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.SmsTemplate;
import com.cmd.exchange.common.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;

public class CoinServiceBase {
    private static Log log = LogFactory.getLog(CoinServiceBase.class);

    void sendSms(ConfigService configService, UserService userService, SmsService smsService, BigDecimal amount, String coin, Integer userId) {
        try {
            int send = configService.getConfigValue(ConfigKey.SEND_MSG_ON_RECV_COIN, 1);
            if (send != 1) {
                return;
            }
            User user = userService.getUserByUserId(userId);
            if (user != null) {
                String msg = SmsTemplate.receivedCoinMsg(amount.doubleValue(), coin, configService.getConfigValue(ConfigKey.SEND_MSG_ON_RECV_COIN_FORMAT));
                smsService.sendSms(user.getAreaCode(), msg, user.getMobile());
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }
}
