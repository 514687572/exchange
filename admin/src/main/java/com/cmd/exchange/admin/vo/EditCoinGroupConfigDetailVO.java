package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "管理员新增信息")
@Getter
@Setter
@NoArgsConstructor
public class EditCoinGroupConfigDetailVO {

    @ApiModelProperty(value = "应配置表Id")
    @NotNull
    private Long id;
    @ApiModelProperty(value = "手续费")
    private String conValue;
    @ApiModelProperty(value = "备注")
    private String remark;
}
