package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/12.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VoucherVo {

    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("云商名称")
    private String merchantName;
    @ApiModelProperty("凭证内容")
    private String content;
    @ApiModelProperty("凭证图片url")
    private String url;
    @ApiModelProperty("凭证时间")
    private int time;
    //@ApiModelProperty("云商信用积分")
    //private Long score;
}
