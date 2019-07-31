package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinGroupConfig implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    @ApiModelProperty(value = "分组对应的Id")
    private Integer groupId;
    @ApiModelProperty(value = "币种名字")
    private String coinName;
    @ApiModelProperty(value = "对应的配置值（如手续费")
    private String conValue;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "0:未禁用 1.禁用")
    private Integer status = 0;


}
