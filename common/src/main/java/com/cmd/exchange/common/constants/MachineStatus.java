package com.cmd.exchange.common.constants;

import com.cmd.exchange.common.enums.ValueEnum;

/**
 * 矿机状态
 */
public enum MachineStatus implements ValueEnum {
    NORMAL(2),   // 启用
    DISABLE(1);  // 禁用

    private int value;

    MachineStatus(int value) {
        this.value = value;
    }
    @Override
    public int getValue() {
        return value;
    }

    public static MachineStatus fromInt(int value) {

        for (MachineStatus s : MachineStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }

        throw new RuntimeException("Unknown TradeStatus: {" + value + "}");
    }


}
