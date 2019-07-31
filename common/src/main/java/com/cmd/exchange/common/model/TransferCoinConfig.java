package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 持币监控，转入转出的监控数量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransferCoinConfig implements Serializable {

    private static final long serialVersionUID = -1L;
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("币种")
    private String coinName;
    @ApiModelProperty("转入的数量")
    private Integer transferInNumber;
    @ApiModelProperty("转出的数量")
    private Integer transferOutNumber;
    @ApiModelProperty("刷新频次，单位为分中，多少分钟服务器刷监控一次")
    private Integer refreshTime;
}
