package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.enums.ValueEnum;

/**
 * Created by Administrator on 2017/8/31.
 */
public enum TradeType implements ValueEnum {
    BUY(1), //买单
    SELL(2), //卖单
    ALL(100);

    int value;

    TradeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
