package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TradeBonusLogRequestVO {
    private Integer userId;
    @ApiModelProperty(value = "交易用户电话")
    private String mobile;
    @ApiModelProperty(value = "推荐用户电话")
    private String referrerMobile;
    private Integer referrerId;
    @ApiModelProperty(value = "开始时间 yyyy-mm-dd hh:mm:ss")
    private String startTime;
    @ApiModelProperty(value = "结束时间 yyyy-mm-dd hh:mm:ss")
    private String endTime;
    @ApiModelProperty(value = "开始页数， 从1开始")
    @NotNull
    private Integer pageNo;
    @NotNull
    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;
}
