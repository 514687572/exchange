package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class MachineVo implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "矿机名称")
    private String machineName;
    @ApiModelProperty(value = "每日产值")
    private BigDecimal machineGive;
    @ApiModelProperty(value = "赠送币种")
    private String machineCoin;
    @ApiModelProperty(value = "矿机类型")
    private Integer machineType;
    @ApiModelProperty(value = "启动或关闭(0关闭，1开启)")
    private Integer status;
    private Date addTime;

    @NotNull
    private Integer pageNo;
    @NotNull
    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;


}
