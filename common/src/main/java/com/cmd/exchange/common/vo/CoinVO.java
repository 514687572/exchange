package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinVO {
    private Integer id;
    // 币种名称
    @NotBlank
    private String name;
    // 币种符号
    @NotBlank
    private String symbol;
    // 币种类别,参考CoinCategory
    @NotBlank
    private String category;
    // 默认显示名
    @NotBlank
    private String displayName;
    // 所有的显示名，用于多语言
    @NotBlank
    private String displayNameAll;
    // 大图片
    @NotBlank
    private String image;
    // 小图片
    @NotBlank
    private String icon;
    // 排序顺序
    @NotNull
    private Integer sort;
    // 币的状态
    @NotNull
    private Integer status;
    // 区块链服务器地址
    @NotBlank
    private String serverAddress;
    // 服务器断开
    @NotNull
    private Integer serverPort;
    // 区块链服务器用户
    private String serverUser;
    // 区块链服务器密码
    private String serverPassword;
    // 区块链服务器合约地址，目前只有类型是token才有用
    private String contractAddress;
    // 区块链服务器其它参数信息
    private String coinSelfParameter;
    @ApiModelProperty(value = "主账户地址")
    private String coinBase;
    @ApiModelProperty(value = "是否是转入直接冻结，是的话这个币转入的时候，会到转入冻结的资金里面")
    private Integer receivedFreeze;
    @ApiModelProperty(value = "普通用户交易时释放锁仓金额的比例")
    private BigDecimal releaseRate;
    @ApiModelProperty(value = "VIP用户交易时释放锁仓金额的比例")
    private BigDecimal releaseRateVip;
    @ApiModelProperty(value = "每天释放的最多次数")
    private Integer maxDayRelNum;
    @ApiModelProperty(value = "每月最大释放的量")
    private BigDecimal maxMonthRel;


    // 提现手续费
    @ApiModelProperty(value = "提现手续费， 0.1代表10%")
    @NotNull
    private BigDecimal transferFeeRate;
    // 最小
    @ApiModelProperty(value = "提现最小金额")
    @NotNull
    private BigDecimal transferMinAmount;
    // 最大
    @ApiModelProperty(value = "提现最大金额")
    @NotNull
    private BigDecimal transferMaxAmount;
    @ApiModelProperty("转账收取固定值")
    private BigDecimal transferFeeStatic;
    @ApiModelProperty("手续费方式0:百分比 1:固定")
    private Integer transferFeeSelect;

    @ApiModelProperty("每天释放锁仓金额比例上限")
    private BigDecimal releaseDayLimitRate;
    @ApiModelProperty("锁仓,转入冻结释放策略,交易时释放策略，0：不释放，1买释放，2卖释放，3买卖都释放")
    private Integer releasePolicy;
    @ApiModelProperty("卖出时普通用户不同价格释放比例配置")
    private BigDecimal sellReleaseConf;
    @ApiModelProperty("用户挂卖单是判断用户的卖单是否大于等于下单时间卖一的5%（比例在后台设置），若小于于卖一5%则此单不释放")
    private BigDecimal sellPriceRate;

}
