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
public class OtcBill implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private Long id;
    private Integer userId;
    private String coinName;
    private String orderNo;
    private Integer type;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal fee;
    private Date createTime;
    private Date lastTime;
}
