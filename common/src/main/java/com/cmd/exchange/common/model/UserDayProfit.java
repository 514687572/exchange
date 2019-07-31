package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("用户每日收益统计")
public class UserDayProfit {
    private BigInteger id;
    private Integer userId;
    @ApiModelProperty("收益统计时间")
    private Date profitTime;
    @ApiModelProperty("当前分润宝总资产")
    private BigDecimal totalBalance = BigDecimal.ZERO;
    @ApiModelProperty("昨日分润宝分润（USDT）")
    private BigDecimal savingProfit = BigDecimal.ZERO;
    @ApiModelProperty("昨日USDT花费数量（买入数字货币）")
    private BigDecimal usdtUseAmount = BigDecimal.ZERO;
    @ApiModelProperty("昨日USDT获取数量（卖出数字货币）")
    private BigDecimal usdtGetAmount = BigDecimal.ZERO;
    @ApiModelProperty("昨日USDT手续费总数")
    private BigDecimal usdtTotalFee = BigDecimal.ZERO;
    @ApiModelProperty("昨日交易获取UKS数量(挖矿)")
    private BigDecimal tradeRewardUks = BigDecimal.ZERO;
    @ApiModelProperty("昨日团队USDT花费数量（买入数字货币）")
    private BigDecimal usdtTeamUseAmount = BigDecimal.ZERO;
    @ApiModelProperty("昨日团队USDT获取数量（卖出数字货币）")
    private BigDecimal usdtTeamGetAmount = BigDecimal.ZERO;
    @ApiModelProperty("昨日超级节点收益")
    private BigDecimal superNodeProfit = BigDecimal.ZERO;
    @ApiModelProperty("团队总收益")
    private BigDecimal usdtTeamTotalReward = BigDecimal.ZERO;
    @ApiModelProperty("昨日团队奖励")
    private BigDecimal usdtTeamReward = BigDecimal.ZERO;
    @ApiModelProperty("超级节总收益")
    private BigDecimal superNodeTotalProfit = BigDecimal.ZERO;



}
