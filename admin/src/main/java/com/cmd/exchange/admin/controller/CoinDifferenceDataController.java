package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.componet.MonitoringDataComponet;
import com.cmd.exchange.common.model.CoinDifference;
import com.cmd.exchange.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "获数据差异模块")
@RestController
@RequestMapping("/coin-data-difference")
public class CoinDifferenceDataController {

    @Autowired
    private MonitoringDataComponet monitoringDataService;

    @ApiOperation(value = "获取数据差异列表信息差异")
    @GetMapping(value = "get-data-difference")
    public CommonResponse getDataDifference() {
        List<CoinDifference> list = monitoringDataService.getCoinDifferenceVOByCoinName();
        return new CommonResponse(list);
    }
}
