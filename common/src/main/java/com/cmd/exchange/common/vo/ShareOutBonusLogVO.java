package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class ShareOutBonusLogVO {
    private BigInteger id;

    private Integer userId;
    @ApiModelProperty(value = "用户电话")
    private String mobile;
    @ApiModelProperty(value = "用户的真实姓名")
    private String realName;
    @ApiModelProperty(value = "分红币种的名称")
    private String coinName;
    @ApiModelProperty(value = "持有的平台币数量")
    private BigDecimal userBaseCoin;
    @ApiModelProperty(value = "分红数量")
    private BigDecimal userBonus;
    @ApiModelProperty(value = "分红时间")
    private Date createTime;
}
