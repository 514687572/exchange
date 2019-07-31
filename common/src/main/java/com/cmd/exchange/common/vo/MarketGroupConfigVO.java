package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MarketGroupConfigVO {

    @ApiModelProperty(value = "交易对Id")
    private Integer id;
    @ApiModelProperty(value = "交易名字")
    private String name;
    @ApiModelProperty(value = "需要买入或卖出的币种，作为货物")
    private String coinName;

    @ApiModelProperty(value = "用于结算的货币，一般是平台的基本货币")
    private String settlementCurrency;
    /**
     * 分组配置的信息
     */
    @ApiModelProperty(value = "'分组配置表的主键'")
    private Integer marketGroupConfigId;

    @ApiModelProperty(value = "交易对Id")
    private Integer marketId;

    @ApiModelProperty(value = "对应的用户分组id")
    private Integer groupId;

    @ApiModelProperty(value = "分组的名称")
    private String groupName;

    @ApiModelProperty(value = "买入收取的手续费")
    private String buyConValue;
    @ApiModelProperty(value = "卖出收取的手续费")
    private String sellConValue;
    @ApiModelProperty(value = "备注'")
    private String remark;
}
