package com.cmd.exchange.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * 参考https://spring.io/guides/gs/messaging-stomp-websocket/
 * 注意： websocket的url和rest接口的url一定要区分，否则websocket握手的请求会跑到rest接口里，导致建立链接失败。
 * 错误的信息： 服务器返回状态码200
 * Created by linmingren on 2017/8/30.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //有3个主题可以发布信息, 客户端注册到这些主题下面的具体的内容
        config.enableSimpleBroker("/ws/market/candles", "/ws/market/time-series",
                "/ws/market/candle_stick", "/ws/market/stats-list", "/market/stats",
                "/ws/market/open-trade-list", "/ws/market/trade-log-list", "/ws/market/stats", "/ws/market/getMarketList",
                "/ws/market/invest-detail-current-list");
        //这个是处理客户端主动发上来的请求
        config.setApplicationDestinationPrefixes("/ws/market");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //如果客户端不支持websocket， 则使用sockjs
        //必须配置setAllowedOrigins， 否则浏览器连接websocket会提示403

        registry.addEndpoint("/ws/market").setAllowedOrigins("*").withSockJS();
        //必须配置握手处理器，否则stomp客户端无法连接
        registry.addEndpoint("/ws/market")
                .setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy()))
                .setAllowedOrigins("*");
    }
}
