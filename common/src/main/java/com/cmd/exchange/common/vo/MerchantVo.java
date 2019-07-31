package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.BankType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MerchantVo {
    @ApiModelProperty(value = "兑换商id", required = true)
    private Integer id;
    @ApiModelProperty(value = "兑换商名称")
    private String name;
    @ApiModelProperty(value = "兑换商邮箱地址")
    private String email;
    @ApiModelProperty(value = "经营的币种名称")
    private String coinName;
    @ApiModelProperty(value = "兑换商类型，1：普通兑换商，2：超级兑换商", required = true)
    private Integer type;
    @ApiModelProperty(value = "银行类型，BANK：银行，ALIPAY：支付宝，WEIXIN：微信", required = true)
    private BankType bankType;
    @ApiModelProperty(value = "兑换商银行名称", required = true)
    private String bankName;
    @ApiModelProperty(value = "兑换商银行账户名", required = true)
    private String bankUser;
    @ApiModelProperty(value = "兑换商银行卡号码", required = true)
    private String bankNo;
    @ApiModelProperty(value = "工作状态（表示兑换商自己是否接单），1：正常工作，0：不工作不接单")
    private Integer workingStatus;
}
