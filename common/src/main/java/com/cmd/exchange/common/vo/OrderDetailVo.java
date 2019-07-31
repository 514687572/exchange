package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailVo {
    @ApiModelProperty("订单id")
    private Integer id;

    @ApiModelProperty("订单号，创建订单的时候自动生成")
    private String orderNo;

    @ApiModelProperty("订单类型，1/BUY：充值，2/SELL：提现")
    private MerchantOrderType type;

    @ApiModelProperty("买入或卖出的币种")
    private String coinName;

    @ApiModelProperty("下单的用户id")
    private Integer userId;
    @ApiModelProperty("下单的用户账号")
    private String userName;
    @ApiModelProperty("下单的用户真实名称")
    private String realName;

    @ApiModelProperty("接单的兑换商id")
    private Integer merchantId;
    @ApiModelProperty("接单的兑换商名称")
    private String merchantName;

    @ApiModelProperty("需要充值或提现的总量")
    private BigDecimal amount;

    @ApiModelProperty("用于结算的货币，一般是CNY或USD")
    private String settlementCurrency;

    @ApiModelProperty("下单时价格")
    private BigDecimal price;

    @ApiModelProperty("订单创建时间")
    private Integer addTime;

    @ApiModelProperty("接单时间")
    private Integer acceptTime;

    @ApiModelProperty("上传凭证时间")
    private Integer proofTime;

    @ApiModelProperty("结束时间，是订单完成或者取消的时间")
    private Integer finishTime;

    @ApiModelProperty("产生的费用")
    private BigDecimal fee;

    @ApiModelProperty("状态状态COMPLETE/1已完成;WAIT_ACCEPT/2待接单,WAIT_PAY/3待付款,PAYED/4待收款,COMPLAIN/5,申诉中,CANCEL/100已取消")
    private MerchantOrderStatus status;

    @ApiModelProperty("支付凭证的图片链接")
    private String credentialUrls;

    @ApiModelProperty("上传支付凭证的说明")
    private String credentialComment;

    @ApiModelProperty("银行类型,BANK/0：银行，ALIPAY/1:阿里,WEIXIN/2:微信")
    private BankType bankType;

    @ApiModelProperty("收款的银行账户名")
    private String bankUser;

    @ApiModelProperty("收款银行名称")
    private String bankName;

    @ApiModelProperty("收款银行卡号码")
    private String bankNo;

    @ApiModelProperty("收款码，支付二维码的图片")
    private String receiptCode;

    @ApiModelProperty("取消类型，缩写，只有状态为取消才有意义")
    private String cancelType;

    @ApiModelProperty("取消的时候输入的备注信息")
    private String cancelComment;

    @ApiModelProperty("下单时备注信息")
    private String remake;

    @ApiModelProperty("订单超时的秒数")
    private int timeoutSecond;

    @ApiModelProperty("超时的时间戳")
    private int timeoutTimestamp;

    @ApiModelProperty("用户电话")
    private String userPhone;

    @ApiModelProperty("兑换商电话")
    private String merchantPhone;

    @ApiModelProperty("超级兑换商电话")
    private String superMerchantPhone;

    @ApiModelProperty("申诉的用户id")
    private Integer appealUserId;

    @ApiModelProperty("是否自己的申诉")
    private boolean isMyAppeal;
}
