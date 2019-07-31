package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.CashMonitoringConfigService;
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

@Api(tags = "持币监控配置模块，转入转出配置")
@RestController
@RequestMapping("/cash_monitoring_config")
public class CashMonitoringConfigController {

    @Autowired
    private CashMonitoringConfigService cashMonitoringConfigService;

    @ApiOperation(value = "增加持币监控转入转出配置")
    @PostMapping(value = "add_cash_monitoring_config")
    public CommonResponse addCashMonitoringConfig(@RequestBody CashMonitoringConfig cashMonitoringConfig) {
        CashMonitoringConfig cashMonitoringConfig1 = cashMonitoringConfigService.getCashMonitoringConfigByName(cashMonitoringConfig.getCoinName());
        if (cashMonitoringConfig1 != null) {
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "币种" + cashMonitoringConfig.getCoinName() + "不能重复配置");
        }
        int i = cashMonitoringConfigService.addCashMonitoringConfig(cashMonitoringConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "修改持币监控转入转出配置")
    @PutMapping(value = "update_cash_monitoring_config")
    public CommonResponse updateCashMonitoringConfig(@RequestBody CashMonitoringConfig cashMonitoringConfig) {
        int i = cashMonitoringConfigService.updateCashMonitoringConfig(cashMonitoringConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "持币监控添加转入转出配置")
    @GetMapping(value = "del_cash_monitoring_config_byId")
    public CommonResponse delCashMonitoringConfigById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        int i = cashMonitoringConfigService.delCashMonitoringConfigById(id);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "根据id查询转入转出监控配置详情")
    @GetMapping(value = "get_cash_monitoring_config_byId")
    public CommonResponse getCashMonitoringConfigById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        CashMonitoringConfig data = cashMonitoringConfigService.getCashMonitoringConfigById(id);
        return new CommonResponse(data);
    }

    @ApiOperation(value = "查询转入转出监控列表（分页）")
    @GetMapping(value = "get_cash_monitoring_config_page")
    public CommonListResponse<List<CashMonitoringConfig>> getCashMonitoringConfigPage(@ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                                      @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<CashMonitoringConfig> list = cashMonitoringConfigService.getCashMonitoringConfigPage(pageNo, pageSize);
        return CommonListResponse.fromPage(list);
    }

    @ApiOperation(value = "查询转入转出监控列表（分页）")
    @GetMapping(value = "get_cash_monitoring_config_list")
    public CommonResponse getCashMonitoringConfigList() {
        List<CashMonitoringConfig> list = cashMonitoringConfigService.getCashMonitoringConfigList();
        return new CommonResponse(list);
    }


}
