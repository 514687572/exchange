package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Otc implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private Long id;
    private Integer userId;
    private String userName;
    private String applNo;
    private Integer type;
    private Integer status;
    private String coinName;
    private String legalName;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal feeRate;
    private BigDecimal fee;
    private BigDecimal amountFrozen;
    private BigDecimal amountSuccess;
    private BigDecimal amountAll;
    private String comment;
    private Date createTime;
    private Date lastTime;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
