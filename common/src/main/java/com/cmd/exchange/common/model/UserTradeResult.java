package com.cmd.exchange.common.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("用户交易结果")
public class UserTradeResult implements Serializable {
    @ApiModelProperty("排行")
    private int ranking;
    @ApiModelProperty("用户ID")
    private int userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("真实名")
    private String realName;
    @ApiModelProperty("总交易量")
    private BigDecimal totalUsdt;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("总交易量")
    private BigDecimal totalFee;
}
