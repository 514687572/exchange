package com.cmd.exchange.common.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashMonitoringVO implements Serializable {

    private static final long serialVersionUID = -1L;
    @ApiModelProperty("用户账户")
    private String account;

    @ApiModelProperty("转入转出类型 1：转入 2：转出")
    private String type;

    @ApiModelProperty("转入数量")
    private BigDecimal rollInNumber;

    @ApiModelProperty("转出数量")
    private BigDecimal rollOutNumber;

    @ApiModelProperty("币种名称")
    private String coinName;

    @ApiModelProperty("源地址")
    private String sourceAddress;

    @ApiModelProperty("目标地址")
    private String goalAddress;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("刷新时间")
    private Date refreshTime;

    @ApiModelProperty("用户手机，只是服务器做协作作用，管理后台只显示Account")
    private String mobile;

    @ApiModelProperty("用户邮箱")
    private String email;
}
