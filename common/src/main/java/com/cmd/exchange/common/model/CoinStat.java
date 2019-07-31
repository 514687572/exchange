package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinStat implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    @ApiModelProperty(value = "该币种历史总手续费统计")
    private BigDecimal totalFee;
    @ApiModelProperty(value = "最近刷新时间")
    private Integer lastRefreshTime;
    @ApiModelProperty(value = "该币种总数量")
    private BigDecimal totalCureency;
}
