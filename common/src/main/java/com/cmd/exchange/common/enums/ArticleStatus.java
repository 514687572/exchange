package com.cmd.exchange.common.enums;

public enum ArticleStatus implements ValueEnum {
    SHOW(1),
    HIDE(2);

    private int value;

    ArticleStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
