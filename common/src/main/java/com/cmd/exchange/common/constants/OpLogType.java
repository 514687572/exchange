package com.cmd.exchange.common.constants;

/**
 * 定义所有日志操作类型
 * 类型不能超过12个字节
 */
public class OpLogType {
    // 用户注册
    public static final String OP_USER_RIGISTER = "UserReg";
    // 用户登录
    public static final String OP_USER_LOGIN = "UserLogin";
    public static final String OP_USER_LOGOUT = "UserLogout";
    // 用户设置支付密码
    public static final String OP_USER_SET_PAY_PWD = "SetPayPwd";
    // 用户修改支付密码
    public static final String OP_USER_EDIT_PAY_PWD = "EditPayPwd";
    public static final String OP_TRADE_CREATE = "TradeAdd";
    public static final String OP_TRADE_CANCEL = "TradeCancel";
    public static final String OP_FINANCE_TRANSFER_OUT = "TransferOut";

    // 将eth兑换成bon
    public static final String OP_ETH_TO_BON = "EthToBon";
    public static final String OP_USDT_TO_BON = "UsdtToBon";
}
