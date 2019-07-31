package com.cmd.exchange.common.constants;

public class CancelOrderType {
    public static final String MerchantRefuse = "NotAcc";   // 管理员没有接单
    public static final String UserCancel = "UserCc";   // 用户自己取消，在管理员接单前
    public static final String UserNotPay = "UsrNoPay"; // 用户自己取消，用户拒绝不付款，所以取消
    public static final String MerchantNotPay = "MerNoPay"; //  兑换商接单后取消，因为兑换商拒绝不付款，所以取消
    public static final String ManagerCancel = "MgrCc";   // 管理员取消
    public static final String TaskCancel = "TaskCc";   // 后台超时取消
}
