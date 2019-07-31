package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.HighFrequencyService;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.TimeMonitoringConfigService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "交易监控配置模块")
@RestController
@RequestMapping("/timeMonitoringConfig")
public class TimeMonitoringConfigController {

    @Autowired
    private TimeMonitoringConfigService timeMonitoringConfigService;
    @Autowired
    private MarketService marketService;
    @Autowired
    public HighFrequencyService highFrequencyService;


    @ApiOperation(value = "获取警告类型配置（分页）列表")
    @GetMapping("get-timeMonitoringConfig-page-list")
    public CommonListResponse<List<TimeMonitoringConfig>> getTimeMonitoringPageList(@ApiParam(value = "警告类型type", required = true) @RequestParam(required = false) String monitoringType,
                                                                                    @ApiParam(value = "页数") @RequestParam Integer pageNo, @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<TimeMonitoringConfig> list = timeMonitoringConfigService.getTimeMonitoringConfigList(monitoringType, pageNo, pageSize);
        return CommonListResponse.fromPage(list);

    }

    @ApiOperation(value = "添加警告类型时间配置")
    @PostMapping(value = "add-timeMonitoring-config")
    public CommonResponse addTimeMonitoringConfig(@RequestBody TimeMonitoringConfig timeMonitoringConfig) {
        if (timeMonitoringConfig != null) {
            Market market = marketService.getMarket(timeMonitoringConfig.getCoinName(), timeMonitoringConfig.getSettlementCurrency());
            if (market == null) {
                return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "交易对不存在，添加失败");
            }
            TimeMonitoringConfig data = timeMonitoringConfigService.getTimeMonitoringConfigByCoinNameAndSellName(timeMonitoringConfig.getCoinName(), timeMonitoringConfig.getSettlementCurrency());
            if (data != null) {
                return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "交易对已经存在，不能重复添加");
            }
            int i = timeMonitoringConfigService.addTimeMonitoringConfig(timeMonitoringConfig);
            if (i > 0) {
                //增加成功后进行更新监控配置信息
                highFrequencyService.initHighFrequency();
                return new CommonResponse(ErrorCode.ERR_SUCCESS);
            }
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "修改交易对配置信息")
    @PutMapping("update-timeMonitoringConfig-byId")
    public CommonResponse updateTimeMonitoringConfigById(@RequestBody(required = true) TimeMonitoringConfig timeMonitoringConfig) {
        if (timeMonitoringConfig != null) {
            int i = timeMonitoringConfigService.updateTimeMonitoringConfigById(timeMonitoringConfig);
            if (i > 0) {
                highFrequencyService.initHighFrequency();
                return new CommonResponse(ErrorCode.ERR_SUCCESS);
            }
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
        }

        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除交易配置")
    @GetMapping(value = "del-TimeMonitoring-config-byId")
    public CommonResponse delTimeMonitoringConfigById(@ApiParam(value = "主键", required = true) @RequestParam(required = true) Integer id) {
        int i = timeMonitoringConfigService.delTimeMonitoringConfigById(id);
        if (i > 0) {
            highFrequencyService.initHighFrequency();
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "获取警告类型配详情")
    @GetMapping("get-timeMonitoringConfig-detail-ById")
    public CommonResponse getTimeMonitoringConfigDetail(@ApiParam(value = "主键", required = true) @RequestParam(required = true) Integer id) {

        return new CommonResponse(timeMonitoringConfigService.getTimeMonitoringConfigById(id));
    }


}
