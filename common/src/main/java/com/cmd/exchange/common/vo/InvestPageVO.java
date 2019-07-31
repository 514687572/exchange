package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvestPageVO {
//    private Integer id;
    private BigDecimal availableBalance;//可用USDT
    private BigDecimal coinAmount;//投资总额
    private String contentInvest;//投资收益文本
    private String contentIntegral;//积分收益文本
    private String contentUnit;//起投10USDT文本

}
