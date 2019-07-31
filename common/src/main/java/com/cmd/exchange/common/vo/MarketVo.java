package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MarketVo {
    @ApiModelProperty(value = "订单id", required = true)
    private Long id;
    @ApiModelProperty(value = "虚拟币名称", required = true)
    private String coinName;
    @ApiModelProperty(value = "用于结算的货币", required = true)
    private String settlementCurrency;
    @ApiModelProperty("虚拟币图标路径")
    private String coinIcon;

    @ApiModelProperty("最后成交价")
    private BigDecimal lastPrice;

    @ApiModelProperty("昨天收盘价")
    private BigDecimal lastDayPrice;
    @ApiModelProperty("昨天平均价格")
    private BigDecimal lastDayAvgPrice;
    @ApiModelProperty("最高价")
    private BigDecimal highPrice;
    @ApiModelProperty("最低价")
    private BigDecimal lowPrice;
    @ApiModelProperty("买一手价格")
    private BigDecimal buyPrice;
    @ApiModelProperty("卖一手价格")
    private BigDecimal sellPrice;
    @ApiModelProperty("涨跌金额")
    private BigDecimal change;
    @ApiModelProperty("涨跌率")
    private BigDecimal changeRate;
    @ApiModelProperty("数据货币的总成交数量")
    private BigDecimal amount;
    @ApiModelProperty("总成交的结算金额")
    private BigDecimal totalCurrency;
}
