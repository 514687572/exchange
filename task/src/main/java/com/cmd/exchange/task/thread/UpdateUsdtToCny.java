package com.cmd.exchange.task.thread;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.mapper.ConfigMapper;
import com.cmd.exchange.common.utils.HttpsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 更新usdt汇率
 */
@Component
public class UpdateUsdtToCny {
    private static Log log = LogFactory.getLog(UpdateUsdtToCny.class);
    @Autowired
    private ConfigMapper configMapper;

    @Scheduled(cron = "0 */1 * * * ?")
    public void updateUsdtToCnyPrice() {
        log.info("begin update Usdt To Cny Price");
        String usdtToCnyPrice = "6.9";
        try {
            usdtToCnyPrice = getUsdtToCnyFromHTML();
        } catch (Exception e) {
            log.error("解析网页出错： ->{}", e);
        }
        if (StringUtils.isNotBlank(usdtToCnyPrice)) {
            configMapper.updateConfigValue(ConfigKey.BCN_PRICE, usdtToCnyPrice);
        }
    }


    /*
     * 使用jsoup解析网页信息
     */
    private static String getUsdtToCnyFromHTML() {
        byte[] bytes = new byte[0];
        try {
            bytes = HttpsUtil.doGet("https://www.feixiaohao.com/currencies/tether/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(new String(bytes));
//        System.out.println("document" + document);
        Elements elements = document.select("span.val.textGreen");
//        System.out.println("elements" + elements);
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements) {
            //System.out.println("当前标签数据：" + element.text());
            stringBuilder.append(element.text());
        }
        return stringBuilder.toString();
    }


}
