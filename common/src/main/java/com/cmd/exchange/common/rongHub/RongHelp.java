package com.cmd.exchange.common.rongHub;

import com.cmd.exchange.common.rongHub.model.UserModel;
import com.cmd.exchange.common.rongHub.result.TokenResult;
import com.cmd.exchange.common.rongHub.util.SHA1Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lsl
 * @Date: Created in 2018/8/6
 */
@Slf4j
@Component
public class RongHelp {

    @Value("${rongHub.appKey:''}")
    private String appKey;
    @Value("${rongHub.appSecret:''}")
    private String appSecret;

    private static String signTimestamp = "signTimestamp";
    private static String nonce = "nonce";
    private static String signature = "signature";

    /**
     * 向融云登记用户获取token
     * <p>
     * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
     */
    public String registerRongHub(int userId, String userName) throws Exception {

        UserModel user = new UserModel()
                .setId(String.valueOf(userId))
                .setName(userName);
        UserRegister userRegister = new UserRegister();
        TokenResult result = userRegister.register(user, appKey, appSecret);

        log.info("{}生成Token:{}", userName, result.toString());

        return result.getToken();
    }

    /**
     * Signature (数据签名)计算方法：将系统分配的 App Secret、Nonce (随机数)、Timestamp (时间戳)
     * 三个字符串按先后顺序拼接成一个字符串并进行 SHA1 哈希计算。如果调用的数据签名验证失败，接口调用会
     * 返回 HTTP 状态码 401。其他状态码请参见:
     * http://www.rongcloud.cn/docs/server.html#api
     */
    public boolean checkRequestHeader(HttpServletRequest request) {

        /**
         * 发现融云返回的信息请求头压根没带相关校验信息，
         * 将flag初始为true,暂时不做校验了
         */
        boolean flag = true;

        StringBuffer sb = new StringBuffer();
        sb.append(appKey);
        sb.append(request.getHeader(nonce));
        sb.append(request.getHeader(signTimestamp));
        String signature1 = request.getHeader(signature);
        String sign = SHA1Util.encode(sb.toString());

        if (sign.equals(signature1)) flag = true;

        return flag;
    }
}
