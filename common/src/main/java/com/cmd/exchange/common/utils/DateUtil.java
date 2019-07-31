package com.cmd.exchange.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取日期字符串
     *
     * @param time Date
     * @return yyyy-MM-dd
     */
    public static String getDateTimeString(Date time) {
        return sdf.format(time);
    }

    public static Date getDate(String date) {
        if (date == null || (date.length() != 10 && date.length() != 19)) {
            return null;
        }
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getDayBeginTimestamp(long timeMillis) {
        // 计算当天0点的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayBegin = calendar.getTime();
        int todayBeginTimestamp = (int) (todayBegin.getTime() / 1000);
        return todayBeginTimestamp;
    }
    public static Date getBeginTimestamp(int time) {

        long timeMillis = time * 1000l;
        return new Date(timeMillis);
    }

    //根据时间类型获取年月日
    public static String getDateYMDStr(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //加上时间
        String datestr = sDateFormat.format(date);
        return datestr;
    }

    //根据时间分秒获取时间
    public static Date getDateByString(String dateStr) {
        String currentTimeStr = getDateYMDStr(new Date());
        String dateStrs = currentTimeStr + " " + dateStr;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        Date date = null;
        try {
            date = sDateFormat.parse(dateStrs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



       /**
          * 当前日期加上天数后的日期
           * @param num 为增加的天数
          * @return
           */
     public static Date plusDay(int num){
          Calendar ca = Calendar.getInstance();
          ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        return ca.getTime();
     }

    /**
     * 指定时间增减
     * @param num
     * @param d
     * @return
     */
     public static Date plusDay(int num,Date d){
          Calendar ca = Calendar.getInstance();
          ca.setTime(d);
          ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        return ca.getTime();
     }
     public static  void main(String[] str){
         plusDay(31);
     }
}
