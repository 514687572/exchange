package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MiningStat {
    private int id;
    // 统计时间，统计的时候所在那天的整天数据
    private int statTime;
    // 今天总的交易（挖矿）直接奖励数(不包括推荐)，奖励是统计时间的下一天到账的
    private BigDecimal tradeBonus;
    // 今天总的交易推荐（挖矿推荐）奖励数，奖励是统计时间的下一天到账的
    private BigDecimal tradeRecBonus;
    // 今天内其他平台币奖励总和（注册和推荐赠送）
    private BigDecimal otherBonus;
}
