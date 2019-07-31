package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransferUsers {



    private Integer id;
    private Integer identity;

    private String name;
    private String mobile;
    private String email;
    private String password;

    private String zoneDescription;//区号

    private Integer proprieter;//社区长 1:是

    private Integer status;//状态 1:正常;2:封号;3:删除;4:清除回收

    private Integer istransfer;




}
