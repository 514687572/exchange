package com.cmd.exchange.common.componet;

import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.service.MatchTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

// 用来计算一样东西每天的总数，用于限制总数
// 这个类没有做好并发控制，只能单线程访问
public class DayAmountLimit {
    private static Logger log = LoggerFactory.getLogger(DayAmountLimit.class);
    private StringRedisTemplate redisTemplate;
    // 保存限制的前缀，在redis中，是map的key名称
    private String keyPrefix;

    // 一天固定的交易的金额
    private double dayLimitAmount;
    //用户今天的锁仓金额数据
    private double receiveFreezeBalance;
    //用于 用户首次交易判断 用户类型
    private Integer vipType;
    //本次交易金额
    private BigDecimal amountTran;
    //不同类型用户释放比例  vip
    private BigDecimal vipUserRate;
    //不同类型用户释放比例  普通
    private BigDecimal userRate;

    public DayAmountLimit(String keyPrefix, StringRedisTemplate redisTemplate,Integer vipType,BigDecimal amountTran,BigDecimal vipUserRate,BigDecimal userRate) {
        this.keyPrefix = keyPrefix;
        this.redisTemplate = redisTemplate;
        this.vipType = vipType;
        this.amountTran = amountTran;
        this.vipUserRate = vipUserRate;
        this.userRate = userRate;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public double getDayLimitAmount() {
        return dayLimitAmount;
    }

    public void setDayLimitAmount(double dayLimitAmount) {
        this.dayLimitAmount = dayLimitAmount;
    }

    public String getReceiveFreezeBalance(String identity) {
        String id = identity.toUpperCase();
        HashOperations<String, String, String> freezeOperations = this.redisTemplate.opsForHash();
        String receiveFreezeBalance = freezeOperations.get(this.keyPrefix, id );

        return receiveFreezeBalance;
    }

    public void setReceiveFreezeBalance(double receiveFreezeBalance) {
        this.receiveFreezeBalance = receiveFreezeBalance;
    }

    // 查看指定标识当天是否使用完次数了，返回true标识用完次数了
    public boolean isDayAmountFinishUp(String identity) {
        return isDayAmountFinishUp(identity, new Date());
    }
    public boolean isFirst(String identity){
        BigDecimal changeFreezeBalance = getUserChaneFreezeBalance(identity);

        return changeFreezeBalance.doubleValue() >= this.dayLimitAmount;
    }

    private BigDecimal getUserChaneFreezeBalance(String identity) {
        BigDecimal changeFreezeBalance;
        if(null == vipType)
        {
            String id = identity.toUpperCase();
            HashOperations<String, String, String> vipTypeOperations = this.redisTemplate.opsForHash();
            String vipTypeStr = vipTypeOperations.get(this.keyPrefix, id + "-T");
            this.vipType = Integer.valueOf(vipTypeStr);
        }

        if (vipType != null && vipType == UserCoin.VIP_TYPE_VIP) {
            //  vip用户
            changeFreezeBalance = this.amountTran.multiply(this.vipUserRate).setScale(8, RoundingMode.HALF_UP);
            log.info("identity:{},交易金额为={},交易释放VIP比例={},交易释放余额={}",identity,this.amountTran,this.vipUserRate,changeFreezeBalance);
        } else {
            changeFreezeBalance = this.amountTran.multiply(this.userRate).setScale(8, RoundingMode.HALF_UP);
            log.info("identity:{},交易金额为={},交易释放普通比例={},交易释放余额={}",identity,this.amountTran,this.userRate,changeFreezeBalance);
        }

        return changeFreezeBalance;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    private synchronized boolean isDayAmountFinishUp(String identity, Date now) {
        String id = identity.toUpperCase();
        HashOperations<String, String, String> keyOperations = this.redisTemplate.opsForHash();
        String day = keyOperations.get(this.keyPrefix, id + "-K");
        if (day == null) {
            HashOperations<String, String, String> freezeOperations = this.redisTemplate.opsForHash();
            freezeOperations.put(this.keyPrefix,id + "-T",Integer.toString(this.vipType));

            return isFirst(identity);
        }
        // 判断时间是否是当天的，只比较月和天，超过年的不可能存在，存在也不管了
        if (getDayByNew(now).equals(day)) {
            HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
            String amount = amountOperations.get(this.keyPrefix, id + "-V");
            double UserChaneFreezeBalance = getUserChaneFreezeBalance(identity).doubleValue()+Double.parseDouble(amount);
            log.info("identity:{},缓存取出当天累计锁仓金额为={},缓存金额加上当前释放余额={}",identity,amount,UserChaneFreezeBalance);

            return UserChaneFreezeBalance >= this.dayLimitAmount;
        }


        return  isFirst(identity);
    }

    // 增加访问计数，返回新的访问计数
    public double addDayAmount(String identity, double amount,double freeze) {
        return addDayAmount(identity, amount, new Date(),freeze);
    }
    public String getDayByNew(Date now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now);
    }
    private synchronized double addDayAmount(String identity, double amount, Date now,double freeze) {
        String id = identity.toUpperCase();
        HashOperations<String, String, String> keyOperations = this.redisTemplate.opsForHash();
        String day = keyOperations.get(this.keyPrefix, id + "-K");
        if (day == null) {
            keyOperations.put(this.keyPrefix, id + "-K", getDayByNew(now));
            HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
            amountOperations.put(this.keyPrefix, id + "-V", Double.toString(amount));

            HashOperations<String, String, String> freezeOperations = this.redisTemplate.opsForHash();
            freezeOperations.put(this.keyPrefix,id + getDayByNew(now),Double.toString(freeze));

        } else {
            if (getDayByNew(now).equals(day)) {
                // 是当天的，直接增加
                HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
                amount = amountOperations.increment(this.keyPrefix, id + "-V", amount);
            } else {
                //HashOperations<String, String, String> deleteOperations = this.redisTemplate.opsForHash();
                //deleteOperations.delete(this.keyPrefix,);
                // 不是当天，重新统计
                keyOperations.put(this.keyPrefix, id + "-K", getDayByNew(now));
                HashOperations<String, String, String> amountOperations = this.redisTemplate.opsForHash();
                amountOperations.put(this.keyPrefix, id + "-V", Double.toString(amount));

                HashOperations<String, String, String> freezeOperations = this.redisTemplate.opsForHash();
                freezeOperations.put(this.keyPrefix,id + getDayByNew(now),Double.toString(freeze));

                HashOperations<String, String, String> vipTyepOperations = this.redisTemplate.opsForHash();
                vipTyepOperations.put(this.keyPrefix,id + "-T",Integer.toString(this.vipType));
            }
        }
        return amount;
    }

    public BigDecimal getAmountTran() {
        return amountTran;
    }

    public void setAmountTran(BigDecimal amountTran) {
        this.amountTran = amountTran;
    }

    public BigDecimal getVipUserRate() {
        return vipUserRate;
    }

    public void setVipUserRate(BigDecimal vipUserRate) {
        this.vipUserRate = vipUserRate;
    }

    public BigDecimal getUserRate() {
        return userRate;
    }

    public void setUserRate(BigDecimal userRate) {
        this.userRate = userRate;
    }

    public void test() throws Exception {
        this.setDayLimitAmount(100.5);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS:1";
            if (!this.isDayAmountFinishUp(id)) {
                double nowAmount = this.addDayAmount(id, 20.5,0);
                System.out.println(id + " count=" + nowAmount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS:" + (i % 4 + 2);
            if (!this.isDayAmountFinishUp(id)) {
                double nowAmount = this.addDayAmount(id, 20.5,0);
                System.out.println(id + " count=" + nowAmount);
            } else {
                System.out.println(id + " count finish up");
            }
        }

        // 增加一些时间重新测试
        Date date = new Date(System.currentTimeMillis() - 1000L * 3600 * 24 * 2);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS:1";
            if (!this.isDayAmountFinishUp(id, date)) {
                double nowCount = this.addDayAmount(id, 20.5, date,0);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS:" + (i % 4 + 2);
            if (!this.isDayAmountFinishUp(id, date)) {
                double nowCount = this.addDayAmount(id, 20.5, date,0);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
        }
    }
}
