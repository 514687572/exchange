package com.cmd.exchange.external.utils;

import com.cmd.exchange.common.utils.IPUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {
    private static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    public static String getRealIp() {
        return IPUtils.getIpAddr(getRequestAttributes().getRequest());
    }

}
