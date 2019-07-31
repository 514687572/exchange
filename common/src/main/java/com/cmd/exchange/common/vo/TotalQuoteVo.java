package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linmingren on 2017/8/30.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TotalQuoteVo {
    @ApiModelProperty("买单委托量占比, 比如0.59, 需要显示成59%")
    private BigDecimal buyProportion;
    @ApiModelProperty("卖单委托量占比")
    private BigDecimal sellProportion;

    @ApiModelProperty("最新的买单")
    private List<QuoteVo> buyQuoteList;
    @ApiModelProperty("最新的卖单")
    private List<QuoteVo> sellQuoteList;
    @ApiModelProperty("最新成交价格")
    private BigDecimal lastPrice;
    //@ApiModelProperty("最后成交价相比昨天收盘价是涨还是跌")
    //private PriceStatus status;
}
