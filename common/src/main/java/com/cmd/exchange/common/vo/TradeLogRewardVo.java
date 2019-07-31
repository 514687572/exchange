package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.TradeFeeReturnDetail;
import com.cmd.exchange.common.model.TradeLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("交易日志和奖励信息")
public class TradeLogRewardVo extends TradeFeeReturnDetail {
    private BigInteger id;
    @ApiModelProperty("交易币种")
    private String coinName;
    @ApiModelProperty("结算货币")
    private String settlementCurrency;
    @ApiModelProperty("价格/单价")
    private BigDecimal price;
    @ApiModelProperty("交易数量")
    private BigDecimal amount;
    @ApiModelProperty("买方用户ID")
    private Integer buyUserId;
    @ApiModelProperty("买方用户名")
    private String buyUserName;
    @ApiModelProperty("卖方用户ID")
    private Integer sellUserId;
    @ApiModelProperty("卖方用户名")
    private String sellUserName;
    @ApiModelProperty("交易时间")
    private Integer addTime;
    @ApiModelProperty("交易时间")
    private Date addTime1;
    @ApiModelProperty("买方支付的手续费（交易币种）")
    private BigDecimal buyFeeCoin;
    @ApiModelProperty("卖方支付的手续费（结算货币）")
    private BigDecimal sellFeeCurrency;
    @ApiModelProperty("买方挂单ID")
    private Integer buyTradeId;
    @ApiModelProperty("卖方挂单ID")
    private Integer sellTradeId;

    @ApiModelProperty("买方的推荐人名称")
    private String buyRecUserName;
    @ApiModelProperty("买方的二级推荐人名称")
    private String buyRec2UserName;
    @ApiModelProperty("卖方的推荐人名称")
    private String sellRecUserName;
    @ApiModelProperty("卖方的二级推荐人名称")
    private String sellRec2UserName;
}
