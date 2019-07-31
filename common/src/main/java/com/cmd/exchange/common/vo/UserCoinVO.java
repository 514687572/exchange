package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserCoinVO implements Serializable {
    private Integer id;
    private Integer userId;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    private String bindAddress;
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeBalance;
    @ApiModelProperty(value = "转入冻结，另外一种冻结金")
    private BigDecimal receiveFreezeBalance;
    @ApiModelProperty(value = "转换为指定货币后的可用余额")
    private BigDecimal availableConvert;
    @ApiModelProperty(value = "转换为指定货币后的冻结金额")
    private BigDecimal freezeConvert;
    @ApiModelProperty(value = "用户该币种VIP类型，0，不是vip，1：vip")
    private Integer vipType;

    //用户币种状态，0：正常，1：冻结使用
    private Boolean status;

    // 提现手续费
    @ApiModelProperty(value = "转出手续费")
    private BigDecimal transferFeeRate;
    // 最小
    @ApiModelProperty(value = "转出最小数量")
    private BigDecimal transferMinAmount;
    // 最大
    @ApiModelProperty(value = "转出最大数量")
    private BigDecimal transferMaxAmount;
    @ApiModelProperty(value = "币种logo地址")
    private String image;
}
