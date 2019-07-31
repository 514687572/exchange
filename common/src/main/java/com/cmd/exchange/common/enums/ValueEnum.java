package com.cmd.exchange.common.enums;

public interface ValueEnum {
    int getValue();

    static ValueEnum valueOfEnum(ValueEnum[] values, int value) {
        //ValueEnum[] values = (ValueEnum[])type.getEnumConstants();
        for (ValueEnum e : values) {
            if (e.getValue() == value) {
                return e;
            }
        }
        throw new RuntimeException("do not find value:" + value + " of enum:" + values);
    }
}
