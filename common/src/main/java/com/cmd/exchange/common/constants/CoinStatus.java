package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.enums.ValueEnum;

public enum CoinStatus implements ValueEnum {
    NORMAL(0),   // 启用
    DISABLE(1);  // 禁用

    int value;

    CoinStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
