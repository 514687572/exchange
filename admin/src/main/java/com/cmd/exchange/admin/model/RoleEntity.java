package com.cmd.exchange.admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/4.
 */
@Setter
@Getter
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -1529215692130234021L;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("创建者id")
    private Integer creator;

    @ApiModelProperty("创建者名称")
    private String creatorName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("状态 (0:禁用 1:启用)")
    private Integer status;


}
