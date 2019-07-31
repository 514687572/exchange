package com.cmd.exchange.common.utils;

import java.util.Random;

/**
 * common-codec B64 Copy
 * Created by jerry on 2017/12/29.
 */
public class B64Copy {

    public static String getRandomSaltForSha256() {
        return "$5$" + getRandomSalt(16);
    }

    public static String getRandomSaltForSha512() {
        return "$6$" + getRandomSalt(128);
    }

    /***************************************************** copy start ***********************************************************/

    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    B64Copy() {
    }

    static void b64from24bit(byte b2, byte b1, byte b0, int outLen, StringBuilder buffer) {
        int w = b2 << 16 & 16777215 | b1 << 8 & '\uffff' | b0 & 255;

        for (int var6 = outLen; var6-- > 0; w >>= 6) {
            buffer.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(w & 63));
        }

    }

    static String getRandomSalt(int num) {
        StringBuilder saltString = new StringBuilder();

        for (int i = 1; i <= num; ++i) {
            saltString.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt((new Random()).nextInt("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
        }

        return saltString.toString();
    }

    /***************************************************** copy end ***********************************************************/
}
