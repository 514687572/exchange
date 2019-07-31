package com.cmd.exchange.admin.config;

import com.cmd.exchange.common.utils.MybatisRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {
    @Autowired
    RedisConnectionFactory jedisConnectionFactory;

    /**
     * 使用中间类解决RedisCache.jedisConnectionFactory的静态注入，从而使MyBatis实现第三方缓存
     *
     * @param jedisConnectionFactory JedisConnectionFactory
     * @return RedisCacheTransfer
     */
    @Bean
    public RedisCacheTransfer redisCacheTransfer(RedisConnectionFactory jedisConnectionFactory) {
        return new RedisCacheTransfer(jedisConnectionFactory);
    }

    class RedisCacheTransfer {

        public RedisCacheTransfer(RedisConnectionFactory jedisConnectionFactory) {
            MybatisRedisCache.setJedisConnectionFactory(jedisConnectionFactory);
        }
    }
}
