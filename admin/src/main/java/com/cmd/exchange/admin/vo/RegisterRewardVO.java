package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class RegisterRewardVO {
    private Integer id;
    private String coinName;
    @ApiModelProperty("注册用户账号")
    private String userName;
    @ApiModelProperty("注册用户电话")
    private String mobile;
    @ApiModelProperty("注册用户真实姓名")
    private String realName;
    @ApiModelProperty("注册奖励数量")
    private BigDecimal rewardAmount;
    @ApiModelProperty("注册时间")
    private Date rewardTime;
}
