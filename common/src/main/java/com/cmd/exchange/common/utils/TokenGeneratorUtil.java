//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cmd.exchange.common.utils;

import java.security.MessageDigest;
import java.util.UUID;

public class TokenGeneratorUtil {
    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public TokenGeneratorUtil() {
    }

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        } else {
            StringBuilder r = new StringBuilder(data.length * 2);
            byte[] var2 = data;
            int var3 = data.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                byte b = var2[var4];
                r.append(hexCode[b >> 4 & 15]);
                r.append(hexCode[b & 15]);
            }

            return r.toString();
        }
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception var3) {
            throw new RuntimeException("生成Token失败", var3);
        }
    }
}
