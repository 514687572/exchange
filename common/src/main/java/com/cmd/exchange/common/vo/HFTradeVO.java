package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HFTradeVO {
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "买入或者卖出的币种名称")
    private String coinName;
    @ApiModelProperty(value = "买入或者卖出的币种交换的币种名称")
    private String settlementCurrency;
    @ApiModelProperty(value = "买入或者卖出")
    private Integer type;
    @ApiModelProperty(value = "交易次数")
    private Integer tradeNum;
    @ApiModelProperty(value = "最近刷新时间")
    private Date refreashTime;

}
