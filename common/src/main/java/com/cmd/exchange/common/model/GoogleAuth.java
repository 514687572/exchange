package com.cmd.exchange.common.model;

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
public class GoogleAuth implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String mobile;
    private String type;
    private String code;
    private Integer status;
    private Date lastTime;
}
