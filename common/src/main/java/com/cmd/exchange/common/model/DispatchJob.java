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
public class DispatchJob implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String userName;
    private Integer userId;
    private String mobile;
    private String dispatchNo;
    private Integer cronId;
    private String cronName;
    private String coinName;
    private String comment;
    private BigDecimal amount;
    private BigDecimal amountAll;
    private BigDecimal lockRate;
    private BigDecimal freeRate;
    private Integer status;
    private Date createTime;
    private Date lastTime;
    private String email;
    private String realName;
}
