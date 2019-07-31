package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.TransactionPairs;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.service.*;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 交易市场接口
 */
@Api(tags = "交易市场接口，用于查看交易市场的一些信息")
@RestController
@RequestMapping("/market")
public class MarketController {
    private static Logger logger = LoggerFactory.getLogger(MarketController.class);

    @Autowired
    private MarketService marketService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private ShareOutBonusService shareOutBonusService;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Value("${isget-from-huobi:false}")
    private Boolean isGetFromHuobi;

    @ApiOperation("获取所有交易市场")
    @GetMapping("")
    public CommonListResponse<Market> getMarketList(
            @ApiParam(value = "市场名称， 格式： 交易货币_结算货币，比如btc_usdt", required = false) @RequestParam(required = false) String name,
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        Page<Market> marketList = marketService.getMarketList(name, pageNo, pageSize);
        return CommonListResponse.fromPage(marketList);
    }

    @ApiOperation("获取当个交易市场")
    @GetMapping("/market")
    public CommonResponse<Market> getMarket(
            @ApiParam(value = "交易货币，比如btc") @RequestParam String coinName,
            @ApiParam(value = "结算货币，比如usdt") @RequestParam String settlementCurrency) {
        return new CommonResponse(marketService.getMarket(coinName, settlementCurrency));
    }


