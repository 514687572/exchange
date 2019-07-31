package com.cmd.exchange.common.exception;


import lombok.Getter;
import lombok.Setter;

/**
 * 服务器异常
 * Created by jerry on 2017/12/21.
 */
@Getter
@Setter
public class ServerException extends RuntimeException {

    int code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
