package com.cmd.exchange.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRecIntegrVo {

    private Integer pid;

    private BigDecimal pIntegration = BigDecimal.ZERO;

    private Integer teamNum;

    private Integer userId;

    private Integer recNum;

    private String reCode;

    private String recUrl;

    private List<UserRecIntegr> recIntegrList;





}
