package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ConfigVO {
    @ApiModelProperty(value = "注册奖励个数")
    @NotNull
    private Integer registerReward;
    @ApiModelProperty(value = "推荐奖励个数")
    @NotNull
    private Integer referrerReward;
    /*@ApiModelProperty(value = "交易返佣， 20代表20%")
    @NotNull
    private BigDecimal tradeReward;*/
    @ApiModelProperty(value = "平台币单价")
    private BigDecimal bcnPrice = BigDecimal.ZERO;
    /*@NotNull
    @ApiModelProperty(value = "排行第一的用户电话")
    @NotBlank
    private String firstRewardPhone;
    @ApiModelProperty(value = "排行第一的平台币数量")
    @NotNull
    private BigDecimal firstRewardAmount;
    @ApiModelProperty(value = "排行第二的用户电话")
    @NotBlank
    private String secondRewardPhone;
    @ApiModelProperty(value = "排行第二的平台币数量")
    @NotNull
    private BigDecimal secondRewardAmount;
    @ApiModelProperty(value = "排行第三的用户电话")
    @NotBlank
    private String thirdRewardPhone;
    @ApiModelProperty(value = "排行第三的平台币数量")
    @NotNull
    private BigDecimal thirdRewardAmount;*/
    @ApiModelProperty(value = "微信名片链接")
    private String wxImageLink;
    @ApiModelProperty(value = "qq链接")
    private String qqLink;
    @ApiModelProperty(value = "1个ETH兑换成平台币（BON）的数量")
    private BigDecimal ethToPlatNum;
    @ApiModelProperty(value = "1个USDT兑换成平台币（BON）的数量")
    private BigDecimal usdtToPlatNum;
    @ApiModelProperty(value = "A组用户的手续费用比率，20表示20%，程序默认为100%")
    private Integer group_A = 100;
    @ApiModelProperty(value = "B组用户的手续费用比率，20表示20%，程序默认为100%")
    private Integer group_B = 100;
    @ApiModelProperty(value = "C组用户的手续费用比率，20表示20%，程序默认为100%")
    private Integer group_C = 100;
    @ApiModelProperty(value = "D组用户的手续费用比率，20表示20%，程序默认为100%")
    private Integer group_D = 100;

    @ApiModelProperty(value = "一级推荐人返佣比例（手续费）")
    @NotNull
    private BigDecimal recUserFeeRate;
    @ApiModelProperty(value = "二级推荐人返佣比例（手续费）")
    @NotNull
    private BigDecimal rec2UserFeeRate;
    /*@ApiModelProperty(value = "转入锁仓币种币币交易买入时释放比例(0.1代表10%)")
    @NotNull
    private BigDecimal receivedReleaseRate;
    @ApiModelProperty(value = "VIP转入锁仓币种币币交易买入时释放比例(0.1代表10%)")
    @NotNull
    private BigDecimal vipReceivedReleaseRate;*/
    @ApiModelProperty(value = "超级节点分润比例（USDT交易手续费费）(0.1代表10%)")
    @NotNull
    private BigDecimal superUserGetFeeRate;
    @ApiModelProperty(value = "分润宝(存钱用户）分润比例（USDT交易手续费费）(0.1代表10%)")
    @NotNull
    private BigDecimal coinSavingUserGetFeeRate;
    @ApiModelProperty(value = "每个交易对2个相同的人每天获取奖励的最多交易奖励次数")
    @NotNull
    private Integer sameTradeRewardMaxTimes;

    @ApiModelProperty(value = "锁仓转账手续费")
    @NotNull
    private Integer lockWarehouseTransferFeeFixed;


    @ApiModelProperty(value = "注册赠送积分给自己的系数")
    @NotNull
    private BigDecimal registerRewardIntegrationA;
    @ApiModelProperty(value = "注册赠送积分给上1级的系数")
    @NotNull
    private BigDecimal registerRewardIntegrationB;
    @ApiModelProperty(value = "注册赠送积分给上2-3级的系数")
    @NotNull
    private BigDecimal registerRewardIntegrationC;
    @ApiModelProperty(value = "注册赠送积分给上4-10级的系数")
    @NotNull
    private BigDecimal registerRewardIntegrationD;
    @ApiModelProperty(value = "投资赠送积分给自己的系数")
    @NotNull
    private BigDecimal investRewardIntegrationA;
    @ApiModelProperty(value = "投资赠送积分给上1级的系数")
    @NotNull
    private BigDecimal investRewardIntegrationB;
    @ApiModelProperty(value = "投资赠送积分给上2-3级的系数")
    @NotNull
    private BigDecimal investRewardIntegrationC;
    @ApiModelProperty(value = "投资赠送积分给上4级及以上无限级的系数")
    @NotNull
    private BigDecimal investRewardIntegrationD;

    @ApiModelProperty(value = "上级需要达到的积分可参与第1种情况分润")
    @NotNull
    private BigDecimal needIntegration1;
    @ApiModelProperty(value = "上级需要达到的积分可参与第2种情况分润")
    @NotNull
    private BigDecimal needIntegration2;
    @ApiModelProperty(value = "上级需要达到的积分可参与第3种情况分润")
    @NotNull
    private BigDecimal needIntegration3;
    @ApiModelProperty(value = "上级需要达到的积分可参与第4种情况分润")
    @NotNull
    private BigDecimal needIntegration4;
    @ApiModelProperty(value = "上级需要达到的积分可参与第5种情况分润")
    @NotNull
    private BigDecimal needIntegration5;
    @ApiModelProperty(value = "上级需要达到的积分可参与第6种情况分润")
    @NotNull
    private BigDecimal needIntegration6;
    @ApiModelProperty(value = "上级需要达到的积分可参与第7种情况分润")
    @NotNull
    private BigDecimal needIntegration7;
    @ApiModelProperty(value = "上级需要达到的积分可参与第8种情况分润")
    @NotNull
    private BigDecimal needIntegration8;
    @ApiModelProperty(value = "上级需要达到的积分可参与第9种情况分润")
    @NotNull
    private BigDecimal needIntegration9;
    @ApiModelProperty(value = "上级需要达到的积分可参与第10种情况分润")
    @NotNull
    private BigDecimal needIntegration10;
    @ApiModelProperty(value = "上级达到第1种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel1;
    @ApiModelProperty(value = "上级达到第2种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel2;
    @ApiModelProperty(value = "上级达到第3种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel3;
    @ApiModelProperty(value = "上级达到第4种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel4;
    @ApiModelProperty(value = "上级达到第5种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel5;
    @ApiModelProperty(value = "上级达到第6种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel6;
    @ApiModelProperty(value = "上级达到第7种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel7;
    @ApiModelProperty(value = "上级达到第8种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel8;
    @ApiModelProperty(value = "上级达到第9种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel9;
    @ApiModelProperty(value = "上级达到第10种情况分润的分润系数")
    @NotNull
    private BigDecimal needLevel10;

    @ApiModelProperty(value = "上1级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel1;
    @ApiModelProperty(value = "上2级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel2;
    @ApiModelProperty(value = "上3级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel3;
    @ApiModelProperty(value = "上4级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel4;
    @ApiModelProperty(value = "上5级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel5;
    @ApiModelProperty(value = "上6级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel6;
    @ApiModelProperty(value = "上7级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel7;
    @ApiModelProperty(value = "上8级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel8;
    @ApiModelProperty(value = "上9级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel9;
    @ApiModelProperty(value = "上10级推荐人分润系数")
    @NotNull
    private BigDecimal referrerLevel10;

    @ApiModelProperty(value = "上1级推荐人分润系数")
    @NotNull
    private BigDecimal childIntegration1;
    @ApiModelProperty(value = "下2级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration2;
    @ApiModelProperty(value = "下3级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration3;
    @ApiModelProperty(value = "下4级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration4;
    @ApiModelProperty(value = "下5级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration5;
    @ApiModelProperty(value = "下6级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration6;
    @ApiModelProperty(value = "下7级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration7;
    @ApiModelProperty(value = "下8级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration8;
    @ApiModelProperty(value = "下9级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration9;
    @ApiModelProperty(value = "下10级参与分润的积分要求")
    @NotNull
    private BigDecimal childIntegration10;


}
