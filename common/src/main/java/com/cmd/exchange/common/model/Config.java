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
public class Config implements Serializable {
    private static final long serialVersionUID = -1L;
    private long id;
    // 属性名
    private String confName;
    // 属性值
    private String confValue;
    // 属性备注信息
    private String comment;
}
