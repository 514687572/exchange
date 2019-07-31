package com.cmd.exchange.common.model;

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
public class UserMachine implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "矿机ID")
    private Integer machineId;
    @ApiModelProperty(value = "矿机名称")
    private String machineName;
    @ApiModelProperty(value = "昨日收益")
    private BigDecimal yesterdayIncome;
    @ApiModelProperty(value = "总收益")
    private BigDecimal totalIncome;
    @ApiModelProperty(value = "赠送币种")
    private String machineCoin;
    @ApiModelProperty(value = "启动或关闭(0关闭，1开启)")
    private Integer machineStatus;
    @ApiModelProperty(value = "昨日收益时间")
    private Date yesterdayTime;
    @ApiModelProperty(value = "是否开启挖矿 1不开始挖矿，2开启挖矿（默认不开启）")
    private Integer isOpen;

    private Integer machineType;

    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date lastTime;

    private Date startLastTime;
    private Date endLastTime;

}
