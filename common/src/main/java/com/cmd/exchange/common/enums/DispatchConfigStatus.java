package com.cmd.exchange.common.enums;

public enum DispatchConfigStatus implements ValueEnum {
    SHOW(1),
    HIDE(0);

    private int value;

    DispatchConfigStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
