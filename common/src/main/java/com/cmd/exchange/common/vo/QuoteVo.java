package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.constants.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 报价记录
 * Created by linmingren on 2017/8/30.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class QuoteVo {
    private static final int VOLUME_SCALE = 4;
    //价格按人民币计算，最小到分
    private static final int PRICE_SCALE = 8;
    @ApiModelProperty("订单类型，买单或卖单")
    private TradeType type;
    @ApiModelProperty("单位数量的价格")
    private BigDecimal price;
    private String coinName;
    @ApiModelProperty(value = "用于结算的货币", required = true)
    private String settlementCurrency;
    @ApiModelProperty("订单数量")
    private BigDecimal amount;
    @ApiModelProperty("订单总额")
    private BigDecimal totalCurrency;
}
