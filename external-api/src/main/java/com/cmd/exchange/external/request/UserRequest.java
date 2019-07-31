package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRequest extends CommonRequest {
    @NotBlank
    @ApiModelProperty(value = "请求手机号", required = true)
    private String mobile;
    @NotBlank
    @ApiModelProperty(value = "币种名称", required = true)
    private String coinName;
}
