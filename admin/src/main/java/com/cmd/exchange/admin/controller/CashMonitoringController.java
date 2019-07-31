package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.CashMonitoringVO;
import com.cmd.exchange.common.vo.CoinTransferVo;
import com.cmd.exchange.service.CashMonitoringConfigService;
import com.cmd.exchange.service.CashMonitoringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "持币监控查询列表")
@RestController
@RequestMapping("/cash_monitoring")
public class CashMonitoringController {

    @Autowired
    private CashMonitoringConfigService cashMonitoringConfigService;

    @Autowired
    private CashMonitoringService cashMonitoringService;

    @ApiOperation(value = "获取各个币种转入转出的监控列表（此列表显示出）")
    @GetMapping("/get_cash_monitoring_list")
    public CommonResponse<List<CashMonitoringVO>> getCashMonitoringVOByNameAndType() {
        List<CashMonitoringConfig> cashMonitoringConfigs = cashMonitoringConfigService.getCashMonitoringConfigList();
        if (cashMonitoringConfigs != null && cashMonitoringConfigs.size() > 0) {
            List<CashMonitoringVO> list = cashMonitoringService.getCashMonitorVoList(cashMonitoringConfigs);

            return new CommonResponse<List<CashMonitoringVO>>(list);
        }
        return new CommonResponse<>(ErrorCode.ERR_SUCCESS, "未配置转入转出监控对象");
    }
}
