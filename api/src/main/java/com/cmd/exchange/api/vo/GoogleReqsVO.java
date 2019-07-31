package com.cmd.exchange.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@ApiModel(description = "请求二维码返回对象")
@Getter
@Setter
@NoArgsConstructor
public class GoogleReqsVO implements Serializable {

    @ApiModelProperty(value = "秘钥")
    private String secret;

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty("二维码参数")
    private String url;


}
