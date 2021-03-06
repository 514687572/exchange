package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user_bill
 *
 * @mbg.generated do_not_delete_during_merge Thu Jun 21 10:03:28 CST 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DelayShareOut implements Serializable {
    private static final long serialVersionUID = -1661830493834252005L;

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

    // 分红时用户基本金额
    private BigDecimal userBaseCoin;


    /**
     * 备注信息，记录一些额外信息，比如变化的订单号等
     */
    private String comment;


    private Date addTime;

}