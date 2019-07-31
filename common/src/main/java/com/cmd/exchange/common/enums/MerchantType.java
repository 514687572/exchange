package com.cmd.exchange.common.enums;

public enum MerchantType implements ValueEnum {
    NORMAL(1),    // 普通兑换商
    SUPER(2);     // 超级兑换商

    private int value;

    MerchantType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
