package com.cmd.exchange.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class SmsCaptcha implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private int id;
    private String mobile;
    private String type;
    private String code;
    private Integer status;
    Date lastTime;
}
