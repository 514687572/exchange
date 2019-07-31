package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TradeLogListVo implements Serializable {
    //当前最后一条TradeLog记录id， 有新的成交肯定会影响委托列表
    private Integer lastTradeLogId;
    @ApiModelProperty("成交订单列表， 时间从小到大排序")
    private List<TradeLogVo> tradeLogList;
}
