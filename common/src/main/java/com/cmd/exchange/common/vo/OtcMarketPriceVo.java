package com.cmd.exchange.common.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OtcMarketPriceVo {

    private int id;

    @ApiModelProperty("币名")
    private String coinName;
    @ApiModelProperty("参考价")
    private BigDecimal lastPrice;
}
