package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class CandleVo implements java.io.Serializable {
    private static final long serialVersionUID = 42292792293453794L;
    @ApiModelProperty("时间， unix时间戳（毫秒）")
    private Long time;
    @ApiModelProperty
    private BigDecimal low;
    @ApiModelProperty
    private BigDecimal high;
    @ApiModelProperty
    private BigDecimal open;
    @ApiModelProperty
    private BigDecimal close;
    @ApiModelProperty("数量")
    private BigDecimal volume;

    @ApiModelProperty("时间格式 2015-09-28 08:00:00")
    public String getDate() {
        return DateUtil.getDateTimeString(new Date(time));
    }

}
