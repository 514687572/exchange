package com.cmd.exchange.api.vo;

import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.vo.PriceType;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CreateTradeRequestVO {
    @ApiModelProperty("委托单类型, BUY:买 SELL:卖")
    @NotNull
    private TradeType type;
    @ApiModelProperty("买卖的数字货币")
    @NotBlank
    private String coinName;
    @ApiModelProperty("用于结算的货币")
    @NotBlank
    private String settlementCurrency;
    @ApiModelProperty("每个数字货币的价格, 限价单必须填写")
    private BigDecimal price;
    @ApiModelProperty("LIMITED: 限价单, MARKET:市价单")
    @NotNull
    private PriceType priceType;

    @ApiModelProperty("委托数量")
    @NotNull
    private BigDecimal amount;
}
