package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
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
@ApiModel("各种类型监控表")
public class TimeMonitoring implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("'监控的类型'")
    private String monitoringType;
    @ApiModelProperty("监控的名字:：挂单监控，买入监控，卖出监控等")
    private String monitoringName;
    @ApiModelProperty("监控的时间;分钟为单位，输入大于0的自然数")
    private Integer numMinutes;

}
