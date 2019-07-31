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
public class EthAddress implements Serializable {
    private static final long serialVersionUID = -1661830493897252605L;

    private Integer id;
    private Integer userId;
    private String address;
    private String password;
    private String filename;
    private String credentials;
    private Date createTime;
}
