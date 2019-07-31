package com.cmd.exchange.common.enums;

public enum ProjectIntroductionStatus implements ValueEnum {
    OPEN(1), //开启
    CLOSE(2); //关闭

    private int value;

    ProjectIntroductionStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
