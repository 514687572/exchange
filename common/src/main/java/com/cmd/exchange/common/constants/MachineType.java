package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.enums.ValueEnum;

/**
 * 矿机类型
 */
public enum MachineType implements ValueEnum {

    REGISTRATION_RECOMMENDATION(1);

    int value;

    MachineType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
