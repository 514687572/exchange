package com.cmd.exchange.common.enums;

import lombok.Getter;

@Getter
public enum AppealRole {
    DEFAULT(0, "缺省"),
    BUY(1, "买家"),
    SELL(2, "卖家");
    private int code;
    private String value;

    AppealRole(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
