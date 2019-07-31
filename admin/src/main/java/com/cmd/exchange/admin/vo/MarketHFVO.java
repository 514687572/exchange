package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "高频交易列表返回对象")
@Getter
@Setter
@NoArgsConstructor
public class MarketHFVO implements Serializable {

    @ApiModelProperty("买入次数")
    private Integer buyCount;
    @ApiModelProperty("卖出次数")
    private Integer sellCount;
    @ApiModelProperty("用户Id")
    private Integer userId;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("刷新时间")
    private Date freshDate;
    @ApiModelProperty("买入卖出的币种")
    private String coinName;
    @ApiModelProperty("交换的币种")
    private String settlementCurrency;
    @ApiModelProperty("交易总次数")
    private Integer tradeAllCount;

}
