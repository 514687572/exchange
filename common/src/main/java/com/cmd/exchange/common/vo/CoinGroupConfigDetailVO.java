package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinGroupConfigDetailVO {

    @ApiModelProperty("对应的分组")
    private Long id;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("费率")
    private String conValue;


}
