package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/7
 */
@Data
@Accessors(chain = true)
public class ChatLogVo {
    @ApiModelProperty("卖家id")
    private String sellId;
    @ApiModelProperty("买家id")
    private String buyId;
    @ApiModelProperty("1：买家消息，2：卖家消息")
    private int role;
    @ApiModelProperty("文本消息 RC:TxtMsg 、 图片消息 RC:ImgMsg 、语音消息 RC:VcMsg 、图文消息 RC:ImgTextMsg 、位置消息 RC:LBSMsg 、添加联系人消息 RC:ContactNtf 、提示条通知消息 RC:InfoNtf 、资料通知消息 RC:ProfileNtf 、通用命令通知消息 RC:CmdNtf")
    private String type;
    @ApiModelProperty("消息")
    private String content;
    @ApiModelProperty("联系时间")
    private Date contactTime;

}
