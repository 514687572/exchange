package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradeBonusLog implements Serializable {
    private static final long serialVersionUID = -1L;

    private BigInteger id;
    private Integer userId;
    // 交易的币种，作为货物
    private String coinName;
    // 用于结算的货币，一般是平台的基本货币
    private String settlementCurrency;
    // 对应t_trade_log的id
    private Integer tradeLogId;
    // 买入卖出类型，1：买入，2：卖出
    private int type;
    // 交易产生的手续费，对于买入，这里说的是交易币种，对于卖出，这里是结算货币
    private BigDecimal tradeFee;
    // 推荐用户id
    private Integer recommendUserId;
    // 交易奖励（挖矿返佣）
    private BigDecimal tradeBonus;
    // 推荐人拿到的奖励
    private BigDecimal recommendBonus;
    private Date createTime;
}
