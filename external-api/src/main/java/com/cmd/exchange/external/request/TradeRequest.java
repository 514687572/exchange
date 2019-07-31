package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TradeRequest extends CommonRequest {
    @NotNull
    @ApiModelProperty(value = "交易币种", required = true)
    private String coinName;
    @NotNull
    @ApiModelProperty(value = "结算币种", required = true)
    private String settlementCurrency;
}
