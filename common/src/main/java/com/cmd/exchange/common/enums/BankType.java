package com.cmd.exchange.common.enums;

public enum BankType implements ValueEnum {
    BANK(0), //银行卡
    ALIPAY(1), //支付宝
    WEIXIN(2);  //微信

    private int value;

    BankType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BankType fromValue(int value) {
        switch (value) {
            case 0:
                return BANK;
            case 1:
                return ALIPAY;
            case 2:
                return WEIXIN;
            default:
                throw new RuntimeException("invalid value:" + value);
        }
    }
}