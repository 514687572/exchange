package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.vo.AddMarketVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.TradeStatVo;
import com.cmd.exchange.service.MarketService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 交易市场接口
 */
@Api(tags = "交易市场接口，用于查看交易市场的一些信息")
@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @ApiOperation("获取所有交易市场")
    @GetMapping("")
    public CommonListResponse<Market> getMarketList(
            @ApiParam(value = "市场名称， 格式： 交易货币_结算货币，比如btc_usdt", required = false) @RequestParam(required = false) String name,
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<Market> marketList = marketService.getMarketList(name, pageNo, pageSize);
        return CommonListResponse.fromPage(marketList);
    }

    @ApiOperation("获取市场详情")
    @GetMapping("detail")
    public CommonResponse<Market> getMarketDetail(
            @ApiParam(value = "市场id", required = true) @RequestParam Integer marketId) {
        Market market = marketService.getMarketById(marketId);
        return new CommonResponse(market);
    }

    @ApiOperation("修改市场")
    @PutMapping("")
    public CommonResponse updateMarket(@Valid @RequestBody AddMarketVO market) {

        if (market.getDel() == 1) {
            List<Integer> ids = new ArrayList<>();
            ids.add(market.getId());
            marketService.deleteMarkets(ids);
            return new CommonResponse();
        }
        marketService.updateMarket(market.toModel());
        return new CommonResponse();
    }

    @ApiOperation("添加市场")
    @PostMapping("")
    public CommonResponse<Integer> addMarket(@Valid @RequestBody AddMarketVO market) {
        return new CommonResponse<>(marketService.addMarket(market.toModel()));
    }

    @ApiOperation("删除市场")
    @DeleteMapping("")
    public CommonResponse deleteMarket(@RequestBody List<Integer> marketIds) {
        marketService.deleteMarkets(marketIds);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("禁用/开启市场")
    @GetMapping("close_or_open_market")
    public CommonResponse closeOrOpenMarket(
            @ApiParam(value = "市场id", required = true) @RequestParam Integer marketId) {
        Market market = marketService.getMarketById(marketId);
        if (market == null) {
            return new CommonResponse(ErrorCode.ERR_RECORD_NOT_EXIST);
        }
        if (market.getClosed() == MarketStatus.SHOW) {
            market.setClosed(MarketStatus.HIDE);
        } else {
            market.setClosed(MarketStatus.SHOW);
        }
        marketService.updateMarket(market);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("获取所有交易市场")
    @GetMapping("/getAllMarketList")
    public CommonResponse<List<Market>> getAllMarketList() {
//        List<Market> allMarkets = marketService.getAllMarkets();
        return new CommonResponse(marketService.getAllMarketsMap());
    }

    @ApiOperation("某个交易区市场交易市场")
    @PostMapping("/getMarketList")
    public CommonResponse<List<Market>> getMarketList(@ApiParam("交易币种") @RequestParam(required = true) String settlementCurrency) {
        List<TradeStatVo> marketListBySettlement = marketService.getMarketListBySettlement(settlementCurrency);
        return new CommonResponse(marketListBySettlement);
    }
 //////

}
