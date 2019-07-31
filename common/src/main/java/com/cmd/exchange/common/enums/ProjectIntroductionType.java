package com.cmd.exchange.common.enums;

public enum ProjectIntroductionType implements ValueEnum {
    INVEST(1), //投资收益
    INTEGRAL(2), //积分收益
    UNIT(3); //起投/USDT

    private int value;

    ProjectIntroductionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
