package com.cmd.exchange.common.constants;

/**
 * 定义资金变化的子类型
 */
public class UserBillType {
    public static final int SUB_TYPE_AVAILABLE = 0;    // 可用基金变化
    public static final int SUB_TYPE_FREEZE = 1;    //  冻结基金变化
    public static final int SUB_TYPE_RECEIVED_FREEZE = 2;    //  转入冻结基金变化
}
