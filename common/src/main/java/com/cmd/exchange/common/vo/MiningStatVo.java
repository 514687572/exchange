package com.cmd.exchange.common.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 挖矿统计
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MiningStatVo implements Serializable {
    private static final long serialVersionUID = -1894806283303239761L;

    @ApiModelProperty("前一日挖矿产出，是最后一次统计的平台币（BON）的数量")
    private BigDecimal lastMiningBon;
    @ApiModelProperty("总BON流通量")
    private BigDecimal totalCirculationBon;
    @ApiModelProperty("今日待分配收入累积折合：获得的各种手续费*分配比例，折合USDT")
    private BigDecimal todayMiningToUsdt;
    @ApiModelProperty("今日连续持有BON每百万方份收入折合USDT")
    private BigDecimal todayMillionBonToUsdt;
    @ApiModelProperty("昨日待分配收入累积折合：获得的各种手续费*分配比例，折合USDT")
    private BigDecimal lastMiningToUsdt;
    @ApiModelProperty("昨日连续持有BON每百万方份收入折合USDT")
    private BigDecimal lastMillionBonToUsdt;

}
