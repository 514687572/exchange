package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "申购平台币的返回对象")
@Getter
@Setter
@NoArgsConstructor
public class PlatCoinConvertVO {
    @ApiModelProperty(value = "下次开始申购的时间（如果当前是申购状态，是本次申购的开始时间）")
    private int nextTimeStart;
    @ApiModelProperty(value = "下次开始申购的结束（如果当前是申购状态，是本次申购的结束时间）")
    private int nextTimeEnd;
    @ApiModelProperty(value = "判断当前是否是申购时间，true表示是申购时间）")
    private boolean nowCanBuy;
}
