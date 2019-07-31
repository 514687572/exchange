package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("记录交易手续费返回给推荐人的明细信息")
public class TradeFeeReturnDetail {
    private BigInteger id;
    @ApiModelProperty("交易的id，t_trade_log表中的id")
    private BigInteger tradeLogId;
    @ApiModelProperty("返还奖励的时间")
    private Date retTime;
    @ApiModelProperty("手续费返还给推荐用户的比例")
    private BigDecimal recUserRate;
    @ApiModelProperty("手续费返还给二级推荐用户的比例")
    private BigDecimal rec2UserRate;
    @ApiModelProperty("买方推荐人返还的手续费币种名称")
    private String buyRetCoin;
    @ApiModelProperty("买方的推荐人ID")
    private Integer buyRecUserId;
    @ApiModelProperty("买方推荐人返还的币个数")
    private BigDecimal buyRecRet;
    @ApiModelProperty("买方的二级推荐人ID")
    private Integer buyRec2UserId;
    @ApiModelProperty("买方二级推荐人返还的币个数")
    private BigDecimal buyRec2Ret;
    @ApiModelProperty("卖方推荐人返还的手续费币种名称")
    private String sellRetCoin;
    @ApiModelProperty("卖方的推荐人ID")
    private Integer sellRecUserId;
    @ApiModelProperty("卖方推荐人返还的币个数")
    private BigDecimal sellRecRet;
    @ApiModelProperty("卖方的二级推荐人ID")
    private Integer sellRec2UserId;
    @ApiModelProperty("卖方二级推荐人返还的币个数")
    private BigDecimal sellRec2Ret;
    @ApiModelProperty("卖方ID")
    private Integer userId;
}
