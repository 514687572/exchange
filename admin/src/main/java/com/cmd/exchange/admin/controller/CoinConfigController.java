package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.mapper.CoinConfigMapper;
import com.cmd.exchange.common.model.CoinConfig;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.CoinVO;
import com.cmd.exchange.service.CoinConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "币种配置接口")
@RestController
@RequestMapping("/coin-config")
public class CoinConfigController {

    @Autowired
    CoinConfigService coinConfigService;

    @ApiOperation(value = "添加币种配置")
    @PostMapping(value = "")
    public CommonResponse addCoinConfig(@RequestBody CoinConfig coinConfig) {
        coinConfigService.addCoinConfig(coinConfig);
        return new CommonResponse();
    }

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public CommonResponse<List<CoinConfig>> getCoinConfigList() {
        return new CommonResponse<>(coinConfigService.getCoinConfigList());
    }

    @ApiOperation(value = "更新配置")
    @PutMapping(value = "")
    public CommonResponse updateCoinConfig(@RequestBody CoinConfig coinConfig) {
        coinConfigService.updateCoinConfig(coinConfig);
        return new CommonResponse();
    }
}
