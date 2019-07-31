package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.TransferType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

//转账历史查询条件
@Data
@Accessors(chain = true)
public class CoinTransferReqVO extends PageReqVO {
    @ApiModelProperty("币种名称")
    @NotBlank
    private String coinName;

    @ApiModelProperty("类型")
    private TransferType type;

    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("类型")
    private String mobile;

    @ApiModelProperty("用户名")
    private Integer userId;

    @ApiModelProperty("")
    private String sourceAddress;

    @ApiModelProperty("")
    private String targetAddress;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("用户类型")
    private String groupType;


}
