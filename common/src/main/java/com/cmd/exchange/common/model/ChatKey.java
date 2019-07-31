package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ChatKey {
    private int id;
    @ApiModelProperty("用户id")
    private int userId;
    @ApiModelProperty("该用户启动融云sdk需要的token，为避免与系统内token混淆，故命名key")
    private String key;
    @ApiModelProperty("更新时间，冗余设计")
    private Date updateTime;
    @ApiModelProperty("生成时间")
    private Date createTime;
}
