package com.cmd.exchange.common.enums;

import lombok.Getter;

@Getter
public enum ApplicationOrderStatus {
    ALL(0, "所有"),
    MATCHING(2, "匹配中"),
    MATCHED(3, "匹配成功, 等待接单"),
    ACCEPTED(4, "已经接单"),
    PAID(5, "已经付款"),
    COMPLAINT(6, "申诉中"),
    FREEZE(7, "冻结"),
    CANCELED(100, "已经取消"),
    DONE(1, "交易成功");

    private int code;
    private String value;

    ApplicationOrderStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
