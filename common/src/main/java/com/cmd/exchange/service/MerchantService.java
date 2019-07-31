package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.mapper.MerchantMapper;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.MerchantVo;
import com.github.pagehelper.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 处理兑换商的逻辑
 */
@Service
public class MerchantService {
    private static Log log = LogFactory.getLog(MerchantService.class);

    @Autowired
    private MerchantMapper merchantMapper;

    public MerchantCoinConf getMerchantCoinConf(String coinName) {
        return merchantMapper.getMerchantCoinConf(coinName);
    }

    // 获取有效兑换币
    public List<MerchantCoinConf> getValidMerchantCoinConf() {
        return merchantMapper.getMerchantCoinConfByStatus(0);
    }

    // 获取所有兑换币
    public List<MerchantCoinConf> getAllMerchantCoinConf() {
        return merchantMapper.getMerchantCoinConfByStatus(null);
    }

    public void addMerchantCoinConf(MerchantCoinConf conf) {
        if (conf.getFeeRate() == null) {
            conf.setFeeRate(BigDecimal.ZERO);
        }
        merchantMapper.addMerchantCoinConf(conf);
    }

    public void updateMerchantCoinConf(MerchantCoinConf conf) {
        merchantMapper.updateMerchantCoinConf(conf);
    }

    public int delMerchantCoinConf(int id) {
        return merchantMapper.delMerchantCoinConf(id);
    }

    public MerchantCoinConf getMerchantCoinConf(int id) {
        return merchantMapper.getMerchantCoinConfById(id);
    }

    public boolean isCoinValid(String coinName) {
        MerchantCoinConf merchantCoinConf = merchantMapper.getMerchantCoinConf(coinName);
        if (merchantCoinConf == null) {
            return false;
        }
        return merchantCoinConf.getStatus() == MerchantCoinConf.STATUS_NORMAL;
    }

    public Merchant getMerchant(int id) {
        return merchantMapper.getMerchant(id);
    }

    // 获取某个虚拟币种的价格
    public BigDecimal getCoinPrice(String coinName, String settlementCurrency) {
        MerchantCoinConf conf = merchantMapper.getMerchantCoinConf(coinName);
        Assert.check(conf == null, ErrorCode.ERR_MER_COIN_INVALID);
        if (settlementCurrency.equalsIgnoreCase("CNY")) {
            if (conf.getCnyPrice() != null || conf.getCnyPrice().compareTo(BigDecimal.ZERO) > 0)
                return conf.getCnyPrice();
        } else if (settlementCurrency.equalsIgnoreCase("USD")) {
            if (conf.getDollarPrice() != null || conf.getDollarPrice().compareTo(BigDecimal.ZERO) > 0)
                return conf.getDollarPrice();
        } else if (settlementCurrency.equalsIgnoreCase("HKD")) {
            if (conf.getHkdollarPrice() != null || conf.getHkdollarPrice().compareTo(BigDecimal.ZERO) > 0)
                return conf.getHkdollarPrice();
        }
        Assert.check(conf == null, ErrorCode.ERR_MER_SET_CUR_INVALID);
        return null;
    }

    /**
     * 获取满足amount交易的兑换商列表，用于买入数字货币
     *
     * @param amount             要兑换的数量
     * @param coinName           要买卖的币种
     * @param settlementCurrency 结算货币（真实货币，场外）
     * @param type               类型：买入还是卖出
     * @param payType            支付方式
     * @param merchantName       兑换商名称
     * @param page               页码
     * @param size               页码大小
     * @return
     */
    public Page<MerchantVo> getMerchantListForUser(BigDecimal amount, String coinName, String settlementCurrency, MerchantOrderType type,
                                                   Integer payType, String merchantName, Integer exceptionMerchantId, int page, int size) {
        return merchantMapper.getMerchantListForUser(amount, coinName, type, 1, merchantName, exceptionMerchantId, new RowBounds(page, size));
    }

    public Page<MerchantVo> getMerchantListForMerchant(BigDecimal amount, String coinName, String settlementCurrency, MerchantOrderType type,
                                                       Integer payType, String merchantName, int page, int size) {
        return merchantMapper.getMerchantListForUser(amount, coinName, type, 2, merchantName, null, new RowBounds(page, size));
    }

    public Merchant getUserMerchant(int userId) {
        return merchantMapper.getUserMerchant(userId);
    }

    public List<MerchantCoin> getMerchantCoins(int merchantId) {
        return merchantMapper.getMerchantCoins(merchantId);
    }

    public MerchantCoin getMerchantCoin(int merchantId, String coinName) {
        return merchantMapper.getMerchantCoin(merchantId, coinName);
    }

