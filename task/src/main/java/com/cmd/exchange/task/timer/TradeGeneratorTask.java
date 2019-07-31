package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.task.crawler.HuobiCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//模拟下单，用来生成K线，方便测试
@Component
public class TradeGeneratorTask {
    private static Logger logger = LoggerFactory.getLogger(TradeGeneratorTask.class);
    @Value("${task.trade.test.generate:false}")
    private Boolean generateData;
    @Value("${task.trade.test.buy-user-id}")
    private Integer buyUserId;
    @Value("${task.trade.test.sell-user-id}")
    private Integer sellUserId;
    @Value("${task.trade.test.fake-trade-id:0}")
    private Integer fakeTradeId;
    @Value("${task.trade.test.provider:huobi}")
    private String provider;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private HuobiCrawler crawler;

    @Scheduled(fixedDelayString = "30000")
    public void createBuyTrade() throws InterruptedException {
        if (!generateData) {
            return;
        }

        for (Market m : marketService.getAllMarkets()) {
            try {
                logger.info("start fetching data for market: {} ", m.getName());
                crawler.updateTradeLog(provider, m.getCoinName(), m.getSettlementCurrency(), buyUserId, sellUserId, fakeTradeId);
            } catch (Exception e) {
                logger.error("failed to fetching data for market: {}", m.getName(), e);
            }
        }
    }
}
