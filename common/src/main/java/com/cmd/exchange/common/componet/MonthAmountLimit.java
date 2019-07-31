package com.cmd.exchange.common.componet;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;

// 用来计算一样东西每月的总数，用于限制总数
// 这个类没有做好并发控制，只能单线程访问
public class MonthAmountLimit {

    private StringRedisTemplate redisTemplate;
    // 保存限制的前缀，在redis中，是map的key名称
    private String keyPrefix;

    // 一天限制的次数
    private double monthLimitAmount;

    public MonthAmountLimit(String keyPrefix, StringRedisTemplate redisTemplate) {
        this.keyPrefix = keyPrefix;
        this.redisTemplate = redisTemplate;
    }

    public double getMonthLimitAmount() {
        return monthLimitAmount;
    }

    public void setMonthLimitAmount(double monthLimitAmount) {
        this.monthLimitAmount = monthLimitAmount;
    }

    // 查看指定标识当天是否使用完次数了，返回true标识用完次数了
    public boolean isMonthAmountFinishUp(String identity) {
        return isMonthAmountFinishUp(identity, new Date());
    }

    private int getMonthBase1(Date time) {
        return time.getMonth() + 1;
    }

    private synchronized boolean isMonthAmountFinishUp(String identity, Date now) {
        String id = identity.toUpperCase();
        HashOperations<String, String, String> keyOperations = this.redisTemplate.opsForHash();
        String month = keyOperations.get(this.keyPrefix, id + "-K");
        if (month == null) {
            return false;
        }
        // 判断时间是否是当天的，只比较月，超过年的不可能存在，存在也不管了
        if (getMonthBase1(now) == Integer.parseInt(month)) {
            HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
            String amount = amountOperations.get(this.keyPrefix, id + "-V");
            return Double.parseDouble(amount) >= this.monthLimitAmount;
        }

        return false;
    }

    // 增加访问计数，返回新的访问计数
    public double addMonthAmount(String identity, double amount) {
        return addMonthAmount(identity, amount, new Date());
    }

    private synchronized double addMonthAmount(String identity, double amount, Date now) {
        String id = identity.toUpperCase();
        HashOperations<String, String, String> keyOperations = this.redisTemplate.opsForHash();
        String month = keyOperations.get(this.keyPrefix, id + "-K");
        if (month == null) {
            keyOperations.put(this.keyPrefix, id + "-K", Integer.toString(getMonthBase1(now)));
            HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
            amountOperations.put(this.keyPrefix, id + "-V", Double.toString(amount));
        } else {
            if (getMonthBase1(now) == Integer.parseInt(month)) {
                // 是当月的，直接增加
                HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
                amount = amountOperations.increment(this.keyPrefix, id + "-V", amount);
            } else {
                // 不是当月，重新统计
                keyOperations.put(this.keyPrefix, id + "-K", Integer.toString(getMonthBase1(now)));
                HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
                amountOperations.put(this.keyPrefix, id + "-V", Double.toString(amount));
            }
        }
        return amount;
    }


    public void test() throws Exception {
        this.setMonthLimitAmount(100.5);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS:1";
            if (!this.isMonthAmountFinishUp(id)) {
                double nowAmount = this.addMonthAmount(id, 20.5);
                System.out.println(id + " count=" + nowAmount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS:" + (i % 4 + 2);
            if (!this.isMonthAmountFinishUp(id)) {
                double nowAmount = this.addMonthAmount(id, 20.5);
                System.out.println(id + " count=" + nowAmount);
            } else {
                System.out.println(id + " count finish up");
            }
        }

        // 增加一些时间重新测试
        Date date = new Date(System.currentTimeMillis() - 1000L * 3600 * 24 * 31);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS:1";
            if (!this.isMonthAmountFinishUp(id, date)) {
                double nowCount = this.addMonthAmount(id, 20.5, date);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS:" + (i % 4 + 2);
            if (!this.isMonthAmountFinishUp(id, date)) {
                double nowCount = this.addMonthAmount(id, 20.5, date);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
        }
    }
}
