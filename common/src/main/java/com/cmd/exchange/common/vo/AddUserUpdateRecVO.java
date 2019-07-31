package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AddUserUpdateRecVO {
    @NotNull
    @ApiModelProperty(value = "插入电话号码", required = false)
    private String mobile;
    @NotNull
    @ApiModelProperty(value = "下级电话号码", required = false)
    private String recMobile;
    @NotNull
    @ApiModelProperty(value = "上级电话号码", required = false)
    private String oldMobile;



}
