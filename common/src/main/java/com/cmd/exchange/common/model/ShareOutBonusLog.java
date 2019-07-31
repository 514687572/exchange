package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShareOutBonusLog implements Serializable {
    private static final long serialVersionUID = -1L;

    private BigInteger id;

    private Integer userId;
    // 分红的币种
    private String coinName;
    // 分红时用户所持有的平台币个数
    private BigDecimal userBaseCoin;
    //分红的数量（coin_name字段中的币种）
    private BigDecimal userBonus;

    private Date createTime;
}
