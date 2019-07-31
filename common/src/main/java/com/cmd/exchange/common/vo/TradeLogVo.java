package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.constants.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 成交记录
 * Created by linmingren on 2017/8/30.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TradeLogVo implements Serializable {

    private static final long serialVersionUID = -1462379172780729411L;
    @ApiModelProperty(value = "订单id", required = true)
    private String coinName;
    @ApiModelProperty(value = "订单id", required = true)
    private String settlementCurrency;

    @ApiModelProperty(value = "用户真实姓名", required = true)
    private String realName;
    @ApiModelProperty(value = "用户电话", required = true)
    private String mobile;
    @ApiModelProperty(value = "BUY: 买单， SELL:卖单", required = true)
    private TradeType type;
    @ApiModelProperty(value = "成交价格", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "成交数量", required = true)
    private BigDecimal amount;
    @JsonIgnore
    private Long addTime;
    private Date createTime;

    @JsonIgnore
    private BigDecimal buyFeeCoin;
    @JsonIgnore
    private BigDecimal sellFeeCurrency;

    // 请求的类型
    private TradeType queryType;

    @ApiModelProperty(value = "该笔交易的手续费")
    public BigDecimal getFee() {
        // 同一个人跟自己交易，会导致type不准确
        if (queryType != null) {
            if (queryType == TradeType.SELL) {
                return sellFeeCurrency;
            }
            if (queryType == TradeType.BUY) {
                return buyFeeCoin;
            }
            return null;
        }
        if (type == TradeType.SELL) {
            return sellFeeCurrency;
        }

        if (type == TradeType.BUY) {
            return buyFeeCoin;
        }

        return null;
    }

    public Date getCreateTime() {
        if (addTime != null) {
            return new Date(addTime * 1000);
        }

        return null;
    }
}
