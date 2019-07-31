package com.cmd.exchange.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;

/**
 * 多语言处理，主要是获取多语言的配置信息
 */
public class Language {
    private static Log log = LogFactory.getLog(Language.class);
    // 语言名称-》语言配置，语言名称例如：zh-cn,zh-tw,en-us
    private static Hashtable<String, Properties> languages = new Hashtable<String, Properties>();

    // 默认语言配置
    private static Properties defaultLanguage = new Properties();

    static {
        try {
            InputStream resource = Language.class.getClassLoader().getResourceAsStream("lang/language.properties");
            defaultLanguage.load(new InputStreamReader(resource, "UTF-8"));
        } catch (Exception ex) {
            log.error("load default language failed", ex);
        }
    }

    /**
     * 获取语言描述
     *
     * @param key      文字名称标识
     * @param language 语言
     * @return 文字描述
     */
    public static String get(String key, String language) {
        Properties pro = languages.get(language);
        if (pro == null) {
            pro = loadLanguage(language);
        }
        String value = pro.getProperty(key);
        if (value == null) {
            return defaultLanguage.getProperty(key);
        }
        return value;
    }

    private static synchronized Properties loadLanguage(String language) {
        Properties pro = languages.get(language);
        if (pro != null) {
            // 并发的情况下可能会出现这种情况
            return pro;
        }
        pro = new Properties();
        try {
            InputStream resource = Language.class.getClassLoader().getResourceAsStream("lang/language_" + language + ".properties");
            pro.load(new InputStreamReader(resource, "UTF-8"));
        } catch (Exception ex) {
            log.error("load language failed:" + language, ex);
        }
        languages.put(language, pro);
        return pro;
    }
}
