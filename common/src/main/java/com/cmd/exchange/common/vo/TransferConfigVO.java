package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TransferConfigVO {
    private String coinName;
    @ApiModelProperty(value = "每天最大转出数量")
    private BigDecimal maxDailyTransferOutAmount;
    @ApiModelProperty(value = "转出手续费，0.2代表20%")
    private BigDecimal transferOutFee;
    @ApiModelProperty(value = "单笔最大转出数量")
    private BigDecimal maxSingleTransferOutAmount;
}
