package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class MarketStatsVo {
    @ApiModelProperty("当前时间")
    private Date time;
    private String coinName;
    private String settlementCurrency;
    @ApiModelProperty("24小时涨跌幅， 0.1代表涨10%， -0.1代表跌10%")
    private BigDecimal lastPrice;
    @ApiModelProperty("24小时涨跌幅， 0.1代表涨10%， -0.1代表跌10%")
    private BigDecimal changePrice;
    @ApiModelProperty("24小时内最高价格")
    private BigDecimal highPrice;
    @ApiModelProperty("24小时最低价格")
    private BigDecimal lowPrice;
    @ApiModelProperty("24小时成交量")
    private BigDecimal volume;
    @ApiModelProperty("24小时成交额")
    private BigDecimal amount;
}
