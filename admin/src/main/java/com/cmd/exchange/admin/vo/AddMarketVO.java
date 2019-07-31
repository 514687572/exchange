package com.cmd.exchange.admin.vo;

import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.model.Market;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddMarketVO {
    private Integer id;
    @ApiModelProperty(value = "交易货币", required = true)
    private String coinName;
    @ApiModelProperty(value = "结算货币", required = true)
    private String settlementCurrency;
    @ApiModelProperty(value = "每日交易开始时间， 格式 hh:mm:00, 比如 09:00:00,默认00:00:00")
    private String dayExchangeBegin;
    @ApiModelProperty(value = "每日交易结束时间， 格式 hh:mm:00, 比如 23:59:59")
    private String dayExchangeEnd;
    @ApiModelProperty(value = "最大涨幅, 20 代表20%", required = true)
    @NotNull
    private Integer maxIncrease;
    @ApiModelProperty(value = "最大跌幅", required = true)
    @NotNull
    private Integer minDecrease;
    @ApiModelProperty(value = "单笔最小下单数量")
    @NotNull
    private BigDecimal minExchangeNum;
    @ApiModelProperty(value = "单笔最大下单数量")
    @NotNull
    private BigDecimal maxExchangeNum;

    @ApiModelProperty(value = "买入手续费, 0.1 代表10%")
    @NotNull
    private BigDecimal feeCoin;
    @ApiModelProperty(value = "卖出手续费 0.1 代表10%")
    @NotNull
    private BigDecimal feeCurrency;
    @ApiModelProperty(value = "是否开放市场， SHOW: 开放 HIDE： 关闭, 默认开放")
    private MarketStatus closed;
    @ApiModelProperty(value = "市场的图片地址")
    private String coinUrl;
    @ApiModelProperty(value = "最大下单总价，如果是0或者null，那么不控制下单总价")
    private BigDecimal maxCurrency;
    @ApiModelProperty(value = "是否删除 0：不删除 ：1删除")
    private Integer del = 0;

    public Market toModel() {
        Market market = new Market();
        BeanUtils.copyProperties(this, market);
        return market;
    }
}
