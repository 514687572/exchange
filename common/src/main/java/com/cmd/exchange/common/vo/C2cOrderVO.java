package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class C2cOrderVO {

    @ApiModelProperty("订单ID")
    private int id;
    @ApiModelProperty("币名")
    private String coinName;
    @ApiModelProperty("单号")
    private String orderNo;
    @ApiModelProperty("买家账号")
    private String buyUser;
    @ApiModelProperty("卖家账号")
    private String sellUser;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("数量")
    private BigDecimal number;
    @ApiModelProperty("总额")
    private BigDecimal amount;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("成交时间")
    private Date finishTime;
    @ApiModelProperty("交易货币")
    private String legalName;
    @ApiModelProperty("状态 1:交易成功，2：匹配中，3：匹配成功, 等待接单,4:已经接单,5:已经付款,6:申诉中,100:已经取消")
    private int status;
    @ApiModelProperty("凭证上传时间")
    private Date uploadCredentialTime;
    @ApiModelProperty("订单完成标识0：未完成，无需标识，1：用户确认，2：后台确认")
    private int tag;
    @ApiModelProperty("撮合时间")
    private Date createTime;

    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;


}
