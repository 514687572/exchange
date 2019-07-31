package com.cmd.exchange.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferOutRequestVO {
    @NotBlank
    @ApiModelProperty("币种")
    String coinName;
    @NotBlank
    @ApiModelProperty("对方地址")
    String toAddress;
    @NotNull
    @ApiModelProperty("数量")
    BigDecimal amount;

    @ApiModelProperty("备注")
    String comment;
    @NotBlank
    @ApiModelProperty("验证码")
    String validCode;
    @NotBlank
    @ApiModelProperty("交易密码")
    String paypassword;
}
