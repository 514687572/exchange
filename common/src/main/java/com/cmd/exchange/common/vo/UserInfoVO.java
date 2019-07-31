package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoVO {
    private Integer id;
    @ApiModelProperty(value = "用户登录名称，全表唯一")
    private String userName;
    @ApiModelProperty(value = "国家代码（86）")
    private String areaCode;
    @ApiModelProperty(value = "用户绑定的手机号码")
    private String mobile;
    @ApiModelProperty(value = "邀请码")
    private String inviteCode;
    @ApiModelProperty(value = "推荐人id")
    private Integer referrer;//
    @ApiModelProperty(value = "推荐人电话")
    private String referrerMobile;
    //
    @ApiModelProperty(value = "用户真实姓名")
    private String realName;
    //实名验证的证件类型，0是默认表示没有认真，1表示中国居民身份证，2是国际护照
    private Integer idCardType;
    //实名验证的证件号码
    private String idCard;
    //实名验证状态，0，未实名验证，1，等待身份证验证，2：身份证号码验证通过
    private int realNameStatus;
    //实名验证的图片1
    private String idCardImg1;
    //实名验证的图片2
    private String idCardImg2;
    //实名验证的图片3
    private String idCardImg3;
    //图片实名验证的状态，0：没有实名验证通过，1： 已经实名验证通过，2：已经提交实名验证，待验证，3：实名验证失败，退回用户重新提交
    private Integer idCardStatus;
    //图片实名验证时间
    private Date idCardTime;
    //当前实名验证的一些额外备注信息，比如验证失败原因
    private String idCardInfo;
    //用户登录次数
    private Integer loginTimes;
    //注册时的ip
    private String registerIp;
    //最近一次登录的用户ip
    private String lastLoginIp;
    //注册时间
    private Date registerTime;
    //最后一次登录时间
    private Date lastLoginTime;
    //用户状态，0：正常，1：被禁用
    private Integer status;
    //邮箱
    private String email;
    //最后一次用户修改资料的时间，登录不修改这个时间
    private Date updateTime;
    //推荐码URL
    private String referrerUrl;
    //推荐人数
    private Integer referrerCount;
    //推荐人中已经通过KYC的数量
    private Integer referrerKYCCount;
    //推荐奖励总和
    private BigDecimal referrerReward;
    //推荐奖励所有包括未认证的
    private BigDecimal referrerRewardAll;
    //用户分组
    @ApiModelProperty(value = "用户分组")
    private String groupType;
    //用户分组名
    @ApiModelProperty(value = "组名")
    private String groupName;
    @ApiModelProperty(value = "用户类型0，正常用户，1：超级节点", required = false)
    private Integer userType;
    @ApiModelProperty(value = "是否有发布OTC权限，1有，其他没有", required = false)
    private Integer isPublishOtc;
    @ApiModelProperty(value = "用户头像" ,required = false)
    private String image;
    //用户积分总和
    private BigDecimal integration;
}
