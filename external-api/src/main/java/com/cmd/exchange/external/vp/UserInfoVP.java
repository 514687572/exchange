package com.cmd.exchange.external.vp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserInfoVP {
    @ApiModelProperty(value = "用户账号")
    private String userName;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;
}
