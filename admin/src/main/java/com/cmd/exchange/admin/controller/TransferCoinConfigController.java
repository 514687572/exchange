package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.OpLogType;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.cmd.exchange.common.model.TransferCoinConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.TransferCoinConfigService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "转入转出数量配置模块")
@RestController
@RequestMapping("/transfer-coin-coinfig")
public class TransferCoinConfigController {

    @Autowired
    private TransferCoinConfigService transferCoinConfigService;


    @ApiOperation(value = "增加转入转出币种监控配置")
    @PostMapping("add-transfer-coin-config")
    public CommonResponse addTransferCoinConfig(TransferCoinConfig sendCoinConfig) {

        TransferCoinConfig transferCoinConfig = transferCoinConfigService.getTransferCoinConfigByCoinName(sendCoinConfig.getCoinName());
        if (transferCoinConfig != null) {
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, sendCoinConfig.getCoinName() + "币种不能重复配置");
        }
        int i = transferCoinConfigService.addTransferCoinConfig(sendCoinConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除转入转出币种监控配置")
    @GetMapping("del-transfer-coin-config")
    public CommonResponse delSendCoinConfig(@ApiParam(value = "配置Id", required = true) @RequestParam(required = true) Integer id) {

        transferCoinConfigService.delTransferCoinConfig(id);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "根据id获取转入转出详情")
    @GetMapping("get-transfer-coin-config-byId")
    public CommonResponse getSendCoinConfigById(@ApiParam(value = "配置Id", required = true) @RequestParam(required = true) Integer id) {
        TransferCoinConfig transferCoinConfig = transferCoinConfigService.getTransferCoinConfigById(id);
        return new CommonResponse(transferCoinConfig);
    }

    @ApiOperation(value = "获取全部转入转出配置")
    @GetMapping("get-transfer-coin-config-all")
    public CommonResponse getSendCoinConfigListAll() {
        List<TransferCoinConfig> transferCoinConfigList = transferCoinConfigService.getTransferCoinConfigList();
        return new CommonResponse(transferCoinConfigList);
    }

    @ApiOperation(value = "获取转入转出配置列表（分页）")
    @GetMapping("get-transfer-coin-config-page")
    public CommonListResponse<List<TransferCoinConfig>> getSendCoinConfigPage(@ApiParam(value = "币种名", required = false) @RequestParam(required = false) String coinName,
                                                                              @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                              @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<TransferCoinConfig> list = transferCoinConfigService.getTransferCoinConfigPage(coinName, pageNo, pageSize);
        return CommonListResponse.fromPage(list);
    }

    @ApiOperation(value = "修改交易对配置信息")
    @PutMapping("update-transfer-coin-config")
    public CommonResponse updateSendCoinConfig(TransferCoinConfig transferCoinConfig) {
        int i = transferCoinConfigService.updateTransferCoinConfig(transferCoinConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }


}
