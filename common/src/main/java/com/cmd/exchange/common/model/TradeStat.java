package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradeStat {
    private int id;

    /**
     * 统计时间
     */
    private int statTime;

    /**
     * 需要买入或卖出的币种，作为货物
     */
    private String coinName;

    /**
     * 用于结算的货币，一般是平台的基本货币
     */
    private String settlementCurrency;

    /**
     * 统计周期，分钟为单位，有1,5,15,30,60,1440
     */
    private int statCycle;

    /**
     * 下单次数
     */
    private Integer orderTimes;

    /**
     * 下单总额
     */
    private BigDecimal orderAmount;

    /**
     * 下订单总结算货币
     */
    private BigDecimal orderTotalCurrency;

    /**
     * 下单总用户数
     */
    private Integer orderUserNum;

    /**
     * 成交总次数
     */
    private Integer dealTimes;

    /**
     * 成交总额
     */
    private BigDecimal dealAmount;

    /**
     * 成交总结算货币金额
     */
    private BigDecimal dealTotalCurrency;

    /**
     * 产生的数字货币费用总额
     */
    private BigDecimal feeAmount;

    /**
     * 产生的结算货币费用总额
     */
    private BigDecimal feeTotalCurrency;


    /**
     * 成交的最小价格
     */
    private BigDecimal dealMinPrice;

    /**
     * 成交的最大价格
     */
    private BigDecimal dealMaxPrice;

    /**
     * 第一单价格，就是开盘价格
     */
    private BigDecimal dealFirstPrice;

    /**
     * 最后一单价格，就是收盘价格
     */
    private BigDecimal dealLastPrice;

}