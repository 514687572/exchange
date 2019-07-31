package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelTradeRequest extends CommonRequest {
    @NotNull
    @ApiModelProperty(value = "订单id", required = true)
    private Integer tradeId;
}
