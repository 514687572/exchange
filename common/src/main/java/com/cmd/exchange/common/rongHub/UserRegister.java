package com.cmd.exchange.common.rongHub;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmd.exchange.common.rongHub.model.UserModel;
import com.cmd.exchange.common.rongHub.result.TokenResult;
import com.cmd.exchange.common.rongHub.util.GsonUtil;
import com.cmd.exchange.common.rongHub.util.HostType;
import com.cmd.exchange.common.rongHub.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 */
@Slf4j
public class UserRegister {
    private static final String UTF8 = "UTF-8";
    private HostType apiHostType = new HostType("http://api.cn.ronghub.com");

    public TokenResult register(UserModel user, String appKey, String appSecret) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(user.id, UTF8));
        sb.append("&name=").append(URLEncoder.encode(user.name, UTF8));
        if (user.portrait != null) {
            sb.append("&portraitUri=").append(URLEncoder.encode(user.portrait, UTF8));
        } else {
            sb.append("&portraitUri=").append(URLEncoder.encode("", UTF8));
        }
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }

        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(apiHostType, appKey, appSecret, "/user/getToken.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);

        return getResult(HttpUtil.returnResult(conn));
    }


    private TokenResult getResult(String response) throws RuntimeException {
        JSONObject object = JSON.parseObject(response);
        String code = String.valueOf(object.get("code"));
        if (code.equals("200")) {
            return (TokenResult) GsonUtil.fromJson(response, TokenResult.class);
        } else {
            log.error("向融云请求注册token未成功！");
            throw new RuntimeException("向融云请求注册token未成功！");
        }

    }
}
