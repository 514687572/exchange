package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.ServerConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.ServerConfigService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "服务器监控服务器配置")
@RestController
@RequestMapping("/server_config")
public class ServerConfigController {
    @Autowired
    private ServerConfigService serverConfigService;

    @ApiOperation(value = "获取服务器相关信息列表")
    @GetMapping(value = "get-server-info-list")
    public CommonListResponse<List<ServerConfig>> getServerInfoList(@ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
                                                                    @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize) {
        Page<ServerConfig> list = serverConfigService.getServerConfigPage(pageNo, pageSize);

        return CommonListResponse.fromPage(list);
    }

    @ApiOperation(value = "添加服务器信息")
    @PostMapping(value = "add-server-info")
    public CommonResponse addServerInfo(@RequestBody ServerConfig serverConfig) {
        int i = serverConfigService.addServerConfig(serverConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }


    @ApiOperation(value = "修改服务器信息配置")
    @PutMapping(value = "update-server-info")
    public CommonResponse updateServerInfo(@RequestBody ServerConfig serverConfig) {
        int i = serverConfigService.updateServerConfig(serverConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除服务器信息配置")
    @GetMapping(value = "del-server-info-byId")
    public CommonResponse delServerInfoById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        int i = serverConfigService.delServerConfigById(id);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "获取服务器信息配置详情")
    @GetMapping(value = "get-server-info-byId")
    public CommonResponse getServerInfoById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        ServerConfig serverConfig = serverConfigService.getServicceConfigById(id);
        return new CommonResponse(serverConfig);
    }
}
