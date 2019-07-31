package com.cmd.exchange.common.vo;

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
public class TokenVo {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("token过期时间")
    private Date expiretTime;
    @ApiModelProperty("是否绑定邮箱")
    private boolean bindEmail;
    @ApiModelProperty("是否绑定手机")
    private boolean bindMobile;
    @ApiModelProperty("是否绑定支付密码")
    private boolean bindPaypwd;
    @ApiModelProperty("是否绑定google验证器")
    private boolean bindGoogleSecret;
    @ApiModelProperty("是否实名认证")
    private boolean realNameAuth;
    //用户在web端或移动端，输入真实姓名、身份证号、所属国家，通过第三方接口进行认证。如果认证通过，系统自动设置C1认证通过
    @ApiModelProperty("c1认证是否通过")
    private boolean c1Authenticated;
    //用户在web端，上传身份照片（三张）并提交到后台进行审核。人工审核通过后，用户方可在web端和与移动端进行C2C交易、充币、数字资产转出操作
    @ApiModelProperty("c2认证是否通过")
    private boolean c2Authenticated;

}
