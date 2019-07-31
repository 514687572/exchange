package com.cmd.exchange.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OfficialGroupVO implements Serializable {

    @ApiModelProperty(value = "微信官方名片图片链接")
    private String wxImageLink;
    @ApiModelProperty(value = "官方QQ群链接")
    private String qqLink;

}
