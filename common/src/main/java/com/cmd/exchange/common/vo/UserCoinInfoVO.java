package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserCoinInfoVO {
    private Integer id;
    private Integer userId;
    @ApiModelProperty(value = "用户账号")
    private String userName;
    @ApiModelProperty(value = "用户真实名称")
    private String realName;
    @ApiModelProperty(value = "币种名称")
    private String coinName;
    private String bindAddress;
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeBalance;
    @ApiModelProperty(value = "转入冻结，另外一种冻结金")
    private BigDecimal receiveFreezeBalance;
    @ApiModelProperty(value = "用户该币种VIP类型，0，不是vip，1：vip")
    private Integer vipType;

    //用户币种状态，0：正常，1：冻结使用
    private Integer status;
    @ApiModelProperty(value = "分组类型")
    private String groupType;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "可用余额字符串")
    private String availableBalanceStr;
    @ApiModelProperty(value = "冻结金额字符串")
    private String freezeBalanceStr;

    public String getAvailableBalanceStr() {
        return availableBalance.toPlainString();
    }

    public String getFreezeBalanceStr() {
        return freezeBalance.toPlainString();
    }
}
