package com.cmd.exchange.common.model;

//支持兑换商兑换的币种

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@ApiModel("支持兑换商兑换的币种")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MerchantCoinConf {
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_DISABLE = 1;

    private Integer id;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("人民币价格")
    private BigDecimal cnyPrice;
    @ApiModelProperty("美元价格")
    private BigDecimal dollarPrice;
    @ApiModelProperty("美元价格")
    private BigDecimal hkdollarPrice;
    @ApiModelProperty(value = "该币种最小能买卖的个数")
    private BigDecimal orderMinAmount;
    @ApiModelProperty(value = "该币种最大能买卖的个数")
    private BigDecimal orderMaxAmount;
    @ApiModelProperty(value = "费率，必须小于1,0.2表示20%")
    private BigDecimal feeRate;
    @ApiModelProperty("状态，0正常，1表示禁用")
    private Integer status;
}
