package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.ExchangeRateService;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.TradeStatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 更新市场昨天的收盘价格
 */
@Component
public class UpdateLastDayPrice {
    private static Log log = LogFactory.getLog(UpdateLastDayPrice.class);
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private ConfigService configService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void updateLastDayPrice() {
        log.info("begin update last day market the last trade price");
        try {
            tradeStatService.updateAllMarketLastDayPrice();

            // 更新汇率表
            updateExchangeRates();
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    private void updateExchangeRates() {
        String platformCoin = configService.getPlatformCoinName();
        List<Market> markets = marketService.getAllMarkets();
        for (Market market : markets) {
            BigDecimal price = market.getLastDayPrice();
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    exchangeRateService.updateExchangeRate(market.getCoinName(), market.getSettlementCurrency(), price, null, "from market");
                } catch (Exception ex) {
                    log.error("", ex);
                }
                // 如果是交易货币是平台币，并且不存在交易货币是这个币，结算货币平台币的交易市场，那么倒过来计算汇率
                if (platformCoin != null && platformCoin.equalsIgnoreCase(market.getCoinName())) {
                    boolean find = false;
                    for (Market market1 : markets) {
                        if (market.getCoinName().equalsIgnoreCase(market1.getSettlementCurrency())
                                && market.getSettlementCurrency().equalsIgnoreCase(market1.getCoinName())) {
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        BigDecimal newPrice = BigDecimal.ONE.divide(price, 8, RoundingMode.HALF_UP).setScale(8, RoundingMode.HALF_UP);
                        log.info("cal price by 1/price, price=" + newPrice + ",coin=" + market.getSettlementCurrency() + ",currency=" + market.getCoinName());
                        exchangeRateService.updateExchangeRate(market.getSettlementCurrency(), market.getCoinName(), newPrice, null, "market inverse");
                    }
                }
            }
        }
    }
}
