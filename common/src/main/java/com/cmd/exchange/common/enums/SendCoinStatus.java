package com.cmd.exchange.common.enums;

public enum SendCoinStatus implements ValueEnum {
    APPLYING(0), //正在申请
    PASSED(1), //审核通过 -- 不需要审核的转出直接就是通过
    FAILED(2), //审核失败
    CONFIRM(3), //节点确认
    ALL(10); //所有状态 -- 查询使用

    private int value;

    SendCoinStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
