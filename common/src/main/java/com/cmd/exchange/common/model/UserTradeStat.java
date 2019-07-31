package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("用户交易统计，主要用于统计盈亏")
public class UserTradeStat {
    private BigInteger id;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("交易币种名称")
    private String coinName;
    @ApiModelProperty("结算货币名称")
    private String settlementCurrency;
    @ApiModelProperty("用户所有买入单所买入的数字货币总量")
    private BigDecimal coinBuyTotal = BigDecimal.ZERO;
    @ApiModelProperty("用户所有买入单所花费的结算货币总量")
    private BigDecimal settlementBuyTotal = BigDecimal.ZERO;
    @ApiModelProperty("用户所有卖出单所卖出的数字货币总量")
    private BigDecimal coinSellTotal = BigDecimal.ZERO;
    @ApiModelProperty("用户所有卖出单所收入的结算货币总量")
    private BigDecimal settlementSellTotal = BigDecimal.ZERO;
    @ApiModelProperty("用户持币量，冗余字段（虚拟的，等于买入总量-卖出总量）（coin_buy_total-coin_sell_total）")
    private BigDecimal holdCoin = BigDecimal.ZERO;
    @ApiModelProperty("总成本/总花费的结算货币的数量")
    private BigDecimal settlementCostTotal = BigDecimal.ZERO;
    @ApiModelProperty("用户买入时收取的所有交易币种手续费总和")
    private BigDecimal coinFee = BigDecimal.ZERO;
    @ApiModelProperty("用户卖出是收取的所有结算货币手续费总和")
    private BigDecimal settlementFee = BigDecimal.ZERO;
    @ApiModelProperty("本统计第一个成交的交易日志的id")
    private BigInteger firstTradeLogId;
    @ApiModelProperty("本统计最后一个成交的交易日志的id")
    private BigInteger lastTradeLogId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("最后一次更新时间")
    private Date updateTime;

    // 以下字段不在数据库里面
    @ApiModelProperty("持仓价格")
    private BigDecimal holdCoinPrice = BigDecimal.ZERO;
    @ApiModelProperty("用户账号名")
    private String userName;
    @ApiModelProperty("总盈利")
    private String profit;
}
