package com.cmd.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存公共的方法，目前用于管理后台进行
 * 各种类型的数据监控（挂单监控，卖出监控，买入监控，各种货币数量监控）
 * 该方法属于公共的方法，其他的位置也可以调用
 */
@Service
public class MonitorCacheService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorCacheService.class);

    RedisTemplate<String, Object> template;

    @Autowired
    public void initRedis(RedisTemplate redisTemplate) {
        template = redisTemplate;
    }


    /***
     * 用于获取数据
     * @param key
     * @return
     */
    public Object getObjectByStringKey(String key) {
        return template.opsForValue().get(key);
    }

    /**
     * 增加数据
     *
     * @param key
     * @param object
     */
    public void setObjectByStringKey(String key, Object object) {
        template.opsForValue().set(key, object);
    }

    /**
     * 移除监控数据
     */
    public void delObjectByStringKey(String key) {
        template.delete(key);
    }

    public void redisPusr(String key, Integer i) {
        template.opsForList().rightPush(key, i);
    }

    public List<Object> redisGetList(String key, int start, int end) {
        return template.opsForList().range(key, start, end);
    }

    /**
     * 服务器监控的redis、
     *
     * @param key
     * @param object
     */
    public void setApiObjectByStringKey(String key, Object object, Long times) {
        template.opsForValue().set(key, object, times, TimeUnit.SECONDS);
    }
}
