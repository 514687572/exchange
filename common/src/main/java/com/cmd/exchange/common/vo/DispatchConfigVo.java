package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.DispatchConfigStatus;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.invoke.SerializedLambda;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DispatchConfigVo {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("任务id")
    private Integer cronId;
    @ApiModelProperty("任务名称")
    private String cronName;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("锁仓名称")
    private String lockName;
    @ApiModelProperty("状态 SHOW 显示 HIDE 隐藏")
    private DispatchConfigStatus status;
    @ApiModelProperty("锁仓比例")
    private BigDecimal lockRate;
    @ApiModelProperty("释放比例")
    private BigDecimal freeRate;
    @ApiModelProperty("创建时间")
    private Date createTime;
    private Date lastTime;
}
