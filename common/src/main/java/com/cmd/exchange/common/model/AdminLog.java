package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AdminLog implements Serializable {
    private static final long serialVersionUID = -1L;
    private long id;
    // 管理员id
    private int adminId;
    // 操作类型
    private String type;
    // 操作时间
    private int addTime;
    // 操作地址
    private String operationIp;
    // 备注信息
    private String comment;
}
