package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDeductionIntegralRequest extends CommonRequest {
    @NotNull
    @ApiModelProperty(value = "多个币种积分扣除，json集合", required = true)
    private String userDeductionIntegrals;

    @NotNull
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @NotNull
    @ApiModelProperty(value = "订单号", required = true)
    private String orderNum;

}
