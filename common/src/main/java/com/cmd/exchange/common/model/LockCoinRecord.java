package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("锁仓记录，锁用户的可用金分期返还")
public class LockCoinRecord {
    private BigInteger id;
    @ApiModelProperty("锁仓批次")
    private String lockNo;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("锁仓比例")
    private BigDecimal lockRate;
    @ApiModelProperty("锁仓币种个数")
    private BigDecimal lockAmount;
    @ApiModelProperty("每次释放比例")
    private BigDecimal releaseRate;
    @ApiModelProperty("每次释放币种个数")
    private BigDecimal releaseAmount;
    @ApiModelProperty("释放周期名称")
    private String cronName;
    @ApiModelProperty("cron表达式")
    private String cronExpression;
    @ApiModelProperty("创建时间")
    private Date createTime;

    /////////////////////////////////////////////////////////////
    // 以下字段不在数据库中
    @ApiModelProperty("用户名称")
    private String userName;
}
