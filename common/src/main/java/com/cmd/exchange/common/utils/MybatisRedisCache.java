package com.cmd.exchange.common.utils;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用Redis实现Mybatis二级缓存
 * Created by jerry on 2017/7/14.
 */
public class MybatisRedisCache implements Cache {

    private static Logger LOGGER = LoggerFactory.getLogger(MybatisRedisCache.class);

    /**
     * mybatis二级缓存统一存储位置
     */
    private static final String MYBATIS_CACHE_STORE = "MYBATIS_CACHE_STORE";

    private static RedisConnectionFactory jedisConnectionFactory;

    private final String id;

    private String cacheName;

    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        LOGGER.debug("MybatisRedisCache:paymentDetailId=" + id);
        this.id = id;
        cacheName = MYBATIS_CACHE_STORE + id;
    }

    @Override
    public void clear() {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            connection.del(serializer.serialize(cacheName));
        } catch (JedisConnectionException e) {
            LOGGER.error("RedisCache缓存操作clear错误，错误原因：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            result = serializer.deserialize(connection.hGet(serializer.serialize(cacheName), serializer.serialize(key)));
        } catch (JedisConnectionException e) {
            LOGGER.error("RedisCache缓存操作getObject错误，错误原因：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    @Override
    public int getSize() {
        int result = 0;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            result = connection.hLen(serializer.serialize(cacheName)).intValue();
        } catch (JedisConnectionException e) {
            LOGGER.error("RedisCache缓存操作getSize错误，错误原因：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            connection.hSet(serializer.serialize(cacheName), serializer.serialize(key), serializer.serialize(value));
        } catch (JedisConnectionException e) {
            LOGGER.error("RedisCache缓存操作putObject错误，错误原因：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Object removeObject(Object key) {
        RedisConnection connection = null;
        Object result = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            result = connection.hDel(serializer.serialize(cacheName), serializer.serialize(key));
        } catch (JedisConnectionException e) {
            LOGGER.error("RedisCache缓存操作removeObject错误，错误原因：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public static void setJedisConnectionFactory(RedisConnectionFactory jedisConnectionFactory) {
        MybatisRedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }

}