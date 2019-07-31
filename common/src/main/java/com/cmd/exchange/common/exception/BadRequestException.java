package com.cmd.exchange.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求错误异常
 * Created by jerry on 2017/12/21.
 */
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    int code;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
