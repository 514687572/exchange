package com.cmd.exchange.external.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ChangeUserCoinRequest extends CommonRequest {
    @NotNull
    @ApiModelProperty(value = "转入的资产", required = true)
    private BigDecimal changeBalance;
    @NotNull
    @ApiModelProperty(value = "转入的手机号", required = true)
    private String mobile;
    @NotNull
    @ApiModelProperty(value = "转入的币种名称", required = true)
    private String coinName;


}
