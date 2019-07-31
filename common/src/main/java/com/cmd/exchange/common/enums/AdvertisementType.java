package com.cmd.exchange.common.enums;

public enum AdvertisementType implements ValueEnum {
    LINK(0),
    TEXT(1);

    private int value;

    AdvertisementType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
