package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
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
public class DispatchReqVO {
    @ApiModelProperty(value = "拨币配置编号")
    private Integer dispatchId;
    @ApiModelProperty(value = "备注")
    private String comment;

    private List<DispatchVO> list;
}
