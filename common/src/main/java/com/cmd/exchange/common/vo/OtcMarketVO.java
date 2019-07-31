package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtcMarketVO {
    @ApiModelProperty(value = "币种")
    private String coinName;
    @ApiModelProperty(value = "法币")
    private String legalName;
    @ApiModelProperty(value = "手续费")
    private BigDecimal feeRate;
    @ApiModelProperty(value = "超时取消，分钟")
    private Integer expiredTimeCancel;
    @ApiModelProperty(value = "超时冻结，分钟")
    private Integer expiredTimeFreeze;
    @ApiModelProperty(value = "最大挂买单数")
    private Integer maxApplBuyCount;
    @ApiModelProperty(value = "最大挂卖单数")
    private Integer maxApplSellCount;
}
