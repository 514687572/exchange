package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TradeWarning implements Serializable {

    private static final long serialVersionUID = -1L;

    //主键
    private Integer id;

    /**
     * 对应的交易对id
     **/
    @ApiModelProperty("对应的交易对id")
    private Integer marketId;


    //警报详情
    @ApiModelProperty("警报详情")
    private String remark;

    //数量（钱的金额，或者币种的数量）
    @ApiModelProperty("数量（钱的金额，或者币种的数量）")
    private BigDecimal amount;

    @ApiModelProperty("1买入 2.卖出")
    private Integer type;
}
