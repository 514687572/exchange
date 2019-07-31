package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 汇率表，用来表示2种货币进行交换的货币，这里的数据可能来自于本站交易市场，也有可能是从外部过来
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExchangeRate implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    // 需要买入或卖出的币种
    private String coinName;
    // 用于结算的货币
    private String settlementCurrency;
    // 价格，表示1个coin需要多少个结算货币进行兑换
    private BigDecimal price;
    // 最近一个小时价格，表示1个coin需要多少个结算货币进行兑换
    private BigDecimal priceHourAgo;
    // 用于说明一些信息，比如来源于那个站点的
    private String comment;
    // 记录增加的时间
    private Date addTime;
    // 最后一次修改时间
    private Date updateTime;
}
