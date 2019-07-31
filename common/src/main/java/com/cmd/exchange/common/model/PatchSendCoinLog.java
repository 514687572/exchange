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
public class PatchSendCoinLog implements Serializable {

    private static final long serialVersionUID = -1L;
    private Integer id;
    @ApiModelProperty(value = "用户的账号")
    private String userAccount;
    @ApiModelProperty(value = "发放的数量")
    private String sendCoinNumber;
    @ApiModelProperty(value = "发放的时间")
    private Date sendTime;
    @ApiModelProperty(value = "1:表示成功 0：表示失败")
    private Integer sendStatus = 1;
    @ApiModelProperty(value = "分组对应的Id")
    private String coinName;
    @ApiModelProperty(value = "失败原因，注释")
    private String remark = "批量发币成功";
    @ApiModelProperty(value = "用户Id")
    private String userId;


}
