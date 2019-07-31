package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.ApplicationOrderStatus;
import com.cmd.exchange.common.enums.ApplicationType;
import com.cmd.exchange.common.enums.BankType;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.utils.NoUtil;
import com.cmd.exchange.common.utils.RandomUtil;
import com.cmd.exchange.common.vo.OtcMarketPriceVo;
import com.cmd.exchange.common.vo.OtcOrderDetailVO;
import com.cmd.exchange.common.vo.OtcOrderVO;
import com.cmd.exchange.common.vo.OtcVO;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理用户场外交易，币是站内的，不过买币的钱是站外的
 * OTC的全拼：Over The Counter
 */
@Service
public class OtcService {
    @Autowired
    OtcMapper otcMapper;
    @Autowired
    OtcOrderMapper otcOrderMapper;
    @Autowired
    OtcBillMapper otcBillMapper;
    @Autowired
    CoinConfigMapper coinConfigMapper;
    @Autowired
    CoinMapper coinMapper;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    OtcMarketMapper otcMarketMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserBankMapper userBankMapper;
    @Autowired
    OtcPayMapper otcPayMapper;
    @Autowired
    OtcStatMapper otcStatMapper;
    @Autowired
    TradeLogMapper tradeLogMapper;
    @Autowired
    ConfigService configService;

    private static final int APPEAL_CODE_LENGTH = 6;

    /**
     * @Desc:
     */
    public void cancelOrderAppeal(int userId, long orderId) {
        OtcOrder appealOrder = otcOrderMapper.getApplicationOrderById(orderId);
        Assert.check(appealOrder.getWithdrawalUserId() != userId && appealOrder.getDepositUserId() != userId, ErrorCode.ERR_C2C_ORDER_INVALID);
        Assert.check(appealOrder.getStatus() != ApplicationOrderStatus.COMPLAINT.getCode(), ErrorCode.ERR_C2C_ORDER_STATUS_ERROR);
        Assert.check(appealOrder.getAppealRole() != userId, ErrorCode.ERR_C2C_ORDER_MANAGER_DEAL);

        int result = otcOrderMapper.updateApplicationOrder(
                new OtcOrder().setId(orderId).setStatus(ApplicationOrderStatus.PAID.getCode()).setLastTime(new Date())
        );
        otcOrderMapper.setApplyRoleNull(appealOrder.getId());
        Assert.check(result != 1, ErrorCode.ERR_C2C_UPDATE_FAILURE);
    }

    /**
     * @Desc: 订单申诉
     */
    public String orderAppeal(int userId, long orderId, String reason) {
        OtcOrder appealOrder = otcOrderMapper.getApplicationOrderById(orderId);
        Assert.check(appealOrder.getWithdrawalUserId() != userId && appealOrder.getDepositUserId() != userId, ErrorCode.ERR_C2C_ORDER_INVALID);
        Assert.check(appealOrder.getStatus() != ApplicationOrderStatus.PAID.getCode(), ErrorCode.ERR_C2C_ORDER_STATUS_ERROR);

        String AppealCode = RandomUtil.getCode(APPEAL_CODE_LENGTH);
        int result = otcOrderMapper.updateApplicationOrder(
                new OtcOrder().setId(orderId).setAppealCode(AppealCode)
                        .setStatus(ApplicationOrderStatus.COMPLAINT.getCode())
                        .setAppealRole(userId).setAppealDesc(reason).setLastTime(new Date())
        );
        Assert.check(result != 1, ErrorCode.ERR_C2C_UPDATE_FAILURE);
        return AppealCode;
    }

    /**
     * @Desc: 获取c2c市场参考价（最新成交价）
     */
    public List<OtcMarketPriceVo> getMarketPrice() {
        BigDecimal platformCoinCnyPrice = configService.getPlatformCoinCnyPrice();
        List<OtcMarketPriceVo> rst = new ArrayList<>();
        List<OtcMarket> list = otcMarketMapper.getOtcMarket();
        for (OtcMarket otc : list) {
            BigDecimal latestPrice = tradeLogMapper.getMarketLatestPrice(otc.getCoinName(), "USDT");  //交易的log中获取USDT
            if (latestPrice != null) {
                latestPrice = latestPrice.multiply(platformCoinCnyPrice);
            } else {
                latestPrice = otc.getLastPrice();
            }
            if (otc.getCoinName().equalsIgnoreCase("USDT")) {
                latestPrice = platformCoinCnyPrice;
            }
            OtcMarketPriceVo otcMarketPriceVo = new OtcMarketPriceVo()
                    .setId(otc.getId()).setCoinName(otc.getCoinName()).setLastPrice(latestPrice);
            rst.add(otcMarketPriceVo);
        }
        return rst;
    }

