package com.cmd.exchange.common.model;

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
public class DelayRelease {
    private Integer id;
    private Date releaseTime;  // 释放的时间
    private String coinName;     // 币种名称
    private Integer userId;       // 用户id
    private BigDecimal amount;       // 释放金额
    private Integer hadRelease; // 释放已经释放，0表示没有，1表示已经释放
    private Date createTime;  // 创建时间
    private Date updateTime;  // 修改时间，一般来说，就是释放时间
}
