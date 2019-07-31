package com.cmd.exchange.task.crawler;


import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.utils.JSONUtil;
import com.cmd.exchange.common.vo.CandleVo;
import okhttp3.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */
@Service
public class HuobiCrawler {
    private static Logger logger = LoggerFactory.getLogger(HuobiCrawler.class);

    @Autowired
    TradeLogMapper tradeLogMapper;

    public void updateTradeLog(String provider, String coinName, String settlementCurrency, Integer buyUserId, Integer sellUserId, Integer fakeTradeId) throws InterruptedException {
        List<CandleVo> candleList = new ArrayList<>();

        if (coinName.equalsIgnoreCase("doge")) {
            candleList.addAll(getGateioQuota(coinName, settlementCurrency));
        } else {
            if (provider.equalsIgnoreCase("huobi")) {
                candleList.addAll(getHuobiQuata(coinName, settlementCurrency));
            } else if (provider.equalsIgnoreCase("coinbase")) {
                candleList.addAll(getCoinbaseQuota(coinName, settlementCurrency));
            }
        }


        //补充成交记录
        CandleVo vo = candleList.get(0);
        //模拟四条交易

        BigDecimal volume = vo.getVolume().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        if (volume.compareTo(BigDecimal.ZERO) == 0) {
            volume = new BigDecimal("1");
        }
        BigDecimal openVolume = volume.multiply(new BigDecimal("0.2"));
        BigDecimal highVolume = volume.multiply(new BigDecimal("0.3"));
        BigDecimal lowVolume = volume.multiply(new BigDecimal("0.4"));
        BigDecimal closeVolume = volume.multiply(new BigDecimal("0.1"));

        tradeLogMapper.addTradeLog(coinName, settlementCurrency, vo.getOpen(), openVolume,
                buyUserId, sellUserId, new BigDecimal("0"), new BigDecimal("0"), fakeTradeId, fakeTradeId);
        Thread.sleep(2000);

        tradeLogMapper.addTradeLog(coinName, settlementCurrency, vo.getHigh(), highVolume,
                buyUserId, sellUserId, new BigDecimal("0"), new BigDecimal("0"), fakeTradeId, fakeTradeId);
        Thread.sleep(2000);

        tradeLogMapper.addTradeLog(coinName, settlementCurrency, vo.getLow(), lowVolume,
                buyUserId, sellUserId, new BigDecimal("0"), new BigDecimal("0"), fakeTradeId, fakeTradeId);
        Thread.sleep(2000);

        tradeLogMapper.addTradeLog(coinName, settlementCurrency, vo.getClose(), closeVolume,
                buyUserId, sellUserId, new BigDecimal("0"), new BigDecimal("0"), fakeTradeId, fakeTradeId);
    }

    private List<CandleVo> getHuobiQuata(String coinName, String settlementCurrency) {
        //SocketAddress proxyAddress = new InetSocketAddress("127.0.0.1",10086);
        //Proxy proxy = new Proxy(Proxy.Type.SOCKS,proxyAddress);
        //如果在本地测试，需要配置代理服务器 new OkHttpClient().newBuilder().proxy(proxy)
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        String period = "1min";

        String symbol = coinName + settlementCurrency;

        //https://api.huobi.pro/market/history/kline?period=1day&size=200&symbol=btcusdt
        //https://api.huobi.pro/market/history/kline?period=5minute&size=200&symbol=btcusdt


        HttpUrl url = new HttpUrl.Builder().scheme("http").host("api.huobi.pro")
                .addPathSegments("market/history/kline")
                .addQueryParameter("period", period)
                .addQueryParameter("size", "1")
                .addQueryParameter("symbol", symbol.toLowerCase())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        List<CandleVo> result = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            HuobiMarketResponse jsonRes = JSONUtil.parseToObject(responseString, HuobiMarketResponse.class);


            if (jsonRes == null || jsonRes.getData() == null || jsonRes.getData().isEmpty()) {
                logger.error("failed to fetch data from huobi, response:{}", responseString);
            }

            for (HuobiMarketResponse.HuobiMarketData candle : jsonRes.getData()) {
                CandleVo vo = new CandleVo();
                vo.setTime(candle.getId());
                vo.setLow(candle.getLow());
                vo.setHigh(candle.getHigh());
                vo.setOpen(candle.getOpen());
                vo.setClose(candle.getClose());
                vo.setVolume(candle.getAmount());
                result.add(vo);
            }

        } catch (IOException e) {
            logger.error("failed to fetch data from huobi, url: {}", url.toString(), e);
        }

