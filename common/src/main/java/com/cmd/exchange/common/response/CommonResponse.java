package com.cmd.exchange.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@Accessors(chain = true)
public class CommonResponse<T> {
    protected int statusCode; //0为正常，其他为错误
    protected String errorMessage;// 错误描述，当statusCode不是0时不为空
    protected T content; //业务相关内容
    protected Long timestamp; //服务器时间, unix 时间戳


    public CommonResponse(int statusCode) {
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis() / 1000l;
    }

    public CommonResponse(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.timestamp = System.currentTimeMillis() / 1000l;
    }

    public CommonResponse(T content) {
        this.statusCode = 0;
        this.content = content;
        this.timestamp = System.currentTimeMillis() / 1000l;
    }

    public CommonResponse() {
        this.timestamp = System.currentTimeMillis() / 1000l;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
