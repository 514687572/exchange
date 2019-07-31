package com.cmd.exchange.common.vo;

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
public class ReferrerVO implements Serializable {
    private static final long serialVersionUID = -1L;

    private String mobile;
    private String email;
    @ApiModelProperty(value = "注册时间")
    private Date registerTime;
    @ApiModelProperty(value = "认证状态：0：没有实名验证通过，1： 已经实名验证通过，2：已经提交实名验证，待验证，3：实名验证失败，退回用户重新提交")
    private int idCardStatus;
}
