package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.model.CronConfig;
import com.cmd.exchange.common.model.DispatchJob;
import com.cmd.exchange.common.model.DispatchLog;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.vo.DispatchConfigVo;
import com.cmd.exchange.common.vo.DispatchReqVO;
import com.cmd.exchange.common.vo.DispatchVO;
import com.cmd.exchange.common.model.DispatchConfig;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.DispatchService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "拨币接口")
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    DispatchService dispatchService;

    @ApiOperation("获取定时任务配置信息")
    @PostMapping(value = "/get-cron-config")
    public CommonResponse<List<CronConfig>> getCronConfigList() {
        return new CommonResponse(dispatchService.getCronConfigList());
    }

    @ApiOperation("获取锁仓配置信息")
    @GetMapping(value = "/get-dispatch-config")
    public CommonResponse<List<DispatchConfigVo>> getDispatchConfigList() {
        return new CommonResponse(dispatchService.getDispatchConfigList());
    }

    @ApiOperation("通过id获取锁仓配置的详情")
    @GetMapping(value = "get-dispatch-info-by-id")
    public CommonResponse getDispatchInfoById(
            @ApiParam(value = "锁仓id") @RequestParam(name = "id", required = true) int id) {
        DispatchConfigVo dispatchConfigVo = dispatchService.getDispatchInfoById(id);
        return new CommonResponse(dispatchConfigVo);
    }

    @ApiOperation("新增锁仓配置信息")
    @PostMapping(value = "/add-dispatch-config")
    public CommonResponse addDispatchConfig(@RequestBody DispatchConfig dispatchConfig) {
        dispatchService.addDispatchConfig(dispatchConfig);
        return new CommonResponse();
    }

    @ApiOperation("管理后台新增锁仓配置信息")
    @PostMapping(value = "/admin-add-dispatch-config")
    public CommonResponse adminAddDispatchConfig(@RequestBody DispatchConfigVo dispatchConfigVo) {
        dispatchService.adminAddDispatchConfig(dispatchConfigVo);
        return new CommonResponse();
    }

    @ApiOperation("修改锁仓配置信息")
    @PutMapping(value = "/mod-dispatch-config")
    public CommonResponse modDispatchConfig(@RequestBody DispatchConfig dispatchConfig) {
        dispatchService.modDispatchConfig(dispatchConfig);
        return new CommonResponse();
    }

    @ApiOperation("修改锁仓配置信息枚举")
    @PutMapping(value = "/update-dispatch-config")
    public CommonResponse updateDispatchConfig(@RequestBody DispatchConfigVo dispatchConfigVo) {
        dispatchService.updateDispatchConfig(dispatchConfigVo);
        return new CommonResponse();
    }

    @ApiOperation("删除锁仓配置信息")
    @DeleteMapping(value = "/del-dispatch-config")
    public CommonResponse delDispatchConfig(@RequestParam("id") Integer id) {
        dispatchService.delDispatchConfig(id);
        return new CommonResponse();
    }

    @ApiOperation("拨币")
    @PostMapping(value = "/dispatch")
    public CommonResponse dispatch(@RequestBody DispatchReqVO dispatchReqVO) {
        dispatchService.dispatch(dispatchReqVO);
        return new CommonResponse();
    }

    @ApiOperation("拨币记录")
    @GetMapping(value = "/get-dispatch")
    public CommonListResponse<DispatchJob> getDispatch(@ApiParam(value = "真实姓名", required = false) @RequestParam(name = "realName", required = false) String realName,
                                                       @ApiParam(value = "userName(用户名)", required = false) @RequestParam(name = "userName", required = false) String userName,
                                                       @ApiParam(value = "币种名称", required = false) @RequestParam(name = "coinName", required = false) String coinName,
                                                       @RequestParam("pageNo") Integer pageNo,
                                                       @RequestParam("pageSize") Integer pageSize) {
        Page<DispatchJob> rst = dispatchService.getDispatchJob(realName, userName, coinName, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation("释放日志")
    @GetMapping(value = "/dispatch-log")
    public CommonListResponse<DispatchLog> dispatchLog(@RequestParam(required = false) Integer userId,
                                                       @RequestParam("pageNo") Integer pageNo,
                                                       @RequestParam("pageSize") Integer pageSize) {
        Page<DispatchLog> rst = dispatchService.getDispatchLog(userId, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }


    @ApiOperation("拨币")
    @PostMapping(value = "/change-rf-coin")
    public CommonResponse changeRfCoin(@RequestBody DispatchReqVO dispatchReqVO) {
        dispatchService.changeRfCoin(dispatchReqVO);
        return new CommonResponse();
    }

}

