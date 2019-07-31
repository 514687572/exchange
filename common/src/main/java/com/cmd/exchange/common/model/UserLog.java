package com.cmd.exchange.common.model;

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
public class UserLog implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private long id;
    private int userId;
    // 操作类型
    private String type;
    // 操作时间
    private Date operationTime;
    // 操作地址
    private String operationIp;
    // 花费时间
    private int usedTime;
    // 是否成功
    private int success;
    // 备注信息
    private String comment;
}
