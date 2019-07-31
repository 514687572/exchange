package com.cmd.exchange.service;

import com.alibaba.fastjson.JSON;
import com.cmd.exchange.common.enums.TransactionPairs;
import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.mapper.TradeStatMapper;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.TradeStat;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.utils.HttpsUtil;
import com.cmd.exchange.common.vo.CandleVo;
import com.cmd.exchange.common.vo.TradeStatVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 交易统计
 */
@Service
public class TradeStatService {
    private static Log log = LogFactory.getLog(TradeStatService.class);
    @Autowired
    private TradeStatMapper tradeStatMapper;
    @Autowired
    private MarketService marketService;
    @Autowired
    private TradeLogMapper tradeLogMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TradeStatService me;    // 自己，用来更新缓存使用
    @Autowired
    private ExchangeRateService exchangeRateService;
    // 上次统计1分钟的时间
    private int oneMinuteLastStat = 0;

    @Value("${isget-from-huobi:false}")
    private Boolean isGetFromHuobi;
    @Value("${huobi-domain:https://api.huobi.br.com}")
    private String huobiDomain;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 统计一分钟的交易情况
     *
     * @param time 统计开始时间，秒为单位，如果不是60的倍数将会纠正为60的倍数（取小于time的最大60倍数）
     * @return 返回统计的结束时间，如果从提供时间到当前不够1分钟（+10秒），那么不会有统计
     */
    private int statOneMinuteTrades(int time) {
        log.info("statOneMinuteTrades begin time:" + time);
        int remainder = time % 60;
        int beginTime = time - remainder;
        int curTime = (int) (System.currentTimeMillis() / 1000);
        // 保留10秒钟的空闲时间，避免由于服务器之间的时间不一样导致统计不正确
        int endTime = curTime - 10 - (curTime - 10) % 60;
        if (endTime == beginTime) {
            return endTime;
        }
        Integer count = tradeStatMapper.statPerOneMinTrade(beginTime, endTime);
        log.info("statPerOneMinTrade return count:" + count + ",begin=" + beginTime + ",end=" + endTime);
        oneMinuteLastStat = endTime;
        return endTime;
    }

    /**
     * 统计自从最后一次后统计时间后每一分钟的交易情况
     *
     * @return 返回统计的结束时间
     */
    public int statOneMinuteTrades() {
        int statBegin = 0;
        TradeStat stat = tradeStatMapper.getLastTradeStat(1);
        if (stat == null) {
            log.info("do not find any stat");
        } else {
            statBegin = stat.getStatTime() + 60;
        }
        return statOneMinuteTrades(statBegin);
    }

    /**
     * 统计一段周期的交易情况
     *
     * @param beginTime 统计开始时间，秒为单位，如果不是60*cycle的倍数将会纠正为60*cycle的倍数（取小于time的最大60*cycle倍数）
     * @param endTime   统计结束时间（不包含），秒为单位，如果不是60*cycle的倍数将会纠正为60*cycle的倍数（取小于time的最大60*cycle倍数）
     * @param cycle     统计周期，如果从提供时间到当前不够cycle分钟（+10秒），那么不会有统计
     * @return 返回统计的结束时间，如果从提供时间到当前不够cycle分钟（+10秒），那么不会有统计
     */
    private int statCycleTrade(int beginTime, int endTime, int cycle) {
        if (cycle < 0) {
            throw new RuntimeException("cycle must > 0,bug get " + cycle);
        }
        if (cycle == 1) {
            return statOneMinuteTrades(beginTime);
        } else if (cycle == 1440) {
            return statOneDayTrades(beginTime, endTime);
        } else if (cycle > 1440) {
            if (cycle == 44640) {
                // 月统计
                return statOneMonthTrades(beginTime, endTime);
            }
            Assert.check(cycle % 1440 != 0, 1, "invalid cycle:" + cycle);
            return statNDayTrades(beginTime, endTime, cycle / 1440);
        }
        int cycleSecond = 60 * cycle;
        beginTime = beginTime - beginTime % cycleSecond;
        // 保留10秒钟的空闲时间，避免由于服务器之间的时间不一样导致统计不正确
        int curTime = (int) (System.currentTimeMillis() / 1000);
        if (endTime + 10 > curTime) {
            endTime = curTime - 10;
        }
        endTime = endTime - endTime % cycleSecond;
        Integer count = tradeStatMapper.statCycleTrade(beginTime, endTime, cycle);
        log.info("statCycleTrade return count:" + count + ",begin=" + beginTime + ",end=" + endTime + ",cycle=" + cycle);
        return endTime;
    }

