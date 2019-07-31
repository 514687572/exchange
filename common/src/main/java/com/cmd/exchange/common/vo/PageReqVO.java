package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageReqVO {

    private @ApiModelProperty()
    Integer pageNo;
    private @ApiModelProperty()
    Integer pageSize;
    private @ApiModelProperty()
    String startTime;
    private @ApiModelProperty()
    String endTime;
}
