package com.cmd.exchange.api.filter;

import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * 用户操作拦截器
 */
public class UserOperatorInterCeptor implements HandlerInterceptor {

    private static final Log log = LogFactory.getLog(UserOperatorInterCeptor.class);


    @Value("${token.expireTime:1800}")
    private int TOKENEXPIRETIME;

    @Autowired
    private RedisTemplate<String, String> redUsersTemplate;

    // 保存解析后的日志注释
    private Hashtable<HandlerMethod, Expression> methodExps = new Hashtable<HandlerMethod, Expression>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        // httpServletRequest.setAttribute("User_Operator", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        log.info("update user token refresh time---");
        log.info("url --->" + httpServletRequest.getRequestURI());

        User user = ShiroUtils.getUser();
        log.info("user info " + user);
        if (user == null) {
            log.info("user info is null");
            return;
        }
        String token = getRequestToken(httpServletRequest);
        int type = 1;
        String plat = httpServletRequest.getHeader("platform");
        if (plat != null && (plat.equalsIgnoreCase("ios") || plat.equalsIgnoreCase("android"))) {
            type = 2;
        }

        Integer userId = user.getId();
        log.info("user_id is {}" + userId);
        //缓存里的token
        String redisToken = null;
        String redisUserIdToken = null;
        redisToken = redUsersTemplate.opsForValue().get("token:" + token);
        log.info("httpServerLet token is" + token);
        log.info("redisToken token is" + redisToken);
        if (type == 1) {

            redisUserIdToken = redUsersTemplate.opsForValue().get("token:" + userId);
            log.info("type ===1 redisUserIdToken token is" + redisUserIdToken);
            if (redisToken != null && redisToken != "" && redisUserIdToken != null && redisUserIdToken != "") {
                if (redisToken.equals(String.valueOf(userId)) && redisUserIdToken.equals(token)) {
                    log.info("type ===1 verifer success true ");
                    this.redUsersTemplate.opsForValue().set("token:" + token, String.valueOf(userId), TOKENEXPIRETIME, TimeUnit.SECONDS);
                    this.redUsersTemplate.opsForValue().set("token:" + userId, token, TOKENEXPIRETIME, TimeUnit.SECONDS);
                }
            }
        } else {
            redisUserIdToken = redUsersTemplate.opsForValue().get("mobile:" + userId);
            log.info("type ===2 redisUserIdToken token is" + redisUserIdToken);
            if (redisToken != null && redisToken != "" && redisUserIdToken != null && redisUserIdToken != "") {
                if (redisToken.equals(String.valueOf(userId)) && redisUserIdToken.equals(token)) {
                    log.info("type ===2 verifer success true ");
                    this.redUsersTemplate.opsForValue().set("token:" + token, String.valueOf(userId), TOKENEXPIRETIME, TimeUnit.SECONDS);
                    this.redUsersTemplate.opsForValue().set("mobile:" + userId, token, TOKENEXPIRETIME, TimeUnit.SECONDS);
                }
            }
        }
    }


    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }
}
