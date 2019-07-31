package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 币的储蓄（锁仓）
 */
@Data
@Accessors(chain = true)
@ApiModel("存钱记录，用于记录和延迟到达")
public class CoinSavingsRecord {
    public static final int TYPE_SAVE = 0;   // 存钱
    public static final int TYPE_GET = 1;   // 取钱

    public static final int STATUS_NO_ARRIVAL = 0;  // 没到账
    public static final int STATUS_ARRIVAL = 1;  // 已到账

    private BigInteger id;
    private Integer userId;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("类型，0转入分润宝，1转出分润宝")
    private Integer type;
    @ApiModelProperty("存储/锁仓的资金")
    private BigDecimal amount;
    @ApiModelProperty("转入转出时间")
    private Date addTime;
    @ApiModelProperty("期望/预计到达时间")
    private Date wantArrivalTime;
    @ApiModelProperty("实际预计到达时间")
    private Date realArrivalTime;
    @ApiModelProperty("状态，0，没到账，1：已经到账")
    private Integer status;

    /////////////////////////////////////
    // 以下字段不在数据库里面
    @ApiModelProperty("userName")
    private String userName;
}
