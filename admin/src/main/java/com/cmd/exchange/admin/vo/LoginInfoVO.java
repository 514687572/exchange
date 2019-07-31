package com.cmd.exchange.admin.vo;


import com.cmd.exchange.admin.model.AdminEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 用户登录信息VO
 * Created by jerry on 2017/12/29.
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginInfoVO extends AdminEntity {

    @ApiModelProperty(notes = "token", dataType = "string")
    private String token;

    public LoginInfoVO(AdminEntity entity, String accessToken) {
        super();
        BeanUtils.copyProperties(entity, this);
        this.token = accessToken;
    }
}
