package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MarketGroupConfig {

    @ApiModelProperty(value = "'主键'")
    private Integer id;
    @ApiModelProperty(value = "'配对对应的交易对Id'")
    private Integer marketId;


    @ApiModelProperty(value = "对应的用户分组id")
    private Integer groupId;
    @ApiModelProperty(value = "买入收取的手续费")
    private String buyConValue;
    @ApiModelProperty(value = "卖出收取的手续费")
    private String sellConValue;
    @ApiModelProperty(value = "备注'")
    private String remark;
    @ApiModelProperty(value = "交易的的币种")
    private String coinName;
    @ApiModelProperty(value = "兑换的币种")
    private String settlementCurrency;


}
