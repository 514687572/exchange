package com.cmd.exchange.common.enums;

/**
 * 0：不释放，1买释放，2卖释放，3买卖都释放
 */
public enum ReleasePolicyEnum implements ValueEnum {
    BUSHIFANG(0),
    BUYSHIFANG(1),
    SELSHIFANG(2),
    ALLSHIFANG(3);

    private int value;

    ReleasePolicyEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
