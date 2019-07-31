package com.cmd.exchange.common.enums;

/**
 * Created by linmingren on 2017/8/31.
 */
public enum TradeStatus implements ValueEnum {
    OPEN(0), //订单尚未成交，或者部分成交
    DEAL(1), //订单已经全部成交
    CANCELED(2), //订单已经被取消
    ALL(3), //所有类型，查询用
    EXCEPTION(100); // 订单异常，无法完成交易

    int value;

    TradeStatus(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public static TradeStatus fromInt(int value) {

        for (TradeStatus s : TradeStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }

        throw new RuntimeException("Unknown TradeStatus: {" + value + "}");
    }
}
