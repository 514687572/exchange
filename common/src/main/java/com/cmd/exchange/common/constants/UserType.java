package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.enums.ValueEnum;

/**
 * 用户类型
 */
public enum UserType implements ValueEnum {
    TRADE_FEE_ORDINARY_USER_RATE(0),
    TRADE_FEE_SUPER_USER_RATE(1),
    TRADE_FEE_SENIOR_USER_RATE(2),
    TRADE_FEE_COMMUNITY_USER_RATE(3);

    int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
