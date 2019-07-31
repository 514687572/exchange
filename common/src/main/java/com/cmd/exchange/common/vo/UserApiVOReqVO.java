package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserApiVOReqVO extends PageReqVO {
    private Integer userId;
    @ApiModelProperty(value = "用户名", required = false)
    private String userName;
    //0：禁用，1：审核通过 2: 审核不通过，3 审核中',
    //PASS:通过 DENY：审核失败 PENDING：审核中 DISABLED：禁用
    @ApiModelProperty(value = "状态， DISABLED: 禁用, SUCCESS:审核通过 FAILED:审核失败 PENDING: 审核中", required = false)
    private String status;
    @ApiModelProperty(value = "真实姓名", required = false)
    private String realName;
    @ApiModelProperty(value = "mobile", required = false)
    private String mobile;

}
