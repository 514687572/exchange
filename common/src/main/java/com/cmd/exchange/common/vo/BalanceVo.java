package com.cmd.exchange.common.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数字币余额
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BalanceVo implements Serializable {

    private static final long serialVersionUID = 3410538694701012907L;
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "冻结余额")
    private BigDecimal blockBalance;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;
}
