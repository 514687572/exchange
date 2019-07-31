package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CancelOrderType;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.enums.MerchantType;
import com.cmd.exchange.common.mapper.SuperMerchantOrderMapper;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.NoUtil;
import com.github.pagehelper.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 处理兑换商的逻辑
 */
@Service
public class SuperMerchantOrderService {
    private static Log log = LogFactory.getLog(SuperMerchantOrderService.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SuperMerchantOrderMapper superMerchantOrderMapper;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private SuperMerchantOrderService me;
    @Autowired
    private ConfigService configService;

    public SuperMerchantOrder buyCoin(int userId, String coinName, String settlementCurrency, BigDecimal price, BigDecimal amount, int superMerchantId, String remake) {
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);
        Merchant superMerchant = merchantService.getMerchant(superMerchantId);
        Assert.check(superMerchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        MerchantCoinConf conf = merchantService.getMerchantCoinConf(coinName);
        Assert.check(conf == null, ErrorCode.ERR_MER_COIN_INVALID);
        Assert.check(amount.compareTo(conf.getOrderMinAmount()) < 0 || amount.compareTo(conf.getOrderMaxAmount()) > 0, ErrorCode.ERR_MER_ACCOUNT_WRONG);
        //Assert.check(amount.compareTo(merchant.getAvailableBalance()) > 0, ErrorCode.ERR_MER_NOT_BALANCE);
        SuperMerchantOrder order = new SuperMerchantOrder();
        order.setAmount(amount);
        order.setOrderNo(NoUtil.getOrderNo());
        order.setCoinName(coinName);
        order.setPrice(price);
        // 银行信息设置为兑换商的信息
        order.setBankType(superMerchant.getBankType());
        order.setBankName(superMerchant.getBankName());
        order.setBankNo(superMerchant.getBankNo());
        order.setBankUser(superMerchant.getBankUser());
        order.setRemake(remake);
        order.setSettlementCurrency(settlementCurrency);
        if (conf.getFeeRate() != null && conf.getFeeRate().compareTo(BigDecimal.ZERO) > 0 && conf.getFeeRate().compareTo(BigDecimal.ONE) < 0) {
            order.setFee(amount.multiply(conf.getFeeRate()).setScale(8, RoundingMode.HALF_UP));
        } else {
            order.setFee(BigDecimal.ZERO);
        }
        order.setStatus(MerchantOrderStatus.WAIT_ACCEPT);
        order.setMerchantId(merchant.getId());
        order.setType(MerchantOrderType.BUY);
        order.setSuperMerchantId(superMerchantId);

        superMerchantOrderMapper.addOrder(order);
        return order;
    }

    @Transactional
    public SuperMerchantOrder sellCoin(int userId, String coinName, String settlementCurrency, BigDecimal price, int userBankId, BigDecimal amount, int superMerchantId, String remake) {
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);
        Merchant superMerchant = merchantService.getMerchant(superMerchantId);
        Assert.check(superMerchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        MerchantCoinConf conf = merchantService.getMerchantCoinConf(coinName);
        Assert.check(conf == null, ErrorCode.ERR_MER_COIN_INVALID);
        Assert.check(amount.compareTo(conf.getOrderMinAmount()) < 0 || amount.compareTo(conf.getOrderMaxAmount()) > 0, ErrorCode.ERR_MER_ACCOUNT_WRONG);
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        UserBank bank = userBankService.getUserBankById(userBankId);
        Assert.check(bank == null || bank.getUserId() != userId, ErrorCode.ERR_PARAM_ERROR);
        SuperMerchantOrder order = new SuperMerchantOrder();
        order.setAmount(amount);
        order.setOrderNo(NoUtil.getOrderNo());
        order.setCoinName(coinName);
        order.setPrice(price);
        order.setRemake(remake);
        // 银行信息设置为兑换商的信息
        order.setBankType(BankType.fromValue(bank.getBankType()));
        order.setBankName(bank.getBankName());
        order.setBankNo(bank.getBankNo());
        order.setBankUser(bank.getBankUser());
        order.setSettlementCurrency(settlementCurrency);
        if (conf.getFeeRate() != null && conf.getFeeRate().compareTo(BigDecimal.ZERO) > 0 && conf.getFeeRate().compareTo(BigDecimal.ONE) < 0) {
            order.setFee(amount.multiply(conf.getFeeRate()).setScale(8, RoundingMode.HALF_UP));
        } else {
            order.setFee(BigDecimal.ZERO);
        }
        order.setStatus(MerchantOrderStatus.WAIT_ACCEPT);
        order.setMerchantId(merchant.getId());
        order.setType(MerchantOrderType.SELL);
        order.setSuperMerchantId(superMerchantId);

        // 扣费用
        merchantService.changeMerchantBalance(merchant.getId(), coinName, amount.negate(), amount, MerchantBill.B2B_MER_SELL_COIN,
                "orderId:" + order.getId());
        superMerchantOrderMapper.addOrder(order);

        return order;
    }

    public Page<SuperMerchantOrder> getMerchantOrders(Integer merchantId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        return superMerchantOrderMapper.getOrders(null, merchantId, status, type, orderNo, new RowBounds(page, size));
    }

    public Page<SuperMerchantOrder> getUserOrders(Integer userId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        return superMerchantOrderMapper.getOrders(merchant.getId(), null, status, type, orderNo, new RowBounds(page, size));
    }

    public Page<SuperMerchantOrder> getOrders(Integer merchantId, Integer superMerchantId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        return superMerchantOrderMapper.getOrders(merchantId, superMerchantId, status, type, orderNo, new RowBounds(page, size));
    }

    @Transactional
    public boolean superMerchantRefuseOrder(int superMerchantUserId, int orderId, String comment) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_ACCEPT, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        Assert.check(superMerchant.getUserId() != superMerchantUserId, ErrorCode.ERR_MER_INVALID_USER);

        String reason;
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            reason = CancelOrderType.MerchantRefuse;
        } else {
            if (order.getType() == MerchantOrderType.SELL && order.getStatus() == MerchantOrderStatus.WAIT_PAY) {
                // 买入，在支付的时候可以取消
                reason = CancelOrderType.MerchantNotPay;
            } else {
                // 状态错误，无法拒绝
                log.warn("superMerchantRefuseOrder cannot reject order,id=" + orderId + ", status=" + order.getStatus() + ",type=" + order.getType());
                Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
                return false;
            }
        }

