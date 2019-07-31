package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.InvestDetail;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.InvestPageVO;
import com.cmd.exchange.service.InvestDetailService;
import com.cmd.exchange.service.InvestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "投资相关接口")
@RestController
@RequestMapping("/invest")
public class InvestController {
    @Autowired
    private InvestService investService;
    @Autowired
    private InvestDetailService investDetailService;

    @ApiOperation(value = "获取投资页信息")
    @GetMapping(value = "/getInvestPage")
    public CommonResponse<InvestPageVO> getInvestPage() {
        return new CommonResponse<>(investService.getInvestPage(ShiroUtils.getUser().getId()));
    }

    @ApiOperation(value = "确认投资")
    @PostMapping(value = "/update-invest")
    public CommonResponse updateInvest(
            @ApiParam(value = "支付密码", required = true) @RequestParam(required = true) String payPassword,
            @ApiParam(value = "投资额", required = true) @RequestParam(required = true) BigDecimal coinAmount) {
        investService.updateInvest(ShiroUtils.getUser().getId(),payPassword,coinAmount);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "获取投资记录列表")
    @GetMapping(value = "/getInvestDetailList")
    public CommonResponse getInvestDetailList(
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        List<InvestDetail> investDetailList = investDetailService.getInvestDetailList(ShiroUtils.getUser().getId(),pageNo,pageSize);
        return new CommonResponse(investDetailList);
    }



}
