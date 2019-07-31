package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserContracts;
import com.cmd.exchange.common.model.UserContractsHistory;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.SmartContractsVo;
import com.cmd.exchange.service.SmartContractstService;
import com.cmd.exchange.service.UserContractstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 智能合约接口
 */
@Api(tags = "智能合约相关信息，列表，下单")
@RestController
@RequestMapping("/contractst")
public class ContractstController {
    private static Logger logger = LoggerFactory.getLogger(ContractstController.class);
    @Autowired
    private SmartContractstService smartContractstService;
    @Autowired
    private UserContractstService userContractstService;

    @ApiOperation("智能合约列表")
    @PostMapping("/getContractstList")
    public CommonListResponse<SmartContractsVo> getMarketList(@ApiParam("交易币种") @RequestParam(required = true) String coinName,
                                                              @ApiParam("分页") @RequestParam(required = false) Integer pageNo,
                                                              @ApiParam("每页记录数量") @RequestParam(required = false) Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        if (pageNo == null) {
            pageNo = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        return CommonListResponse.fromPage(smartContractstService.getSmartContractsList(coinName, pageNo, pageSize));
    }

    @ApiOperation("智能合约列表")
    @PostMapping("/getUserContractstList")
    public CommonListResponse<UserContracts> getUserContractstList(
            @ApiParam("分页") @RequestParam(required = false) Integer pageNo,
            @ApiParam("每页记录数量") @RequestParam(required = false) Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        if (pageNo == null) {
            pageNo = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        return CommonListResponse.fromPage(userContractstService.getUserContractsList(user.getId(), pageNo, pageSize));
    }

    @ApiOperation("智能合约列表")
    @PostMapping("/getUserContractstHisList")
    public CommonListResponse<UserContractsHistory> getUserContractstHisList(
            @ApiParam("分页") @RequestParam(required = false) Integer pageNo,
            @ApiParam("每页记录数量") @RequestParam(required = false) Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        if (pageNo == null) {
            pageNo = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        return CommonListResponse.fromPage(userContractstService.getUserContractsHisList(user.getId(), pageNo, pageSize));
    }

    @ApiOperation("智能合约存入")
    @PostMapping("/contractstBuy")



    public CommonResponse contractstBuy(@ApiParam("合约ID") @RequestParam(required = true) Integer id) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        smartContractstService.contractstBuy(id, user.getId());

        return new CommonResponse();
    }
}
