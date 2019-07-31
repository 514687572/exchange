package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenTradeListVo implements Serializable {
    //当前最后一条Trade记录的id
    private Integer lastTradeId;
    //当前最后一条TradeLog记录id， 有新的成交肯定会影响委托列表
    private Integer lastTradeLogId;
    @ApiModelProperty("买单列表， 价格从高到低")
    private List<TradeVo> buyTradeList;
    @ApiModelProperty("卖单列表， 价格从高到低")
    private List<TradeVo> sellTradeList;
}
