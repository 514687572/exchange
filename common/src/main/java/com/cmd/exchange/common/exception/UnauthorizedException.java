package com.cmd.exchange.common.exception;

/**
 * 未授权异常
 * Created by jerry on 2017/12/28.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("用户登录状态已过期，请重新登录");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
