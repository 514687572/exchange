package com.cmd.exchange.common.annotation;

/**
 * 用来注明哪些接口是不需要认证就可以访问的
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @interface Auth {
    /**
     * 是否需要认证通过后才能访问
     *
     * @return 是否需要认证
     */
    boolean requireLogin() default true;
}
