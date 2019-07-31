package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "批量拨币的错误返回信息")
@Getter
@Setter
@NoArgsConstructor
public class PatchErrorVO {
    @ApiModelProperty("用户账户")
    private String userAccount;
    @ApiModelProperty("错误原因")
    private String remark;
}
