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
public class GoogleSecret implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private Integer userId;
    private String secret;
    private Integer status;
    private Date createTime;
    private Date lastTime;
}
