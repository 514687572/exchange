package com.cmd.exchange.admin.vo;

import com.cmd.exchange.common.vo.PageReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterRewardReqVO extends PageReqVO {
    @ApiModelProperty("被推荐用户手机")
    private String mobile;
    @ApiModelProperty("推荐用户手机")
    private String referrerMobile;

    //推荐人id
    private Integer referrer;

    private Integer userId;
}
