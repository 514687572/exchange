package com.cmd.exchange.common.enums;

/**
 * Created by Administrator on 2017/8/31.
 */
public enum MerchantOrderType implements ValueEnum {
    BUY(1), //买入
    SELL(2), //卖出
    ALL(100);

    private int value;

    MerchantOrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
