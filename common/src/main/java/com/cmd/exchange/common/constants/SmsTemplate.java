package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class SmsTemplate {
    public static String loginIpChangeSMS(String mobile) {
        String msg = String.format("****%s的手机用户，您在%s登录了ETEX，若非您本人操作，请及时修改密码。",
                mobile.substring(mobile.length() - 4, mobile.length()), DateUtil.getDateTimeString(new Date()));
        return msg;
    }

    public static String transferCheckPass(double amount, String coinName) {
        String msg = String.format("【ETEX】尊敬的用户：您提现的%f %s已经汇出，请及时登录钱包查看。", amount, coinName);
        return msg;
    }

    public static String receivedCoinMsg(double amount, String coinName, String format) {
        String msg;
        if (format == null) {
            msg = String.format("【ETEX】尊敬的用户：您充值的 %f %s 已经到账，可以进行交易。", amount, coinName);
        } else {
            msg = String.format(format, amount, coinName);
        }
        return msg;
    }
}
