package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.service.GlobalMonitorService;
import com.cmd.exchange.admin.vo.GlobalMonitor;
import com.cmd.exchange.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "监控模块全局监控")
@RestController
@RequestMapping("global-monitor")
public class GlobalMonitorController {

    @Autowired
    private GlobalMonitorService globalMonitorService;

    @ApiOperation(value = "获取全局监控的列表")
    @GetMapping(value = "/get-global-monitor-status")
    public CommonResponse getGlobalMonitorStatus() {

        GlobalMonitor globalMonitor = globalMonitorService.getGolbalMonitor();
        return new CommonResponse(globalMonitor);
    }

}
