package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CancelOrderType;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.enums.MerchantType;
import com.cmd.exchange.common.mapper.UserMerchantOrderMapper;
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
public class UserMerchantOrderService {
    private static Log log = LogFactory.getLog(UserMerchantOrderService.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserMerchantOrderMapper userMerchantOrderMapper;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserMerchantOrderService me;

    public UserMerchantOrder buyCoin(int userId, String coinName, String settlementCurrency, BigDecimal price, BigDecimal amount, int merchantId, String remake) {
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);
        Merchant merchant = merchantService.getMerchant(merchantId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        MerchantCoinConf conf = merchantService.getMerchantCoinConf(coinName);
        Assert.check(conf == null, ErrorCode.ERR_MER_COIN_INVALID);
        Assert.check(amount.compareTo(conf.getOrderMinAmount()) < 0 || amount.compareTo(conf.getOrderMaxAmount()) > 0, ErrorCode.ERR_MER_ACCOUNT_WRONG);
        //Assert.check(amount.compareTo(merchant.getAvailableBalance()) > 0, ErrorCode.ERR_MER_NOT_BALANCE);
        UserMerchantOrder order = new UserMerchantOrder();
        order.setAmount(amount);
        order.setOrderNo(NoUtil.getOrderNo());
        order.setCoinName(coinName);
        order.setPrice(price);
        // 银行信息设置为兑换商的信息
        order.setBankType(merchant.getBankType());
        order.setBankName(merchant.getBankName());
        order.setBankNo(merchant.getBankNo());
        order.setBankUser(merchant.getBankUser());
        order.setRemake(remake);
        order.setSettlementCurrency(settlementCurrency);
        if (conf.getFeeRate() != null && conf.getFeeRate().compareTo(BigDecimal.ZERO) > 0 && conf.getFeeRate().compareTo(BigDecimal.ONE) < 0) {
            order.setFee(amount.multiply(conf.getFeeRate()).setScale(8, RoundingMode.HALF_UP));
        } else {
            order.setFee(BigDecimal.ZERO);
        }
        order.setStatus(MerchantOrderStatus.WAIT_ACCEPT);
        order.setMerchantId(merchantId);
        order.setType(MerchantOrderType.BUY);
        order.setUserId(userId);

        userMerchantOrderMapper.addOrder(order);
        return order;
    }

    @Transactional
    public UserMerchantOrder sellCoin(int userId, String coinName, String settlementCurrency, BigDecimal price, int userBankId, BigDecimal amount, int merchantId, String remake) {
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);
        Merchant merchant = merchantService.getMerchant(merchantId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        MerchantCoinConf conf = merchantService.getMerchantCoinConf(coinName);
        Assert.check(conf == null, ErrorCode.ERR_MER_COIN_INVALID);
        Assert.check(amount.compareTo(conf.getOrderMinAmount()) < 0 || amount.compareTo(conf.getOrderMaxAmount()) > 0, ErrorCode.ERR_MER_ACCOUNT_WRONG);
        UserBank bank = userBankService.getUserBankById(userBankId);
        Assert.check(bank == null || bank.getUserId() != userId, ErrorCode.ERR_PARAM_ERROR);
        UserMerchantOrder order = new UserMerchantOrder();
        order.setAmount(amount);
        order.setOrderNo(NoUtil.getOrderNo());
        order.setCoinName(coinName);
        order.setRemake(remake);
        order.setPrice(price);
        // 银行信息设置为兑换商的信息
        order.setBankType(BankType.fromValue(bank.getBankType()));
        order.setBankName(bank.getBankName());
        order.setBankNo(bank.getBankNo());
        order.setReceiptCode(bank.getReceiptCode());
        order.setBankUser(bank.getBankUser());
        order.setSettlementCurrency(settlementCurrency);
        if (conf.getFeeRate() != null && conf.getFeeRate().compareTo(BigDecimal.ZERO) > 0 && conf.getFeeRate().compareTo(BigDecimal.ONE) < 0) {
            order.setFee(amount.multiply(conf.getFeeRate()).setScale(8, RoundingMode.HALF_UP));
        } else {
            order.setFee(BigDecimal.ZERO);
        }
        order.setStatus(MerchantOrderStatus.WAIT_ACCEPT);
        order.setMerchantId(merchantId);
        order.setType(MerchantOrderType.SELL);
        order.setUserId(userId);

        // 扣费用
        userCoinService.changeUserCoin(userId, coinName, amount.negate(), amount, UserBillReason.C2B_USER_SELL_COIN,
                "orderId:" + order.getId() + ",coin:" + order.getCoinName());

        userMerchantOrderMapper.addOrder(order);

        return order;
    }

    public Page<UserMerchantOrder> getMerchantOrders(Integer merchantId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        return userMerchantOrderMapper.getOrders(null, merchantId, status, type, orderNo, new RowBounds(page, size));
    }

    public Page<UserMerchantOrder> getUserOrders(Integer userId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        return userMerchantOrderMapper.getOrders(userId, null, status, type, orderNo, new RowBounds(page, size));
    }

    public Page<UserMerchantOrder> getOrders(Integer userId, Integer merchantId, MerchantOrderStatus status, MerchantOrderType type, String orderNo, int page, int size) {
        return userMerchantOrderMapper.getOrders(userId, merchantId, status, type, orderNo, new RowBounds(page, size));
    }

    @Transactional
    public boolean merchantRefuseOrder(int merchantUserId, int orderId, String comment) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_ACCEPT, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant.getUserId() != merchantUserId, ErrorCode.ERR_MER_INVALID_USER);

