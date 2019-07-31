package com.cmd.exchange.common.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserToken implements Serializable {

    private static final long serialVersionUID = -1661830493897252605L;

    private Integer id;

    private Integer userId;

    private String token;

    private Date expireTime;

    private Date lastUpdateTime;

}
