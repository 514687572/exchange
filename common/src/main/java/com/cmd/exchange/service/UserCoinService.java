package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillType;
import com.cmd.exchange.common.mapper.EtcAddressMapper;
import com.cmd.exchange.common.mapper.EthAddressMapper;
import com.cmd.exchange.common.mapper.UserBillMapper;
import com.cmd.exchange.common.mapper.UserCoinMapper;
import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.UserCoinVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
public class UserCoinService {
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    @Autowired
    private UserCoinMapper userCoinMapper;
    @Autowired
    private UserBillMapper userBillMapper;
    @Autowired
    private EthAddressMapper ethAddressMapper;
    @Autowired
    private EtcAddressMapper etcAddressMapper;

    /**
     * 第一次审核判余额
     *
     * @param userId
     * @param coinName
     * @param changeAvailableBalance
     * @param changeFreezeBalance
     * @param reason
     * @param logComment
     */
    @Transactional
    public void firstChangeUserCoin(int userId, String coinName, BigDecimal changeAvailableBalance, BigDecimal changeFreezeBalance, String reason, String logComment) {
        int changeAvailable = changeAvailableBalance.compareTo(ZERO);
        int changeFreeze = changeFreezeBalance.compareTo(ZERO);
        if (changeAvailable == 0 && changeFreeze == 0) {
            return;
        }
        // 调整精度
        if (changeAvailableBalance.scale() > 8)
            changeAvailableBalance = changeAvailableBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        if (changeFreezeBalance.scale() > 8)
            changeFreezeBalance = changeFreezeBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        int changeCount = userCoinMapper.changeUserCoin(userId, coinName, changeAvailableBalance, changeFreezeBalance);
        if (changeCount == 0) {
            if (userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName) == null) {
                // 没有钱包，手工创建一个新的钱包
                insertUserCoin(userId, coinName);
            } else {
                Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
            }
            // 重新修改钱包
            changeCount = userCoinMapper.changeUserCoin(userId, coinName, changeAvailableBalance, changeFreezeBalance);
        }
        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
    }

    private void insertUserCoin(int userId, String coinName) {
        UserCoin coin = new UserCoin();
        coin.setCoinName(coinName);
        coin.setUserId(userId);
        coin.setAvailableBalance(BigDecimal.ZERO);
        coin.setFreezeBalance(BigDecimal.ZERO);
        userCoinMapper.add(coin);
    }

    @Transactional
    public void changeUserReceivedFreezeCoin(int userId, String coinName, BigDecimal change, String reason, String logComment) {
        log.info("冻结金变化：userId ->{}, coinName ->{}, change ->{}, reason ->{}, logComment ->{}", userId, coinName, change, reason, logComment);
        if (change.compareTo(BigDecimal.ZERO) == 0) return;
        // 调整精度
        if (change.scale() > 8) change = change.setScale(8, BigDecimal.ROUND_HALF_UP);
        int changeCount = userCoinMapper.changeUserReceivedFreezeCoin(userId, coinName, change);
        if (changeCount == 0) {
            if (userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName) == null) {
                // 没有钱包，手工创建一个新的钱包
                insertUserCoin(userId, coinName);
            } else {
                Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
            }
            // 重新修改钱包
            changeCount = userCoinMapper.changeUserReceivedFreezeCoin(userId, coinName, change);
        }
        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
        // 增加日志
        userBillMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_RECEIVED_FREEZE, reason, change, logComment);
    }

    // 把收款冻结金转入可用金，如果用户冻结金不够，那么会转最大的金额，返还转移的金额
    @Transactional
    public BigDecimal recFreezeToAvailBalance(int userId, String coinName, BigDecimal amountTran, BigDecimal userRate,
                                              BigDecimal vipUserRate, String reason, String logComment) {

        BigDecimal changeFreezeBalance;

        UserCoin coin = userCoinMapper.lockUserCoin(userId, coinName);
        if (coin == null || coin.getReceiveFreezeBalance() == null || coin.getReceiveFreezeBalance().compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        if (coin.getVipType() != null && coin.getVipType() == UserCoin.VIP_TYPE_VIP) {
            //  vip用户
            changeFreezeBalance = amountTran.multiply(vipUserRate).setScale(8, RoundingMode.HALF_UP);
        } else {
            changeFreezeBalance = amountTran.multiply(userRate).setScale(8, RoundingMode.HALF_UP);
        }

        if (coin.getReceiveFreezeBalance().compareTo(changeFreezeBalance) < 0) {
            changeFreezeBalance = coin.getReceiveFreezeBalance();
        }
        int changeCount = userCoinMapper.receivedFreezeCoinToAvailable(userId, coinName, changeFreezeBalance);
        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
        userBillMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_AVAILABLE, reason, changeFreezeBalance, logComment);
        userBillMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_FREEZE, reason, changeFreezeBalance, logComment);
        return changeFreezeBalance;
    }


    /**
     * 改变用户金额
     *
     * @param userId                 用户id
     * @param coinName               币种
     * @param changeAvailableBalance 可用币变化数，可以是负数或0
     * @param changeFreezeBalance    冻结币变化数，可以是负数或0
     * @param reason                 变化原因，在UserBillReason总定义
     * @param logComment             账单日志
     */
    @Transactional
    public void changeUserCoin(int userId, String coinName, BigDecimal changeAvailableBalance, BigDecimal changeFreezeBalance, String reason, String logComment) {
        int changeAvailable = changeAvailableBalance.compareTo(ZERO);
        int changeFreeze = changeFreezeBalance.compareTo(ZERO);
        if (changeAvailable == 0 && changeFreeze == 0) {
            return;
        }
        // 调整精度
        if (changeAvailableBalance.scale() > 8)
            changeAvailableBalance = changeAvailableBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        if (changeFreezeBalance.scale() > 8)
            changeFreezeBalance = changeFreezeBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        int changeCount = userCoinMapper.changeUserCoin(userId, coinName, changeAvailableBalance, changeFreezeBalance);
        if (changeCount == 0) {
            if (userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName) == null) {
                // 没有钱包，手工创建一个新的钱包
                insertUserCoin(userId, coinName);
            } else {
                Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
            }
            // 重新修改钱包
            changeCount = userCoinMapper.changeUserCoin(userId, coinName, changeAvailableBalance, changeFreezeBalance);
        }
        Assert.check(changeCount != 1, ErrorCode.ERR_BALANCE_INSUFFICIENT);

        // 增加修改日志
        if (changeAvailable != 0) {
            userBillMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_AVAILABLE, reason, changeAvailableBalance, logComment);
        }
        // 增加修改日志
        if (changeFreeze != 0) {
            userBillMapper.insertUserBill(userId, coinName, UserBillType.SUB_TYPE_FREEZE, reason, changeFreezeBalance, logComment);
        }
    }

    //添加新的币钱包
    @Transactional
    public void addUserCoin(int userId, String coinName) {
        UserCoinVO userCoin = userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (userCoin == null) {
            userCoinMapper.add(new UserCoin().setUserId(userId).setCoinName(coinName));
        }
    }

    public void addUserCoin(int userId, String coinName, String address) {
        UserCoinVO userCoin = userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (userCoin == null) {
            userCoinMapper.add(new UserCoin().setUserId(userId).setCoinName(coinName).setBindAddress(address));
        }
    }

    public void updateUserCoinAddress(int userId, String coinName, String address) {
        UserCoinVO userCoin = userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (userCoin != null) {
            userCoinMapper.updateUserCoinAddress(userId, coinName, address);
        }
    }

    public Integer getUserIdByCoinNameAndAddress(String coinName, String address) {
        return userCoinMapper.getUserIdByCoinNameAndAddress(coinName, address);
    }

    public UserCoin getUserCoinByCoinNameAndAddress(String coinName, String address) {
        return userCoinMapper.getUserCoinByAddressAndCoinName(address, coinName);
    }

    public UserCoinVO getUserCoinByUserIdAndCoinName(int userId, String coinName) {
        return userCoinMapper.getUserCoinByUserIdAndCoinName(userId, coinName);
    }

    public UserCoinVO lockUserCoinByUserIdAndCoinName(int userId, String coinName) {
        return userCoinMapper.lockUserCoinByUserIdAndCoinName(userId, coinName);
    }

    public int addEthAddress(EthAddress ethAddress) {
        return ethAddressMapper.add(ethAddress);
    }

    public int addEtcAddress(EthAddress ethAddress) {
        return etcAddressMapper.add(ethAddress);
    }

    public EthAddress getEthAddressByUserId(int userId) {
        return ethAddressMapper.getEthAddressByUserId(userId);
    }

    public EthAddress getEthAddressByAddress(String address) {
        return ethAddressMapper.getEthAddressByAddress(address);
    }

    public BigDecimal getSumOfCoin(String coinName) {
        return userCoinMapper.getSumOfCoin(coinName);
    }

    public BigDecimal getSumOfAvailableCoin(String coinName) {
        return userCoinMapper.getSumOfAvailableCoin(coinName);
    }

    // 获取某个币的所有地址
    public List<String> getAllAddressByCoinName(String coinName) {
        return userCoinMapper.getAllAddressByCoinName(coinName);
    }

    public int setUserCoinVipType(Integer userId, String coinName, Integer vipType) {
        return userCoinMapper.setUserCoinVipType(userId, coinName, vipType);
    }
}