        return result;
    }

    //https://data.gateio.io/api2/1/candlestick2/doge_usdt?group_sec=60&range_hour=1
    private List<CandleVo> getGateioQuota(String coinName, String settlementCurrency) {
        SocketAddress proxyAddress = new InetSocketAddress("127.0.0.1", 1080);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
        //如果在本地测试，需要配置代理服务器 new OkHttpClient().newBuilder().proxy(proxy)
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        String period = "1min";

        String symbol = coinName + settlementCurrency;

        //https://api.huobi.pro/market/history/kline?period=1day&size=200&symbol=btcusdt
        //https://api.huobi.pro/market/history/kline?period=5minute&size=200&symbol=btcusdt


        HttpUrl url = new HttpUrl.Builder().scheme("https").host("data.gateio.io")
                .addPathSegments("api2/1/candlestick2/" + coinName.toUpperCase() + "_" + settlementCurrency)
                .addQueryParameter("group_sec", "60")
                .addQueryParameter("range_hour", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        List<CandleVo> result = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            GateioResponse jsonRes = JSONUtil.parseToObject(responseString, GateioResponse.class);

            if (jsonRes == null || jsonRes.getData() == null || jsonRes.getData().isEmpty()) {
                logger.error("failed to fetch data from gateio, response:{}", responseString);
            }

            for (String[] candle : jsonRes.getData()) {
                CandleVo vo = new CandleVo();
                vo.setTime(Long.valueOf(candle[0]));
                vo.setLow(new BigDecimal(candle[4]));
                vo.setHigh(new BigDecimal(candle[3]));
                vo.setOpen(new BigDecimal(candle[5]));
                vo.setClose(new BigDecimal(candle[2]));
                vo.setVolume(new BigDecimal(candle[1]));
                result.add(vo);
            }

        } catch (IOException e) {
            logger.error("failed to fetch data from gateio, url: {}", url.toString(), e);
        }

        return result;
    }


    private List<CandleVo> getCoinbaseQuota(String coinName, String settlementCurrency) {
        OkHttpClient client = new OkHttpClient();

        //2017-12-14T00:35:05.265Z
        Date endDate = new Date();
        Date startDate = DateUtils.addMinutes(endDate, -2);
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String start = DateFormatUtils.format(startDate, pattern);
        String end = DateFormatUtils.format(endDate, pattern);

        int granularity = 60;

        logger.info("start: {}, end: {} ", start, end);
        //https://docs.gdax.com/?javascript#products
        //https://www.okex.com/rest_request.html
        //测试商户app id是wp_app_test, 签名带有特殊字符在路径中传递必须编码

        if (coinName.equalsIgnoreCase("usdt")) {
            coinName = "usd";
        }

        if (settlementCurrency.equalsIgnoreCase("usdt")) {
            settlementCurrency = "usd";
        }


        HttpUrl url = new HttpUrl.Builder().scheme("https").host("api.gdax.com").addPathSegments("products/" + coinName.toLowerCase() + "-" + settlementCurrency.toLowerCase() + "/candles")
                .addQueryParameter("start", start)
                .addQueryParameter("end", end)
                .addQueryParameter("granularity", "" + granularity)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        List<CandleVo> result = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            List<List<Object>> jsonRes = (List<List<Object>>) JSONUtil.parseToObject(responseString, List.class);

            for (List<Object> candle : jsonRes) {
                CandleVo vo = new CandleVo();
                vo.setTime(Long.valueOf(String.valueOf(candle.get(0))));
                vo.setLow(new BigDecimal(String.valueOf(candle.get(1))));
                vo.setHigh(new BigDecimal(String.valueOf(candle.get(2))));
                vo.setOpen(new BigDecimal(String.valueOf(candle.get(3))));
                vo.setClose(new BigDecimal(String.valueOf(candle.get(4))));
                vo.setVolume(new BigDecimal(String.valueOf(candle.get(5))));
                result.add(vo);
            }

        } catch (IOException e) {
            logger.error("failed to fetch data", e);
        }

        return result;
    }

    private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            }, null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            return factory;
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }


    public static void main(String[] args) {
        System.getProperties().put("http.proxyPort", "10086");
        System.getProperties().put("http.proxyHost", "localhost");

        HuobiCrawler crawler = new HuobiCrawler();

        //LTC、BCH、ETC、HSR、QTUM、DOGE、OMG、VEN
        String[] coinName = new String[]{"BTC"};
        for (String c : coinName) {
            try {
                List<CandleVo> coinbaseQuota = crawler.getHuobiQuata(c, "USDT");

                for (CandleVo v : coinbaseQuota) {
                    System.out.println(JSONUtil.toJSONString(v));
                }
            } catch (Exception e) {
                logger.error("failed to get data: {}", c, e);
            }
        }
    }
}
