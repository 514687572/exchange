package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 币的储蓄（锁仓）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("币种存储，锁仓，分润宝")
public class CoinSavings {
    private Integer id;
    private Integer userId;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("存储/锁仓的资金")
    private BigDecimal balance;
    @ApiModelProperty("正在提走中的资金（用于延迟到账）")
    private BigDecimal withdrawing;

    /////////////////////////////////////
    // 以下字段不在数据库里面
    @ApiModelProperty("userName")
    private String userName;
}
