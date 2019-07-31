package com.cmd.exchange.external.request;

import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.vo.PriceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateTradeRequest extends TradeRequest {
    @NotNull
    @ApiModelProperty(value = "交易类型, BUY:买单， SELL:卖单", required = true)
    private TradeType type;
    @NotNull
    @ApiModelProperty(value = "LIMITED: 限价单, MARKET:市价单", required = true)
    @NotNull
    private PriceType priceType;
    @ApiModelProperty(value = "交易价格", required = false)
    private BigDecimal price;
    @NotNull
    @ApiModelProperty(value = "交易数量", required = true)
    private BigDecimal amount;
}
