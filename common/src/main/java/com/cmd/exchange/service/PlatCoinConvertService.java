package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.DelayReleaseMapper;
import com.cmd.exchange.common.model.DelayRelease;
import com.cmd.exchange.common.vo.PlatCoinConvertVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 该服务主要是用来做私募的，非正常币种交换，只支持部分货币换成平台币
 */
@Service
public class PlatCoinConvertService {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private DelayReleaseMapper delayReleaseMapper;
    @Autowired
    private PlatCoinConvertService me;

    /**
     * 将eth换成平台币
     *
     * @param userId   兑换的用户
     * @param ethCount 要兑换的eth个数
     */
    @Transactional
    public void ethToPlatCoin(int userId, BigDecimal ethCount) {
        if (ethCount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("ethCount must > 0");
        BigDecimal freezePercent = configService.getConfigValue(ConfigKey.CONVERT_FREEZE_PERCENT, new BigDecimal(70), true);
        BigDecimal ethToPlatNum = configService.getConfigValue(ConfigKey.ETH_TO_PLAT_NUM, new BigDecimal(60000), false);
        log.info("ethToPlatCoin freezePercent=" + freezePercent + ",ethToPlatNum=" + ethToPlatNum + ",ethCount=" + ethCount);
        BigDecimal platCount = ethCount.multiply(ethToPlatNum);
        BigDecimal freezeCount = platCount.multiply(freezePercent);
        BigDecimal getCount = platCount.subtract(freezeCount);

        // 开始转换,这里写死了eth，不规范
        userCoinService.changeUserCoin(userId, "ETH", ethCount.negate(), BigDecimal.ZERO, UserBillReason.CON_TO_PLAT,
                "platCount=" + platCount + ",freeze=" + freezeCount);
        userCoinService.changeUserCoin(userId, configService.getPlatformCoinName(), getCount, freezeCount, UserBillReason.CON_TO_PLAT,
                "ethCount=" + ethCount + ",platCount=" + platCount);

        addDelayReleaseCoin(configService.getPlatformCoinName(), userId, freezeCount);
    }

    /**
     * 将usdt换成平台币
     *
     * @param userId    兑换的用户
     * @param usdtCount 要兑换的eth个数
     */
    @Transactional
    public void usdtToPlatCoin(int userId, BigDecimal usdtCount) {
        if (usdtCount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("ethCount must > 0");
        BigDecimal freezePercent = configService.getConfigValue(ConfigKey.CONVERT_FREEZE_PERCENT, new BigDecimal(70), true);
        BigDecimal usdtToPlatNum = configService.getConfigValue(ConfigKey.USDT_TO_PLAT_NUM, new BigDecimal(130), false);
        log.info("usdtToPlatCoin freezePercent=" + freezePercent + ",ethToPlatNum=" + usdtToPlatNum + ",usdtCount=" + usdtCount);
        BigDecimal platCount = usdtCount.multiply(usdtToPlatNum);
        BigDecimal freezeCount = platCount.multiply(freezePercent);
        BigDecimal getCount = platCount.subtract(freezeCount);

        // 开始转换,这里写死了usdt，不规范
        userCoinService.changeUserCoin(userId, "USDT", usdtCount.negate(), BigDecimal.ZERO, UserBillReason.CON_TO_PLAT,
                "platCount=" + platCount + ",freeze=" + freezeCount);
        userCoinService.changeUserCoin(userId, configService.getPlatformCoinName(), getCount, freezeCount, UserBillReason.CON_TO_PLAT,
                "usdtCount=" + usdtCount + ",platCount=" + platCount);

        addDelayReleaseCoin(configService.getPlatformCoinName(), userId, freezeCount);
    }

    /**
     * 增加延迟释放记录，分5次释放
     *
     * @param coinName 币种名称
     * @param userId   用户id
     * @param total    总币个数
     */
    private void addDelayReleaseCoin(String coinName, int userId, BigDecimal total) {
        try {
            if (total.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            Date first = getFirstReleaseTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(first);
            BigDecimal oneRelease = total.divide(new BigDecimal(5), 8, RoundingMode.HALF_UP);
            for (int i = 0; i < 5; i++) {
                delayReleaseMapper.add(calendar.getTime(), coinName, userId, oneRelease);
                calendar.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 释放一条记录
     *
     * @param time
     * @return
     */
    @Transactional
    public DelayRelease releaseOne(Date time) {
        DelayRelease delayRelease = delayReleaseMapper.lockOneRecord(time);
        if (delayRelease == null) {
            return null;
        }
        delayReleaseMapper.releaseRecord(delayRelease.getId());
        userCoinService.changeUserCoin(delayRelease.getUserId(), delayRelease.getCoinName(), delayRelease.getAmount(),
                delayRelease.getAmount().negate(), UserBillReason.CON_TO_PLAT_RELEASE, "create:" + delayRelease.getCreateTime());
        return delayRelease;
    }

    public void releaseAll() {
        Date now = new Date();
        DelayRelease record;
        while ((record = me.releaseOne(now)) != null) {
            log.info("release coin,id=" + record.getId() + ",userId=" + record.getUserId() + ",amount=" + record.getAmount()
                    + ",want release date=" + record.getReleaseTime());
        }
    }

    public PlatCoinConvertVO getNextTimeConvert() throws ParseException {
        String bonBuyTime = configService.getConfigValue(ConfigKey.CONVERT_BON_TIME, "2018-07-25 15:00:00-20:00:00,2018-07-26 15:00:00-20:00:00,2018-07-27 15:00:00-20:00:00,2018-07-28 15:00:00-20:00:00");
        String timeArr[] = bonBuyTime.split(",");
        long curTime = System.currentTimeMillis();
        PlatCoinConvertVO vo = new PlatCoinConvertVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String timeStr : timeArr) {
            timeStr = timeStr.trim();
            String dayAndTimeArr[] = timeStr.split(" ");
            String timeOfDayArr[] = dayAndTimeArr[1].split("-");
            Date begin = sdf.parse(dayAndTimeArr[0] + " " + timeOfDayArr[0]);
            Date end = sdf.parse(dayAndTimeArr[0] + " " + timeOfDayArr[1]);
            // 时间没到
            if (curTime < begin.getTime()) {
                vo.setNextTimeStart((int) (begin.getTime() / 1000));
                vo.setNextTimeEnd((int) (end.getTime() / 1000));
                return vo;
            }
            // 在当前时间
            if (curTime < end.getTime()) {
                vo.setNextTimeStart((int) (begin.getTime() / 1000));
                vo.setNextTimeEnd((int) (end.getTime() / 1000));
                vo.setNowCanBuy(true);
                return vo;
            }
        }
        return vo;
    }

    /**
     * 获取第一次释放的时间，以后每个月释放一次
     *
     * @return
     */
    private Date getFirstReleaseTime() throws ParseException {
        String bonReleaseTime = configService.getConfigValue(ConfigKey.CONVERT_BON_RELEASE_TIME,
                "2018-07-25=2018-09-01,2018-07-26=2018-09-02,2018-07-27=2018-09-03,2018-07-28=2018-09-04");
        String releaseTimeArr[] = bonReleaseTime.split(",");
        Date curTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDateFormat = sdf.format(curTime);
        for (String releaseOneStr : releaseTimeArr) {
            String[] tmpStr = releaseOneStr.split("=");
            if (curDateFormat.equals(tmpStr[0].trim())) {
                return sdf.parse(tmpStr[1]);
            }
        }
        return null;
    }
}
