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
public class UserRewardLog implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private Integer id;
    private Integer userId;
    private Integer from;
    private String reason;
    private String coinName;
    private BigDecimal amount;
    private String comment;
    private Integer status;
    private Date lastTime;
}
