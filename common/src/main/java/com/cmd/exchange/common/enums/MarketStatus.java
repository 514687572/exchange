package com.cmd.exchange.common.enums;

public enum MarketStatus implements ValueEnum {
    SHOW(0),
    HIDE(1);

    private int value;

    MarketStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
