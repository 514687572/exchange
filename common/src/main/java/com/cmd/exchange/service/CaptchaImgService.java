package com.cmd.exchange.service;

import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.CageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CaptchaImgService {

    private static final int LEN = 4;
    private static final int OVERTIME = 3;
    private static final String PRE_KEY = "CAPTCHA_IMG_";

    private RedisTemplate<String, String> redUsersTemplate;

    @Autowired
    public CaptchaImgService(RedisTemplate<String, String> redUsersTemplate) {
        this.redUsersTemplate = redUsersTemplate;
        this.redUsersTemplate.setKeySerializer(new StringRedisSerializer());
        this.redUsersTemplate.setValueSerializer(new StringRedisSerializer());
    }

    public String getWord(String nationalCode, String mobile) {
        String word = CageUtil.getWordsNumber(LEN);
        this.redUsersTemplate.opsForValue().set(PRE_KEY + nationalCode + mobile, word, OVERTIME, TimeUnit.MINUTES);
        return word;
    }

    public void check(String nationalCode, String mobile, String words) {
        log.info("imageCode viterfy {}----", words);
        log.info("nationalCode is:-> {},mobile is:-> {}", nationalCode, mobile);
        String key = PRE_KEY + nationalCode + mobile;
        String targetWords = this.redUsersTemplate.opsForValue().get(key);
        log.info("imageCode viterfy get redis code is {}----", targetWords);
        Assert.check(targetWords == null, 500, "图形验证码超时或错误");
        Assert.check(!targetWords.toLowerCase().equals(words.toLowerCase()), 500, "图形验证码错误");
        this.redUsersTemplate.delete(key);
    }
}