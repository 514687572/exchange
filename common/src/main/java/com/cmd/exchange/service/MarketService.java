package com.cmd.exchange.service;

import com.cmd.exchange.blockchain.bitcoin.JSON;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.mapper.CoinMapper;
import com.cmd.exchange.common.mapper.MarketMapper;
import com.cmd.exchange.common.mapper.TradeStatMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.HttpsUtil;
import com.cmd.exchange.common.vo.CandleVo;
import com.cmd.exchange.common.vo.TimeSeriesVo;
import com.cmd.exchange.common.vo.TradeStatVo;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketService {
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private TradeStatMapper tradeStatMapper;
    @Autowired
    private CoinMapper coinMapper;

    @Value("${huobi-domain}")
    private String huobiDomain;
    @Value("${huobi-k-size}")
    private Integer huobiKSize;
    @Autowired
    RedisTemplate redisTemplate;


    @Transactional
    public Integer addMarket(Market market) {

        Market oldMarket = marketMapper.getMarket(market.getCoinName(), market.getSettlementCurrency());
        Assert.check(oldMarket != null, ErrorCode.ERR_MARKET_ALREADY_EXIST);

        Coin coin = coinMapper.getCoinByName(market.getCoinName());
        Assert.check(coin == null, ErrorCode.ERR_COIN_NOT_EXIST);
        coin = coinMapper.getCoinByName(market.getSettlementCurrency());
        Assert.check(coin == null, ErrorCode.ERR_CURRENCY_NOT_EXIST);


        market.setName(market.getCoinName() + "_" + market.getSettlementCurrency());
        marketMapper.add(market);
        return 0;
    }

    public Page<Market> getMarketList(String name, Integer pageNo, Integer pageSize) {
        return marketMapper.getMarketList(name, new RowBounds(pageNo, pageSize));
    }

    public Market getMarket(String coinName, String settlementCurrency) {
        return marketMapper.getMarket(coinName, settlementCurrency);
    }

    @Transactional
    public void deleteMarkets(List<Integer> marketIds) {
        for (Integer marketId : marketIds) {
            int rows = marketMapper.deleteMarket(marketId);
            Assert.check(rows == 0, 1, "failed to delete market: " + marketId);
        }
    }

    public List<Market> getAllMarkets() {
        return marketMapper.getAllMarkets();
    }
    public List<String> getMarketCoinNamesByClosed() {
        List<String> stringList = new ArrayList<>();
        List<Market> allMarkets = getAllMarkets();
        if (CollectionUtils.isEmpty(allMarkets)) {
            return stringList;
        }
        getAllMarkets().stream().filter(data -> MarketStatus.SHOW.equals(data.getClosed())).forEach(n -> stringList.add(n.getCoinName()));

        return stringList;
    }

    public Map<String, List<Market>> getAllMarketsMap() {
        List<Market> allMarkets = getAllMarkets();
        if (CollectionUtils.isEmpty(allMarkets)) {
            return null;
        }
        return allMarkets.stream()
                .filter(data -> MarketStatus.SHOW.equals(data.getClosed()))
                .collect(Collectors.groupingBy(Market::getSettlementCurrency));
    }

    public List<TradeStatVo> getMarketListBySettlement(String settlementCurrency) {
        if (StringUtils.isBlank(settlementCurrency))
            return null;
        List<TradeStatVo> list = Lists.newArrayList();
        for (Market m : getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW && settlementCurrency.equals(m.getSettlementCurrency())) {
                list.add(tradeStatService.getLast24HourTradeStat(m.getCoinName(), m.getSettlementCurrency()));
            }
        }
        return list;
    }

    public void updateLastDayPrice(Integer id, BigDecimal price) {
        marketMapper.updateLastDayPrice(id, price);
    }

    public Market getMarketById(Integer marketId) {
        return marketMapper.getMarketById(marketId);
    }

    public void updateMarket(Market market) {
        market.setName(market.getCoinName() + "_" + market.getSettlementCurrency());
        marketMapper.updateMarket(market);
    }

    // 支持 1，5,15,30,60,240,1D,5D,1W,1M
    public Integer getMinuteFromResolution(String resolution) {
        if (resolution.equalsIgnoreCase("1D")) {
            return 1440;
        } else if (resolution.equalsIgnoreCase("5D")) {
            return 1440 * 5;
        } else if (resolution.equalsIgnoreCase("1W")) {
            return 1440 * 7;
        } else if (resolution.equalsIgnoreCase("1M")) {
            return 1440 * 31;
        }
        return Integer.valueOf(resolution);
    }

    // 支持 1，5,15,30,60,240,1D,5D,1W,1M
    // 兼容火币网API
    public String getResolution(String resolution) {
        Integer timeDiff;
        if (resolution.equalsIgnoreCase("1D")) {
            return "1day";
        } else if (resolution.equalsIgnoreCase("1W")) {
            return "1week";
        } else if (resolution.equalsIgnoreCase("1M")) {
            return "1mon";
        } else if (resolution.equalsIgnoreCase("5D")) {
            timeDiff = 1440 * 5;
        } else {
            timeDiff = Integer.valueOf(resolution);
        }
        if (timeDiff <= 1) {
            return "1min";
        } else if (timeDiff > 1 && timeDiff <= 5) {
            return "5min";
        } else if (timeDiff > 5 && timeDiff <= 15) {
            return "15min";
        } else if (timeDiff > 15 && timeDiff <= 30) {
            return "30min";
        } else if (timeDiff > 30 && timeDiff <= 60) {
            return "60min";
        } else {
            return "1year";
        }
    }

    public List<CandleVo> getCandles(String resolution, String coinName, String settlementCurrency, Date startTime, Date endTime) {
        //Integer minutes = resolution.equalsIgnoreCase("1D") ? 1440 : Integer.valueOf(resolution);
        Integer minutes = getMinuteFromResolution(resolution);
        // 最多返回1000个周期
        if (endTime.getTime() - 1000L * 60 * minutes * 1000 > startTime.getTime()) {
            startTime.setTime(endTime.getTime() - 1000L * 60 * minutes * 1000);
        }
        List<CandleVo> candleVos = tradeStatMapper.getCandles(minutes, coinName, settlementCurrency, startTime.getTime() / 1000, endTime.getTime() / 1000);
        // 获取最新缓存数据，不在数据库里面，需要添加最新的缓存数据
        if (minutes <= 1440) {
            List<CandleVo> newCandles = tradeStatService.getNewTradeStats(coinName, settlementCurrency, minutes);
            for (CandleVo candle : newCandles) {
                // 如果candleVos不存在，那么添加上去，candleVos是按时间降序排序的,newCandles是按时间升序排序的
                if (candleVos == null || candleVos.size() == 0) {
                    candleVos.add(candle);
                    continue;
                }
                CandleVo dbCandle = candleVos.get(0);
                if (dbCandle.getTime() < candle.getTime()) {
                    // 不存在，增加一个到最前面
                    candleVos.add(0, candle);
                }
            }
        }
        return candleVos;
    }

    public List<CandleVo> getCandlesFromHuobi(String resolution, String symbol) {
        String period = getResolution(resolution);
        String uri = huobiDomain + "/market/history/kline?period=" + period + "&size=" + huobiKSize + "&symbol=" + symbol;
        String kLineKey = period + huobiKSize + symbol;
        String kLineData = (String) redisTemplate.opsForHash().get("kLineData", kLineKey);
        if (StringUtils.isBlank(kLineData)) {
            byte[] bytes = new byte[0];
            try {
                log.info("调用火币网k线数据，uri->{}", uri);
                bytes = HttpsUtil.doGet(uri);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("接口调用异常 -> {}", e);
            }
            kLineData = new String(bytes);
            log.info("调用火币网k线数据，返回->{}", kLineData);
            // 出现调用异常的时候使用第二重持久化缓存
            if (StringUtils.isBlank(kLineData)) {
                log.error("火币网数据返回空或者异常");
                kLineData = (String) redisTemplate.opsForHash().get("kLineData_Persistence", kLineKey);
            }
            redisTemplate.opsForHash().put("kLineData", kLineKey, kLineData);
            redisTemplate.expire("kLineData", 1, TimeUnit.MINUTES);

        }
        Map parseMap = (Map) JSON.parse(kLineData);
        log.debug(new Date() + "请求火币网K数据结果： " + parseMap);
        // 第二重持久化缓存
        if ("ok".equals(String.valueOf(parseMap.get("status")))) {
            redisTemplate.opsForHash().put("kLineData_Persistence", kLineKey, kLineData);
        } else {  // 如果返回数据异常也使用第二重缓存
            kLineData = (String) redisTemplate.opsForHash().get("kLineData_Persistence", kLineKey);
            parseMap = (Map) JSON.parse(kLineData);
        }

        List<Map> datas = (List<Map>) parseMap.get("data");
        if (CollectionUtils.isEmpty(datas)) { // 如果返回数据为空（基本不会出现）也使用第二重缓存
            log.error("请求出错，详情： ——" + String.valueOf(parseMap.get("err-msg")));
            kLineData = (String) redisTemplate.opsForHash().get("kLineData_Persistence", kLineKey);
            parseMap = (Map) JSON.parse(kLineData);
            datas = (List<Map>) parseMap.get("data");
        }
        List<CandleVo> candleVos = new ArrayList<>();
        datas.forEach(data -> {
            CandleVo candleVo = new CandleVo()
                    .setClose(new BigDecimal(String.valueOf(data.get("close"))))
                    .setHigh(new BigDecimal(String.valueOf(data.get("high"))))
                    .setLow(new BigDecimal(String.valueOf(data.get("low"))))
                    .setOpen(new BigDecimal(String.valueOf(data.get("open"))))
                    .setTime(Long.valueOf(String.valueOf(data.get("id"))) * 1000)
                    .setVolume(new BigDecimal(String.valueOf(data.get("vol"))));
            candleVos.add(candleVo);
        });
        return candleVos;
    }


    public List<TimeSeriesVo> getTimeSeries(String coinName, String settlementCurrency, Date startTime, Date endTime) {
        List<CandleVo> candleList = tradeStatMapper.getCandles(1, coinName, settlementCurrency, startTime.getTime() / 1000, endTime.getTime() / 1000);

        return TimeSeriesVo.fromCandleList(candleList);
    }

    public List<TradeStatVo> getLast24HourTradeStat(String coinName, String settlementCurrency) {
        List<TradeStatVo> tradeStatList = Lists.newArrayList();
        for (Market m : getAllMarkets()) {
            if (StringUtils.isNotBlank(coinName) && !m.getCoinName().equals(coinName)) {
                continue;
            }
            if (StringUtils.isNotBlank(settlementCurrency) && !m.getSettlementCurrency().equals(settlementCurrency)) {
                continue;
            }
            tradeStatList.add(tradeStatService
                    .getLast24HourTradeStat(m.getCoinName(), m.getSettlementCurrency())
                    .setCoinUrl(m.getCoinUrl())
            );
        }
        return tradeStatList;
    }

    public Market getMarketByName(String coinName, String settlementCurrency) {
        return marketMapper.getMarket(coinName, settlementCurrency);
    }
}
