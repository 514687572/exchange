package com.cmd.exchange.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class OnlineCountService {

    @Autowired
    private RedisTemplate<String, String> redUsersTemplate;

    public Integer getOnlineCount() {
        Integer count = 0;
        try {
            Set<String> mobileSet = redUsersTemplate.keys("mobile*");
            Set<String> tokenSet = redUsersTemplate.keys("token*");

            if (mobileSet != null) {
                count = count + mobileSet.size();
            }
            if (tokenSet != null) {
                count = count + tokenSet.size();
            }
            count = count / 2;
            return count;
        } catch (Exception e) {
            log.error("", e);
        }
        return count;
    }
}
