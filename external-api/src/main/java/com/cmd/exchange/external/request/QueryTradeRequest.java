package com.cmd.exchange.external.request;

import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.constants.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryTradeRequest extends TradeRequest {
    @NotNull
    @ApiModelProperty(value = "交易类型, ", required = true)
    private TradeType type;
    @NotNull
    @ApiModelProperty(value = "页码， 从1开始", required = true)
    private Integer pageNo;
    @NotNull
    @ApiModelProperty(value = "每页记录数", required = true)
    private Integer pageSize;
}
