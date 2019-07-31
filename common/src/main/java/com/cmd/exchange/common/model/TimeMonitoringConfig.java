package com.cmd.exchange.common.model;

import com.cmd.exchange.common.constants.MonitorTradeType;
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
@ApiModel("各种类型监控时间配置")
public class TimeMonitoringConfig implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("'监控类型：高频挂单监控")
    private String monitoringType = MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE;

    @ApiModelProperty("`coin_name`需要买入或卖出的币种，作为货物',")
    private String coinName;

    @ApiModelProperty("settlement_currency'用于结算的货币，一般是平台的基本货币',")
    private String settlementCurrency;

    @ApiModelProperty("买入次数")
    private Integer buyNumber;

    @ApiModelProperty("卖出次数")
    private Integer sellNumber;

    @ApiModelProperty("分钟为单位，输入大于0的自然数")
    private Integer numMinutes;
}
