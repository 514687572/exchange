package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.ServiceMontitorVO;
import com.cmd.exchange.service.MonitorCacheService;
import com.cmd.exchange.service.ServerConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "服务器监控模块")
@RestController
@RequestMapping("/service_monitor")
@Slf4j
public class ServiceMonitorController {

    @Autowired
    private MonitorCacheService monitorCacheService;


    @Autowired
    private ServerConfigService serverConfigService;
    @Autowired
    private RedisTemplate<String, String> redTemplate;

    @ApiOperation(value = "获取服务器的不正常模块")
    @GetMapping(value = "serviceinfo")
    public CommonResponse getServiceMonitorSattus() {

        List<ServiceMontitorVO> list = serverConfigService.getServiceMototorList();
        return new CommonResponse(list);

    }
}
