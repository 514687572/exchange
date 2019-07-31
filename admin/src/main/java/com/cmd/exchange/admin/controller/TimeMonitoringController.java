package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.vo.EditCoinGroupConfigDetailVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.CoinGroupConfig;
import com.cmd.exchange.common.model.TimeMonitoring;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.CoinGroupConfigVO;
import com.cmd.exchange.common.vo.CoinVO;
import com.cmd.exchange.common.vo.MarketGroupConfigVO;
import com.cmd.exchange.service.TimeMonitoringConfigService;
import com.cmd.exchange.service.TimeMonitoringService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "监控类型控制模块")
@RestController
@RequestMapping("/timeMonitoring")
public class TimeMonitoringController {
    @Autowired
    private TimeMonitoringService timeMonitoringService;

    @Autowired
    private TimeMonitoringConfigService timeMonitoringConfigService;

    @ApiOperation(value = "获取警告类型配置（分页）列表")
    @GetMapping("get-timeMonitoring-page-list")
    public CommonListResponse<List<TimeMonitoring>> getTimeMonitoringPageList(@ApiParam(value = "警告类型名称", required = false) @RequestParam(required = false) String monitoringName,
                                                                              @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                              @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<TimeMonitoring> list = timeMonitoringService.getTimeMonitoringPageList(monitoringName, pageNo, pageSize);
        return CommonListResponse.fromPage(list);

    }

    @ApiOperation(value = "获取警告类型配置列表（所有数据）")
    @GetMapping("get-timeMonitoring-list")
    public CommonResponse getTimeMonitoringList() {

        List<TimeMonitoring> list = timeMonitoringService.getTimeMonitoringList();
        return new CommonResponse(list);
    }

    @ApiOperation(value = "获取警告Id配置详情")
    @GetMapping("get-timeMonitoring-detail-ById")
    public CommonResponse getTimeMonitoringDetail(@ApiParam(value = "主键", required = true) @RequestParam(required = true) Integer id) {

        TimeMonitoring timeMonitoring = timeMonitoringService.getTimeMonitoringById(id);
        return new CommonResponse(timeMonitoring);
    }

    @ApiOperation(value = "获取警告类型配置详情")
    @GetMapping("get-timeMonitoring-detail-byType")
    public CommonResponse getTimeMonitoringDetailbyType(@ApiParam(value = "类型", required = true) @RequestParam(required = true) String monitoringType) {

        TimeMonitoring timeMonitoring = timeMonitoringService.getTimeMonitoringByType(monitoringType);
        return new CommonResponse(timeMonitoring);
    }


    @ApiOperation(value = "删除警告类型配置")
    @GetMapping(value = "del-TimeMonitoring-byId")
    public CommonResponse delTimeMonitoring(@ApiParam(value = "主键", required = true) @RequestParam(required = true) Integer id
    ) {

        // TimeMonitoringConfig timeMonitoringConfig =  timeMonitoringConfigService.getTimeMonitoringConfigByType(monitoringType);
        // if(timeMonitoringConfig!=null){
        //   return new CommonResponse(ErrorCode.ERR_PARAM_ERROR,"对不起，改配置使用中，无法删除");
        // }
        timeMonitoringService.delTimeMonitoring(id);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }


    @ApiOperation(value = "修改警告类型配置名字，只能修改名字和时间")
    @PutMapping("update-timeMonitoring")
    public CommonResponse updateCoinGroupConfig(@RequestBody TimeMonitoring timeMonitoring) {

        if (timeMonitoring != null) {
            TimeMonitoring data = new TimeMonitoring();
            data.setNumMinutes(timeMonitoring.getNumMinutes());
            data.setMonitoringName(timeMonitoring.getMonitoringName());
            data.setId(timeMonitoring.getId());
            timeMonitoringService.updateTimeMonitoring(data);
        }
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "添加警告类型配置")
    @PostMapping(value = "add-timeMonitoring")
    public CommonResponse addTimeMonitoring(@RequestBody TimeMonitoring timeMonitoring) {
        if (timeMonitoring != null) {
            String MonitoringType = "TIME_MONITORING_" + timeMonitoring.getMonitoringName();

            TimeMonitoring data = timeMonitoringService.getTimeMonitoringByType(MonitoringType);
            if (data != null) {
                return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "该类型已经存在，添加失败");
            }
            TimeMonitoring timeMonitoring1 = new TimeMonitoring();
            timeMonitoring1.setMonitoringType("TIME_MONITORING_" + timeMonitoring.getMonitoringName());
            timeMonitoring1.setMonitoringName(timeMonitoring.getMonitoringName());
            timeMonitoring1.setNumMinutes(timeMonitoring.getNumMinutes());
            int i = timeMonitoringService.add(timeMonitoring1);
            if (i > 0) {
                return new CommonResponse(ErrorCode.ERR_SUCCESS);
            }
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }


}
