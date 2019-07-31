package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
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
@ApiModel("在固定的时间修改用户资产")
public class ChangeCoin {
    public static final int STATUS_NOT_CHANGE = 0;  // 没有修改
    public static final int STATUS_CHANGED = 1;  // 已经修改

    private Long id;
    // 期望的资金变化的时间
    private Date wantChangeTime;
    // 币种名称
    private String coinName;
    private int userId;
    // 是否已经修改，0表示没有修改，1表示修改了
    private int status;
    // 修改的可用余额
    private BigDecimal changeAvailable;
    // 修改的冻结金
    private BigDecimal changeFreeze;
    // 变化原因，用于账单
    private String reason;
    // 备注信息，用于账单，记录一些额外信息，比如变化的订单号等
    private String comment;
    // 记录场景时间
    private Date createTime;
    // 真实的币变化时间，就是修改t_user_coin的时间
    private Date updateTime;
}
