package com.cmd.exchange.api.config;


import com.cmd.exchange.common.oauth2.OAuth2Filter;
import com.cmd.exchange.common.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 *
 * @author lwx 2017/09/01
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/api/**", "anon");
        //同步融云聊天信息
        filterMap.put("/chat/sync/messages", "anon");
        //管理后台接口

        //swagger配置
        filterMap.put("/swagger**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/configuration/ui", "anon");

        //otc
        filterMap.put("/otc-trade/application-list", "anon");
        filterMap.put("/otc-trade/market-price", "anon");

        //同步融云聊天信息
        filterMap.put("/chat/sync/messages", "anon");

        filterMap.put("/user/verify-code", "anon");
        filterMap.put("/user/register*", "anon");
        filterMap.put("/user/reset-pwd", "anon");
        filterMap.put("/user/reset-pay-pwd", "anon");
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/national-code", "anon");
        filterMap.put("/cloud-trade/test", "anon");
        //用户推荐排行
        filterMap.put("/user/reward-ranking", "anon");
        filterMap.put("/user/check-verify-code", "anon");
        filterMap.put("/user/is-token-valid", "anon");

        filterMap.put("/google_verify/verify-code", "anon");
        filterMap.put("/google_verify/isbind-google-code", "anon");
        filterMap.put("/app-version", "anon");


        filterMap.put("/img/upload", "auth");
        filterMap.put("/img/upload-batch", "auth");
        filterMap.put("/info/register-protocol", "anon");
        filterMap.put("/feedback/**", "anon");

        //行情
        filterMap.put("/trade/open-trade-list", "anon");
        filterMap.put("/trade/trade-log-list", "anon");
        filterMap.put("/market/**", "anon");
        filterMap.put("/ws/market/**", "anon");
        //文章
        filterMap.put("/article/**", "anon");
        filterMap.put("/advertise/**", "anon");
        // 兑换平台币（私募）
        filterMap.put("/bon-convert/bon-buy-time", "anon");
        // 比赛排行
        filterMap.put("/trade-match-activity/**", "anon");


        filterMap.put("/**/*.png", "anon");
        filterMap.put("/**/*.jpg", "anon");
        filterMap.put("/**/*.jpeg", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**/*.html", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "auth");

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
