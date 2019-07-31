package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.model.CoinDifference;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.CoinDifferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "数据差异监控配置接口")
@RestController
@RequestMapping("/coin-difference")
public class CoinDifferenceController {

    @Autowired
    private CoinDifferenceService coinDifferenceService;

    @ApiOperation(value = "获取数据监控差异的列表")
    @GetMapping(value = "/get-coin-difference-list")
    public CommonResponse getCoinDifferenceList() {
        List<CoinDifference> list = coinDifferenceService.getCoinDifferenceList();
        return new CommonResponse(list);
    }
}
