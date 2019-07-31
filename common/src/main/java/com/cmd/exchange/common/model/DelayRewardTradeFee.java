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
public class DelayRewardTradeFee implements Serializable {
    private static final long serialVersionUID = -1661830493854605L;

    private BigInteger id;

    private Integer userId;

    /**
     * 变化的币种名称，缩写，编译查看
     */
    private String coinName;

    /**
     * 变化大小，正数表示增加金额，负数表示减少金额
     */
    private BigDecimal changeAmount;


    /**
     * 备注信息，记录一些额外信息，比如变化的订单号等
     */
    private String comment;


    private Date addTime;

}