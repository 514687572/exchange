package com.cmd.exchange.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvestDetailVO {
    private String userName;//用户登录名称
    private BigDecimal amount;//投资额

}
