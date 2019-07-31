package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.enums.UserApiStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserApiVO {
    private Integer id;
    private Integer userId;
    private String apiKey;
    private String apiSecret;
    //
    @ApiModelProperty(value = "ip白名单, 允许使用CIDR格式来指定一个网段， 比如 1.2.3.4/16. 逗号隔开")
    private String whiteIpList;
    private Date createTime;
    //过期时间
    @ApiModelProperty(value = "过期时间")
    private Date expiredTime;
    @ApiModelProperty(value = "更新时间")
    private Date lastTime;
    @ApiModelProperty(value = "状态, DISABLED: 禁用, SUCCESS:审核通过 FAILED:审核失败 PENDING: 审核中")
    private UserApiStatus status;
    private String mobile;
    private String userName;
    private String areaCode;
    private String comment;
    @ApiModelProperty(value = "真是姓名")
    private String realName;

    public String[] getIpList() {
        if (StringUtils.isBlank(whiteIpList)) {
            return new String[0];
        }

        //过滤中文逗号
        whiteIpList = whiteIpList.replace("，", ",");
        return whiteIpList.split(",");
    }
}
