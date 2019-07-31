package com.cmd.exchange.api.ws;

import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.mapper.MarketMapper;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.TradeStatService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MarketTask {
    private static Logger logger = LoggerFactory.getLogger(MarketTask.class);
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private MarketService marketService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private MarketMapper marketMapper;

    private Integer[] TRADE_STAT_CYCLE = new Integer[]{1, 5, 15, 30, 60, 240, 1440, 1440 * 5, 1440 * 7, 1440 * 31};

    //发送k线
    @Scheduled(fixedDelayString = "${cmd.market.send-candlestick-interval}")
    public void sendCandles() {
        for (Market m : marketService.getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW) {

                Date currentTime = new Date();

                for (Integer cycle : TRADE_STAT_CYCLE) {
                    try {
                        send(m, cycle, currentTime);
                    } catch (Exception e) {
                        logger.error("failed to send kline data， market: {}, cycle: {}", m.getName(), cycle);
                    }

                }
            }
        }
    }

    /**
     * @param cycle 分钟
     */
    private void send(Market m, Integer cycle, Date currentTime) {
        //只查询最后2条统计记录
        Date startTime = DateUtils.addMinutes(currentTime, -cycle * 2);

        String resolution = cycle == 1440 ? "1D" : cycle + "";
        if (cycle == 1440 * 5) {
            resolution = "5D";
        } else if (cycle == 1440 * 7) {
            resolution = "1W";
        } else if (cycle == 1440 * 31) {
            resolution = "1M";
        }

        List<CandleVo> candles = marketService.getCandles(resolution, m.getCoinName(), m.getSettlementCurrency(), startTime, currentTime);
        CandleResponse response = new CandleResponse();
        response.setKline(candles).setResolution(resolution).setSymbol(m.getCoinName() + "/" + m.getSettlementCurrency()).setType("kline");
        String destination = "/ws/market/candles?symbol=" + m.getCoinName() + "/" + m.getSettlementCurrency() + "&resolution=" + resolution + "&type=kline";
        template.convertAndSend(destination, response);
    }

    //发送k线
    @Scheduled(fixedDelayString = "${cmd.market.send-candlestick-interval}")
    public void sendTimeSeries() {
        for (Market m : marketService.getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW) {
                Date endTime = new Date();
                Date startTime = DateUtils.addDays(endTime, -30);
                List<CandleVo> candles = marketService.getCandles("1", m.getCoinName(), m.getSettlementCurrency(), startTime, endTime);
                template.convertAndSend("/ws/market/time-series?coinName=" + m.getCoinName() + "&settlementCurrency=" + m.getSettlementCurrency(), candles);
            }
        }
    }

    @Scheduled(fixedDelayString = "${cmd.market.send-trade-interval}")
    public void sendOpenTrade() {
        for (Market m : marketService.getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW) {
                OpenTradeListVo candles = tradeService.getOpenTradeList(m.getCoinName(), m.getSettlementCurrency());
                template.convertAndSend("/ws/market/open-trade-list?coinName=" + m.getCoinName() + "&settlementCurrency=" + m.getSettlementCurrency(), candles);
            }
        }
    }

    @Scheduled(fixedDelayString = "${cmd.market.send-trade-interval}")
    public void sendTradeLog() {
        for (Market m : marketService.getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW) {

                TradeLogListVo candles = tradeService.getTradeLogList(m.getCoinName(), m.getSettlementCurrency());
                template.convertAndSend("/ws/market/trade-log-list?coinName=" + m.getCoinName() + "&settlementCurrency=" + m.getSettlementCurrency(), candles);
            }
        }
    }

    @Scheduled(fixedDelayString = "${cmd.market.send-trade-interval}")
    public void sendTradeStat() {
        for (Market m : marketService.getAllMarkets()) {
            if (m.getClosed() == MarketStatus.SHOW) {
                TradeStatVo marketStat = tradeStatService.getLast24HourTradeStat(m.getCoinName(), m.getSettlementCurrency());
                template.convertAndSend("/ws/market/stats?coinName=" + m.getCoinName() + "&settlementCurrency=" + m.getSettlementCurrency(), marketStat);
            }
        }
    }

    @Scheduled(fixedDelayString = "${cmd.market.send-trade-interval}")
    public void sendAllTradeStat() {
        List<TradeStatVo> ls = marketService.getLast24HourTradeStat(null, null);
        template.convertAndSend("/ws/market/stats-list", ls);
    }

    @Scheduled(fixedDelayString = "${cmd.market.send-trade-interval}")
    public void getMarketList() {
        List<String> settlementCurrencys = marketMapper.getAllDistinctSettlementCurrency();
        settlementCurrencys.forEach(sc -> {
            List<TradeStatVo> marketListBySettlement = marketService.getMarketListBySettlement(sc);
            template.convertAndSend("/ws/market/getMarketList?settlementCurrency=" + sc, marketListBySettlement);
        });
    }
}
