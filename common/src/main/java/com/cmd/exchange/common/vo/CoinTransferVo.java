package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.SendCoinStatus;
import com.cmd.exchange.common.enums.TransferType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CoinTransferVo {
    @ApiModelProperty("数据库id")
    private Integer id;
    @JsonIgnore
    private Integer userId;
    @ApiModelProperty("用户电话")
    private String mobile;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("来源地址")
    private String sourceAddress;
    @ApiModelProperty("目标地址")
    private String targetAddress;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("交易hash")
    private String txid;
    @ApiModelProperty("数量")
    private BigDecimal amount;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("转账时间")
    private Date transferTime;
    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("转账时间")

    @JsonIgnore
    private Integer receivedTime;

    private SendCoinStatus status;

    @ApiModelProperty("类型")
    private TransferType type;
    @ApiModelProperty("用户类型")
    private String groupType;
    @ApiModelProperty("用户类型名")
    private String groupName;
    @ApiModelProperty("转账类型:1，内部 2.外部")
    private String tradeType;

    @ApiModelProperty("是否内部转账 true：表示内部转账 false:表示外部转账")
    private boolean isInnerTransfer;

}
