package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RewardLogVO implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "交易用户电话")
    private String mobile;
    @ApiModelProperty(value = "交易用户email")
    private String email;
    @ApiModelProperty(value = "币种")
    private String coinName;
    @ApiModelProperty(value = "数量")
    private BigDecimal amount;
    @ApiModelProperty(value = "备注")
    private String comment;
    @ApiModelProperty(value = "时间")
    private Date lastTime;
}
