package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradeStatVo implements Serializable {
    private static final long serialVersionUID = -1894806283303999761L;
    @ApiModelProperty(value = "交易货币")
    private String coinName;
    @ApiModelProperty(value = "结算货币")
    private String settlementCurrency;
    @ApiModelProperty(value = "统计开始时间（包含刚好是这个时间点的记录）")
    private int startTime;                  //
    @ApiModelProperty(value = "统计结束时间（不包含刚好是这个时间点的记录）")
    private int endTime;                    //
    @ApiModelProperty(value = "在统计开始前的收盘价，比如要统计最近24小时的数据，这个就是24小时前的收盘价")
    private BigDecimal priceBeforeStat;    //
    @ApiModelProperty(value = "最近一次成交价格")
    private BigDecimal latestPrice;        //
    @ApiModelProperty(value = "最近一次成交价格对应的人民币金额")
    private BigDecimal latestCnyPrice;
    private BigDecimal sumAmount;          //
    @ApiModelProperty(value = "总交易的结算货币数")
    private BigDecimal sumCurrency;        //
    @ApiModelProperty(value = "总交易的技术")
    private int dealTimes;                 //
    @ApiModelProperty(value = "最小成交价格")
    private BigDecimal minPrice;           //
    @ApiModelProperty(value = "最大成交价格")
    private BigDecimal maxPrice;           //
    @ApiModelProperty(value = " 统计时间内，第一单成交价格")
    private BigDecimal firstPrice;         //
    @ApiModelProperty(value = "统计时间内，最后一单成交价格")
    private BigDecimal lastPrice;          //
    @ApiModelProperty(value = "涨跌率，是最新价格跟统计前价格相比的")
    private double changeRate;
    @ApiModelProperty(value = "交易货币图标")
    private String coinUrl;
}
