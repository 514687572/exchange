package com.cmd.exchange.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Optional;

public class RequestUtil {
    private static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    public static Locale getLanguage() {
        boolean present = Optional.ofNullable(getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .isPresent();
        if (!present)
            return Locale.SIMPLIFIED_CHINESE;

        String languages = getRequestAttributes().getRequest().getHeader("Accept-Language");
        if (StringUtils.isBlank(languages)) {
            return Locale.SIMPLIFIED_CHINESE;
        }

        //zh-CN,zh;q=0.9, 只处理第一个语言, zh-CN 和zh_CN都可以
        String language = languages.split(",")[0].replace("-", "_");
        String[] localeString = language.split("_");
        if (localeString.length < 2) {
            return Locale.SIMPLIFIED_CHINESE;
        }

        Locale locale = new Locale(localeString[0], localeString[1]);

        return locale;
    }

    public static String getRealIp() {
        return IPUtils.getIpAddr(getRequestAttributes().getRequest());
    }

}
