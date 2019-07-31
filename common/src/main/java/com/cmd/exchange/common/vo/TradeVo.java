package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.constants.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradeVo implements Serializable {

    private static final long serialVersionUID = 5093647397447004509L;
    @ApiModelProperty(value = "订单id", required = true)
    private Integer id;
    @ApiModelProperty(value = "姓名")
    private String realName;
    @ApiModelProperty(value = "电话")
    private String mobile;
    private Integer userId;
    @JsonIgnore
    private Integer addTime;
    @ApiModelProperty(value = "委托时间", required = true)
    private Date createdAt;

    @JsonIgnore
    private Integer lastTradeTime;
    @ApiModelProperty(value = "交易完成时间", required = true)
    private Date finishTradeTme;

    @ApiModelProperty(value = "委托类型， 买单，还是卖单", required = true)
    private TradeType type;
    @ApiModelProperty(value = "限价类型，0限价交易，1市价交易", required = false)
    private int priceType;
    @ApiModelProperty(value = "虚拟币名称", required = true)
    private String coinName;
    @ApiModelProperty(value = "用于结算的货币", required = true)
    private String settlementCurrency;
    @ApiModelProperty(value = "虚拟币图标", required = true)
    private String coinIcon;
    @ApiModelProperty(value = "委托价格", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "委托数量", required = true)
    private BigDecimal amount;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "0表示为不需要标红,1：表示可以标红")
    private Integer isRed = 0;

    private BigDecimal totalCurrency;
    //卖一价格
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "成交价格", required = true)
    //private BigDecimal dealPrice;
    public BigDecimal getDealPrice() {
        if (dealAmount == null || dealCurrency == null || dealAmount.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return dealCurrency.divide(dealAmount, 8, BigDecimal.ROUND_HALF_UP);
    }

    @ApiModelProperty(value = "成交数量", required = true)
    private BigDecimal dealAmount;
    @ApiModelProperty(value = "已经成交的结算货币数量，对于买入：已经花费的结算货币，对于卖出：已经赚取的结算货币数量（包括费用)", required = true)
    private BigDecimal dealCurrency;
    @ApiModelProperty(value = "交易状态, 目前有3中状态： 未完全成交，完全成交，已经撤单，", required = true)
    private TradeStatus status;

    @ApiModelProperty(value = "以当前价格挂单的币的总量")
    private BigDecimal totalAmount;

    @JsonIgnore
    private BigDecimal feeCoin;
    @JsonIgnore
    private BigDecimal feeCurrency;

    public Date getCreatedAt() {
        return addTime != null ? new Date(addTime * 1000l) : null;
    }

    public Date getFinishTradeTme() {
        return lastTradeTime != null ? new Date(lastTradeTime * 1000l) : null;
    }

    @ApiModelProperty(value = "该笔订单的手续费")
    public BigDecimal getFee() {
        if (type == TradeType.SELL) {
            return feeCurrency;
        } else {
            return feeCoin;
        }
    }

    @ApiModelProperty(value = "成交后是成交均价")
    public BigDecimal getAvgPrice() {
        if (dealCurrency != null && dealAmount != null && dealAmount.compareTo(BigDecimal.ZERO) > 0) {
            return dealCurrency.divide(dealAmount, 8, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }
}
