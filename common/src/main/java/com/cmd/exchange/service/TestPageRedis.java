package com.cmd.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestPageRedis {
    @Autowired
    private MonitorCacheService monitorCacheService;

    public List<String> getList() {
        String testKey = "testId";
        Map<Integer, String> hashMap = new HashMap<>();
        for (Integer i = 0; i < 100; i++) {
            hashMap.put(i, "string" + i);
            monitorCacheService.redisPusr(testKey, i);
            monitorCacheService.setObjectByStringKey("testId" + i, hashMap);

        }
        Long count = 0L;    // 用户总数;
        List<Object> objects = monitorCacheService.redisGetList(testKey, 11, 19);
        List<Integer> strs = new ArrayList<Integer>();
        for (Object o : objects) {
            strs.add((Integer) o);
            System.out.println("----" + o);
        }
        for (Integer i : strs) {
            Map<Integer, String> hashMap1 = (Map<Integer, String>) monitorCacheService.getObjectByStringKey("testId" + i);
            System.out.println(hashMap1.get(i));
        }

        return null;

    }

}
