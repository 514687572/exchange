package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtcMarket implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private int id;
    private String coinName;
    private String legalName;
    private Date onMarketTime;
    private Date dayExchangeBegin;
    private Date dayExchangeEnd;
    private BigDecimal lastPrice;
    private BigDecimal minExchangeNum;
    private BigDecimal maxExchangeNum;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private BigDecimal feeRate;
    private Integer expiredTimeCancel;
    private Integer expiredTimeFreeze;
    private Integer maxApplBuyCount;
    private Integer maxApplSellCount;


}
