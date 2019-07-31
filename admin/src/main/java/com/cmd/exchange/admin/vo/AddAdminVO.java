package com.cmd.exchange.admin.vo;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.common.utils.EncryptionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 注册信息VO
 * Created by jerry on 2017/12/29.
 */
@ApiModel(description = "管理员新增信息")
@Getter
@Setter
@NoArgsConstructor
public class AddAdminVO {

    @ApiModelProperty(notes = "账号，登录使用", dataType = "string")
    private String userName;

    @ApiModelProperty(notes = "职位名称", dataType = "string")
    private String position;

    @ApiModelProperty(notes = "角色id", dataType = "Integer")
    private Long roleId;

    @ApiModelProperty(notes = "明文密码", dataType = "string")
    private String password;


    public AdminEntity getUserEntity(Long creator) {
        String[] pwd = EncryptionUtil.getEncryptPassword(password);
        AdminEntity entity = new AdminEntity(userName, pwd[0], pwd[1], "", creator);
        entity.setPosition(position);
        return entity;
    }
}
