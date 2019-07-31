package com.cmd.exchange.common.model;


import com.cmd.exchange.common.constants.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Admin implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private String userName;
    private String password;
    // 登录次数
    private int loginTimes;
    // 上次登录的ip
    private String lastLoginIp;
    // 创建时间
    private int addTime;
    // 上次登录时间
    private String lastLoginTime;
    // 状态
    private AdminStatus status;
}
