package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户节点奖励
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserNodeReward implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "奖励节点名称")
    private String nodeName;
    @ApiModelProperty(value = "奖励同步时间")
    private Integer synRewardTime;
    @ApiModelProperty(value = "奖励比率")
    private BigDecimal rewardRate;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "奖励节点状态")
    private Integer status;
    @ApiModelProperty(value = "智能合约奖励同步时间")
    private Integer synSmartTime;


}
