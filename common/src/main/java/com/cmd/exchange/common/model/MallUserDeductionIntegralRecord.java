package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商城积分扣除业务 记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MallUserDeductionIntegralRecord implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    // 币种名称
    private String coinName;
    // 用户ID
    private Integer userId;
    // 手机号
    private String mobile;
    // 扣除积分
    private BigDecimal deductionIntegral;
    // sign
    private String sign;
    //插入时间
    private Date addTime;
    //扣除时间
    private Integer changeTime;
    //订单号
    private String orderNum;
}
