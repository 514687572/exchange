package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.UserDayProfit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDayProfitVo extends UserDayProfit {
    @ApiModelProperty("昨日矿机收益 1 开始挖矿  2挖矿中")
    private Integer machineOpen = 1;
    @ApiModelProperty("矿机名称")
    private String machineName = "智能矿机";
    @ApiModelProperty("矿机币种")
    private String machineCoin = "";
    @ApiModelProperty("昨日矿机收益")
    private BigDecimal machineYesterdayIncome = new BigDecimal(0);
    @ApiModelProperty("昨日超级节点收益")
    private BigDecimal superNodeDayProfit = new BigDecimal(0);
    @ApiModelProperty("超级节点总收益")
    private BigDecimal superNodeTotalProfit = new BigDecimal(0);
    @ApiModelProperty("昨日团队交易总额")
    private BigDecimal usdtDayTeamTotaExchange = new BigDecimal(0);
    @ApiModelProperty("昨日团队奖励")
    private BigDecimal usdtDayTeamAward = new BigDecimal(0);
    @ApiModelProperty("团队总收益")
    private BigDecimal usdtTeamTotalProfit = new BigDecimal(0);

}
