package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("服务器配置列表")
public class ServerConfig implements Serializable {

    private static final long serialVersionUID = -1402494593;

    private Integer id;

    @ApiModelProperty("服务器类型：.交易服 .运营后台服务器 .数据库服。撮合服务器 5.nginx")
    private String serverType;

    @ApiModelProperty("服务器内部ip")
    private String serverInnerAddress;

    @ApiModelProperty("服务器外部地址")
    private String serverExternalAddress;

    @ApiModelProperty("'服务器名'")
    private String serverName;

    @ApiModelProperty("服务器端口")
    private String port;
}
