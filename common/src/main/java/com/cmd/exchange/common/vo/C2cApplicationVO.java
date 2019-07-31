package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class C2cApplicationVO {
    @ApiModelProperty("订单ID")
    private int id;
    @ApiModelProperty("币名")
    private String coinName;
    @ApiModelProperty("单号")
    private String orderNo;
    @ApiModelProperty("用户")
    private String createUser;
    @ApiModelProperty("类型 1：买，2：卖")
    private int type;
    @ApiModelProperty("总数量")
    private BigDecimal amount;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("剩余数量")
    private BigDecimal rAmount;
    @ApiModelProperty("匹配数量")
    private BigDecimal mAmount;
    @ApiModelProperty("交易成功数量")
    private BigDecimal sAmount;
    @ApiModelProperty("状态1：完成，2：匹配中，100：取消")
    private int status;
    @ApiModelProperty("挂单时间")
    private Date createTime;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;

}