    public void userUpdateMerchant(int userId, Merchant merchant) {
        Merchant oldMerchant = merchantMapper.getUserMerchant(userId);
        Assert.check(oldMerchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(oldMerchant.getUserId() != userId, ErrorCode.ERR_MER_NO_MERCHANT);
        // 用户不能修改用户状态，用户资产
        merchant.setId(oldMerchant.getId());
        merchant.setUserId(null);
        merchant.setStatus(null);
        merchant.setType(null);
        merchantMapper.updateMerchant(merchant);
    }

    // 修改兑换商信息，管理员用
    public void updateMerchant(Merchant merchant) {
        Merchant oldMerchant = merchantMapper.getMerchant(merchant.getId());
        Assert.check(oldMerchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        merchant.setUserId(null);
        merchantMapper.updateMerchant(merchant);
    }

    // 申请成为兑换商
    public void applyTobeMerchant(Merchant merchant) {
        // 检查是否重名
        Merchant oldMerchant = merchantMapper.getMerchantByUserId(merchant.getUserId());
        if (oldMerchant == null) {
            merchantMapper.addMerchant(merchant);
        } else {
            Assert.check(oldMerchant.getStatus() != 3, ErrorCode.ERR_MER_MER_EXISTS);
            merchant.setId(oldMerchant.getId());
            merchantMapper.updateMerchant(merchant);
        }
    }

    public Merchant getMerchantByUserId(int userId) {
        return merchantMapper.getMerchantByUserId(userId);
    }

    // 搜索兑换商，如果是null，那么忽略该条件
    public Page<Merchant> searchMerchant(Integer type, Integer userId, String merchantName, Integer status, int page, int pageSize) {
        return merchantMapper.searchMerchant(type, userId, merchantName, status, new RowBounds(page, pageSize));
    }


    @Transactional
    public void changeMerchantBalance(int merchantId, String coinName, BigDecimal changeAvailableBalance, BigDecimal changeFreezeBalance, String reason, String logComment) {
        int changeAvailable = changeAvailableBalance.compareTo(BigDecimal.ZERO);
        int changeFreeze = changeFreezeBalance.compareTo(BigDecimal.ZERO);
        if (changeAvailable == 0 && changeFreeze == 0) {
            return;
        }

        // 调整精度
        if (changeAvailableBalance.scale() > 8)
            changeAvailableBalance = changeAvailableBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        if (changeFreezeBalance.scale() > 8)
            changeFreezeBalance = changeFreezeBalance.setScale(8, BigDecimal.ROUND_HALF_UP);
        int changeCount = merchantMapper.changeMerchantBalance(merchantId, coinName, changeAvailableBalance, changeFreezeBalance);
        if (changeCount == 0) {
            if (merchantMapper.getMerchantCoin(merchantId, coinName) == null) {
                // 没有钱包，手工创建一个新的钱包
                MerchantCoin coin = new MerchantCoin();
                coin.setCoinName(coinName);
                coin.setMerchantId(merchantId);
                coin.setAvailableBalance(BigDecimal.ZERO);
                coin.setFreezeBalance(BigDecimal.ZERO);
                merchantMapper.addMerchantCoin(coin);
            } else {
                Assert.check(changeCount != 1, ErrorCode.ERR_MER_NOT_BALANCE, "Not Enough Available Balance");
            }
            // 重新修改钱包
            changeCount = merchantMapper.changeMerchantBalance(merchantId, coinName, changeAvailableBalance, changeFreezeBalance);
        }
        Assert.check(changeCount != 1, ErrorCode.ERR_MER_NOT_BALANCE, "Not Enough Available Balance: " + changeCount);


        // 增加修改日志
        if (changeAvailable != 0) {
            merchantMapper.insertMerchantBill(merchantId, coinName, MerchantBill.SUB_TYPE_AVAILABLE, reason, changeAvailableBalance, logComment);
        }
        // 增加修改日志
        if (changeFreeze != 0) {
            merchantMapper.insertMerchantBill(merchantId, coinName, MerchantBill.SUB_TYPE_FREEZE, reason, changeFreezeBalance, logComment);
        }
    }

    public BigDecimal getMerchantAvailableBalance(int merchantId, String coinName) {
        MerchantCoin coin = merchantMapper.getMerchantCoin(merchantId, coinName);
        if (coin == null) return BigDecimal.ZERO;
        return coin.getAvailableBalance();
    }


    //////////////////////////////////////////////////////////////////////////////
    public Page<MerchantBill> getMerchantBill(Integer merchantId, String coinName, String type, Integer startTime, Integer endTime, int page, int size) {
        return merchantMapper.getMerchantBills(merchantId, coinName, type, startTime, endTime, new RowBounds(page, size));
    }

}
