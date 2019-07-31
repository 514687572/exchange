package com.cmd.exchange.common.componet;

import com.cmd.exchange.common.utils.DateUtil;

import java.util.*;

// 用来计算一样东西每天的次数，用于限制次数
public class DayCountLimit {
    public static class DayCount {
        public String identity;    // 标识
        public int count;       // 当天访问次数
        public Date statTime;  //  统计的时间（只有日期有效，时间无效）

        public DayCount(String identity) {
            this.identity = identity;
            this.statTime = new Date();
        }
    }

    // 一天限制的次数
    private int dayLimitCount;

    private HashMap<String, DayCount> statDayCounts = new HashMap<>();

    public int getDayLimitCount() {
        return dayLimitCount;
    }

    public void setDayLimitCount(int dayLimitCount) {
        this.dayLimitCount = dayLimitCount;
    }

    public HashMap<String, DayCount> getAllStats() {
        return (HashMap<String, DayCount>) this.statDayCounts.clone();
    }

    // 查看指定标识当天是否使用完次数了，返回true标识用完次数了
    public boolean isDayCountFinishUp(String identity) {
        return isDayCountFinishUp(identity, new Date());
    }

    private synchronized boolean isDayCountFinishUp(String identity, Date now) {
        String id = identity.toUpperCase();
        DayCount dayCount = statDayCounts.get(id);
        if (dayCount == null) {
            return false;
        }
        // 判断时间是否是当天的，只比较月和日，超过年的不可能存在，存在也不管了
        if (now.getMonth() == dayCount.statTime.getMonth() && now.getDate() == dayCount.statTime.getDate()) {
            return dayCount.count >= this.dayLimitCount;
        }

        return false;
    }

    // 增加访问计数，返回新的访问计数
    public int addDayCount(String identity) {
        return addDayCount(identity, new Date());
    }

    private synchronized int addDayCount(String identity, Date now) {
        String id = identity.toUpperCase();
        DayCount dayCount = statDayCounts.get(id);
        if (dayCount == null) {
            dayCount = new DayCount(identity);
            statDayCounts.put(identity, dayCount);
        } else {
            if (now.getMonth() == dayCount.statTime.getMonth() && now.getDate() == dayCount.statTime.getDate()) {
                // 是当天的，不需要重新统计
            } else {
                // 重新统计
                dayCount.statTime = now;
                dayCount.count = 0;
            }
        }
        dayCount.count++;
        return dayCount.count;
    }

    public synchronized void removeOldStats() {
        Date dateBegin = new Date();
        dateBegin.setHours(0);
        dateBegin.setMinutes(0);
        Iterator<Map.Entry<String, DayCount>> iter = this.statDayCounts.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, DayCount> entry = iter.next();
            if (entry.getValue().statTime.compareTo(dateBegin) < 0) {
                iter.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DayCountLimit dayCountLimit = new DayCountLimit();
        dayCountLimit.setDayLimitCount(5);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS/USDT:1";
            if (!dayCountLimit.isDayCountFinishUp(id)) {
                int nowCount = dayCountLimit.addDayCount(id);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS/USDT:" + (i % 4 + 2);
            if (!dayCountLimit.isDayCountFinishUp(id)) {
                int nowCount = dayCountLimit.addDayCount(id);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
        }
        System.out.println("before remove count=" + dayCountLimit.statDayCounts.size());
        dayCountLimit.removeOldStats();
        System.out.println("after remove count=" + dayCountLimit.statDayCounts.size());

        // 增加一些时间重新测试
        Date date = new Date(System.currentTimeMillis() - 1000L * 3600 * 111);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(1000 * 1);
            String id = "UKS/USDT:1";
            if (!dayCountLimit.isDayCountFinishUp(id, date)) {
                int nowCount = dayCountLimit.addDayCount(id, date);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
            Thread.sleep(1000 * 1);
            id = "UKS/USDT:" + (i % 4 + 2);
            if (!dayCountLimit.isDayCountFinishUp(id, date)) {
                int nowCount = dayCountLimit.addDayCount(id, date);
                System.out.println(id + " count=" + nowCount);
            } else {
                System.out.println(id + " count finish up");
            }
        }

        System.out.println("before remove count=" + dayCountLimit.statDayCounts.size());
        dayCountLimit.removeOldStats();
        System.out.println("after remove count=" + dayCountLimit.statDayCounts.size());
    }
}
