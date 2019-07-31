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
public class CronConfig implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String cronName;
    private Integer cronType;
    private String cronExpression;
    private String cronNo;
    private Integer status;
    private String cronClassName;
    private Date createTime;
    private Date lastTime;
}
