package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.componet.GlobalDataComponet;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.MarketGroupConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.MarketGroupConfigVO;
import com.cmd.exchange.service.MarketGroupConfigService;
import com.cmd.exchange.service.MarketService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "交易对手续配置")
@RestController
@RequestMapping("/marketGroupConfig")
public class MarketGroupConfigController {

    @Autowired
    private MarketGroupConfigService marketGroupConfigService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private GlobalDataComponet globalDataComponet;

    @ApiOperation(value = "交易对手续配置列表")
    @GetMapping("get-market-group-config-list")
    public CommonListResponse<List<MarketGroupConfigVO>> getMarketGroupConfigList(@ApiParam(value = "市场名称， 格式： 交易货币_结算货币，比如btc_usdt", required = false) @RequestParam(required = false) String name,
                                                                                  @ApiParam(value = "页数") @RequestParam Integer pageNo,
                                                                                  @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<MarketGroupConfigVO> marketGroupConfigList = marketGroupConfigService.getMarketGroupConfigListByName(name, pageNo, pageSize);
        return CommonListResponse.fromPage(marketGroupConfigList);

    }

    @ApiOperation(value = "获取交易对手续费配置详情")
    @GetMapping("get-market-group-config-byId")
    public CommonResponse<MarketGroupConfigVO> getMarketGroupConfigById(@ApiParam(value = "交易对配置Id", required = true) @RequestParam(required = false) Integer marketGroupConfigId) {
        MarketGroupConfigVO marketGroupConfigVO = marketGroupConfigService.getMarketGroupConfigById(marketGroupConfigId);
        if (marketGroupConfigVO == null) {
            return new CommonResponse(ErrorCode.ERE_MARKET_GROUP_CONFIG_NOT_EXIST);
        }
        return new CommonResponse(marketGroupConfigVO);

    }

    @ApiOperation(value = "增加交易对手续配置列表")
    @PostMapping("add-market-group-config")
    public CommonResponse addMarketGroupConfig(@RequestBody MarketGroupConfig marketGroupConfig) {
        if (marketGroupConfig != null) {
            Market market = marketService.getMarketById(marketGroupConfig.getMarketId());
            if (market == null) {
                return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
            }

            int i = marketGroupConfigService.countMarketGroupConfigByMarketIdAndGroupId(market.getCoinName(), market.getSettlementCurrency(), marketGroupConfig.getGroupId());
            if (i > 0) {
                Assert.check(true, 10, "交易市场配置已存在");
            }
            marketGroupConfig.setCoinName(market.getCoinName());
            marketGroupConfig.setSettlementCurrency(market.getSettlementCurrency());
            marketGroupConfigService.addPatchMarketGroupConfig(marketGroupConfig);
            globalDataComponet.initConValueMap();
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
    }

    @ApiOperation(value = "修改交易对手续配置列表")
    @PutMapping("update-market-group-config")
    public CommonResponse updateMarketGroupConfig(@RequestBody MarketGroupConfigVO marketGroupConfVO) {
        MarketGroupConfig marketGroupConfig = new MarketGroupConfig();
        //Market market = marketService.getMarketById(marketGroupConfig.getMarketId());
        MarketGroupConfigVO marketGroupConfigVo1 = marketGroupConfigService.getMarketGroupConfigById(marketGroupConfVO.getMarketGroupConfigId());

        Market market = marketService.getMarket(marketGroupConfVO.getCoinName(), marketGroupConfVO.getSettlementCurrency());

        if (marketGroupConfigVo1.getGroupId() != marketGroupConfVO.getGroupId()) {

            int i = marketGroupConfigService.countMarketGroupConfigByMarketIdAndGroupId(market.getCoinName(), market.getSettlementCurrency(), marketGroupConfVO.getGroupId());
            if (i > 0) {
                return new CommonResponse(ErrorCode.ERR_RECORD_EXIST);
            }
        }

        marketGroupConfig.setId(marketGroupConfVO.getMarketGroupConfigId());
        marketGroupConfig.setBuyConValue(marketGroupConfVO.getBuyConValue());
        marketGroupConfig.setSellConValue(marketGroupConfVO.getSellConValue());
        marketGroupConfig.setMarketId(marketGroupConfVO.getMarketId());
        marketGroupConfig.setGroupId(marketGroupConfVO.getGroupId());
        marketGroupConfig.setCoinName(market.getCoinName());
        marketGroupConfig.setSettlementCurrency(market.getSettlementCurrency());
        marketGroupConfigService.updateMarketGroupConfigById(marketGroupConfig);
        globalDataComponet.initConValueMap();
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation(value = "删除交易对手续配置列表")
    @GetMapping("del-market-group-config-byId")
    public CommonResponse delMarketGroupConfigById(@ApiParam(value = "配置表配置Id", required = false) @RequestParam(required = false) Integer marketGroupConfigId) {
        marketGroupConfigService.delMarketGroupConfigById(marketGroupConfigId);
        globalDataComponet.initConValueMap();
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }
}
