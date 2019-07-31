package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.MachineStatus;
import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.MiningStatVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

/**
 * 分红服务
 * 对用户进行分红，包括奖励交易手续费和奖励平台币等
 */
@Service
public class ShareOutBonusService {
    private static Log log = LogFactory.getLog(ShareOutBonusService.class);
    @Autowired
    private ConfigService configService;
    @Autowired
    private TradeLogMapper tradeLogMapper;
    @Autowired
    private ShareOutBonusService me;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private ShareOutBonusLogMapper shareOutBonusLogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CoinSavingsMapper coinSavingsMapper;
    @Autowired
    private UserDayProfitMapper userDayProfitMapper;

    @Autowired
    private UserNodeRewardMapper userNodeRewardMapper;

    @Autowired
    private UserMachineMapper userMachineMapper;

    @Autowired
    private UserMachineService userMachineService;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private UserBillMapper userBillMapper;


    // 将最先入库的一条分红转换到用户资产表，成功转移返回true
    @Transactional
    public boolean transferOneUserBonus() {
        DelayShareOut delayShareOut = shareOutBonusLogMapper.getFirstDelayShareOut();
        if (delayShareOut == null) return false;
        if (delayShareOut.getUserBaseCoin().compareTo(BigDecimal.ZERO) > 0) {
            userCoinService.changeUserCoin(delayShareOut.getUserId(), delayShareOut.getCoinName(), delayShareOut.getChangeAmount(),
                    BigDecimal.ZERO, UserBillReason.TRADE_SAVING_BONUS, delayShareOut.getComment());
        } else {
            userCoinService.changeUserCoin(delayShareOut.getUserId(), delayShareOut.getCoinName(), delayShareOut.getChangeAmount(),
                    BigDecimal.ZERO, UserBillReason.TRADE_SHARE_BONUS, delayShareOut.getComment());
        }
        //shareOutBonusLogMapper.addLog(delayShareOut.getUserId(), delayShareOut.getCoinName(), delayShareOut.getUserBaseCoin(), delayShareOut.getChangeAmount());
        shareOutBonusLogMapper.deleteDelayShareOut(delayShareOut.getId());
        return true;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 超级节点分红，分红规则：每天USDT手续费根据设置分红，每个超级节点均分这些手续费
    public void statSuperNodeYesterdayProfit() {
        int todayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        // 如果不存在记录，设置为昨天第一次的时间
        int lastStatTime = configService.getConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, todayBeginTimestamp - 3600 * 24);
        // 获取超级节点分红比例
        BigDecimal shareOutRate = configService.getConfigValue(ConfigKey.TRADE_FEE_SUPER_USER_RATE, BigDecimal.ZERO, true);
        if (shareOutRate.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn(ConfigKey.TRADE_FEE_SUPER_USER_RATE + "=" + shareOutRate + ",ignore");
            configService.setConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        log.info("begin statSuperNodeYesterdayProfit, beginTime=" + lastStatTime + ",endTime=" + todayBeginTimestamp
                + ",total rate=" + shareOutRate);
        // 获取所有费用
        BigDecimal totalUsdtFee = tradeLogMapper.statUsdtFee(lastStatTime, todayBeginTimestamp);
        log.info("totalUsdtFee=" + totalUsdtFee);
        if (totalUsdtFee == null || totalUsdtFee.compareTo(BigDecimal.ZERO) <= 0) {
            configService.setConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        BigDecimal totalShareOut = totalUsdtFee.multiply(shareOutRate);
        log.info("totalShareOut=" + totalShareOut);
        // 获取所有超级节点用户的ID
        List<Integer> userIds = userMapper.getAllSuperUserIds();
        log.info("super user count=" + userIds.size() + ",userIds:" + userIds);
        if (userIds.size() == 0) {
            configService.setConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        BigDecimal oneUserProfit = totalShareOut.divide(new BigDecimal(userIds.size()), 8, RoundingMode.FLOOR);
        log.info("oneUserProfit=" + oneUserProfit);
        if (oneUserProfit.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        // 先把所有的收益写到临时表，然后依次返还
        me.addProfitToTmpTable(userIds, oneUserProfit, todayBeginTimestamp);
        log.info("end addProfitToTmpTable");
        // 开始逐个返还
        while (true) {
            if (!me.transferOneUserBonus()) {
                break;
            }
        }
        log.info("end transferOneUserBonus");
    }

    // 特殊节点分红，分红规则：每天USDT手续费根据设置分红，每个特殊节点均分这些手续费
    public void statNodeYesterdayProfit() {


        List<UserNodeReward> userNodeRewards = userNodeRewardMapper.getInfo(new UserNodeReward().setStatus(1));

        if (null == userNodeRewards || userNodeRewards.size() == 0) {
            return;
        }

        int todayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        for (UserNodeReward userNodeReward : userNodeRewards) {

            try {

                int lastStatTime = userNodeReward.getSynRewardTime() == null ? todayBeginTimestamp - 3600 * 24 : userNodeReward.getSynRewardTime();
                BigDecimal totalUsdtFee = tradeLogMapper.statUsdtFee(lastStatTime, todayBeginTimestamp);
                nodeReward(userNodeReward,totalUsdtFee,todayBeginTimestamp,1);

            } catch (Exception e) {
                log.error("用户节点分红异常:", e);
            }
        }


    }
    public void smartNodeYesterdayProfit() {


        List<UserNodeReward> userNodeRewards = userNodeRewardMapper.getInfo(new UserNodeReward().setStatus(1));

        if (null == userNodeRewards || userNodeRewards.size() == 0) {
            return;
        }

        int todayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        for (UserNodeReward userNodeReward : userNodeRewards) {

            try {

                int lastStatTime = userNodeReward.getSynSmartTime() == null ? todayBeginTimestamp - 3600 * 24 : userNodeReward.getSynSmartTime();
                Date statTime = DateUtil.getBeginTimestamp(lastStatTime);
                Date todayBeginTime = DateUtil.getBeginTimestamp(todayBeginTimestamp);
                BigDecimal totalUsdtFee = userBillMapper.smartUsdtFee(statTime, todayBeginTime,UserBillReason.CONTRACTST_FEE);
                //扣除手续均为负数需要转正数
                totalUsdtFee = totalUsdtFee != null ? totalUsdtFee.abs(): BigDecimal.ZERO;
                nodeReward(userNodeReward,totalUsdtFee,todayBeginTimestamp,2);

            } catch (Exception e) {
                log.error("智能合约节点分红异常:", e);
            }
        }


    }

    /**
     * 用户节点分润
     * @param userNodeReward  用户节点
     * @param totalUsdtFee  分润金额
     * @param todayBeginTimestamp  当天零点
     * @param tab   标记 1交易分润 2合约分润
     */
    private void nodeReward(UserNodeReward userNodeReward ,BigDecimal totalUsdtFee,int todayBeginTimestamp,int tab) {


        //获取节点分红比例
        BigDecimal rate = userNodeReward.getRewardRate() == null ? BigDecimal.ZERO : userNodeReward.getRewardRate();
        // 如果不存在记录，设置为昨天第一次的时间

        //返回
        if(tab == 1){
            userNodeReward.setSynRewardTime(todayBeginTimestamp);
        }else{
            userNodeReward.setSynSmartTime(todayBeginTimestamp);
        }

        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn(userNodeReward.getNodeName() + "=" + rate + ",ignore");
            userNodeRewardMapper.mod(userNodeReward);
            return;
        }


        log.info("begin nodeReward, beginTime=" + rate + ",endTime=" + todayBeginTimestamp
                + ",total rate=" + rate);
        // 获取所有费用

        log.info("totalUsdtFee=" + totalUsdtFee);
        if (totalUsdtFee == null || totalUsdtFee.compareTo(BigDecimal.ZERO) <= 0) {
            userNodeRewardMapper.mod(userNodeReward);
            return;
        }
        BigDecimal totalShareOut = totalUsdtFee.multiply(rate);
        log.info("totalShareOut=" + totalShareOut);
        //TODO : 获取所有超级节点用户的ID  待测试
        List<Integer> userIds = userMapper.getUserIdsByNodeId(userNodeReward.getId());
        log.info("super user count=" + userIds.size() + ",userIds:" + userIds);
        if (userIds.size() == 0) {
            userNodeRewardMapper.mod(userNodeReward);
            return;
        }
        BigDecimal oneUserProfit = totalShareOut.divide(new BigDecimal(userIds.size()), 8, RoundingMode.FLOOR);
        log.info("oneUserProfit=" + oneUserProfit);
        if (oneUserProfit.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        // 先把所有的收益写到临时表，然后依次返还
        me.addProfitToTmpTable(userIds, oneUserProfit,userNodeReward);//需要修改  未完
        log.info("end addProfitToTmpTable");
        // 开始逐个返还
        while (true) {
            if (!me.transferOneUserBonus()) {
                break;
            }
        }
        log.info("end transferOneUserBonus");
    }

    @Transactional
    public void addProfitToTmpTable(List<Integer> userIds, BigDecimal oneUserProfit, int endTime) {
        Date now = new Date();
        for (Integer userId : userIds) {
            shareOutBonusLogMapper.addDelayShareOut(userId, "USDT", BigDecimal.ZERO, oneUserProfit, "profit time:" + now + ",count=" + userIds.size());
        }
        configService.setConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, String.valueOf(endTime));
    }
    @Transactional
    public void addProfitToTmpTable(List<Integer> userIds, BigDecimal oneUserProfit,UserNodeReward userNodeReward) {
        Date now = new Date();
        for (Integer userId : userIds) {
            shareOutBonusLogMapper.addDelayShareOut(userId, "USDT", BigDecimal.ZERO, oneUserProfit, "profit time:" + now + ",count=" + userIds.size());
        }
        //configService.setConfigValue(ConfigKey.REWARD_TRADE_FEE_LAST_TIME, String.valueOf(endTime));
        userNodeRewardMapper.mod(userNodeReward);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 分润宝分红，分红规则：每天USDT手续费根据设置分红，所有分润宝里面余额超过指定数量的用户根据资金大小进行分红
    public void statSavingYesterdayProfit() {
        int todayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        // 如果不存在记录，设置为昨天第一次的时间
        int lastStatTime = configService.getConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, todayBeginTimestamp - 3600 * 24);
        // 获取超级节点分红比例
        BigDecimal shareOutRate = configService.getConfigValue(ConfigKey.TRADE_FEE_SAVING_RATE, BigDecimal.ZERO, true);
        if (shareOutRate.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn(ConfigKey.TRADE_FEE_SAVING_RATE + "=" + shareOutRate + ",ignore");
            configService.setConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        // 参与分红的最小金额
        BigDecimal minShareOutBalance = configService.getConfigValue(ConfigKey.REWARD_MIN_COIN_SAVING, new BigDecimal("5000"), false);
        log.info("begin statSavingYesterdayProfit, beginTime=" + lastStatTime + ",endTime=" + todayBeginTimestamp
                + ",total rate=" + shareOutRate + ",minShareOutBalance=" + minShareOutBalance);
        // 获取所有费用
        BigDecimal totalUsdtFee = tradeLogMapper.statUsdtFee(lastStatTime, todayBeginTimestamp);
        log.info("totalUsdtFee=" + totalUsdtFee);
        if (totalUsdtFee == null || totalUsdtFee.compareTo(BigDecimal.ZERO) <= 0) {
            configService.setConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        BigDecimal totalShareOut = totalUsdtFee.multiply(shareOutRate);
        log.info("totalShareOut=" + totalShareOut);

        // 获取所有分润宝余额大于一定余额的用户
        List<CoinSavings> lsCoinSavings = coinSavingsMapper.getUsersByMinBalance(configService.getPlatformCoinName(), minShareOutBalance);

        // 先把所有的收益写到临时表，然后依次返还
        me.addSavingProfitToTmpTable(lsCoinSavings, totalShareOut, todayBeginTimestamp);
        log.info("end addProfitToTmpTable");
        // 开始逐个返还
        while (true) {
            if (!me.transferOneUserBonus()) {
                break;
            }
        }
        log.info("end statSavingYesterdayProfit");
    }

    @Transactional
    public void addSavingProfitToTmpTable(List<CoinSavings> lsCoinSavings, BigDecimal totalShareOut, int endTime) {
        BigDecimal totalCoin = BigDecimal.ZERO;
        for (CoinSavings coinSavings : lsCoinSavings) {
            totalCoin = totalCoin.add(coinSavings.getBalance());
        }
        log.info("share out user count=" + lsCoinSavings.size() + ",totalCoin=" + totalCoin);
        if (lsCoinSavings.size() == 0 || totalCoin.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        Date now = new Date();
        for (CoinSavings coinSavings : lsCoinSavings) {
            BigDecimal profit = totalShareOut.multiply(coinSavings.getBalance()).divide(totalCoin, 8, RoundingMode.FLOOR);
            log.info("user id=" + coinSavings.getUserId() + ",user balance=" + coinSavings.getBalance() + ",profit=" + profit);
            if (profit.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            shareOutBonusLogMapper.addDelayShareOut(coinSavings.getUserId(), "USDT", coinSavings.getBalance(), profit,
                    "share out time:" + now + ",total=" + totalShareOut + ",balance=" + coinSavings.getBalance());
        }
        configService.setConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, String.valueOf(endTime));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 用户收益
    public void updateUserDayProfit() {
        // 步骤
        // 1。 产生所有用户收益记录
        // 2。对每条用户数据进行统计
        int dayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        Date todayBegin = new Date(1000L * dayBeginTimestamp);
        int count = userDayProfitMapper.initUserProfitOfDay(todayBegin);
        log.info("initUserProfitOfDay return " + count);

        // 统计昨天卖出的USDT手续费总额
        int yesterdayBeginTimestamp = dayBeginTimestamp - 3600 * 24;
        List<UserTradeResult> sellStat = tradeLogMapper.statUserUsdtSellTrade(yesterdayBeginTimestamp, dayBeginTimestamp);
        if (sellStat != null && sellStat.size() > 0) {
            for (UserTradeResult stat : sellStat) {
                userDayProfitMapper.updateUserTradeStat(stat.getUserId(), todayBegin, null, stat.getTotalUsdt(), stat.getTotalFee());
            }
        }
        List<UserTradeResult> buyStat = tradeLogMapper.statUserUsdtBuyTrade(yesterdayBeginTimestamp, dayBeginTimestamp);
        if (buyStat != null && buyStat.size() > 0) {
            for (UserTradeResult stat : buyStat) {
                userDayProfitMapper.updateUserTradeStat(stat.getUserId(), todayBegin, stat.getTotalUsdt(), null, null);
            }
        }


        // 统计用户昨天超级节点分红收益 UserBillReason.TRADE_SHARE_BONUS
        count = userDayProfitMapper.updateSuperNodeShareOut(todayBegin, UserBillReason.TRADE_SHARE_BONUS);
        log.info("updateSuperNodeShareOut return=" + count);

        //统计用户超级节点总收益
        count = userDayProfitMapper.updateSuperNodeShareOutTotal(todayBegin, UserBillReason.TRADE_SHARE_BONUS);
        log.info("updateSuperNodeShareOutTotal return=" + count);

        // 统计用户昨天分润宝分红收益 UserBillReason.TRADE_SAVING_BONUS
        //count = userDayProfitMapper.updateCoinSavingShareOut(todayBegin, UserBillReason.TRADE_SAVING_BONUS);
        //log.info("updateSuperNodeShareOut return=" + count);

        // 统计用户昨天交易获取UKS数量 UserBillReason.TRADE_BONUS
        //count = userDayProfitMapper.updateTradeRewardUks(todayBegin, UserBillReason.TRADE_BONUS);
        //log.info("updateSuperNodeShareOut return=" + count);

        //统计团队   昨天团队奖励 UserBillReason.TRADE_BONUS
        count = userDayProfitMapper.updateTradeRewardTeam(todayBegin, UserBillReason.TRADE_BONUS);
        log.info("updateTradeRewardTeam return=" + count);

        //统计团队  总收益   UserBillReason.TRADE_BONUS
        count = userDayProfitMapper.updateTradeRewardTeamTotal(todayBegin, UserBillReason.TRADE_BONUS);
        log.info("updateTradeRewardTeamTotal return=" + count);

        // 统计团队交易情况
        userDayProfitMapper.statTeamTrade(todayBegin);
    }

    public UserDayProfit getUserLastProfit(int userId) {
        return userDayProfitMapper.getUserLastProfit(userId);
    }


    public void updateUserMachineProfit(){


        List<UserMachine> userMachines = userMachineService.yesterdayUserMachineProfitList();
        int userMachineCount = userMachines.size();
        if(userMachineCount==0){
            log.info("yesterdayUserMachineProfitList return = 0" );
            return;
        }

        for(UserMachine userMachine:userMachines)
        {
            Machine oneInfo = machineMapper.getOneInfo(new Machine().setId(userMachine.getMachineId()));
            userCoinService.changeUserReceivedFreezeCoin(userMachine.getUserId(), oneInfo.getMachineCoin(),oneInfo.getMachineGive(), UserBillReason.USER_MACHINE_BONUS, "Method:updateUserMachineProfit");
        }
        log.info("yesterdayUserMachineProfitList return = "+ userMachineCount);

        //执行用户挖矿收益 和 统计
        int count =  userMachineMapper.updateUserMachineIncomeAndOpen();
        log.info("updateUserMachineIncomeAndOpen return=" + count);


    }
    /**
     * 团队奖励根据被推荐人卖出usdt手续费 乘以百分比
     */
    public void statTeamYesterdayProfit() {
        int todayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        // 如果不存在记录，设置为昨天第一次的时间
        int lastStatTime = configService.getConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, todayBeginTimestamp - 3600 * 24);
        // 获取超级节点分红比例
        BigDecimal shareOutRate = configService.getConfigValue(ConfigKey.TRADE_FEE_SAVING_RATE, BigDecimal.ZERO, true);
        if (shareOutRate.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn(ConfigKey.TRADE_FEE_SAVING_RATE + "=" + shareOutRate + ",ignore");
            configService.setConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        // 参与分红的最小金额
        BigDecimal minShareOutBalance = configService.getConfigValue(ConfigKey.REWARD_MIN_COIN_SAVING, new BigDecimal("5000"), false);
        log.info("begin statSavingYesterdayProfit, beginTime=" + lastStatTime + ",endTime=" + todayBeginTimestamp
                + ",total rate=" + shareOutRate + ",minShareOutBalance=" + minShareOutBalance);
        // 获取所有费用
        BigDecimal totalUsdtFee = tradeLogMapper.statUsdtFee(lastStatTime, todayBeginTimestamp);
        log.info("totalUsdtFee=" + totalUsdtFee);
        if (totalUsdtFee == null || totalUsdtFee.compareTo(BigDecimal.ZERO) <= 0) {
            configService.setConfigValue(ConfigKey.REWARD_COIN_SAVING_LAST_TIME, String.valueOf(todayBeginTimestamp));
            return;
        }
        BigDecimal totalShareOut = totalUsdtFee.multiply(shareOutRate);
        log.info("totalShareOut=" + totalShareOut);

        // 获取所有分润宝余额大于一定余额的用户
        List<CoinSavings> lsCoinSavings = coinSavingsMapper.getUsersByMinBalance(configService.getPlatformCoinName(), minShareOutBalance);

        // 先把所有的收益写到临时表，然后依次返还
        me.addSavingProfitToTmpTable(lsCoinSavings, totalShareOut, todayBeginTimestamp);
        log.info("end addProfitToTmpTable");
        // 开始逐个返还
        while (true) {
            if (!me.transferOneUserBonus()) {
                break;
            }
        }
        log.info("end statSavingYesterdayProfit");
    }
}
