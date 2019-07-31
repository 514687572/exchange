package com.cmd.exchange.common.enums;

public enum MerchantOrderStatus implements ValueEnum {
    COMPLETE(1),         // 已经完成
    WAIT_ACCEPT(2),     // 待接单，这个是初始下单状态
    WAIT_PAY(3),         // 待付款，已经接单状态
    PAYED(4),            // 已经付款，待收款状态
    COMPLAIN(5),         // 申诉状态
    CANCEL(100);         // 取消状态

    private int value;

    MerchantOrderStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
