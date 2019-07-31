package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@ApiModel(description = "服务器信息")
@Getter
@Setter
@NoArgsConstructor
public class ServiceMontitorVO implements Serializable {
    @ApiModelProperty("服务器名称")
    private String serviceName;

    @ApiModelProperty("服务器状态 true:表示正常，false:表示异常")
    private boolean serviceStatus;

    @ApiModelProperty("内部ip")
    private String internalIp;

    @ApiModelProperty("外部ip")
    private String externalIp;

    @ApiModelProperty("内部端口")
    private String internalPort = "22";

    @ApiModelProperty("外部端口")
    private String externalPort;

    @ApiModelProperty("异常内容")
    private String exceptionContext;

}
