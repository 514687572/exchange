package com.cmd.exchange.external.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.UserApiStatus;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.TradeLogVo;
import com.cmd.exchange.common.vo.TradeVo;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.cmd.exchange.external.request.*;
import com.cmd.exchange.external.service.MallService;
import com.cmd.exchange.external.utils.CIDRUtil;
import com.cmd.exchange.external.utils.RequestUtil;
import com.cmd.exchange.service.FinanceService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.UserApiService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.List;

@Api(tags = "交易接口")
@RestController
@RequestMapping("/mall")
public class MallController {
    private static Logger logger = LoggerFactory.getLogger(MallController.class);

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

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MallService mallService;

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
                    logger.error("failed to parse CIDR string: {}", ip);
                }
            }
        }


        logger.error("request IP: {} is not in white list: {}, apiKey: {}", requestIP, userApi.getWhiteIpList(), userApi.getApiKey());

        Assert.check(true, ErrorCode.ERR_API_INVALID_IP);
    }

    @ApiOperation(value = "对接商城积分扣除业务")
    @PostMapping("deduction-integral")
    public CommonResponse userDeductionIntegral(UserDeductionIntegralRequest request) {
        getUserApi(request);

        mallService.userDeductionIntegrals(request);

        return new CommonResponse<>();
    }
}
