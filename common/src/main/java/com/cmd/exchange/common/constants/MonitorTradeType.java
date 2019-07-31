package com.cmd.exchange.common.constants;

/**
 * 交易中次数的监控定义的类型
 */
public class MonitorTradeType {

    public static final String HIGH_HZ_TIME_MONITORING_TRADE_TYPE = "TIME_MONITORING_TRADE";//挂单监控（“这个位置只是针对挂单”)

    public static final String HIGH_HZ_TIME_MONITORING_H_F_TRADE = "TIME_MONITORING_H_F_TRADE";//高频交易

    public static final String HIGH_HZ_TIME_MONITORING_COIN = "TIME_MONITORING_COIN";//持币监控

    public static final String HIGH_HZ_TIME_MONITORING_ONLINE = "TIME_MONITORING_ONLINE";//在线人数监控

    public static final String HIGH_HZ_TIME_MONITORING_SYSTEM = "TIME_MONITORING_SYSTEM";//系统监控

}
