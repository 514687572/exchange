package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.CronConfigMapper;
import com.cmd.exchange.common.mapper.LockCoinRecordMapper;
import com.cmd.exchange.common.mapper.UserBillMapper;
import com.cmd.exchange.common.model.CronConfig;
import com.cmd.exchange.common.model.LockCoinRecord;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserBill;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.NoUtil;
import com.cmd.exchange.common.vo.UserBillVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.github.pagehelper.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class LockUserCoinService {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private LockCoinRecordMapper lockCoinRecordMapper;
    @Autowired
    private ChangeCoinService changeCoinService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private CronConfigMapper cronConfigMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBillMapper userBillMapper;
    @Autowired
    private LockUserCoinService me;

    public Page<LockCoinRecord> findRecord(Integer userId, String coinName, String lockNo, String beginTime, String endTime, int pageNo, int pageSize) {
        return lockCoinRecordMapper.findRecord(userId, coinName, lockNo, beginTime, endTime, new RowBounds(pageNo, pageSize));
    }

    public void lockUserCoin(String userNames, String coinName, double lockRate, double releaseRate, int cronId) {
        String[] userNameArr = userNames.split(",");
        List<User> users = new LinkedList<>();
        for (String userName : userNameArr) {
            userName = userName.trim();
            if (userName.length() == 0) continue;
            User user = userService.getUserByUserName(userName);
            Assert.check(user == null, 500, "用户不存在：" + userName);
            // 查看重复
            for (User oldUser : users) {
                Assert.check(oldUser.getId().equals(user.getId()), 500, "用户重复：" + userName);
            }
            users.add(user);
        }
        Assert.check(users.size() == 0, 500, "没有任何用户信息");
        me.lockUserCoin(users, coinName, lockRate, releaseRate, cronId);
    }

    public void lockUserGroupCoin(String groupType, String coinName, double lockRate, double releaseRate, int cronId) {
        List<User> users = userService.getAllUserIdNameByGroupType(groupType);
        Assert.check(users.size() == 0, 500, "用户组下没有任何用户");
        me.lockUserCoin(users, coinName, lockRate, releaseRate, cronId);
    }

    @Transactional
    public void lockUserCoin(List<User> users, String coinName, double lockRate, double releaseRate, int cronId) {
        BigDecimal bigLockRate = new BigDecimal(lockRate);
        BigDecimal bigReleaseRate = new BigDecimal(releaseRate);
        CronConfig cronConfig = cronConfigMapper.getCronConfig(cronId);
        String lockNo = NoUtil.getOrderNo();
        Assert.check(cronConfig.getCronType() != 1, 500, "cron type must be 1,cronId=" + cronId);
        for (User user : users) {
            UserCoinVO userCoinVO = userCoinService.lockUserCoinByUserIdAndCoinName(user.getId(), coinName);
            if (userCoinVO == null) {
                log.warn("user " + user.getUserName() + " do not have coin record of " + coinName);
                continue;
            }
            if (userCoinVO.getAvailableBalance().compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("user " + user.getUserName() + " coin is zero: " + userCoinVO.getAvailableBalance());
                continue;
            }
            // 计算锁仓总额
            BigDecimal totalLock = userCoinVO.getAvailableBalance().multiply(bigLockRate).setScale(8, RoundingMode.HALF_UP);
            if (totalLock.compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("user " + user.getUserName() + " coin " + coinName + " total lock too small:" + totalLock);
                continue;
            }
            BigDecimal retOneTime = totalLock.multiply(bigReleaseRate).setScale(8, RoundingMode.HALF_UP);
            if (retOneTime.compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("user " + user.getUserName() + " coin " + coinName + " every time return too small:" + retOneTime);
                continue;
            }
            // 扣用户可用金，开始写返还记录，写记录
            LockCoinRecord record = new LockCoinRecord();
            record.setUserId(user.getId()).setCoinName(userCoinVO.getCoinName()).setCronExpression(cronConfig.getCronExpression())
                    .setLockAmount(totalLock).setLockRate(bigLockRate).setCronName(cronConfig.getCronName()).setReleaseAmount(retOneTime)
                    .setReleaseRate(bigReleaseRate).setLockNo(lockNo);
            lockCoinRecordMapper.insertRecord(record);
            userCoinService.changeUserCoin(user.getId(), userCoinVO.getCoinName(), totalLock.negate(), totalLock, UserBillReason.LOCK_COIN,
                    "recordId=" + record.getId());
            changeCoinService.transferFreeToAvailableAtTime(user.getId(), userCoinVO.getCoinName(), cronConfig.getCronExpression(),
                    totalLock, retOneTime, UserBillReason.LOCK_COIN_RELEASE, "recordId=" + record.getId());
        }
    }

    public Page<UserBillVO> getLockCoinReturnList(Integer userId, String coinName, String startTime, String endTime, Integer pageNo, Integer pageSize) {
        return userBillMapper.getUserBills(userId, UserBillReason.LOCK_COIN_RELEASE, coinName, startTime, endTime, new RowBounds(pageNo, pageSize));
    }
}