    //获取OTC配置
    public List<OtcMarket> getOtcMarket() {
        return otcMarketMapper.getOtcMarket();
    }

    //挂买单(充值)
    @Transactional
    public void deposit(int userId, String coinName, String legalName, BigDecimal amount, BigDecimal price, BigDecimal minAmount, BigDecimal maxAmount) {
        Coin coin = coinMapper.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_NOT_SUPPORT_COIN);
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(coinName);
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        //Assert.check(amount.doubleValue()<otcMarket.getMinExchangeNum().doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_LOW);
        //Assert.check(amount.doubleValue()>otcMarket.getMaxExchangeNum().doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getIsPublishOtc().intValue() != 1, ErrorCode.ERR_PUBLISH_OTC);
        Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        int cnt = otcMapper.getApplicationBuyCount(userId, coinName);
        Assert.check(otcMarket.getMaxApplBuyCount().intValue() <= cnt, ErrorCode.ERR_C2C_MAX_APPLICATION_BUY_COUNT);

        Otc application = new Otc().setUserId(userId).setApplNo(NoUtil.getOrderNo())
                .setCoinName(coinName).setLegalName(legalName)
                .setAmount(amount).setAmountAll(amount)
                .setPrice(price).setAmountFrozen(BigDecimal.ZERO).setAmountSuccess(BigDecimal.ZERO)
                .setFeeRate(otcMarket.getFeeRate()).setFee(amount.multiply(otcMarket.getFeeRate()))
                .setStatus(ApplicationOrderStatus.MATCHING.getCode()).setType(ApplicationType.BUY.getType())
                .setMinAmount(minAmount).setMaxAmount(maxAmount)
                .setUserName(user.getUserName());

