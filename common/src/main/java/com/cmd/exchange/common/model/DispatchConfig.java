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
public class DispatchConfig implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private Integer cronId;
    private String cronName;
    private String lockName;
    private BigDecimal lockRate;
    private BigDecimal freeRate;
    private Integer status;
    private Date createTime;
    private Date lastTime;
}
