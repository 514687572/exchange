package com.cmd.exchange.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("用户锁仓信息")
public class CoinSavingsVo {
    private Integer userId;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("用户存储/锁仓的资金")
    private BigDecimal userBalance;
    @ApiModelProperty("总存储/锁仓的资金")
    private BigDecimal totalBalance;
    @ApiModelProperty("用户难度系数（每个小时可挖量）")
    private BigDecimal maxCoinPerHour;
}
