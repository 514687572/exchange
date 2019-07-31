package com.cmd.exchange.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户的操作
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
    /**
     * @return 操作类型
     */
    String type();

    /**
     * @return 备注信息
     */
    String comment() default "";
}
