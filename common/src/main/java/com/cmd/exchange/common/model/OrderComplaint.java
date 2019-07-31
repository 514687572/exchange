package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("订单申诉")
public class OrderComplaint implements Serializable {
    private static final long serialVersionUID = -547876834346544544L;

    public static final int TYPE_USER = 1;   // 用户申诉
    public static final int TYPE_MERCHANT = 2;   // 兑换商申诉

    public static final int STATUS_PROCESS = 0;   // 申诉中
    public static final int STATUS_COMPLETE = 1;   // 申诉完成
    public static final int STATUS_CANCEL = 2;   // 申诉已经被取消


    private Integer id;

    private Integer userId;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "申诉类型，1：用户申诉，2：兑换商申诉")
    private Integer type;

    @ApiModelProperty(value = "支付凭证的图片链接")
    private String credentialUrls;

    @ApiModelProperty(value = "上传支付凭证的说明")
    private String credentialComment;

    @ApiModelProperty(value = "状态，0：申诉中，1：已完成，2：申诉已经取消")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Integer addTime;

    @ApiModelProperty(value = "完成时间")
    private Integer finishTime;

}