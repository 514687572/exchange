package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "拓扑关系图玩家信息")
@Getter
@Setter
@NoArgsConstructor
public class TopologyVO implements Serializable {

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("用户注册时间")
    private Date registerTime;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("用户手机号码")
    private String mobile;

    @ApiModelProperty("是否有下级别")
    private Boolean subordinateBool = false;

    @ApiModelProperty("用户下级列表")
    private List<TopologyVO> topologyVOList = new ArrayList<>();
}
