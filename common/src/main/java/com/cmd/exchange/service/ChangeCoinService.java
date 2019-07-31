package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.ChangeCoinMapper;
import com.cmd.exchange.common.mapper.CronConfigMapper;
import com.cmd.exchange.common.model.ChangeCoin;
import com.cmd.exchange.common.model.CronConfig;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ChangeCoinService {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private ChangeCoinMapper changeCoinMapper;
    @Autowired
    private ChangeCoinService changeCoinService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private CronConfigMapper cronConfigMapper;

    @Value("${task.change_coin.load_num:100000}")
    private int loadNum = 100000;
    @Value("${task.change_coin.thread_num:10}")
    private int threadNum = 10;

    class ChangeCoinTask implements Runnable {
        private long id;
        private CountDownLatch countDownLatch;

        public ChangeCoinTask(long id, CountDownLatch countDownLatch) {
            this.id = id;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                changeCoinService.changeCoinById(this.id);
            } catch (Exception e) {
                log.error("", e);
            }
            countDownLatch.countDown();
        }
    }

    @Transactional
    public void changeCoinById(long id) {
        ChangeCoin changeCoin = changeCoinMapper.lockChangeCoin(id);
        if (changeCoin.getStatus() != ChangeCoin.STATUS_NOT_CHANGE) {
            log.warn("ChangeCoin have change before: " + ReflectionToStringBuilder.toString(changeCoin));
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("do change coin of:" + ReflectionToStringBuilder.toString(changeCoin));
        }
        userCoinService.changeUserCoin(changeCoin.getUserId(), changeCoin.getCoinName(), changeCoin.getChangeAvailable(),
                changeCoin.getChangeFreeze(), changeCoin.getReason(), changeCoin.getComment());
        changeCoinMapper.markChanged(id);
        if (log.isDebugEnabled()) {
            log.debug("end do change coin of:" + ReflectionToStringBuilder.toString(changeCoin));
        }
    }

    // 把指定时间之前需要修改的币进行修改
    public void doAllChangeCoin(Date before) {
        log.info("begin doAllChangeCoin,loadNum=" + this.loadNum + ",threadNum=" + this.threadNum + ",date=" + before);
        List<Long> ids = changeCoinMapper.getWaitChangeIds(before, loadNum);
        if (ids == null || ids.size() == 0) {
            log.info("no change coin work to do");
            return;
        }
        log.info("id count=" + ids.size() + ",date=" + before);
        // 启动线程池来做这个事情
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(this.threadNum, this.threadNum, 0, TimeUnit.MICROSECONDS, queue);
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());
        for (Long id : ids) {
            ChangeCoinTask task = new ChangeCoinTask(id, countDownLatch);
            executor.execute(task);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        executor.shutdown();
        log.info("end doAllChangeCoin");
    }

    /**
     * 根据设定的时间，将冻结资产转移到可用资产
     *
     * @param userId          用户id
     * @param coinName        币种名称
     * @param cron            转移的时间定义，这个是cron的定义，参考spring boot标签@Scheduled
     * @param totalBalance    总共要转移的资产
     * @param oneTimeTransfer 每次转移的资产数
     * @param reason          转移的时候写的类型（用于账单）
     * @param comment         转移的时候写的备注信息（用于账单）
     */
    @Transactional
    public void transferFreeToAvailableAtTime(int userId, String coinName, String cron, BigDecimal totalBalance, BigDecimal oneTimeTransfer,
                                              String reason, String comment) {
        if (oneTimeTransfer.scale() > 8) {
            oneTimeTransfer = oneTimeTransfer.setScale(8, RoundingMode.HALF_UP);
        }
        if (oneTimeTransfer.compareTo(BigDecimal.ZERO) <= 0) return;
        if (totalBalance.scale() > 8) {
            totalBalance = totalBalance.setScale(8, RoundingMode.FLOOR);
        }
        TimeZone timeZone = TimeZone.getDefault();
        CronTrigger cronTrigger = new CronTrigger(cron, timeZone);
        SimpleTriggerContext context = new SimpleTriggerContext();
        BigDecimal remain = totalBalance;
        while (remain.compareTo(BigDecimal.ZERO) > 0) {
            Date date = cronTrigger.nextExecutionTime(context);
            ChangeCoin changeCoin = new ChangeCoin();
            changeCoin.setCoinName(coinName);
            changeCoin.setUserId(userId);
            changeCoin.setReason(reason);
            changeCoin.setComment(comment);
            changeCoin.setWantChangeTime(date);
            changeCoin.setStatus(ChangeCoin.STATUS_NOT_CHANGE);
            BigDecimal transfer;
            if (remain.compareTo(oneTimeTransfer) > 0) {
                transfer = oneTimeTransfer;
                remain = remain.subtract(oneTimeTransfer);
            } else {
                transfer = remain;
                remain = BigDecimal.ZERO;
            }
            changeCoin.setChangeAvailable(transfer);
            changeCoin.setChangeFreeze(transfer.negate());
            changeCoinMapper.addChangeCoin(changeCoin);
            context.update(date, date, date);
        }
    }

    public int delOldChangeCoin(Date beforeTime) {
        return changeCoinMapper.delOldChangeCoin(beforeTime);
    }

}
