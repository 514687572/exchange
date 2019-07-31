package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

@Data
@Accessors(chain = true)
public class UpdateUserVO {
    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;
    @ApiParam(value = "地区号码", required = false)
    private String areaCode;
    @ApiModelProperty(value = "电话号码", required = false)
    private String mobile;
    @ApiModelProperty(value = "用户状态 1:禁用 0：启用", required = false)
    private Integer status;
    @ApiModelProperty(value = "0：没有实名验证通过，1： 已经实名验证通过，2：已经提交实名验证，待验证，3：实名验证失败，退回用户重新提交")
    private Integer realNameStatus;
    @ApiModelProperty(value = "玩家分组 GROUP_TYPE_BASE:无分组， GROUP_TYPE_A：A组，GROUP_TYPE_B：B组 。。。", required = false)
    private String groupType;
    @ApiModelProperty(value = "用户类型0，正常用户，1：超级节点", required = false)
    private Integer userType;
    @ApiModelProperty(value = "是否有发布OTC权限，1有，其他没有", required = false)
    private Integer isPublishOtc;


}
