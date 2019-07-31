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
public class OtcStat implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private Integer userId;
    private String coinName;
    private String legalName;
    private BigDecimal amountPass;
    private BigDecimal amountFail;
    private Integer tradePass;
    private Integer tradeFail;
    private Date createTime;
    private Date lastTime;
}