        String reason;
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            reason = CancelOrderType.MerchantRefuse;
        } else {
            if (order.getType() == MerchantOrderType.SELL && order.getStatus() == MerchantOrderStatus.WAIT_PAY) {
                // 买入，在支付的时候可以取消
                reason = CancelOrderType.MerchantNotPay;
            } else {
                // 状态错误，无法取消
                log.warn("merchant cannot cancel order,id=" + orderId + ", status=" + order.getStatus() + ",type=" + order.getType());
                Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
                return false;
            }
        }

        int count = userMerchantOrderMapper.cancelOrder(orderId, reason, comment);
        if (count == 0) {
            log.warn("merchantRefuseOrder failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
            return false;
        } else {
            log.info("merchantRefuseOrder, id=" + orderId);
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + orderId);
            return true;
        }
    }

    @Transactional
    public boolean userCancelOrder(int userId, int orderId, String comment) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getUserId() != userId, ErrorCode.ERR_MER_INVALID_USER);
        String reason;
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            reason = CancelOrderType.UserCancel;
        } else {
            if (order.getType() == MerchantOrderType.BUY && order.getStatus() == MerchantOrderStatus.WAIT_PAY) {
                // 买入，在支付的时候可以取消
                reason = CancelOrderType.UserNotPay;
            } else {
                // 状态错误，无法取消
                log.warn("user cannot cancel order,id=" + orderId + ", status=" + order.getStatus() + ",type=" + order.getType());
                Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
                return false;
            }
        }
        int count = userMerchantOrderMapper.cancelOrder(orderId, reason, comment);
        if (count == 0) {
            log.warn("userCancelOrder failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_STATUS_WRONG);
            return false;
        } else {
            log.info("userCancelOrder, id=" + orderId);
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + orderId);
            return true;
        }
    }

    // 订单取消的时候退还冻结金，order里面状态不能是cancel，否则无法判断是否要返回冻结金
    private void orderCancelReturnCoin(UserMerchantOrder order, String reason, String comment) {
        if (order.getType() == MerchantOrderType.SELL) {
            // 卖币，一开始就冻结了用户资金，所以直接返还冻结金
            userCoinService.changeUserCoin(order.getUserId(), order.getCoinName(), order.getAmount(), order.getAmount().negate(),
                    UserBillReason.C2B_USER_CC_SELL, "orderId:" + order.getId());
            return;
        }
        // 买入，只有在接受订单后才可以扣款
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            return;
        }
        merchantService.changeMerchantBalance(order.getMerchantId(), order.getCoinName(), order.getAmount(), order.getAmount().negate(),
                reason, comment);
    }

    // 取消过期的没有接单的订单
    public void cancelTimeoutOrders() {
        int timeout = configService.getConfigValue(ConfigKey.MERCHANT_ORDER_TIMEOUT, 3600 * 24);  // 默认一天
        int count = 0;
        log.debug("cancelTimeoutOrders begin");
        int lastId = 0;
        while (count < 50) {
            UserMerchantOrder order = userMerchantOrderMapper.getNextTimeoutOrder(lastId, timeout);
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

    public void taskCancelOrder(UserMerchantOrder cancelOrder, int timeOut) {
        int curTime = (int) (System.currentTimeMillis() / 1000);
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(cancelOrder.getId());
        if ((order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT && curTime - order.getAddTime() > timeOut) ||
                (order.getStatus() == MerchantOrderStatus.WAIT_PAY && curTime - order.getAcceptTime() > timeOut)) {
            // 满足添加
        } else {
            log.warn("order do not match cancel order:id=" + order.getId() + ",status=" + order.getStatus() + ",addTime=" + order.getAddTime() + ",acceptTime=" + order.getAcceptTime());
            return;
        }
        log.info("cancel order:id=" + order.getId() + ",status=" + order.getStatus() + ",addTime=" + order.getAddTime() + ",acceptTime=" + order.getAcceptTime());
        int count = userMerchantOrderMapper.cancelOrder(order.getId(), CancelOrderType.TaskCancel, "Timeout=" + timeOut);
        if (count == 0) {
            log.warn("userCancelOrder failed, id=" + order.getId());
            throw new RuntimeException("cancel failed");
        } else {
            log.info("userCancelOrder, id=" + order.getId());
            orderCancelReturnCoin(order, MerchantBill.C2B_CANCEL, "orderId:" + order.getId());
        }

    }


    @Transactional
    public boolean merchantAcceptOrder(int merchantUserId, int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_ACCEPT, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(order.getMerchantId() != merchant.getId(), ErrorCode.ERR_MER_INVALID_USER);
        Assert.check(merchant.getType() != MerchantType.NORMAL.getValue(), ErrorCode.ERR_MER_NO_MERCHANT);

        if (order.getType() == MerchantOrderType.BUY) {
            // 如果是买，那么冻结兑换商的对应金额
            Assert.check(merchantService.getMerchantAvailableBalance(merchant.getId(), order.getCoinName()).compareTo(order.getAmount()) < 0, ErrorCode.ERR_MER_NOT_BALANCE);
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), order.getAmount().negate(), order.getAmount(),
                    MerchantBill.C2B_USER_BUY_COIN, "orderId:" + order.getId());
        }

        int count = userMerchantOrderMapper.acceptOrder(orderId);
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
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.WAIT_PAY, ErrorCode.ERR_MER_STATUS_WRONG);
        // 如果是买，那么用户上传凭证，如果是卖，那么兑换商上传凭证
        if (order.getType() == MerchantOrderType.BUY) {
            Assert.check(order.getUserId() != userId, ErrorCode.ERR_MER_STATUS_WRONG);
        } else {
            Merchant merchant = merchantService.getMerchant(order.getMerchantId());
            Assert.check(merchant.getUserId() != userId, ErrorCode.ERR_MER_STATUS_WRONG);
        }
        int count = userMerchantOrderMapper.uploadVoucher(orderId, credentialComment, credentialUrls);
        if (count == 0) {
            log.warn("uploadVoucher failed, id=" + orderId);
            return false;
        } else {
            log.info("uploadVoucher, id=" + orderId);
            return true;
        }
    }

    public UserMerchantOrder getOrder(int orderId) {
        return userMerchantOrderMapper.getOrder(orderId);
    }

    // 兑换商确认收到站外的钱
    @Transactional
    public boolean merchantConfirmReceived(int merchantUserId, int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.PAYED, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(order.getType() != MerchantOrderType.BUY, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant.getUserId() != merchantUserId, ErrorCode.ERR_MER_STATUS_WRONG);

        // 确认收款，修改状态
        int count = userMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("merchantConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
            return false;
        } else {
            log.info("merchantConfirmReceived, id=" + orderId);
            // 扣除兑换商的虚拟币，增加用户虚拟币
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.C2B_USER_BUY_COIN, "orderId:" + orderId);
            userCoinService.changeUserCoin(order.getUserId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    UserBillReason.C2B_USER_BUY_COIN, "orderId:" + order.getId() + ",fee=" + order.getFee());
            return true;
        }
    }

    // 用户确认收到站外的钱
    @Transactional
    public boolean userConfirmReceived(int userId, int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() != MerchantOrderStatus.PAYED, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(order.getType() != MerchantOrderType.SELL, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(order.getUserId() != userId, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        // 把币转给兑换商
        int count = userMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("userConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
            return false;
        } else {
            log.info("userConfirmReceived, id=" + orderId);
            // 增加兑换商的虚拟币，减少用户虚拟币
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.C2B_USER_SELL_COIN, "orderId:" + orderId + ",fee=" + order.getFee());
            userCoinService.changeUserCoin(order.getUserId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    UserBillReason.C2B_USER_SELL_COIN, "orderId:" + order.getId());
            return true;
        }
    }

    // 管理员强制完成订单
    @Transactional
    public void mgrConfirmReceived(int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        // 转账在待付款和待接单状态不能强制完成收款
        Assert.check(order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT || order.getStatus() == MerchantOrderStatus.WAIT_PAY, ErrorCode.ERR_MER_STATUS_WRONG);
        // 如果是取消或者是完成状态不能修改状态
        Assert.check(order.getStatus() == MerchantOrderStatus.CANCEL || order.getStatus() == MerchantOrderStatus.COMPLETE, ErrorCode.ERR_MER_STATUS_WRONG);

        int count = userMerchantOrderMapper.completeOrder(orderId);
        if (count == 0) {
            log.warn("mgrConfirmReceived failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
        }
        if (order.getType() == MerchantOrderType.BUY) {
            // 买，把币拨给用户 扣除兑换商的虚拟币，增加用户虚拟币
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    MerchantBill.C2B_USER_BUY_COIN, "orderId:" + orderId);
            userCoinService.changeUserCoin(order.getUserId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    UserBillReason.C2B_USER_BUY_COIN, "orderId:" + orderId);
        } else {
            // 卖，把币拨给兑换商  增加兑换商的虚拟币，减少用户虚拟币
            merchantService.changeMerchantBalance(merchant.getId(), order.getCoinName(), order.getAmount().subtract(order.getFee()), BigDecimal.ZERO,
                    MerchantBill.C2B_USER_SELL_COIN, "orderId:" + orderId);
            userCoinService.changeUserCoin(order.getUserId(), order.getCoinName(), BigDecimal.ZERO, order.getAmount().negate(),
                    UserBillReason.C2B_USER_SELL_COIN, "orderId:" + orderId);
        }
    }

    @Transactional
    public void mgrCancelOrder(int orderId, String comment) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        // 如果是取消或者是完成状态不能修改状态
        Assert.check(order.getStatus() == MerchantOrderStatus.CANCEL || order.getStatus() == MerchantOrderStatus.COMPLETE, ErrorCode.ERR_MER_STATUS_WRONG);
        orderCancelReturnCoin(order, MerchantBill.C2B_MGR_CANCEL, "orderId:" + orderId);
        userMerchantOrderMapper.cancelOrder(orderId, CancelOrderType.ManagerCancel, comment);
    }

    @Transactional
    public void appeal(int userId, int orderId, String credentialComment, String credentialUrls) {
        OrderComplaint oldOc = userMerchantOrderMapper.getOrderComplaintByOrder(orderId);
        Assert.check(oldOc != null, ErrorCode.ERR_MER_OC_EXISTS);
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(userId != order.getUserId() && userId != merchant.getUserId(), ErrorCode.ERR_MER_NO_ORDER);
        // 暂时只有付款后或待收款才能申诉
        Assert.check(order.getStatus() != MerchantOrderStatus.PAYED && order.getStatus() != MerchantOrderStatus.WAIT_PAY, ErrorCode.ERR_MER_STATUS_WRONG);
        OrderComplaint oc = new OrderComplaint();
        // 允许双方投诉
        /*if(order.getStatus() == MerchantOrderStatus.PAYED) {
            if(order.getType() == MerchantOrderType.BUY) {
                // 对于买单，用户付款后商家没完成订单，用户可以投诉
                Assert.check(userId != order.getUserId(), ErrorCode.ERR_MER_STATUS_WRONG);
                oc.setType(OrderComplaint.TYPE_USER);
            } else {
                // 对于卖单，商家付款后用户长时间没完成订单，商家可以申诉
                Assert.check(userId != merchant.getUserId(), ErrorCode.ERR_MER_STATUS_WRONG);
                oc.setType(OrderComplaint.TYPE_MERCHANT);
            }
        } else {
            if(order.getType() == MerchantOrderType.BUY) {
                // 用户不肯付款，兑换商投诉
                Assert.check(userId != merchant.getUserId(), ErrorCode.ERR_MER_STATUS_WRONG);
                oc.setType(OrderComplaint.TYPE_MERCHANT);
            } else {
                // 对于卖单，商家不肯付款，用户投诉
                Assert.check(userId != order.getUserId(), ErrorCode.ERR_MER_STATUS_WRONG);
                oc.setType(OrderComplaint.TYPE_USER);
            }

        }*/
        if (userId == order.getUserId()) {
            oc.setType(OrderComplaint.TYPE_USER);
        } else {
            oc.setType(OrderComplaint.TYPE_MERCHANT);
        }

        int count = userMerchantOrderMapper.appealOrder(orderId);
        if (count == 0) {
            log.warn("appealOrder failed, id=" + orderId);
            Assert.failed(ErrorCode.ERR_MER_NO_ORDER);
        }
        // 增加申诉对象
        oc.setCredentialComment(credentialComment);
        oc.setCredentialUrls(credentialUrls);
        oc.setOrderId(orderId);
        oc.setUserId(userId);
        userMerchantOrderMapper.addComplaint(oc);
    }

    @Transactional
    public void cancelAppeal(int userId, int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.lockOrder(orderId);
        Assert.check(order.getStatus() != MerchantOrderStatus.COMPLAIN, ErrorCode.ERR_MER_STATUS_WRONG);
        Merchant merchant = merchantService.getMerchant(order.getMerchantId());
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_MERCHANT);
        Assert.check(userId != order.getUserId() && userId != merchant.getUserId(), ErrorCode.ERR_MER_NO_ORDER);

        OrderComplaint oldOc = userMerchantOrderMapper.getOrderComplaintByOrder(orderId);
        Assert.check(oldOc.getUserId() != userId, ErrorCode.ERR_MER_INVALID_USER);

        // 返回上一个状态，有可能是待付款或待收款
        MerchantOrderStatus lastStatus = MerchantOrderStatus.WAIT_PAY;
        if (order.getProofTime() != null) {
            // 已经上传凭证，说明是已经付款，待收款状态
            lastStatus = MerchantOrderStatus.PAYED;
        }
        userMerchantOrderMapper.updateOrderStatus(orderId, lastStatus);
        userMerchantOrderMapper.updateComplaintStatus(oldOc.getId(), OrderComplaint.STATUS_CANCEL);
    }

    /**
     * @param ocId          申诉id
     * @param completeOrder true表示完成申诉，将订单设置为交易完成状态，否则为取消状态，退还金额
     */
    @Transactional
    public void completeAppeal(int ocId, int orderId, boolean completeOrder) {
        OrderComplaint oc = userMerchantOrderMapper.lockOrderComplaint(ocId);
        Assert.check(oc.getStatus() != OrderComplaint.STATUS_PROCESS, ErrorCode.ERR_MER_STATUS_WRONG);
        Assert.check(oc.getOrderId() != orderId, ErrorCode.ERR_MER_NO_ORDER);
        if (completeOrder) {
            mgrConfirmReceived(oc.getOrderId());
        } else {
            mgrCancelOrder(oc.getOrderId(), "Appeal Cancel");
        }
        userMerchantOrderMapper.updateComplaintStatus(ocId, OrderComplaint.STATUS_COMPLETE);
    }

    public OrderComplaint getOrderComplaint(int ocId) {
        return userMerchantOrderMapper.getOrderComplaint(ocId);
    }

    public OrderComplaint getUserOrderComplaint(int userId, int orderId) {
        UserMerchantOrder order = userMerchantOrderMapper.getOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        if (userId != order.getUserId()) {
            Merchant merchant = merchantService.getMerchant(order.getMerchantId());
            Assert.check(merchant.getUserId() != userId, ErrorCode.ERR_MER_STATUS_WRONG);
        }
        return userMerchantOrderMapper.getOrderComplaintByOrder(orderId);
    }

    public OrderComplaint getOrderComplaintByOrderid(int orderId) {
        return userMerchantOrderMapper.getOrderComplaintByOrder(orderId);
    }

    public Page<OrderComplaint> getOrderComplaintList(Integer userId, int page, int size) {
        return userMerchantOrderMapper.getOrderComplaintList(userId, new RowBounds(page, size));
    }
}
