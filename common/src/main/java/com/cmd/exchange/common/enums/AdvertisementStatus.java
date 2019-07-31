package com.cmd.exchange.common.enums;

public enum AdvertisementStatus implements ValueEnum {
    SHOW(0),
    HIDE(1);

    private int value;

    AdvertisementStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
