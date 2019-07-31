package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinDifferenceConfig implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    @ApiModelProperty(value = "区块上平台内多出的数量")
    private String moreDifference;
    @ApiModelProperty(value = "区块上平台少的差额")
    private Integer lessDifference;
    @ApiModelProperty(value = "刷新时间")
    private Integer refreshTime;
}
