package com.cmd.exchange.admin.excel;

public class ClassCasrUtil {

    public static <T> T get(Class<T> clz, Object o) {
        if (clz.isInstance(o)) {
            return clz.cast(o);
        }
        return null;
    }
}
