package com.cmd.exchange.common.model;

import com.cmd.exchange.common.enums.UserApiStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserApi {
    private Integer id;
    private Integer userId;
    private String apiKey;
    private String apiSecret;
    //ip白名单, 允许使用CIDR格式来指定一个网段， 比如 1.2.3.4/16. 逗号隔开
    private String whiteIpList;
    private String comment;
    private Date createTime;
    //过期时间
    private Date expiredTime;
    private Date lastTime;
    private UserApiStatus status;


}
