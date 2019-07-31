package com.cmd.exchange.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

//兼容TradeView k线控件的返回格式
@Data
@Accessors(chain = true)
public class TradeViewCandleResponseVO {
    //时间，unix时间戳
    private Integer[] t;
    private BigDecimal[] o;
    private BigDecimal[] c;
    private BigDecimal[] h;
    private BigDecimal[] l;
    private BigDecimal[] v;
    private String s = "ok";
}
