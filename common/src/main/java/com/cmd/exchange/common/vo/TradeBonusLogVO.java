package com.cmd.exchange.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class TradeBonusLogVO {
    @ApiModelProperty(value = "id")
    private BigInteger id;
    @JsonIgnore
    private Integer userId;
    @ApiModelProperty(value = "交易用户真实姓名")
    private String realName;
    @ApiModelProperty(value = "交易用户的电话")
    private String mobile;
    // 交易的币种，作为货物
    private String coinName;
    // 用于结算的货币，一般是平台的基本货币
    private String settlementCurrency;

    // 买入卖出类型，1：买入，2：卖出
    private int type;
    // 交易产生的手续费，对于买入，这里说的是交易币种，对于卖出，这里是结算货币
    @ApiModelProperty(value = "交易手续费")
    private BigDecimal tradeFee;
    // 推荐用户id
    private Integer recommendUserId;
    @ApiModelProperty(value = "推荐人电话")
    private String recommendUserMobile;
    // 交易奖励（挖矿返佣）
    @ApiModelProperty(value = "交易用户返佣")
    private BigDecimal tradeBonus;
    // 推荐人拿到的奖励
    @ApiModelProperty(value = "推荐人返佣")
    private BigDecimal recommendBonus;
    @ApiModelProperty(value = "奖励时间")
    private Date createTime;
}
