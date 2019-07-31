package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TradeWarningDetail implements Serializable {

    private static final long serialVersionUID = -1L;

    //主键
    private Integer id;

    //用户Id
    private Integer userId;

    //创建时间
    private Date creatTime = new Date();

    //'是否确认：0：待确认 1：已经确认 2：拒绝 3.其他'
    private Integer sureType;

    //警告类型
    private String warningType;

    //对应报警配置表Id
    private Integer tradearningd;
    //审核时间
    private Date auditTime;

    //对应的交易Id
    private Long tradeogd;

}
