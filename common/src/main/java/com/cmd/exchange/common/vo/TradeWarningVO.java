package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TradeWarningVO {

    //主键
    private Integer id;

    /**
     * 对应的交易对id
     **/
    @ApiModelProperty("对应交易对Id")
    private Integer marketId;

    //警报详情
    @ApiModelProperty("警报描述，后台暂时隐藏")
    private String remark;

    //数量（钱的金额，或者币种的数量）
    @ApiModelProperty("警告配置的数量")
    private BigDecimal amount;

    @ApiModelProperty("交易对名字")
    private String name;

    @ApiModelProperty("需要交易的币种名字")
    private String coinName;
    @ApiModelProperty("用于结算的货币，一般是平台的基本货币")
    private String settlementCurrency;

    @ApiModelProperty("买入还是卖出")
    private Integer type;

}
