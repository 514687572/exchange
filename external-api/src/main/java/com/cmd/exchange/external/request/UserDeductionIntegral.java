package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UserDeductionIntegral {
    @NotNull
    @ApiModelProperty(value = "扣除的积分", required = true)
    private BigDecimal changeBalance;

    @NotNull
    @ApiModelProperty(value = "币种名称", required = true)
    private String coinName;


}
