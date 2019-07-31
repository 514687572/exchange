package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserRewardVO {
    @ApiModelProperty(value = "推荐人电话")
    private String phone;
    @ApiModelProperty(value = "推荐人获得的奖励数量")
    private BigDecimal reward;

    public UserRewardVO(String phone, BigDecimal reward) {
        this.phone = phone;
        this.reward = reward;
    }
}
