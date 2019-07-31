package com.cmd.exchange.external.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/10/11.
 */
public class SignUtil {
    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * @param algorithm "md5" 或者 "sha-256"
     * @param hashKey
     * @param params
     * @return
     */
    public static String getSignString(String algorithm, String hashKey, Map<String, String> params) {
        if (algorithm == null) {
            algorithm = "MD5";
        }

        if (!algorithm.toLowerCase().equals("md5") && !algorithm.toLowerCase().equals("sha-256")) {
            return null;
        }

        StringBuilder sbd = new StringBuilder();

        List<String> list = Lists.newArrayList();
        list.addAll(params.keySet());
        Collections.sort(list);
        int i = 0;
        for (String key : list) {
            Object value = params.get(key);
            if (value != null && value.toString().trim().length() > 0) {
                if (i > 0) sbd.append("&");
                sbd.append(key).append("=").append(value.toString());
                i++;
            }
        }

        logger.debug("验签字段排序后({})", sbd.toString());

        String returnMsg = null;
        if (algorithm.equals("md5")) {
            returnMsg = Encrypt.toMd5(sbd.append("&secret=" + hashKey).toString()).toUpperCase();
        } else {
            returnMsg = Encrypt.toSha256(sbd.append("&secret=" + hashKey).toString()).toUpperCase();
        }

        logger.debug("在上面的值最后拼接商户 key({})并 md5 且大写的值是({})\n", hashKey, returnMsg);

        return returnMsg;
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String sign = Encrypt.toMd5("appid=wp_app_test&body=OKOK&mch_id=wptest&nonce_str=4816&notify_url=www.baidu.com&out_trade_no=pay2017102072396580&sign_type=MD5&spbill_create_ip=236.128.86.33&total_fee=12&trade_type=native&key=test").toUpperCase();
        System.out.println(sign);
    }
}
