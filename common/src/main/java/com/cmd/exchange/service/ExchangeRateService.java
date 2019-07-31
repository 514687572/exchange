package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.mapper.ExchangeRateMapper;
import com.cmd.exchange.common.mapper.MarketMapper;
import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.mapper.TradeStatMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.ExchangeRate;
import com.cmd.exchange.common.model.Market;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExchangeRateService {
    private static Log log = LogFactory.getLog(ExchangeRateService.class);
    @Autowired
    private ExchangeRateMapper exchangeRateMapper;
    @Autowired
    private CoinService coinService;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private TradeStatMapper tradeStatMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TradeLogMapper tradeLogMapper;

    public ExchangeRate getExchangeRate(String coinName, String settlementCurrency) {
        return exchangeRateMapper.getExchangeRate(coinName, settlementCurrency);
    }

    // 更新所有市场24小时前价格
    public void updateAllMarketHourAgoPrice() {
        String platformCoin = configService.getPlatformCoinName();
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        // int time24ago = currentTime - 3600 * 24;
        List<Market> markets = marketMapper.getAllMarkets();
        for (Market market : markets) {
            if (market.getClosed() != MarketStatus.SHOW) {
                continue;
            }
            BigDecimal guestRate24H = tradeStatMapper.getAvgPriceOfHour(market.getCoinName(), market.getSettlementCurrency(), currentTime);
            if (guestRate24H != null && guestRate24H.compareTo(BigDecimal.ZERO) > 0) {
                updateExchangeRate(market.getCoinName(), market.getSettlementCurrency(), null, guestRate24H, "trade stat");

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
                        BigDecimal newPrice = BigDecimal.ONE.divide(guestRate24H, 8, RoundingMode.HALF_UP).setScale(8, RoundingMode.HALF_UP);
                        log.info("cal price by 1/price, price=" + newPrice + ",coin=" + market.getSettlementCurrency() + ",currency=" + market.getCoinName());
                        updateExchangeRate(market.getSettlementCurrency(), market.getCoinName(), null, newPrice, "market inverse");
                    }
                }
            }
        }
    }

    /**
     * 通过已有的兑换率，补充指定货币作为结算货币兑换所有其他数字货币的兑换率
     *
     * @param coinName 货币名称
     */
    public void calCoinAllExchangeRate(String coinName) {
        if (coinName == null) {
            return;
        }
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        int time24ago = currentTime;// - 3600 * 24;
        List<Coin> allCoins = coinService.getCoin();
        for (Coin coin : allCoins) {
            if (coin.getName().equals(coinName)) {
                continue;
            }
            Market market = marketMapper.getMarket(coin.getName(), coinName);
            if (market != null) {
                // 市场，只更新24小时前价格
                continue;
            }
            // 不存在，找一个其它货币来计算可能的兑换率
            boolean inverse = false;
            BigDecimal guestRate = exchangeRateMapper.guestExchangeRate(coin.getName(), coinName);
            if (guestRate == null || guestRate.compareTo(BigDecimal.ZERO) <= 0) {
                log.info("guestExchangeRate failed,coin=" + coin.getName() + ",settlementCurrency=" + coinName);
                // 使用反转市场来算
                Market tmpMarket = marketMapper.getMarket(coinName, coin.getName());
                if (tmpMarket == null || tmpMarket.getLastDayPrice() == null || tmpMarket.getLastDayPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                // 市场关闭不计算
                if (tmpMarket.getClosed() != MarketStatus.SHOW) {
                    continue;
                }
                log.info("guestExchangeRate by inverse,coin=" + coinName + ",settlementCurrency=" + coin.getName());
                guestRate = BigDecimal.ONE.divide(tmpMarket.getLastDayPrice(), 8, RoundingMode.HALF_UP);
                inverse = true;
            }
            BigDecimal guestRate24H = exchangeRateMapper.guestHourAgoExchangeRate(coin.getName(), coinName);
            if (guestRate24H == null || guestRate24H.compareTo(BigDecimal.ZERO) <= 0) {
                log.info("guestHourAgoExchangeRate failed,coin=" + coin.getName() + ",settlementCurrency=" + coinName);
                // 使用反转市场来算
                Market tmpMarket = marketMapper.getMarket(coinName, coin.getName());
                // 市场关闭不计算
                if (tmpMarket == null || tmpMarket.getClosed() != MarketStatus.SHOW) {
                    continue;
                }
                // 获取市场24小时钱价格
                guestRate24H = tradeStatMapper.getAvgPriceOfHour(coinName, coin.getName(), time24ago);
                if (guestRate24H == null || guestRate24H.compareTo(BigDecimal.ZERO) <= 0) {
                    log.info("getAvgPriceOfHour failed,coin=" + coinName + ",settlementCurrency=" + coin.getName());
                    guestRate24H = guestRate;
                }
                log.info("guestExchangeRate by inverse,coin=" + coinName + ",settlementCurrency=" + coin.getName());
                guestRate24H = BigDecimal.ONE.divide(guestRate24H, 8, RoundingMode.HALF_UP);
            }

            log.info("guestExchangeRate success,coin=" + coin.getName() + ",settlementCurrency=" + coinName + ",guestRate=" + guestRate);

            ExchangeRate newRate = new ExchangeRate();
            newRate.setCoinName(coin.getName());
            newRate.setSettlementCurrency(coinName);
            newRate.setComment(inverse ? "inverse" : "guess");
            newRate.setPrice(guestRate.setScale(8, RoundingMode.HALF_UP));
            newRate.setPriceHourAgo(guestRate24H.setScale(8, RoundingMode.HALF_UP));
            try {
                ExchangeRate rate = exchangeRateMapper.getExchangeRate(coin.getName(), coinName);
                if (rate != null) {
                    // 修改兑换率
                    rate.setComment("guest");
                    rate.setCoinName(null);
                    rate.setSettlementCurrency(null);
                    rate.setPrice(newRate.getPrice());
                    rate.setPriceHourAgo(newRate.getPriceHourAgo());
                    exchangeRateMapper.updateExchangeRate(rate);
                } else {
                    exchangeRateMapper.add(newRate);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    public void updateExchangeRate(String coinName, String settlementCurrency, BigDecimal rate, BigDecimal rateHourAgo, String comment) {
        if ((rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) && (rateHourAgo == null || rateHourAgo.compareTo(BigDecimal.ZERO) <= 0)) {
            return;
        }
        ExchangeRate exchangeRate = exchangeRateMapper.getExchangeRate(coinName, settlementCurrency);
        if (exchangeRate == null) {
            if ((rate == null) && (rateHourAgo != null)) rate = rateHourAgo;
            // 不存在，增加一个
            ExchangeRate newRate = new ExchangeRate();
            newRate.setCoinName(coinName);
            newRate.setSettlementCurrency(settlementCurrency);
            newRate.setComment(comment);
            newRate.setPrice(rate);
            if (rateHourAgo == null) rateHourAgo = rate;
            newRate.setPriceHourAgo(rateHourAgo);
            exchangeRateMapper.add(newRate);
        } else {
            exchangeRate.setComment(comment);
            exchangeRate.setPrice(rate);
            exchangeRate.setPriceHourAgo(rateHourAgo);
            exchangeRateMapper.updateExchangeRate(exchangeRate);
        }
    }

    public List<ExchangeRate> getCoinExchangeRate(String coinName) {
        return exchangeRateMapper.getCoinExchangeRate(coinName);
    }

    public BigDecimal getCoinCommDisplayPrice(String coinName) {
        // 先从费率表获取结算货币的人民币价格，如果没有在获取配置表中的通用价格来计算
        String commName = configService.getConfigValue(ConfigKey.COM_LEGAL_MONEY_DISP, "CNY");
        if (coinName.equalsIgnoreCase(commName)) {
            return BigDecimal.ONE;
        }
        ExchangeRate rate = getExchangeRate(coinName, commName);
        if (rate != null && rate.getPrice() != null && rate.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            return rate.getPrice();
        } else {
            // 有可能市场是新创建的，查看是否有最新的市场价格
            BigDecimal price = tradeLogMapper.getLatestPrice(coinName, commName);
            if (price != null) return price;
            // 剩下情况直接使用默认价格
            BigDecimal platformCoinCnyPrice = configService.getPlatformCoinCnyPrice();
            return platformCoinCnyPrice;
        }
    }

    public BigDecimal geOrGuessCoinCommDisplayPrice(String coinName) {
        // 先从费率表获取结算货币的人民币价格，如果没有使用中间表猜测
        String commCoin = configService.getConfigValue(ConfigKey.COM_LEGAL_MONEY_DISP, "CNY");
        if (coinName.equalsIgnoreCase(commCoin)) {
            return BigDecimal.ONE;
        }
        ExchangeRate rate = getExchangeRate(coinName, commCoin);
        if (rate != null && rate.getPrice() != null && rate.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            return rate.getPrice();
        } else {
            BigDecimal guestRate = exchangeRateMapper.guestExchangeRate(coinName, commCoin);
            if (guestRate != null) {
                return guestRate;
            }
            // 有可能市场是新创建的，查看是否有最新的市场价格
            BigDecimal price = tradeLogMapper.getLatestPrice(coinName, commCoin);
            if (price != null) return price;
        }
        return BigDecimal.ZERO;
    }
}