        int count = superMerchantOrderMapper.cancelOrder(orderId, reason, comment);
        if (count == 0) {
            log.warn("superMerchantRefuseOrder failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
            return false;
        } else {
            log.info("merchantRefuseOrder, id=" + orderId);
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + orderId);
            return true;
        }
    }

    // 普通兑换商取消订单
    @Transactional
    public boolean merchantCancelOrder(int userId, int orderId, String comment) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(order.getMerchantId() != merchant.getId(), ErrorCode.ERR_MER_INVALID_USER);
        String reason;
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            reason = CancelOrderType.UserCancel;
        } else {
            if (order.getType() == MerchantOrderType.BUY && order.getStatus() == MerchantOrderStatus.WAIT_PAY) {
                // 买入，在支付的时候可以取消
                reason = CancelOrderType.UserNotPay;
            } else {
                // 状态错误，无法取消
                log.warn("merchant cannot cancel order,id=" + orderId + ", status=" + order.getStatus() + ",type=" + order.getType());
                Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
                return false;
            }
        }
        int count = superMerchantOrderMapper.cancelOrder(orderId, reason, comment);
        if (count == 0) {
            log.warn("merchantCancelOrder failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
            return false;
        } else {
            log.info("merchantCancelOrder, id=" + orderId);
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + orderId);
            return true;
        }
    }

    // 订单取消的时候退还冻结金，order里面状态不能是cancel，否则无法判断是否要返回冻结金
    private void orderCancelReturnCoin(SuperMerchantOrder order, String reason, String comment) {
        if (order.getType() == MerchantOrderType.SELL) {
            // 卖币，一开始就冻结了用户资金，所以直接返还冻结金
            merchantService.changeMerchantBalance(order.getMerchantId(), order.getCoinName(), order.getAmount(), order.getAmount().negate(),
                    reason, comment);
            return;
        }
        // 买入，只有在接受订单后才可以扣款
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            return;
        }
        merchantService.changeMerchantBalance(order.getSuperMerchantId(), order.getCoinName(), order.getAmount(), order.getAmount().negate(),
                reason, comment);
    }

    // 取消过期的没有接单的订单
    public void cancelTimeoutOrders() {
        int timeout = configService.getConfigValue(ConfigKey.MERCHANT_ORDER_TIMEOUT, 3600 * 24);  // 默认一天
        int count = 0;
        log.debug("cancelTimeoutOrders begin");
        int lastId = 0;
        while (count < 50) {
            SuperMerchantOrder order = superMerchantOrderMapper.getNextTimeoutOrder(lastId, timeout);
            if (order == null) {
                break;
            }
            count++;
            try {
                me.taskCancelOrder(order, timeout);
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        log.info("cancelTimeoutOrders end,count=" + count);
    }

    public void taskCancelOrder(SuperMerchantOrder cancelOrder, int timeOut) {
        int curTime = (int) (System.currentTimeMillis() / 1000);
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(cancelOrder.getId());
        if ((order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT && curTime - order.getAddTime() > timeOut) ||
                (order.getStatus() == MerchantOrderStatus.WAIT_PAY && curTime - order.getAcceptTime() > timeOut)) {
            // 满足添加
        } else {
            log.warn("order do not match cancel order:id=" + order.getId() + ",status=" + order.getStatus() + ",addTime=" + order.getAddTime() + ",acceptTime=" + order.getAcceptTime());
            return;
        }
        log.info("cancel order:id=" + order.getId() + ",status=" + order.getStatus() + ",addTime=" + order.getAddTime() + ",acceptTime=" + order.getAcceptTime());
        int count = superMerchantOrderMapper.cancelOrder(order.getId(), CancelOrderType.TaskCancel, "Timeout=" + timeOut);
        if (count == 0) {
            log.warn("userCancelOrder failed, id=" + order.getId());
            throw new RuntimeException("cancel failed");
        } else {
            log.info("userCancelOrder, id=" + order.getId());
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + order.getId());
        }

    }

    @Transactional
    public boolean merchantAcceptOrder(int superMerchantUserId, int orderId) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_ACCEPT, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        Assert.check(superMerchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(order.getSuperMerchantId() != superMerchant.getId(), ErrorCode.ERR_MER_INVALID_USER);
        Assert.check(superMerchant.getType() != MerchantType.SUPER.getValue(), ErrorCode.ERR_MER_NO_MERCHANT);

        if (order.getType() == MerchantOrderType.BUY) {
            // 如果是买，那么冻结兑换商的对应金额
            Assert.check(merchantService.getMerchantAvailableBalance(superMerchant.getId(), order.getCoinName()).compareTo(order.getAmount()) < 0, ErrorCode.ERR_MER_NOT_BALANCE);
            merchantService.changeMerchantBalance(superMerchant.getId(), order.getCoinName(), order.getAmount().negate(), order.getAmount(),
                    MerchantBill.B2B_MER_BUY_COIN, "orderId:" + order.getId());
        }

        int count = superMerchantOrderMapper.acceptOrder(orderId);
        if (count == 0) {
            log.warn("merchantAcceptOrder failed, id=" + orderId);
            return false;
        } else {
            log.info("merchantAcceptOrder, id=" + orderId);
        }

        return true;
    }

    /**
     * 上传凭证（表示已经付款，到已经付款状态），这个有可能是普通用户，也可能是商家
     */
    @Transactional
    public boolean uploadVoucher(int userId, int orderId, String credentialComment, String credentialUrls) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_PAY, ErrorCode.ERR_MER_STATUS_WRONG);
        // 如果是买，那么用户上传凭证，如果是卖，那么兑换商上传凭证
        if (order.getType() == MerchantOrderType.BUY) {
            Merchant merchant = merchantService.getMerchant(order.getMerchantId());
            Assert.check(userId != merchant.getUserId(), ErrorCode.ERR_MER_STATUS_WRONG);
        } else {
            Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
            Assert.check(superMerchant.getUserId() != userId, ErrorCode.ERR_MER_STATUS_WRONG);
        }
        int count = superMerchantOrderMapper.uploadVoucher(orderId, credentialComment, credentialUrls);
        if (count == 0) {
            log.warn("uploadVoucher failed, id=" + orderId);
            return false;
        } else {
            log.info("uploadVoucher, id=" + orderId);
            return true;
        }
    }

    public SuperMerchantOrder getOrder(int orderId) {
        return superMerchantOrderMapper.getOrder(orderId);
    }

    // 兑换商确认收到站外的钱
    @Transactional
    public boolean superMerchantConfirmReceived(int merchantUserId, int orderId) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.PAYED, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(order.getType() != MerchantOrderType.BUY, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        Assert.check(superMerchant.getUserId() != merchantUserId, ErrorCode.ERR_MER_STATUS_WRONG);

        // 确认收款，修改状态
        int count = superMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("merchantConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
            return false;
        } else {
            log.info("merchantConfirmReceived, id=" + orderId);
            // 扣除兑换商的虚拟币，增加用户虚拟币
            merchantService.changeMerchantBalance(superMerchant.getId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.B2B_MER_BUY_COIN, "orderId:" + orderId);
            merchantService.changeMerchantBalance(order.getMerchantId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.B2B_MER_BUY_COIN, "orderId:" + order.getId() + ",fee=" + order.getFee());
            return true;
        }
    }

    // 兑换商确认收到站外的钱
    @Transactional
    public boolean userConfirmReceived(int userId, int orderId) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.PAYED, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(order.getType() != MerchantOrderType.SELL, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant.getUserId() != userId, ErrorCode.ERR_MER_NO_ORDER);
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        // 把币转给兑换商
        int count = superMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("userConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
            return false;
        } else {
            log.info("userConfirmReceived, id=" + orderId);
            // 增加兑换商的虚拟币，减少用户虚拟币
            merchantService.changeMerchantBalance(superMerchant.getId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.B2B_MER_SELL_COIN, "orderId:" + orderId + ",fee=" + order.getFee());
            merchantService.changeMerchantBalance(order.getMerchantId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.B2B_MER_SELL_COIN, "orderId:" + order.getId());
            return true;
        }
    }

    // 管理员强制完成订单
    @Transactional
    public void mgrConfirmReceived(int orderId) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        // 转账在待付款和待接单状态不能强制完成收款
        Assert.check(order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT || order.getStatus() == MerchantOrderStatus.WAIT_PAY, ErrorCode.ERR_MER_STATUS_WRONG);
        // 如果是取消或者是完成状态不能修改状态
        Assert.check(order.getStatus() == MerchantOrderStatus.CANCEL || order.getStatus() == MerchantOrderStatus.COMPLETE, ErrorCode.ERR_MER_STATUS_WRONG);

        int count = superMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("mgrConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
        }
        if (order.getType() == MerchantOrderType.BUY) {
            // 买，把币拨给用户 扣除兑换商的虚拟币，增加用户虚拟币
            merchantService.changeMerchantBalance(superMerchant.getId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.B2B_MER_BUY_COIN, "orderId:" + orderId);
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.B2B_MER_BUY_COIN, "orderId:" + orderId);
        } else {
            // 卖，把币拨给超级兑换商  增加兑换商的虚拟币，减少用户虚拟币
            merchantService.changeMerchantBalance(superMerchant.getId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.B2B_MER_SELL_COIN, "orderId:" + orderId);
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.B2B_MER_SELL_COIN, "orderId:" + orderId);
        }
    }

    @Transactional
    public void mgrCancelOrder(int orderId, String comment) {
        SuperMerchantOrder order = superMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        // 如果是取消或者是完成状态不能修改状态
        Assert.check(order.getStatus() == MerchantOrderStatus.CANCEL || order.getStatus() == MerchantOrderStatus.COMPLETE, ErrorCode.ERR_MER_STATUS_WRONG);
        orderCancelReturnCoin(order, MerchantBill.C2B_MGR_CANCEL, "orderId:" + orderId);
        superMerchantOrderMapper.cancelOrder(orderId, CancelOrderType.ManagerCancel, comment);
    }

}
