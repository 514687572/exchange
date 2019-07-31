package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.HighFrequencyService;
import com.cmd.exchange.admin.vo.MarketHFVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "高频交易监控模块")
@RestController
@RequestMapping("hfmonitor")
public class HFTradeMonitorController {

    @Autowired
    public HighFrequencyService highFrequencyService;

    @ApiOperation(value = "获取报警高频交易监控的列表")
    @GetMapping(value = "/get_high_frequency_list")
    public CommonResponse getHighFrequencyList(@ApiParam(required = false, value = "买入或者卖出币种名称") @RequestParam(required = false) String coinName,
                                               @ApiParam(required = false, value = "买入或者卖出") @RequestParam(required = false) String settlementCurrency) {
        List<MarketHFVO> list = new ArrayList<>();
        if (coinName != null && settlementCurrency != null) {
            list = highFrequencyService.getMarketHFVOList(coinName, settlementCurrency);
        } else {
            list = highFrequencyService.getMarketHFVOListAll();
        }
        if (list != null && list.size() > 0) {
            return new CommonResponse(list);
        }

        return new CommonResponse(ErrorCode.ERR_RECORD_NOT_EXIST, "该交易对无产生报警列表或者未配置该交易对报警配置");

    }


}
