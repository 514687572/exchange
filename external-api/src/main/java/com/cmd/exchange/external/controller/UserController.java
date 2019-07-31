package com.cmd.exchange.external.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.UserApiStatus;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.external.request.CommonRequest;
import com.cmd.exchange.external.request.UserRequest;
import com.cmd.exchange.external.utils.CIDRUtil;
import com.cmd.exchange.external.utils.RequestUtil;
import com.cmd.exchange.external.vp.UserInfoVP;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.UserApiService;
import com.cmd.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.UnknownHostException;

@Api(tags = "用户信息接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Value("${external-api.verify-sign:true}")
    private Boolean verifySign;
    @Value("${external-api.verify-timestamp:true}")
    private Boolean verifyTimestamp;
    @Value("${external-api.rate-limit.max-counts:10}")
    private Integer rateLimitCounts;

    @Autowired
    TradeService tradeService;

    @Autowired
    UserApiService userApiService;


    private UserApiVO getUserApi(CommonRequest request) {
        UserApiVO userApi = userApiService.getUserApiByKey(request.getApiKey());

        Assert.check(userApi == null, ErrorCode.ERR_API_INVALID_KEY);
        //Assert.check(userApi.getStatus() == UserApiStatus.DISABLED, ErrorCode.ERR_API_KEY_DISABLED);
        Assert.check(userApi.getStatus() != UserApiStatus.PASS, ErrorCode.ERR_API_KEY_DISABLED);

        if (verifyTimestamp) {
            Assert.check(!request.isValidTimestamp(), ErrorCode.ERR_API_INVALID_TIMESTAMP);
        }

        checkIpWhiteList(userApi);

        if (verifySign) {
            boolean verify = request.isValidSign(userApi.getApiSecret());
            Assert.check(!verify, ErrorCode.ERR_API_INVALID_SIGN);
        }

        return userApi;
    }

    private void checkIpWhiteList(UserApiVO userApi) {
        if (StringUtils.isBlank(userApi.getWhiteIpList())) {
            return;
        }

        String requestIP = RequestUtil.getRealIp();

        for (String ip : userApi.getIpList()) {
            if (StringUtils.isNotBlank(ip)) {
                try {
                    if (CIDRUtil.isInRange(requestIP, ip)) {
                        return;
                    }
                } catch (UnknownHostException e) {
                    log.error("failed to parse CIDR string: {}", ip);
                }
            }
        }


        log.error("request IP: {} is not in white list: {}, apiKey: {}", requestIP, userApi.getWhiteIpList(), userApi.getApiKey());

        Assert.check(true, ErrorCode.ERR_API_INVALID_IP);
    }


    @ApiOperation("查询用户可用余额")
    @PostMapping("/getAvailableBalance")
    public CommonResponse<UserInfoVP> getAvailableBalance(@Valid UserRequest request) {
        getUserApi(request);
        String mobile = request.getMobile();
        String coinName = request.getCoinName();
        UserInfoVP build = UserInfoVP.builder()
                .userName(mobile)
                .coinName(coinName)
                .availableBalance(userService.getAvailableBalance(mobile, coinName))
                .build();
        return new CommonResponse(build);
    }

}
