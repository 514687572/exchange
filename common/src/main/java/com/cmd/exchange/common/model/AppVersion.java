package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVersion implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "code")
    private Integer code;
    @ApiModelProperty(value = "版本名称")
    private String versionName;
    @ApiModelProperty(value = "平台名称")
    private String platform;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "备注说明")
    private String content;
    @ApiModelProperty(value = "是否强制升级:1强制")
    private Integer force;
    private Date createTime;
    private Date lastTime;
}
