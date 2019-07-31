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
public class UserRecIntegr {

    private Integer userId;

    private BigDecimal integration = BigDecimal.ZERO;
}