    @ApiOperation("获取指定市场的k线数据")
    @GetMapping(value = "candles")
    public CommonResponse<List<CandleVo>> getCandles(
            @ApiParam("k线单位，目前支持：1，5,15,30,60,240,1D,5D,1W,1M") @RequestParam String resolution,
            @ApiParam("结算币种") @RequestParam String settlementCurrency,
            @ApiParam("交易币种") @RequestParam String coinName,
            @ApiParam("开始时间戳： unix时间戳，单位秒") @RequestParam(required = false) Integer startTimestamp,
            @ApiParam("结束时间戳： unix时间戳，单位秒") @RequestParam(required = false) Integer endTimestamp,
            @ApiParam("开始时间： 格式yyyy-mm-dd hh:mm:ss") @RequestParam(required = false) Date startTime,
            @ApiParam("结束时间： 格式yyyy-mm-dd hh:mm:s") @RequestParam(required = false) Date endTime) {

        if (startTimestamp != null) {
            startTime = new Date(startTimestamp * 1000l);
        }

        if (endTimestamp != null) {
            endTime = new Date(endTimestamp * 1000l);
        }

        Assert.check(startTime == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(endTime == null, ErrorCode.ERR_PARAM_ERROR);

        List<CandleVo> candles;
        if (isGetFromHuobi) {
            String symbol = (coinName + settlementCurrency).toLowerCase();
            if (TransactionPairs.BTCUSDT.getValue().equals(symbol) || TransactionPairs.ETHUSDT.getValue().equals(symbol)) {
                candles = marketService.getCandlesFromHuobi(resolution, symbol);
            } else {
                candles = marketService.getCandles(resolution, coinName, settlementCurrency, startTime, endTime);
            }
        } else {
            candles = marketService.getCandles(resolution, coinName, settlementCurrency, startTime, endTime);
        }
        return new CommonResponse<>(candles);
    }

    @ApiOperation("获取指定市场的分时数据")
    @GetMapping(value = "time-series")
    public CommonResponse<List<TimeSeriesVo>> getTimeSeries(
            @ApiParam("结算币种") @RequestParam String settlementCurrency,
            @ApiParam("交易币种") @RequestParam String coinName,
            @ApiParam("开始时间戳： unix时间戳，单位秒") @RequestParam(required = false) Integer startTimestamp,
            @ApiParam("结束时间戳： unix时间戳，单位秒") @RequestParam(required = false) Integer endTimestamp,
            @ApiParam("开始时间： 格式yyyy-mm-dd hh:mm:ss") @RequestParam(required = false) Date startTime,
            @ApiParam("结束时间： 格式yyyy-mm-dd hh:mm:s") @RequestParam(required = false) Date endTime) {

        if (startTimestamp != null) {
            startTime = new Date(startTimestamp * 1000l);
        }

        if (endTimestamp != null) {
            endTime = new Date(endTimestamp * 1000l);
        }

        Assert.check(startTime == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(endTime == null, ErrorCode.ERR_PARAM_ERROR);

        List<TimeSeriesVo> candles = marketService.getTimeSeries(coinName, settlementCurrency, startTime, endTime);
        return new CommonResponse<>(candles);
    }


    @ApiOperation("所有市场24小时内成交记录")
    @GetMapping(value = "stats-list")
    public CommonResponse<List<TradeStatVo>> getMarketStatsList(
            @ApiParam("结算币种") @RequestParam(required = false) String settlementCurrency,
            @ApiParam("交易币种") @RequestParam(required = false) String coinName) {
        List<TradeStatVo> statsList = marketService.getLast24HourTradeStat(coinName, settlementCurrency);
        return new CommonResponse<>(statsList);
    }

    @ApiOperation("查询指定市场24小时内成交记录")
    @GetMapping(value = "stats")
    public CommonResponse<TradeStatVo> getMarketStats(
            @ApiParam("结算币种") @RequestParam String settlementCurrency,
            @ApiParam("交易币种") @RequestParam String coinName) {

        TradeStatVo marketStat = tradeStatService.getLast24HourTradeStat(coinName, settlementCurrency);
        return new CommonResponse<>(marketStat);
    }


    @ApiOperation("获取当天委托(未完全成交的订单)")
    @GetMapping(value = "open-trade-list")
    public CommonResponse<OpenTradeListVo> getOpenTradeList(
            @ApiParam("交易币种") @RequestParam String coinName,
            @ApiParam("结算币种") @RequestParam String settlementCurrency) {
        OpenTradeListVo tradeList = tradeService.getOpenTradeList(coinName, settlementCurrency);
        return new CommonResponse<>(tradeList);
    }

    @ApiOperation("获取成交订单列表")
    @GetMapping(value = "trade-log-list")
    public CommonResponse<TradeLogListVo> getTradeLogList(@ApiParam("交易币种") @RequestParam String coinName,
                                                          @ApiParam("结算币种") @RequestParam String settlementCurrency) {
        TradeLogListVo tradeList = tradeService.getTradeLogList(coinName, settlementCurrency);
        return new CommonResponse<>(tradeList);
    }

    /*@ApiOperation("获取挖矿信息")
    @GetMapping(value = "mining-info")
    public CommonResponse<MiningStatVo> getLatestMiningStatInfo() {
        MiningStatVo info = shareOutBonusService.getLatestMiningStat();
        return new CommonResponse<MiningStatVo>(info);
    }*/

    @ApiOperation("获取某种币对于人民币的价格")
    @GetMapping(value = "coinPrice")
    public CommonResponse<BigDecimal> getCoinCnyPrice(@ApiParam("币种名称") @RequestParam String coinName) {
        BigDecimal price = exchangeRateService.getCoinCommDisplayPrice(coinName);
        return new CommonResponse<BigDecimal>(price);
    }

    @ApiOperation("获取k线图，最新2条数据")
    @GetMapping(value = "candles-latest")
    public CommonResponse<List<CandleVo>> getLatestCandles(
            @ApiParam("周期，分别是1,15,30,60,1D") @RequestParam String resolution,
            @ApiParam("交易货币") @RequestParam String coinName,
            @ApiParam("结算货币") @RequestParam String settlementCurrency) {
        int cycle = 1;
        if (resolution.equalsIgnoreCase("1D")) {
            cycle = 1440;
        } else {
            cycle = Integer.parseInt(resolution);
        }
        Date endTime = new Date();
        Date startTime = DateUtils.addMinutes(endTime, -cycle * 2);

        List<CandleVo> candles = marketService.getCandles(resolution, coinName, settlementCurrency, startTime, endTime);
        return new CommonResponse(candles);
    }

    @ApiOperation("获取所有交易市场")
    @GetMapping("/getAllMarketList")
    public CommonResponse<List<Market>> getAllMarketList() {
//        List<Market> allMarkets = marketService.getAllMarkets();
        return new CommonResponse(marketService.getAllMarketsMap());
    }

    @ApiOperation("获取所有交易市场")
    @GetMapping("/getAllMarketCoinNames")
    public CommonResponse<List<Market>> getAllMarketCoinNames() {
        return new CommonResponse(marketService.getMarketCoinNamesByClosed());
    }

    @ApiOperation("某个交易区市场交易市场")
    @PostMapping("/getMarketList")
    public CommonResponse<List<Market>> getMarketList(@ApiParam("交易币种") @RequestParam(required = true) String settlementCurrency) {
        List<TradeStatVo> marketListBySettlement = marketService.getMarketListBySettlement(settlementCurrency);
        return new CommonResponse(marketListBySettlement);
    }
}
