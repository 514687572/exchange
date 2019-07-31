package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtcOrderDetailVO {
    private Long id;
    private String orderNo;

    private String sellName;
    private String sellRealName;
    private String sellMobile;
    private String sellEmail;
    private String sellIdCard;

    private String buyName;
    private String buyRealName;
    private String buyMobile;
    private String buyEmail;
    private String buyIdCard;

    @ApiModelProperty("状态 1:交易成功，2：匹配中，3：匹配成功, 等待接单,4:已经接单,5:已经付款,6:申诉中,100:已经取消")
    private Integer status;
    @ApiModelProperty("申诉人角色1：买家，2：卖家")
    private Integer appeal;
    @ApiModelProperty("申诉说明")
    private String appealDesc;
    @ApiModelProperty("申诉图片")
    private String credentialUrls;
}
