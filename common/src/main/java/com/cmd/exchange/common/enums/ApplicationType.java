package com.cmd.exchange.common.enums;

import lombok.Getter;

@Getter
public enum ApplicationType {
    ALL(0, "所有"),
    BUY(1, "买"),
    SELL(2, "卖");


    int type;
    String value;

    ApplicationType(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
