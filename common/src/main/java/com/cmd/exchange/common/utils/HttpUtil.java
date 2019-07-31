package com.cmd.exchange.common.utils;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

/*
<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
<dependency>
<groupId>org.apache.httpcomponents</groupId>
<artifactId>httpclient</artifactId>
<version>4.5.4</version>
</dependency>
*/


public class HttpUtil {
    private static final String CHARSET = "UTF-8";

    public static String httpGet(String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            //httpGet.addHeader("token","8464465"); 添加header信息
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //http post请求（数据编码格式application/x-www-form-urlencoded）
    public static String HttpPost(String url, Map<String, Object> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, CHARSET);
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * http post请求（数据编码格式application/x-www-form-urlencoded）
     *
     * @param url     请求地址
     * @param headers
     * @param params  请求参数(参数格式："key1=value1&key2=value2")
     */
    public static String HttpPost(String url, Map<String, String> headers, String params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(params, CHARSET);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * http post请求（数据编码格式application/json）
     *
     * @param url  请求地址
     * @param json 请求参数（参数格式为json字符串）
     */
    public static String HttpPostJson(String url, String json) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            //中文乱码
            StringEntity stringEntity = new StringEntity(json, CHARSET);
            //数据编码格式
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, CHARSET);
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String HttpPostDigest(String userName, String passWord, String url, String json) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            Credentials creds = new UsernamePasswordCredentials(userName, passWord);
            URI uri = new URI(url);
            httpClient.getCredentialsProvider().setCredentials(new AuthScope(uri.getHost(), uri.getPort()), (Credentials) creds);

            httpClient.getParams().setParameter(AuthPolicy.DIGEST, Collections.singleton(AuthPolicy.DIGEST));
            httpClient.getAuthSchemes().register(AuthPolicy.DIGEST, new DigestSchemeFactory());
            HttpPost method = new HttpPost(url);
            method.addHeader("Content-type", "application/json; charset=utf-8");
            method.setHeader("Accept", "application/json");
            method.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
            HttpResponse response = httpClient.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String str = EntityUtils.toString(entity, CHARSET);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
