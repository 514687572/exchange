package com.cmd.exchange.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 * Created by jerry on 2017/12/21.
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
