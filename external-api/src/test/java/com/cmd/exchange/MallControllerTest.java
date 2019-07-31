package com.cmd.exchange;

import com.alibaba.fastjson.JSON;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.external.request.UserDeductionIntegral;
import com.cmd.exchange.external.utils.SignUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class MallControllerTest {
    RestTemplate restTemplate;

    private static String serverUrl = "http://127.0.0.1:8193";
    //api key
    private static String key = "3ddb8cf2292139111e4e6aa68d4d133d";
    private static String secret = "66640eb4-4b75-492c-a46a-5364eddddaaa";

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void userDeductionIntegral() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        List<UserDeductionIntegral> userDeductionIntegrals = new ArrayList<>();
        UserDeductionIntegral userDeductionIntegral = new UserDeductionIntegral();
        userDeductionIntegral.setCoinName("UKS");
        userDeductionIntegral.setChangeBalance(BigDecimal.ONE);
        userDeductionIntegrals.add(userDeductionIntegral);
        userDeductionIntegral = new UserDeductionIntegral();
        userDeductionIntegral.setChangeBalance(BigDecimal.ONE);
        userDeductionIntegral.setCoinName("USDT");
        userDeductionIntegrals.add(userDeductionIntegral);
        String s = JSON.toJSONString(userDeductionIntegrals);
        System.out.println("userDeductionIntegrals:" + s);

        ;
        //parameters.add("changeBalance", "1.88888885698");
        parameters.add("mobile", "18851517225");
        parameters.add("userDeductionIntegrals", s);
        parameters.add("apiKey", key);
        parameters.add("timestamp", System.currentTimeMillis() / 1000l + "");
        parameters.add("orderNum", System.currentTimeMillis() + "");
        parameters.add("sign", getSign(parameters));

        sendRequst(HttpMethod.POST, "/mall/deduction-integral", parameters);
        //  sendRequst(HttpMethod.POST, "/mall/deduction-integral", parameters);
    }

    @Test
    public void cancelTrade() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.add("tradeId", "16979");
        parameters.add("apiKey", key);
        parameters.add("timestamp", System.currentTimeMillis() / 1000l + "");
        parameters.add("sign", getSign(parameters));

        sendRequst(HttpMethod.POST, "/trades/cancel", parameters);
    }

    @Test
    public void getOpenTrades() {

        List<NameValuePair> values = new ArrayList();
        values.add(new BasicNameValuePair("pageNo", "1"));
        values.add(new BasicNameValuePair("pageSize", "10"));
        values.add(new BasicNameValuePair("apiKey", key));
        values.add(new BasicNameValuePair("timestamp", System.currentTimeMillis() / 1000l + ""));
        values.add(new BasicNameValuePair("sign", getSign(values)));

        String path = URLEncodedUtils.format(values, Charset.defaultCharset());

        sendRequst(HttpMethod.GET, "/trades/current?" + path, null);
    }


    private void sendRequst(HttpMethod method, String url, MultiValueMap<String, String> parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                parameters, headers);

        ResponseEntity<CommonResponse> responseEntity = null;


        if (method == HttpMethod.GET) {
            responseEntity = restTemplate.getForEntity(serverUrl + url, CommonResponse.class);
        } else {
            responseEntity = restTemplate.postForEntity(serverUrl + url, request, CommonResponse.class);
        }

        assertEquals(responseEntity.getBody().getErrorMessage(), responseEntity.getBody().getStatusCode(), 0);

        System.out.println("response code: " + responseEntity.getBody().getStatusCode() + ",content:" + responseEntity.getBody().getContent());
    }

    private static String getSign(MultiValueMap<String, String> parameters) {
        Map<String, String> maps = new HashMap<>();
        //sign 字段不参加签名
        for (String key : parameters.keySet()) {
            if (!key.equalsIgnoreCase("sign")) {
                maps.put(key, parameters.get(key).get(0));
            }
        }

        return SignUtil.getSignString("md5", secret, maps);
    }

    private static String getSign(List<NameValuePair> parameters) {
        Map<String, String> maps = new HashMap<>();
        //sign 字段不参加签名
        for (NameValuePair key : parameters) {
            if (!key.getName().equalsIgnoreCase("sign")) {
                maps.put(key.getName(), key.getValue());
            }
        }

        return SignUtil.getSignString("md5", secret, maps);
    }

    @Test
    public void getSign() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        /*parameters.add("coinName", "BTC");
        parameters.add("settlementCurrency","USDT");
        parameters.add("type","BUY");
        parameters.add("priceType","LIMITED");
        parameters.add("amount","1");
        parameters.add("price","1");*/

        parameters.add("pageNo", "1");
        parameters.add("pageSize", "10");

        parameters.add("apiKey", "3ddb8cf2292139111e4e6aa68d4d133d");
        parameters.add("timestamp", "1530861209");

        System.out.println(getSign(parameters));
    }

}
