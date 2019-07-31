package com.cmd.exchange.admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/5.
 */
@Getter
@Setter
public class RoleResEntity implements Serializable {

    private static final long serialVersionUID = -695119752701486318L;

    @ApiModelProperty(notes = "角色id", dataType = "Integer")
    private Integer roleId;

    @ApiModelProperty(notes = "资源id", dataType = "Integer")
    private Integer resId;
}