        otcMapper.add(application);
    }

    //挂卖单(提现)
    @Transactional
    public void withdral(int userId, String coinName, String legalName, BigDecimal amount, BigDecimal price, BigDecimal minAmount, BigDecimal maxAmount) {
        Coin coin = coinMapper.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_NOT_SUPPORT_COIN);
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(coinName);
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        //Assert.check(amount.doubleValue()<otcMarket.getMinExchangeNum().doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_LOW);
        //Assert.check(amount.doubleValue()>otcMarket.getMaxExchangeNum().doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getIsPublishOtc().intValue() != 1, ErrorCode.ERR_PUBLISH_OTC);
        Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        int cnt = otcMapper.getApplicationSellCount(userId, coinName);
        Assert.check(otcMarket.getMaxApplSellCount().intValue() <= cnt, ErrorCode.ERR_C2C_MAX_APPLICATION_SELL_COUNT);

        //检查自己是否有绑定银行卡
        List<UserBank> banks = userBankMapper.getUserBankByUserId(userId, null, 1);
        Assert.check(banks == null || banks.size() <= 0, ErrorCode.ERR_USER_NOT_BIND_BANK);

        BigDecimal fee = amount.multiply(otcMarket.getFeeRate());
        String applicationNo = NoUtil.getOrderNo();

        userCoinService.changeUserCoin(userId, coinName, amount.add(fee).negate(), amount.add(fee), UserBillReason.C2C_FREEZE, "C2C挂卖单冻结:" + applicationNo);

        Otc application = new Otc().setUserId(userId).setApplNo(applicationNo)
                .setCoinName(coinName).setLegalName(legalName)
                .setAmount(amount).setAmountAll(amount)
                .setPrice(price).setAmountFrozen(BigDecimal.ZERO).setAmountSuccess(BigDecimal.ZERO)
                .setFeeRate(otcMarket.getFeeRate()).setFee(amount.multiply(otcMarket.getFeeRate()))
                .setStatus(ApplicationOrderStatus.MATCHING.getCode()).setType(ApplicationType.SELL.getType())
                .setMinAmount(minAmount).setMaxAmount(maxAmount)
                .setUserName(user.getUserName());

        otcMapper.add(application);

    }

    //选择市场卖单充值购买
    @Transactional
    public Long depositOrder(int userId, Long withdralApplicationId, BigDecimal amount, String comment) {
        Otc withdralApplication = otcMapper.lockApplicationById(withdralApplicationId);
        Assert.check(userId == withdralApplication.getUserId().intValue(), ErrorCode.ERR_C2C_APPLICATION_OWNER);
        Assert.check(withdralApplication == null, ErrorCode.ERR_C2C_BUY_APPLICATIN_NOT_EXIST);
        Assert.check(withdralApplication.getStatus() != ApplicationOrderStatus.MATCHING.getCode(), ErrorCode.ERR_C2C_APPLICATION_STATUS_ERROR);
        Assert.check(withdralApplication.getAmount().doubleValue() < amount.doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);


        BigDecimal totalAmount = withdralApplication.getAmount().multiply(withdralApplication.getPrice());
        BigDecimal appAmount = amount.multiply(withdralApplication.getPrice());
        BigDecimal minAmount = withdralApplication.getMinAmount();
        BigDecimal maxAmount = withdralApplication.getMaxAmount();
        if (appAmount.compareTo(minAmount) < 0) {
            if (appAmount.compareTo(totalAmount) != 0
                    && minAmount.divide(withdralApplication.getPrice(), 6, RoundingMode.DOWN).compareTo(amount) > 0) {
                Assert.check(true, ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_LOW);
            }
        } else if (appAmount.compareTo(maxAmount) > 0) {
            Assert.check(true, ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);
        }

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        //最大买单数检查
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(withdralApplication.getCoinName());
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        int cnt = otcMapper.getApplicationBuyCount(userId, withdralApplication.getCoinName());
        Assert.check(otcMarket.getMaxApplBuyCount().intValue() <= cnt, ErrorCode.ERR_C2C_MAX_APPLICATION_BUY_COUNT);

        //生成购买者挂单申请
        Otc application = new Otc().setUserId(userId).setApplNo(NoUtil.getOrderNo())
                .setCoinName(withdralApplication.getCoinName()).setLegalName(withdralApplication.getLegalName())
                .setAmount(amount).setAmountAll(amount)
                .setPrice(withdralApplication.getPrice()).setAmountFrozen(BigDecimal.ZERO).setAmountSuccess(BigDecimal.ZERO)
                .setFeeRate(withdralApplication.getFeeRate()).setFee(amount.multiply(withdralApplication.getFeeRate()))
                .setStatus(ApplicationOrderStatus.MATCHING.getCode()).setType(ApplicationType.BUY.getType())
                .setUserName(user.getUserName()).setMinAmount(withdralApplication.getMinAmount()).setMaxAmount(withdralApplication.getMaxAmount());
        otcMapper.add(application);

        /*
         * amount小于卖单的数量
         * */
        //卖方扣除冻结,失败抛出异常
        int rst = otcMapper.frozenAmount(withdralApplicationId, amount);
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        //买方扣除冻结,失败抛出异常
        rst = otcMapper.frozenAmount(application.getId(), amount);
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        OtcOrder otcOrder = new OtcOrder();
        otcOrder.setDepositApplicationId(application.getId())
                .setDepositUserId(application.getUserId())
                .setDepositUserName(application.getUserName())
                .setWithdrawalApplicationId(withdralApplication.getId())
                .setWithdrawalUserId(withdralApplication.getUserId())
                .setWithdrawalUserName(withdralApplication.getUserName())
                .setOrderNo(NoUtil.getOrderNo())
                .setCoinName(application.getCoinName())
                .setLegalName(application.getLegalName())
                .setAmount(amount)
                .setFeeRate(withdralApplication.getFeeRate())
                .setPrice(withdralApplication.getPrice())
                .setStatus(ApplicationOrderStatus.ACCEPTED.getCode())
                .setDepositDesc(application.getComment())
                .setWithdrawalDesc(withdralApplication.getComment())
                .setAcceptTime(new Date())
                .setExpireTime(new Date(new Date().getTime() + otcMarket.getExpiredTimeCancel() * 60 * 1000))
                .setCreateId(userId);

        otcOrderMapper.add(otcOrder);
        return otcOrder.getId();

    }

    //选择市场买单提现卖出
    @Transactional
    public Long withdralOrder(int userId, Long depositApplicationId, BigDecimal amount, String comment) {
        Otc depositApplication = otcMapper.lockApplicationById(depositApplicationId);
        Assert.check(userId == depositApplication.getUserId().intValue(), ErrorCode.ERR_C2C_APPLICATION_OWNER);
        Assert.check(depositApplication == null, ErrorCode.ERR_C2C_BUY_APPLICATIN_NOT_EXIST);
        Assert.check(depositApplication.getStatus() != ApplicationOrderStatus.MATCHING.getCode(), ErrorCode.ERR_C2C_APPLICATION_STATUS_ERROR);
        Assert.check(depositApplication.getAmount().doubleValue() < amount.doubleValue(), ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);
        Assert.check(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_PARAM_ERROR);

        BigDecimal totalAmount = depositApplication.getAmount().multiply(depositApplication.getPrice());
        BigDecimal appAmount = amount.multiply(depositApplication.getPrice());
        BigDecimal minAmount = depositApplication.getMinAmount();
        BigDecimal maxAmount = depositApplication.getMaxAmount();
        if (appAmount.compareTo(minAmount) < 0) {
            if (appAmount.compareTo(totalAmount) != 0
                    && minAmount.divide(depositApplication.getPrice(), 6, RoundingMode.DOWN).compareTo(amount) > 0) {
                Assert.check(true, ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_LOW);
            }
        } else if (appAmount.compareTo(maxAmount) > 0) {
            Assert.check(true, ErrorCode.ERR_C2C_ORDER_AMOUNT_TOO_HIGH);
        }

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        //检查自己是否有绑定银行卡
        List<UserBank> banks = userBankMapper.getUserBankByUserId(userId, null, 1);
        Assert.check(banks == null || banks.size() <= 0, ErrorCode.ERR_USER_NOT_BIND_BANK);

        //最大买卖数检查
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(depositApplication.getCoinName());
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        int cnt = otcMapper.getApplicationBuyCount(userId, depositApplication.getCoinName());
        Assert.check(otcMarket.getMaxApplBuyCount().intValue() <= cnt, ErrorCode.ERR_C2C_MAX_APPLICATION_BUY_COUNT);

        //生成购买者挂单申请
        String applicationNo = NoUtil.getOrderNo();
        BigDecimal fee = amount.multiply(depositApplication.getFeeRate());
        Otc application = new Otc().setUserId(userId).setApplNo(applicationNo)
                .setCoinName(depositApplication.getCoinName()).setLegalName(depositApplication.getLegalName())
                .setAmount(amount).setAmountAll(amount)
                .setPrice(depositApplication.getPrice()).setAmountFrozen(BigDecimal.ZERO).setAmountSuccess(BigDecimal.ZERO)
                .setFeeRate(depositApplication.getFeeRate()).setFee(fee)
                .setStatus(ApplicationOrderStatus.MATCHING.getCode()).setType(ApplicationType.SELL.getType()).setComment(comment)
                .setUserName(user.getUserName()).setMinAmount(depositApplication.getMinAmount()).setMaxAmount(depositApplication.getMaxAmount());
        otcMapper.add(application);

        /*
         * amount小于买单的数量
         * */
        //买方扣除冻结,失败抛出异常
        int rst = otcMapper.frozenAmount(depositApplicationId, amount);
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        //卖方扣除冻结,失败抛出异常
        rst = otcMapper.frozenAmount(application.getId(), amount);
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);


        //冻结数额
        userCoinService.changeUserCoin(userId, application.getCoinName(), amount.add(fee).negate(), amount.add(fee), UserBillReason.C2C_FREEZE, "C2C挂卖单冻结:" + applicationNo);

        OtcOrder otcOrder = new OtcOrder();
        otcOrder.setDepositApplicationId(depositApplication.getId())
                .setDepositUserId(depositApplication.getUserId())
                .setDepositUserName(depositApplication.getUserName())
                .setWithdrawalApplicationId(application.getId())
                .setWithdrawalUserId(application.getUserId())
                .setWithdrawalUserName(application.getUserName())
                .setOrderNo(NoUtil.getOrderNo())
                .setCoinName(application.getCoinName())
                .setLegalName(application.getLegalName())
                .setAmount(amount)
                .setFeeRate(depositApplication.getFeeRate())
                .setPrice(depositApplication.getPrice())
                .setStatus(ApplicationOrderStatus.ACCEPTED.getCode())
                .setDepositDesc(depositApplication.getComment())
                .setWithdrawalDesc(application.getComment())
                .setAcceptTime(new Date())
                .setExpireTime(new Date(new Date().getTime() + otcMarket.getExpiredTimeCancel() * 60 * 1000))
                .setCreateId(userId);

        otcOrderMapper.add(otcOrder);
        return otcOrder.getId();
    }

    //上传支付凭证(买家)
    @Transactional
    public void uploadCredential(int userId, Long applicationOrderId, String credentialComment, String credentialUrls, Integer payId) {
        OtcOrder otcOrder = otcOrderMapper.lockApplicationOrderById(applicationOrderId);
        Assert.check(otcOrder == null, ErrorCode.ERR_C2C_ORDER_NOT_EXIST);
        Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.ACCEPTED.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        //检查是否是自己的订单
        Assert.check(userId != otcOrder.getDepositUserId(), ErrorCode.ERR_C2C_ORDER_INVALID);

        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(otcOrder.getCoinName());
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        //检查支付方式ID的是否是提现人的
        UserBank userBank = userBankMapper.getUserBankById(payId);
        Assert.check(userBank.getUserId().intValue() != otcOrder.getWithdrawalUserId(), ErrorCode.ERR_PARAM_ERROR);

        OtcOrder tmp = new OtcOrder().setId(otcOrder.getId())
                .setCredentialComment(credentialComment).setCredentialUrls(credentialUrls)
                .setUploadCredentialTime(new Date()).setStatus(ApplicationOrderStatus.PAID.getCode())
                .setExpireTime(new Date(new Date().getTime() + otcMarket.getExpiredTimeFreeze() * 60 * 1000))
                .setPayType(userBank.getBankType());
        otcOrderMapper.updateApplicationOrder(tmp);

        //支付方式记录
        OtcPay otcPay = new OtcPay().setUserId(userBank.getUserId())
                .setBankType(userBank.getBankType())
                .setBankName(userBank.getBankName())
                .setBankUser(userBank.getBankUser())
                .setBankNo(userBank.getBankNo())
                .setOrderId(otcOrder.getId());
        otcPayMapper.add(otcPay);
    }

    //确认订单
    @Transactional
    public void confirmOrder(int userId, Long applicationOrderId, String payPassword) {
        OtcOrder otcOrder = otcOrderMapper.getApplicationOrderById(applicationOrderId);
        Assert.check(otcOrder == null, ErrorCode.ERR_C2C_ORDER_NOT_EXIST);
        //检查是否是自己的订单
        Assert.check(userId != otcOrder.getWithdrawalUserId(), ErrorCode.ERR_C2C_ORDER_INVALID);
        Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.PAID.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);

        //检查密码
        User user = userMapper.getUserByUserId(userId);
        Assert.check(StringUtils.isBlank(user.getPayPassword()), ErrorCode.ERR_USER_PAY_PASSWORD_NOT_FOUND);
        Assert.check(!EncryptionUtil.check(payPassword, user.getPayPassword(), user.getPaySalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);

        this.dealOrder(userId, applicationOrderId, null);
    }

    //取消订单
    @Transactional
    public void cancelOrder(int userId, Long applicationOrderId, String comment) {
        OtcOrder otcOrder = otcOrderMapper.getApplicationOrderById(applicationOrderId);
        Assert.check(otcOrder == null, ErrorCode.ERR_C2C_ORDER_NOT_EXIST);

        //检查是否是自己的订单
        Assert.check(userId != otcOrder.getDepositUserId() && userId != otcOrder.getWithdrawalUserId(), ErrorCode.ERR_C2C_ORDER_INVALID);
        //只有代付款和已付款可以取消
        Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.ACCEPTED.getCode() &&
                otcOrder.getStatus() != ApplicationOrderStatus.PAID.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        //已经付款提现的人不能取消
        if (otcOrder.getStatus() == ApplicationOrderStatus.PAID.getCode() && userId == otcOrder.getWithdrawalUserId().intValue()) {
            Assert.check(true, ErrorCode.ERR_C2C_ORDER_INVALID);
        }

        this.cancelOrder(applicationOrderId, comment);
    }

    @Transactional
    public void dealOrder(Integer userId, Long orderId, String desc) {
        OtcOrder otcOrder = otcOrderMapper.lockApplicationOrderById(orderId);
        if (userId != null) {
            //用户自己
            Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.PAID.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        } else {
            //管理端
            Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.PAID.getCode()
                    && otcOrder.getStatus() != ApplicationOrderStatus.COMPLAINT.getCode() && otcOrder.getStatus() != ApplicationOrderStatus.FREEZE.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        }

        //对应的挂单处理
        int rst = otcMapper.increaseAmountSuccess(otcOrder.getWithdrawalApplicationId(), otcOrder.getAmount());
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        rst = otcMapper.increaseAmountSuccess(otcOrder.getDepositApplicationId(), otcOrder.getAmount());
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(otcOrder.getCoinName());
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        //卖方冻结扣除
        userCoinService.changeUserCoin(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName(),
                BigDecimal.ZERO, otcOrder.getAmount().add(otcOrder.getAmount().multiply(otcOrder.getFeeRate())).negate(),
                UserBillReason.UNFREEZE, "C2C订单卖出成功冻结扣除:" + otcOrder.getId());

        //买方收入新增
        userCoinService.changeUserCoin(otcOrder.getDepositUserId(), otcOrder.getCoinName(),
                otcOrder.getAmount(), BigDecimal.ZERO, UserBillReason.C2C_SUCCESS, "C2C订单买入成功新增:" + otcOrder.getId());

        //更新订单状态
        otcOrderMapper.updateApplicationOrder(
                new OtcOrder().setId(otcOrder.getId())
                        .setStatus(ApplicationOrderStatus.DONE.getCode())
                        .setFinishTime(new Date()).setRemark(desc)
        );

        //更新对应货币最新价格
        otcMarketMapper.mod(new OtcMarket().setId(otcMarket.getId()).setLastPrice(otcOrder.getPrice()));

        //添加记录
        OtcBill depositBill = new OtcBill()
                .setUserId(otcOrder.getDepositUserId())
                .setType(ApplicationType.BUY.getType())
                .setCoinName(otcOrder.getCoinName())
                .setAmount(otcOrder.getAmount())
                .setOrderNo(otcOrder.getOrderNo());
        otcBillMapper.add(depositBill);
        OtcBill withdrawBill = new OtcBill()
                .setUserId(otcOrder.getWithdrawalUserId())
                .setType(ApplicationType.SELL.getType())
                .setCoinName(otcOrder.getCoinName())
                .setAmount(otcOrder.getAmount())
                .setOrderNo(otcOrder.getOrderNo());
        otcBillMapper.add(withdrawBill);

        //更新信用数据
        int tradePass = otcOrderMapper.getOtcOrderTradePass(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName());
        int tradeFail = otcOrderMapper.getOtcOrderTradeFail(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName());
        BigDecimal amountPass = otcOrderMapper.getOtcOrderAmountPass(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName());
        BigDecimal amountFail = otcOrderMapper.getOtcOrderAmountFail(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName());
        if (amountPass == null)
            amountPass = BigDecimal.ZERO;
        if (amountFail == null)
            amountFail = BigDecimal.ZERO;

        OtcStat otcStat = otcStatMapper.getOtcStat(otcOrder.getWithdrawalUserId(), otcOrder.getCoinName());
        if (otcStat == null) {
            otcStatMapper.add(new OtcStat().setUserId(otcOrder.getWithdrawalUserId()).setCoinName(otcOrder.getCoinName()).setLegalName(otcOrder.getLegalName())
                    .setTradePass(tradePass).setTradeFail(tradeFail).setAmountPass(amountPass).setAmountFail(amountFail));
        } else {
            otcStatMapper.mod(new OtcStat().setId(otcStat.getId()).setTradePass(tradePass).setTradeFail(tradeFail).setAmountPass(amountPass).setAmountFail(amountFail));
        }
    }

    @Transactional
    public void cancelOrder(Long orderId, String comment) {
        OtcOrder otcOrder = otcOrderMapper.lockApplicationOrderById(orderId);
        //只有接单或者申诉中才可以取消，申诉中的订单是管理端取消
        Assert.check(otcOrder.getStatus() != ApplicationOrderStatus.ACCEPTED.getCode() &&
                otcOrder.getStatus() != ApplicationOrderStatus.COMPLAINT.getCode() &&
                otcOrder.getStatus() != ApplicationOrderStatus.PAID.getCode() &&
                otcOrder.getStatus() != ApplicationOrderStatus.FREEZE.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);

        Otc depositApplication = otcMapper.getApplicationById(otcOrder.getDepositApplicationId());
        Otc withdrawApplication = otcMapper.getApplicationById(otcOrder.getWithdrawalApplicationId());
        Assert.check(depositApplication == null || withdrawApplication == null, ErrorCode.ERR_C2C_ORDER_INVALID);

        //挂卖单已经取消了就恢复余额
        if (withdrawApplication.getStatus() == ApplicationOrderStatus.CANCELED.getCode()
                || withdrawApplication.getStatus() == ApplicationOrderStatus.DONE.getCode()) {
            userCoinService.changeUserCoin(withdrawApplication.getUserId(), withdrawApplication.getCoinName(),
                    otcOrder.getAmount().add(otcOrder.getAmount().multiply(otcOrder.getFeeRate())),
                    otcOrder.getAmount().add(otcOrder.getAmount().multiply(otcOrder.getFeeRate())).negate(),
                    UserBillReason.C2C_UNFREEZE, "C2C订单取消恢复余额:" + otcOrder.getId());
        }

        //对应的挂单处理
        int rst = otcMapper.unFrozenAmount(otcOrder.getWithdrawalApplicationId(), otcOrder.getAmount());
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        rst = otcMapper.unFrozenAmount(otcOrder.getDepositApplicationId(), otcOrder.getAmount());
        Assert.check(rst <= 0, ErrorCode.ERR_PARAM_ERROR);

        //更新订单状态
        otcOrderMapper.updateApplicationOrder(
                new OtcOrder().setId(otcOrder.getId())
                        .setStatus(ApplicationOrderStatus.CANCELED.getCode())
                        .setCancelTime(new Date()).setCancelDesc(comment)
        );

        //市场选择者处理
        if (otcOrder.getCreateId().intValue() == depositApplication.getUserId().intValue()) {
            //更新对应的挂单取消
            if (depositApplication.getStatus() == ApplicationOrderStatus.MATCHING.getCode()) {
                otcMapper.updateApplicationStatus(depositApplication.getId(), ApplicationOrderStatus.CANCELED.getCode());
            }
        }
        if (otcOrder.getCreateId().intValue() == withdrawApplication.getUserId().intValue()) {
            //恢复余额
            if (withdrawApplication.getStatus() != ApplicationOrderStatus.CANCELED.getCode()
                    && withdrawApplication.getStatus() != ApplicationOrderStatus.DONE.getCode()) {
                userCoinService.changeUserCoin(withdrawApplication.getUserId(), withdrawApplication.getCoinName(),
                        otcOrder.getAmount().add(otcOrder.getAmount().multiply(otcOrder.getFeeRate())),
                        otcOrder.getAmount().add(otcOrder.getAmount().multiply(otcOrder.getFeeRate())).negate(),
                        UserBillReason.C2C_UNFREEZE, "C2C订单取消恢复余额:" + otcOrder.getId());
                otcMapper.updateApplicationStatus(withdrawApplication.getId(), ApplicationOrderStatus.CANCELED.getCode());
            }
        }
    }

    //冻结订单
    @Transactional
    public void frozenOrder(OtcOrder otcOrder, String comment) {
        OtcOrder otc = otcOrderMapper.lockApplicationOrderById(otcOrder.getId());
        Assert.check(otc.getStatus() != ApplicationOrderStatus.PAID.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        otcOrderMapper.updateApplicationOrder(
                new OtcOrder().setId(otc.getId()).setStatus(ApplicationOrderStatus.FREEZE.getCode()).setRemark(comment)
        );
    }

    //取消挂单
    @Transactional
    public void cancelApplication(int userId, Long applicationId) {
        Otc application = otcMapper.lockApplicationById(applicationId);
        Assert.check(application == null, ErrorCode.ERR_C2C_APPLICATION_INVALID);
        Assert.check(application.getUserId() != userId, ErrorCode.ERR_C2C_APPLICATION_INVALID);
        Assert.check(application.getStatus() != ApplicationOrderStatus.MATCHING.getCode(), ErrorCode.ERR_C2C_APPLICATION_INVALID);

        //卖单取消，要恢复余额定义
        if (application.getType() == ApplicationType.SELL.getType()) {
            userCoinService.changeUserCoin(userId, application.getCoinName(),
                    application.getAmount().add(application.getAmount().multiply(application.getFeeRate())),
                    application.getAmount().add(application.getAmount().multiply(application.getFeeRate())).negate(),
                    UserBillReason.C2C_UNFREEZE, "C2C挂单取消:" + application.getId());

            otcMapper.updateApplicationStatus(applicationId, ApplicationOrderStatus.CANCELED.getCode());
        } else {
            otcMapper.updateApplicationStatus(applicationId, ApplicationOrderStatus.CANCELED.getCode());
        }
    }

    //获取挂单
    public Page<OtcVO> getApplicationList(String coinName, Integer type, Integer[] status, Integer pageNo, Integer pageSize) {
        Page<OtcVO> rst = new Page<>();
        Page<Otc> arrRst = otcMapper.getApplication(coinName, type, status, new RowBounds(pageNo, pageSize));
        rst.setPageNum(arrRst.getPageNum());
        rst.setPageSize(arrRst.getPageSize());
        rst.setTotal(arrRst.getTotal());
        for (Otc otc : arrRst) {
            OtcVO vo = new OtcVO().setIsAlipay(false).setIsBank(false).setIsWeixin(false);
            BeanUtils.copyProperties(otc, vo);

            //这里不合理，影响性能
            //OtcStat otcStat = otcStatMapper.getOtcStat(otc.getUserId(), otc.getCoinName());
            OtcStat otcStat = otcStatMapper.statisticsTradeByUserId(otc.getUserId(), otc.getCoinName());
            if (otcStat != null) {
                vo.setTradePass(otcStat.getTradePass()).setTradeFail(otcStat.getTradeFail())
                        .setAmountPass(otcStat.getAmountPass()).setAmountFail(otcStat.getAmountFail());
            }

            //获取银行卡支付宝微信
            List<UserBank> ll = userBankMapper.getUserBankByUserId(otc.getUserId(), null, 1);
            for (UserBank bank : ll) {
                if (bank.getBankType() == BankType.BANK.getValue() && bank.getStatus() == 1)
                    vo.setIsBank(true);
                if (bank.getBankType() == BankType.ALIPAY.getValue() && bank.getStatus() == 1)
                    vo.setIsAlipay(true);
                if (bank.getBankType() == BankType.WEIXIN.getValue() && bank.getStatus() == 1)
                    vo.setIsWeixin(true);
            }
            rst.add(vo);
        }
        return rst;
    }

    //获取我的挂单
    public Page<OtcVO> getMyApplicationList(Integer userId, String coinName, Integer type, Integer[] status, Integer pageNo, Integer pageSize) {
        Page<OtcVO> rst = new Page<>();
        Page<Otc> arrRst = otcMapper.getApplicationByUserId(userId, coinName, type, status, new RowBounds(pageNo, pageSize));
        rst.setPageNum(arrRst.getPageNum());
        rst.setPageSize(arrRst.getPageSize());
        rst.setTotal(arrRst.getTotal());
        for (Otc otc : arrRst) {
            OtcVO vo = new OtcVO().setIsAlipay(false).setIsBank(false).setIsWeixin(false);
            BeanUtils.copyProperties(otc, vo);
            //获取银行卡支付宝微信
            List<UserBank> ll = userBankMapper.getUserBankByUserId(otc.getUserId(), null, 1);
            for (UserBank bank : ll) {
                if (bank.getBankType() == BankType.BANK.getValue() && bank.getStatus() == 1)
                    vo.setIsBank(true);
                if (bank.getBankType() == BankType.ALIPAY.getValue() && bank.getStatus() == 1)
                    vo.setIsAlipay(true);
                if (bank.getBankType() == BankType.WEIXIN.getValue() && bank.getStatus() == 1)
                    vo.setIsWeixin(true);
            }
            rst.add(vo);
        }
        return rst;
    }

    //获取订单
    public Page<OtcOrder> getApplicationOrderList(String coinName, Integer[] status, Integer pageNo, Integer pageSize) {
        return otcOrderMapper.getApplicationOrder(coinName, status, new RowBounds(pageNo, pageSize));
    }

    //获取我的订单(多了一个type:1买，2卖)
    public Page<OtcOrderVO> getMyApplicationOrderList(Integer userId, String coinName, Integer type, Integer[] status, Integer pageNo, Integer pageSize) {
        return otcOrderMapper.getApplicationOrderByUserId(userId, coinName, type, status, new RowBounds(pageNo, pageSize));
        //otcOrderMapper.getApplicationOrderByUserId(userId, coinName, type, status, new RowBounds(pageNo, pageSize));
    }


    //获取超时订单
    public List<OtcOrder> getApplicationOrderExpire(String coinName, Integer status) {
        Assert.check(status != ApplicationOrderStatus.PAID.getCode() && status != ApplicationOrderStatus.ACCEPTED.getCode(), ErrorCode.ERR_C2C_ORDER_INVALID);
        return otcOrderMapper.getApplicationOrderExpire(coinName, status, new Date());
    }

    //获取挂单信息
    public Otc getApplicationInfo(Long id) {
        return otcMapper.getApplicationById(id);
    }

    //获取订单信息
    public OtcOrderVO getOtcOrderInfo(Long id, Integer userId) {
        OtcOrder otc = otcOrderMapper.getApplicationOrderById(id);
        List<UserBank> list = userBankMapper.getUserBankByUserId(otc.getWithdrawalUserId(), null, 1);
        OtcOrderVO vo = new OtcOrderVO();
        BeanUtils.copyProperties(otc, vo);

        vo.setList(list);
        return vo;
    }

    //获取单信息原始数据
    public OtcOrder getOtcOrderInfo2(Long id) {
        return otcOrderMapper.getApplicationOrderById(id);
    }

    //获取订单详细数据
    public OtcOrderDetailVO getOtcOrderDetailVO(Long id) {
        OtcOrder vo = this.getOtcOrderInfo2(id);
        Assert.check(vo == null, ErrorCode.ERR_PARAM_ERROR);

        User buyUser = userMapper.getUserByUserId(vo.getDepositUserId());
        User sellUser = userMapper.getUserByUserId(vo.getWithdrawalUserId());
        Assert.check(buyUser == null || sellUser == null, ErrorCode.ERR_C2C_ORDER_INVALID);

        return new OtcOrderDetailVO().setBuyEmail(buyUser.getEmail()).setBuyIdCard(buyUser.getIdCard())
                .setBuyMobile(buyUser.getMobile()).setBuyName(buyUser.getUserName()).setBuyRealName(buyUser.getRealName())
                .setId(vo.getId()).setAppeal(vo.getAppealRole()).setAppealDesc(vo.getAppealDesc()).setOrderNo(vo.getOrderNo())
                .setSellEmail(sellUser.getEmail()).setSellIdCard(sellUser.getIdCard()).setSellMobile(sellUser.getMobile())
                .setSellName(sellUser.getUserName()).setSellRealName(sellUser.getRealName()).setStatus(vo.getStatus()).setCredentialUrls(vo.getCredentialUrls());
    }
}
