package com.cmd.exchange.common.enums;

public enum ArticleType implements ValueEnum {
    NOTICE(1),
    NEWS(2), //资讯
    HELP(3),
    AGREEMENT(4), //注册协议
    FEE(5), //费用说明
    SERVICE(6), //服务条款
    PRIVACY(7), //隐私声明
    JOIN_US(8), //加入我们
    PLATFORM(9), //平台说明
    MERCHANT(10), //商家认证公告
    ALL(-1); //所有类型

    private int value;

    ArticleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
