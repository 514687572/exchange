package com.cmd.exchange.common.model;

import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_order
 *
 * @mbg.generated do_not_delete_during_merge Thu Jun 21 10:03:28 CST 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("普通兑换商提交给超级兑换商的订单")
public class SuperMerchantOrder implements Serializable {
    private static final long serialVersionUID = -1243242342556566L;

    private Integer id;

    @ApiModelProperty("订单号，创建订单的时候自动生成")
    private String orderNo;

    @ApiModelProperty("订单类型，1：充值，2：提现")
    private MerchantOrderType type;

    @ApiModelProperty("需要买入或卖出的币种")
    private String coinName;

    @ApiModelProperty("下单的兑换商id")
    private Integer merchantId;

    @ApiModelProperty("接单的超级兑换商id，0表示没有人接单")
    private Integer superMerchantId;

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

    @ApiModelProperty("产生的费用，对于充值（买），最后用户收入是amount-fee,对于提现（卖），最后兑换商收入是amount-fee，只有在最后成交的时候才会扣费用")
    private BigDecimal fee;

    @ApiModelProperty("状态状态1已完成;2待接单,3待付款,4待收款,5,申诉中100已取消")
    private MerchantOrderStatus status;

    @ApiModelProperty("支付凭证的图片链接")
    private String credentialUrls;

    @ApiModelProperty("上传支付凭证的说明")
    private String credentialComment;

    @ApiModelProperty("银行类型,1/BANK:银行卡，2/ALIPAY：支付宝，3/WEIXIN：微信")
    private BankType bankType;

    @ApiModelProperty("收款的银行账户名")
    private String bankUser;

    @ApiModelProperty("收款银行名称")
    private String bankName;

    @ApiModelProperty("收款银行卡号码")
    private String bankNo;

    @ApiModelProperty("取消类型，缩写，只有状态为取消才有意义")
    private String cancelType;

    @ApiModelProperty("取消的时候输入的备注信息")
    private String cancelComment;

    @ApiModelProperty("下单时备注信息")
    private String remake;

    // 该字段不在数据库总
    @ApiModelProperty("下单的兑换商名称")
    private String merchantName;

    // 该字段不在数据库总
    @ApiModelProperty("接单的超级兑换商名称")
    private String superMerchantName;
}