    /**
     * 将时间修正为一天的0点0分0秒
     *
     * @param time 时间戳
     * @return
     */
    private int correctTimeToDayBegin(int time) {
        Date date = new Date((long) time * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public int statOneDayTrades(int beginTime, int endTime) {
        // 修正开始和结束时间都是一天的开始时间
        int beginTimeCorrect = correctTimeToDayBegin(beginTime);
        int endTimeCorrect = correctTimeToDayBegin(endTime);
        Integer count = tradeStatMapper.statDayTrade(beginTimeCorrect, endTimeCorrect);
        log.info("statOneDayTrades return count:" + count + ",begin=" + beginTime + ",end=" + endTime +
                ",sql begin time=" + beginTimeCorrect + ", sql end time=" + endTimeCorrect);
        return endTimeCorrect;
    }

    public int statNDayTrades(int beginTime, int endTime, int days) {
        // 修正开始和结束时间都是一天的开始时间
        int beginTimeCorrect = correctTimeToDayBegin(beginTime);
        Integer count = tradeStatMapper.statNDayTrade(beginTimeCorrect, endTime, days);
        log.info("statNDayTrades return count:" + count + ",begin=" + beginTime + ",end=" + endTime + ",days=" + days +
                ",sql begin time=" + beginTimeCorrect);
        return count != null ? count : 0;
    }

    private int correctTimeToMonBegin(int time) {
        Date date = new Date((long) time * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public int statOneMonthTrades(int beginTime, int endTime) {
        // 修正开始和结束时间都是一天的开始时间
        int beginTimeCorrect = correctTimeToMonBegin(beginTime);
        int endTimeCorrect = correctTimeToMonBegin(endTime);
        Integer count = tradeStatMapper.statMonthTrade(beginTimeCorrect, endTimeCorrect);
        log.info("statOneMonthTrades return count:" + count + ",begin=" + beginTime + ",end=" + endTime +
                ",sql begin time=" + beginTimeCorrect + ", sql end time=" + endTimeCorrect);
        return endTimeCorrect;
    }

    /**
     * 统计自从最后一次后统计时间后指定周期的交易情况
     *
     * @param cycle 统计周期
     * @return 返回统计的结束时间
     */
    public int statCycleTrades(int cycle) {
        int statBegin = 0;
        int cycleSecond = 60 * cycle;
        // 统计结束时间，找最后一次1分钟的统计时间 + 60，因为是通过1分钟的统计汇聚的，必须要有1分钟的记录才可以
        int statEnd = oneMinuteLastStat;
        if (oneMinuteLastStat == 0) {
            TradeStat stat1 = tradeStatMapper.getLastTradeStat(1);
            if (stat1 == null) {
                log.warn("do not has 1 minute status,can not work");
                return 0;
            }
            statEnd = stat1.getStatTime() + 60;
        }

        TradeStat stat = tradeStatMapper.getLastTradeStat(cycle);
        if (stat == null) {
            log.info("do not find any stat,cycle=" + cycle);
        } else {
            statBegin = stat.getStatTime() + cycleSecond;
        }
        return statCycleTrade(statBegin, statEnd, cycle);
    }

    /**
     * 更新所有市场昨天的收盘价
     */
    public void updateAllMarketLastDayPrice() {
        // 计算今天0点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayBegin = calendar.getTime();
        int todayBeginTimestamp = (int) (todayBegin.getTime() / 1000);
        // 获取所有市场
        List<Market> markets = marketService.getAllMarkets();
        for (Market market : markets) {
            BigDecimal price = null;
            if (("btc".equalsIgnoreCase(market.getCoinName()) && "usdt".equalsIgnoreCase(market.getSettlementCurrency()))) {
                price = getMarketClosePriceFromHuobi("btcusdt");
            } else if ("eth".equalsIgnoreCase(market.getCoinName()) && "usdt".equalsIgnoreCase(market.getSettlementCurrency())) {
                price = getMarketClosePriceFromHuobi("ethusdt");
            } else {
                price = tradeLogMapper.getMarketLastPrice(market.getCoinName(), market.getSettlementCurrency(), todayBeginTimestamp);
            }
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                log.info("update last day price=" + price + ",coin=" + market.getCoinName() + ",currency=" + market.getSettlementCurrency());
                marketService.updateLastDayPrice(market.getId(), price);
            }
        }
    }

    private BigDecimal getMarketClosePriceFromHuobi(String symbol) {
        String uri = huobiDomain + "/market/detail?symbol=" + symbol;
        byte[] bytes = new byte[0];
        try {
           // log.info("调用火币网交易对交易数据,uri-> " + uri);
            bytes = HttpsUtil.doGet(uri);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("接口调用异常 -> {}", e);
        }
        String lastPriceData = new String(bytes);
        if (StringUtils.isBlank(lastPriceData)) {
            return null;
        }
        BigDecimal closePrice = null;
        Map parseMap = (Map) JSON.parse(lastPriceData);
        if ("ok".equals(parseMap.get("status"))) {
            Map tickMap = (Map) parseMap.get("tick");
            if (null != tickMap) {
                closePrice = (BigDecimal) tickMap.get("close");
            }
        }
        return closePrice;
    }

    /**
     * 统计24小时内的实时数据
     */
    public void statLast24HourTrade() {
        log.debug("statLast24HourTrade");
        // 获取所有市场
        List<Market> markets = marketService.getAllMarkets();
        for (Market market : markets) {
            // 保存到缓存里面
            me.updateLast24HourTradeStat(market.getCoinName(), market.getSettlementCurrency());
        }
    }

    @Cacheable(value = "stat")
    public TradeStatVo getLast24HourTradeStat(String coinName, String settlementCurrency) {
        int now = (int) (System.currentTimeMillis() / 1000);
        int time24HourBefore = now - 3600 * 24;
        TradeStatVo result = new TradeStatVo();
        // 24小时之前的价格
        BigDecimal priceBeforeStat = tradeLogMapper.getMarketLastPrice(coinName, settlementCurrency, time24HourBefore);

        BigDecimal latestPrice;
        double changeRate = 0;
        Market m = marketService.getMarketByName(coinName, settlementCurrency);
//        if (isGetFromHuobi) {
        if (true) {

            // 从火币网拉取 交易对最新成交价格
            String symbol = (coinName + settlementCurrency).toLowerCase();
            if (TransactionPairs.BTCUSDT.getValue().equalsIgnoreCase(symbol)
                    || TransactionPairs.ETHUSDT.getValue().equalsIgnoreCase(symbol)
                    || TransactionPairs.EOSUSDT.getValue().equalsIgnoreCase(symbol)
                    || TransactionPairs.LTCUSDT.getValue().equalsIgnoreCase(symbol)
                    || TransactionPairs.BCHUSDT.getValue().equalsIgnoreCase(symbol)
                    || TransactionPairs.XRPUSDT.getValue().equalsIgnoreCase(symbol)) {
//                latestPrice = getMarketLatestPriceFromHuobi(symbol);
                latestPrice = getClosePriceFromHuobi(symbol);
                changeRate = getMarketChangeRateFromHuobi(symbol).doubleValue();
                log.info("火币网的最近价格是：" + String.valueOf(latestPrice) + ", 涨跌幅是： " + String.valueOf(changeRate));
            } else {
                latestPrice = tradeLogMapper.getMarketLatestPrice(coinName, settlementCurrency);
                if (latestPrice != null && m != null && m.getLastDayPrice() != null && m.getLastDayPrice().compareTo(BigDecimal.ZERO) != 0) {
                    changeRate = latestPrice.subtract(m.getLastDayPrice()).divide(m.getLastDayPrice(), 8, RoundingMode.HALF_UP).doubleValue();
                }
            }
        } else {
            // 最近一次的价格
            latestPrice = tradeLogMapper.getMarketLatestPrice(coinName, settlementCurrency);
            if (latestPrice != null && m != null && m.getLastDayPrice() != null && m.getLastDayPrice().compareTo(BigDecimal.ZERO) != 0) {
                changeRate = latestPrice.subtract(m.getLastDayPrice()).divide(m.getLastDayPrice(), 8, RoundingMode.HALF_UP).doubleValue();
            }
        }

        // 统计24小时内的最高价，最低价，成交价
        Map statInfo = tradeStatMapper.getStatInfoFromLog(coinName, settlementCurrency, time24HourBefore, now);
        // 涨跌幅修改为跟昨天收盘价比较,注释掉老代码
        if (priceBeforeStat == null) {
            priceBeforeStat = BigDecimal.ZERO;
        }
        if (latestPrice == null && m != null) {
            // 没有最新价，使用设置的昨天收盘价（就是开盘价）
            latestPrice = m.getLastDayPrice();
        }
        result.setCoinName(coinName);
        result.setSettlementCurrency(settlementCurrency);
        result.setStartTime(time24HourBefore);
        result.setEndTime(now);
        result.setPriceBeforeStat(priceBeforeStat);
        result.setLatestPrice(latestPrice);
        result.setChangeRate(changeRate);
        if (statInfo != null && Integer.parseInt(statInfo.get("deal_times").toString()) > 0) {
            result.setSumAmount(new BigDecimal(statInfo.get("sum_amount").toString()));
            result.setSumCurrency(new BigDecimal(statInfo.get("sum_currency").toString()));
            result.setDealTimes(Integer.parseInt(statInfo.get("deal_times").toString()));
            result.setMinPrice(new BigDecimal(statInfo.get("min_price").toString()));
            result.setMaxPrice(new BigDecimal(statInfo.get("max_price").toString()));
            result.setFirstPrice(new BigDecimal(statInfo.get("first_price").toString()));
            result.setLastPrice(new BigDecimal(statInfo.get("last_price").toString()));
        }

        if (result.getLatestPrice() != null) {
            BigDecimal commPrice = exchangeRateService.getCoinCommDisplayPrice(settlementCurrency);
            if (commPrice != null) {
                result.setLatestCnyPrice(result.getLatestPrice().multiply(commPrice));
            }
            // 先从费率表获取结算货币的人民币价格，如果没有在获取配置表中的通用价格来计算
            /*String commCoin = configService.getConfigValue(ConfigKey.COM_LEGAL_MONEY_DISP, "CNY");
            if(settlementCurrency.equalsIgnoreCase(commCoin)) {
                result.setLatestCnyPrice(result.getLatestPrice());
            } else {
                ExchangeRate rate = exchangeRateService.getExchangeRate(settlementCurrency, commCoin);
                if(rate != null && rate.getPrice() != null && rate.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    result.setLatestCnyPrice(result.getLatestPrice().multiply(rate.getPrice()));
                } else {
                    BigDecimal platformCoinCnyPrice = configService.getPlatformCoinCnyPrice();
                    if (platformCoinCnyPrice != null && result.getLatestPrice() != null) {
                        result.setLatestCnyPrice(result.getLatestPrice().multiply(platformCoinCnyPrice));
                    }
                }
            }*/
        }

        return result;
    }

    private BigDecimal getMarketLatestPriceFromHuobi(String symbol) {
        String uri = huobiDomain + "/market/trade?symbol=" + symbol;
        String lastPriceData = (String) redisTemplate.opsForHash().get("lastPriceFromHUOBI", symbol);
        if (StringUtils.isBlank(lastPriceData)) {
            byte[] bytes = new byte[0];
            try {
                //log.debug("调用火币网交易对交易数据,uri-> " + uri);
                bytes = HttpsUtil.doGet(uri);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("接口调用异常 -> {}", e);
            }
            lastPriceData = new String(bytes);
            if (StringUtils.isBlank(lastPriceData)) {
                return BigDecimal.ZERO;
            }
            redisTemplate.opsForHash().put("lastPriceFromHUOBI", symbol, lastPriceData);
            redisTemplate.expire("lastPriceFromHUOBI", 5, TimeUnit.SECONDS);
        }
        Map parseMap = (Map) JSON.parse(lastPriceData);
        //log.debug(new Date() + "请求火币网获 Trade Detail 数据结果： " + parseMap);
        if ("ok".equals(parseMap.get("status"))) {
            Map tickMap = (Map) parseMap.get("tick");
            List dataList = (List) tickMap.get("data");
            Map tradeMap = (Map) dataList.get(0);
            return new BigDecimal(String.valueOf(tradeMap.get("price")));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 从火币网计算获取涨幅
     *
     * @param symbol
     * @return
     */
    private BigDecimal getMarketChangeRateFromHuobi(String symbol) {
        String uri = huobiDomain + "/market/tickers";
        String lastPriceData = (String) redisTemplate.opsForHash().get("marketChangeRate", symbol);
        if (StringUtils.isBlank(lastPriceData)) {
            byte[] bytes = new byte[0];
            try {
                //log.info("调用火币网交易对交易数据,uri-> " + uri);
                bytes = HttpsUtil.doGet(uri);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("接口调用异常 -> {}", e);
            }
            lastPriceData = new String(bytes);

            if (StringUtils.isBlank(lastPriceData)) {
                return BigDecimal.ZERO;
            }
            redisTemplate.opsForHash().put("marketChangeRate", symbol, lastPriceData);
            redisTemplate.expire("marketChangeRate", 1, TimeUnit.SECONDS);
        }
        Map parseMap = (Map) JSON.parse(lastPriceData);
        //log.info(new Date() + "请求火币网获所有交易对的最新 Tickers结果： " + parseMap);
        BigDecimal changeRatePrice = BigDecimal.ZERO;
        if ("ok".equals(String.valueOf(parseMap.get("status")))) {
            List<Map> dataList = (List<Map>) parseMap.get("data");
            for (Map data : dataList) {
                String dataSymbol = String.valueOf(data.get("symbol"));
                if (symbol.equalsIgnoreCase(dataSymbol)) {
                    BigDecimal openPrice = (BigDecimal) data.get("open");
                    BigDecimal closePrice = (BigDecimal) data.get("close");
                    changeRatePrice = closePrice.subtract(openPrice).divide(openPrice, 8, RoundingMode.HALF_UP);
                }
            }
        }
        return changeRatePrice;
    }

    /**
     * 从火币网获取收盘价
     *
     * @param symbol
     * @return
     */
    private BigDecimal getClosePriceFromHuobi(String symbol) {
        String uri = huobiDomain + "/market/tickers";
        String closePriceData = (String) redisTemplate.opsForHash().get("ClosePrice", symbol);
        if (StringUtils.isBlank(closePriceData)) {
            byte[] bytes = new byte[0];
            try {
                //log.info("调用火币网交易对交易数据,uri-> " + uri);
                bytes = HttpsUtil.doGet(uri);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("接口调用异常 -> {}", e);
            }
            closePriceData = new String(bytes);
            if (StringUtils.isBlank(closePriceData)) {
                return BigDecimal.ZERO;
            }
            redisTemplate.opsForHash().put("ClosePrice", symbol, closePriceData);
            redisTemplate.expire("ClosePrice", 1, TimeUnit.SECONDS);
        }
        Map parseMap = (Map) JSON.parse(closePriceData);
        //log.info(new Date() + "请求火币网获所有交易对的最新 Tickers结果： " + parseMap);
        BigDecimal close = BigDecimal.ZERO;
        if ("ok".equals(String.valueOf(parseMap.get("status")))) {
            List<Map> dataList = (List<Map>) parseMap.get("data");
            for (Map data : dataList) {
                String dataSymbol = String.valueOf(data.get("symbol"));
                if (symbol.equalsIgnoreCase(dataSymbol)) {
                    close = (BigDecimal) data.get("close");
                }
            }
        }
        return close;
    }

    @CachePut(value = "stat")
    public TradeStatVo updateLast24HourTradeStat(String coinName, String settlementCurrency) {
        return getLast24HourTradeStat(coinName, settlementCurrency);
    }

    // 获取最近1到2次的统计信息（实时从原始表中统计的）
    @Cacheable(value = "newTradeStat")
    public List<CandleVo> getNewTradeStats(String coinName, String settlementCurrency, int cycle) {
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int statSecond = 60 * cycle;
        int remain = currentTime % statSecond;
        int beginTime = currentTime - remain - statSecond;
        // 如果是1440（按天统计），那么只统计当天
        if (cycle == 1440) {
            beginTime = DateUtil.getDayBeginTimestamp(1000L * currentTime);
        }
        List<CandleVo> result = new ArrayList<CandleVo>();
        int statSime = beginTime;
        while (statSime < currentTime) {
            Map statInfo = tradeStatMapper.getStatInfoFromLog(coinName, settlementCurrency, statSime, statSime + statSecond);
            if (statInfo.size() < 5) {
                statSime = statSime + statSecond;
                continue;
            }
            CandleVo candle = new CandleVo();
            candle.setTime(1000L * statSime);
            candle.setVolume(new BigDecimal(statInfo.get("sum_amount").toString()));
            candle.setLow(new BigDecimal(statInfo.get("min_price").toString()));
            candle.setHigh(new BigDecimal(statInfo.get("max_price").toString()));
            candle.setOpen(new BigDecimal(statInfo.get("first_price").toString()));
            candle.setClose(new BigDecimal(statInfo.get("last_price").toString()));
            statSime = statSime + statSecond;
            result.add(candle);
        }
        return result;
    }

    @CachePut(value = "newTradeStat")
    public List<CandleVo> updateNewTradeStats(String coinName, String settlementCurrency, int cycle) {
        return getNewTradeStats(coinName, settlementCurrency, cycle);
    }


    public List<TradeStat> getTradeStatOfTimeAndCycle(int beginTime, int cycle) {
        return tradeStatMapper.getTradeStatOfTimeAndCycle(beginTime, cycle);
    }

    // 统计一段时间内的费用
    public List<TradeStat> getTradeFeeStatOfTime(int beginTime, int endTime) {
        return tradeStatMapper.getTradeFeeStatOfTime(beginTime, endTime);
    }


    /**
     * 统计一段时间内的费用总和
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<TradeStat> getSumFeeBetweenTime(int beginTime, int endTime) {
        return tradeStatMapper.getSumFeeBetweenTime(beginTime, endTime);
    }


}
