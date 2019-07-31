package com.cmd.exchange.external.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.UserApiStatus;
import com.cmd.exchange.common.model.UserApi;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.TradeLogVo;
import com.cmd.exchange.common.vo.TradeVo;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.cmd.exchange.external.request.*;
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
@RequestMapping("/trades")
public class TradeController {
    private static Logger logger = LoggerFactory.getLogger(TradeController.class);

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
    FinanceService financeService;

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


    @ApiOperation("委托下单")
    @PostMapping(value = "")
    public CommonResponse<Integer> createTrade(@Valid CreateTradeRequest request) {

        UserApiVO userApi = getUserApi(request);


        TradeVo trade = new TradeVo();
        trade.setUserId(userApi.getUserId());
        trade.setType(request.getType()).setCoinName(request.getCoinName()).setSettlementCurrency(request.getSettlementCurrency())
                .setPrice(request.getPrice()).setAmount(request.getAmount());

        Integer tradeId = tradeService.createTrade(trade);
        return new CommonResponse<>(tradeId);
    }

    @ApiOperation("撤销订单")
    @PostMapping(value = "/cancel")
    public CommonResponse cancelTrade(CancelTradeRequest request) {
        UserApiVO userApi = getUserApi(request);

        tradeService.cancelTrade(userApi.getUserId(), request.getTradeId(), UserBillReason.TRADE_USER_CANCEL);
        return new CommonResponse<>();
    }

    @ApiOperation("查询指定用户的历史订单（已经成交或者已经取消）列表")
    @GetMapping(value = "history")
    public CommonListResponse<TradeVo> getDealTrades(QueryTradeRequest request) {
        UserApiVO userApi = getUserApi(request);

        Page<TradeVo> tradeList = tradeService.getTradeHistoryListByUser(userApi.getUserId(), request.getCoinName(), request.getSettlementCurrency(), TradeType.ALL, request.getPageNo(), request.getPageSize());
        return CommonListResponse.fromPage(tradeList);
    }

    @ApiOperation("查询指定用户的当前订单（未成交）列表")
    @GetMapping(value = "current")
    public CommonListResponse<TradeVo> getOpenTrades(QueryTradeRequest request) {
        UserApiVO userApi = getUserApi(request);

        Page<TradeVo> tradeList = tradeService.getOpenTradeListByUser(userApi.getUserId(), request.getType() != null ? request.getType() : TradeType.ALL,
                request.getCoinName(), request.getSettlementCurrency(), request.getPageNo(), request.getPageSize());
        return CommonListResponse.fromPage(tradeList);
    }

    @ApiOperation("查询指定订单的成交记录")
    @GetMapping(value = "match-result")
    public CommonResponse<List<TradeLogVo>> getTrades(CancelTradeRequest request) {
        UserApiVO userApi = getUserApi(request);

        Page<TradeLogVo> tradeLogList = tradeService.getTradeLogList(request.getTradeId(), 1, 100);
        return new CommonResponse<>(tradeLogList);
    }

    @ApiOperation(value = "获取所有资产余额")
    @GetMapping("user-balance")
    public CommonResponse<List<UserCoinVO>> getAllCoinBalance(CommonRequest request) {
        UserApiVO userApi = getUserApi(request);
        List<UserCoinVO> userCoinList = financeService.getBalanceByUserId(userApi.getUserId());
        return new CommonResponse(userCoinList);
    }

    @ApiOperation(value = "外部转入资产余额")
    @PostMapping("change-balance")
    public CommonResponse changeUserCoinBalance(ChangeUserCoinRequest request) {
        UserApiVO userApi = getUserApi(request);

        financeService.changeUserCoinBalance(request.getCoinName(), request.getMobile(), request.getChangeBalance(), request.getSign(), request.getTimestamp());

        return new CommonResponse<>();
    }
}
