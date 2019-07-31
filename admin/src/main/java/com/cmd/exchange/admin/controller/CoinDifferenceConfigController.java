package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.CoinDifferenceConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.CoinDifferenceConfigService;
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

@Api(tags = "数据差异监控配置接口")
@RestController
@RequestMapping("/coin-difference-config")
public class CoinDifferenceConfigController {

    @Autowired
    private CoinDifferenceConfigService coinDifferenceConfigService;

    @ApiOperation(value = "添加币种数据差异监控配置")
    @PostMapping(value = "add-coin-difference-config")
    public CommonResponse addcCoinDifferenceConfig(@RequestBody CoinDifferenceConfig coinDifferenceConfig) {
        if (coinDifferenceConfig == null) {
            return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
        }
        CoinDifferenceConfig coinData = coinDifferenceConfigService.getCoinDifferenceConfigByName(coinDifferenceConfig.getCoinName());
        if (coinData != null) {
            return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE, "币种" + coinDifferenceConfig.getCoinName() + "不能重复配置");
        }
        int i = coinDifferenceConfigService.addCoinDifferenceConfig(coinDifferenceConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除币种数据差异监控配置")
    @GetMapping(value = "del-coin-difference-config-byId")
    public CommonResponse delCoinDifferenceConfigById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        coinDifferenceConfigService.delCoinDifferenceConfigById(id);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "删除币种数据差异监控配置")
    @PutMapping(value = "update-coin-difference-config-byId")
    public CommonResponse updateCoinDifferenceConfig(@RequestBody CoinDifferenceConfig coinDifferenceConfig) {
        int i = coinDifferenceConfigService.updateCoinDifferenceConfig(coinDifferenceConfig);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "获取币种数据差异监控配置详情")
    @GetMapping(value = "get-coin-difference-config-byId")
    public CommonResponse getCoinDifferenceConfigById(@ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id) {
        CoinDifferenceConfig data = coinDifferenceConfigService.getCoinDifferenceConfigById(id);
        return new CommonResponse(data);

    }

    @ApiOperation(value = "获取币种数据差异监控配置列表（所有，不分页）")
    @GetMapping(value = "get-coin-difference-config-all")
    public CommonResponse<List<CoinDifferenceConfig>> getCoinDifferenceConfigAll() {

        List<CoinDifferenceConfig> list = coinDifferenceConfigService.getCoinDifferenceConfigAll();

        return new CommonResponse(list);
    }

    @ApiOperation(value = "获取币种数据差异监控配置列表（分页）")
    @GetMapping(value = "get-coin-difference-config-page")
    public CommonListResponse<List<CoinDifferenceConfig>> getCoinDifferenceConfigPage(@ApiParam(value = "币种名字", required = false) @RequestParam(required = false) String coinName,
                                                                                      @ApiParam(value = "页数") @RequestParam Integer pageNo, @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<CoinDifferenceConfig> list = coinDifferenceConfigService.getCoinDifferenceConfigPage(coinName, pageNo, pageSize);
        return CommonListResponse.fromPage(list);
    }

}
