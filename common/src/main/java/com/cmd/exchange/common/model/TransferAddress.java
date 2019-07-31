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
public class TransferAddress implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    //转账地址所属的用户id
    private Integer userId;
    //币种名称
    private String coinName;
    //用户对应币种的转出地址
    private String address;
    //地址的名称，便于记忆
    private String name;
    //状态，0表示正常，1表示禁用
    private Integer status;
    //创建时间
    private Date createTime;
    private Date lastTime;
}