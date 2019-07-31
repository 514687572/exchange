package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinDifference {

    @ApiModelProperty("币种名称")
    private String coinName;

    @ApiModelProperty("历史该币种总数")
    private BigDecimal rollInTotal;

    @ApiModelProperty("今日新增币种的数量")
    private BigDecimal todyAddRollInTotal;

    @ApiModelProperty("用户该币种总余额")
    private BigDecimal userBalanceTotal;

    @ApiModelProperty("历史积分总数")
    private BigDecimal pointTotal;

    @ApiModelProperty("今日新增的币的数量")
    private BigDecimal todayAddPointTotal;

    @ApiModelProperty("服务器刷新监控时间点")
    private Date refreshTime;

    @ApiModelProperty("是否报警")
    private boolean warning = false;
}
