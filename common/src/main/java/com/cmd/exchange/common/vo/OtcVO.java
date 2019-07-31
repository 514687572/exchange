package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
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
public class OtcVO implements Serializable {
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
    private Boolean isBank;
    private Boolean isWeixin;
    private Boolean isAlipay;
    @ApiModelProperty(value = "c2c交易成功笔数")
    private Integer tradePass;
    @ApiModelProperty(value = "c2c交易失败笔数")
    private Integer tradeFail;
    @ApiModelProperty(value = "c2c交易成功数额")
    private BigDecimal amountPass;
    @ApiModelProperty(value = "c2c交易失败数额")
    private BigDecimal amountFail;
}
