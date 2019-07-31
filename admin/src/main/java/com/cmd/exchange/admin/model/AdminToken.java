package com.cmd.exchange.admin.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdminToken implements Serializable {

    private Integer id;

    private Integer userId;

    private String token;

    private Date expireTime;

    private Date lastUpdateTime;

}
