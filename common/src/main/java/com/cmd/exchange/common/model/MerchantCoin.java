package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user_coin
 *
 * @mbg.generated do_not_delete_during_merge Thu Jun 21 10:03:28 CST 2018
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantCoin implements Serializable {
    private static final long serialVersionUID = -1661830493893472505L;

    private Integer id;
    @ApiModelProperty(value = "兑换商ID")
    private Integer merchantId;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;
    @ApiModelProperty(value = "可用余额")
    private BigDecimal freezeBalance;
    //用户币种状态，0：正常，1：冻结使用
    private Integer status;
}