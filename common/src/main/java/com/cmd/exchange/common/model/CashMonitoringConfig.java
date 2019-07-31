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
public class CashMonitoringConfig implements Serializable {

    private Integer id;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    @ApiModelProperty(value = "转入数量")
    private String rollInNumber;
    @ApiModelProperty(value = "转出数量")
    private String rollOutNumber;
    @ApiModelProperty(value = "最近查询时间")
    private Integer lastRefreshTime;
}
