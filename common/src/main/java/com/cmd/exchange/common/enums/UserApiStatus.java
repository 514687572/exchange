package com.cmd.exchange.common.enums;

public enum UserApiStatus implements ValueEnum {
    DISABLED(0),
    PASS(1), //审核通过
    DENY(2), //审核不通过
    PENDING(3); //审核中

    private int value;

    UserApiStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
