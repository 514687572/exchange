package com.cmd.exchange.common.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegisterVo {
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "身份证号")
    private String idcard;
    @ApiModelProperty(value = "证件类型(1-身份证,2-护照)")
    private String certType;
    @ApiModelProperty(value = "国家名称")
    private String national;
    @ApiModelProperty(value = "认证图片1")
    private String idCardImg1;
    @ApiModelProperty(value = "认证图片2")
    private String idCardImg2;
    @ApiModelProperty(value = "认证图片3")
    private String idCardImg3;
    @ApiModelProperty(value = "图片验证时间")
    private Date idCardTime;
    @ApiModelProperty(value = "认证状态:0：没有实名验证通过，1： 已经实名验证通过，2：已经提交实名验证，待验证，3：实名验证失败")
    private Integer idCardStatus;

}
