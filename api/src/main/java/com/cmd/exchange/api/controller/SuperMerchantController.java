package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.enums.ValueEnum;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.MerchantVo;
import com.cmd.exchange.common.vo.OrderDetailVo;
import com.cmd.exchange.common.vo.VoucherVo;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.MerchantService;
import com.cmd.exchange.service.SuperMerchantOrderService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Api(tags = "超级兑换商相关接口，主要用于使用真实货币兑换数字货币，兑换商跟超级兑换商之间进行")
@RestController
@RequestMapping("/super-merchant")
public class SuperMerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private SuperMerchantOrderService superMerchantOrderService;
    @Autowired
    private ConfigService configService;

    @ApiOperation(value = "超级兑换商列表")
    @GetMapping("/list")
    public CommonListResponse<MerchantVo> merchantList(
            @ApiParam(name = "amount", value = "要兑换的数量", required = true) @RequestParam BigDecimal amount,
            @ApiParam(name = "coinName", value = "购买或卖出的币种名称)", required = true) @RequestParam String coinName,
            @ApiParam(name = "settlementCurrency", value = "结算币种名称(CNY,USD等)", required = true) @RequestParam String settlementCurrency,
            @ApiParam(name = "type", value = "买卖类型(BUY,SELL)", required = true) @RequestParam MerchantOrderType type,
            @ApiParam(name = "payType", value = "支付方式(1银行卡2支付宝0全部)", required = false) @RequestParam Integer payType,
            @ApiParam(name = "merchantName", value = "云商姓名(模糊匹配)", required = false) @RequestParam(required = false) String merchantName,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Assert.check(size > 100, ErrorCode.ERR_PARAM_ERROR);
        Page<MerchantVo> ls = merchantService.getMerchantListForMerchant(amount, coinName, settlementCurrency, type, payType, merchantName, page, size);
        return CommonListResponse.fromPage(ls);
    }

    @ApiOperation(value = "普通兑换商下单进行充值或买入币种/充值")
    @PostMapping("buy-coin")
    public CommonResponse<SuperMerchantOrder> buyCoin(@ApiParam(name = "coinName", value = "购买或卖出的币种名称(CNY,USD等)", required = true) @RequestParam String coinName,
                                                      @ApiParam(name = "settlementCurrency", value = "结算币种名称(CNY,USD等)", required = true) @RequestParam String settlementCurrency,
                                                      @ApiParam(value = "买入的虚拟币个数", required = true) @RequestParam BigDecimal amount,
                                                      @ApiParam(value = "超级兑换商id", required = true) @RequestParam Integer merchantId,
                                                      @ApiParam(value = "备注", required = true) @RequestParam String remake) {
        BigDecimal price = merchantService.getCoinPrice(coinName, settlementCurrency);
        SuperMerchantOrder order = superMerchantOrderService.buyCoin(ShiroUtils.getUser().getId(), coinName, settlementCurrency, price, amount, merchantId, remake);
        return new CommonResponse<SuperMerchantOrder>(order);
    }

    @ApiOperation(value = "普通用户下单进行提现或卖出币种/提现")
    @PostMapping("sell-coin")
    public CommonResponse<SuperMerchantOrder> sellCoin(@ApiParam(name = "coinName", value = "购买或卖出的币种名称(CNY,USD等)", required = true) @RequestParam String coinName,
                                                       @ApiParam(name = "settlementCurrency", value = "结算币种名称(CNY,USD等)", required = true) @RequestParam String settlementCurrency,
                                                       @ApiParam(required = true, name = "userBankId", value = "提现地址的ID") @RequestParam int userBankId,
                                                       @ApiParam(value = "卖出的虚拟币个数", required = true) @RequestParam BigDecimal amount,
                                                       @ApiParam(value = "超级兑换商id", required = true) @RequestParam Integer merchantId,
                                                       @ApiParam(value = "交易密码", required = true) @RequestParam String payPassword,
                                                       @ApiParam(value = "备注", required = true) @RequestParam String remake) {
        // 检测交易密码
        User user = ShiroUtils.getUser();
        Assert.check(StringUtils.isBlank(user.getPayPassword()), ErrorCode.ERR_USER_PAY_PASSWORD_NOT_FOUND);
        Assert.check(!EncryptionUtil.check(payPassword, user.getPayPassword(), user.getPaySalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);
        BigDecimal price = merchantService.getCoinPrice(coinName, settlementCurrency);
        SuperMerchantOrder order = superMerchantOrderService.sellCoin(ShiroUtils.getUser().getId(), coinName, settlementCurrency, price, userBankId, amount, merchantId, remake);
        return new CommonResponse<SuperMerchantOrder>(order);
    }

    @ApiOperation(value = "超级兑换商供给订单列表，返回的是普通兑换商向当前用户（超级兑换商）发起的订单")
    @GetMapping("to-me-orders")
    public CommonListResponse<SuperMerchantOrder> toMeOrders(
            //@ApiParam(value = "币种名称", required = true) @RequestParam String coinName,
            @ApiParam(value = "状态类型(0,全部;1已完成;2待接单,3待付款,4待收款,100已取消)", required = true) @RequestParam Integer status,
            @ApiParam(value = "类型(0全部1充值2提现)", required = true) @RequestParam Integer oprStyle,
            @ApiParam(value = "订单号", required = false) @RequestParam(required = false) String orderNo,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) int page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) int size) {
        Assert.check(size >= 100, ErrorCode.ERR_PAGE_SIZE_TOO_LARGE);
        MerchantOrderStatus enumStatus = null;
        if (status != null && status != 0) {
            enumStatus = (MerchantOrderStatus) ValueEnum.valueOfEnum(MerchantOrderStatus.values(), status);
        }
        MerchantOrderType enumType = null;
        if (oprStyle != null && oprStyle != 0) {
            enumType = (MerchantOrderType) ValueEnum.valueOfEnum(MerchantOrderType.values(), oprStyle);
        }
        Merchant merchant = merchantService.getMerchantByUserId(ShiroUtils.getUser().getId());
        if (merchant == null) {
            return new CommonListResponse();
        }
        Page<SuperMerchantOrder> orders = superMerchantOrderService.getMerchantOrders(merchant.getId(), enumStatus, enumType, orderNo, page, size);
        for (SuperMerchantOrder order : orders) {
            // 显示兑换商的名称
            Merchant merchant1 = merchantService.getMerchant(order.getMerchantId());
            if (merchant1 != null) {
                order.setMerchantName(merchant1.getName());
            }
        }
        return CommonListResponse.fromPage(orders);
    }


    @ApiOperation(value = "我的申请订单列表，返回的是当前用户向兑换商发起的订单")
    @GetMapping("apply-order-list")
    public CommonResponse<SuperMerchantOrder> applyOrderList(
            @ApiParam(value = "状态类型(0,全部;1已完成;2待接单,3待付款,4待收款,100已取消)", required = true) @RequestParam Integer status,
            @ApiParam(value = "类型(1充值2提现)", required = true) @RequestParam Integer oprStyle,
            @ApiParam(value = "订单号", required = false) @RequestParam(required = false) String orderNo,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Assert.check(size >= 100, ErrorCode.ERR_PAGE_SIZE_TOO_LARGE);
        MerchantOrderStatus enumStatus = null;
        if (status != null && status != 0) {
            enumStatus = (MerchantOrderStatus) ValueEnum.valueOfEnum(MerchantOrderStatus.values(), status);
        }
        MerchantOrderType enumType = null;
        if (oprStyle != null && oprStyle != 0) {
            enumType = (MerchantOrderType) ValueEnum.valueOfEnum(MerchantOrderType.values(), oprStyle);
        }
        Page<SuperMerchantOrder> orders = superMerchantOrderService.getUserOrders(ShiroUtils.getUser().getId(), enumStatus, enumType, orderNo, page, size);
        for (SuperMerchantOrder order : orders) {
            // 显示兑换商的名称
            Merchant merchant1 = merchantService.getMerchant(order.getSuperMerchantId());
            if (merchant1 != null) {
                order.setSuperMerchantName(merchant1.getName());
            }
        }
        return CommonListResponse.fromPage(orders);
    }

    @ApiOperation(value = "兑换商拒绝接单")
    @PostMapping("refuse-order")
    public CommonResponse refuseOrder(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId,
                                      @ApiParam(value = "取消备注信息", required = true) @RequestParam String comment) {
        superMerchantOrderService.superMerchantRefuseOrder(ShiroUtils.getUser().getId(), orderId, comment);
        return new CommonResponse();
    }

    @ApiOperation(value = "普通兑换商取消接单")
    @PostMapping("cancel-order")
    public CommonResponse cancelOrder(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId,
                                      @ApiParam(value = "取消备注信息", required = true) @RequestParam String comment) {
        superMerchantOrderService.merchantCancelOrder(ShiroUtils.getUser().getId(), orderId, comment);
        return new CommonResponse();
    }

    @ApiOperation(value = "超级兑换商确认接单")
    @PostMapping("accept-order")
    public CommonResponse merchantAcceptOrder(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        superMerchantOrderService.merchantAcceptOrder(ShiroUtils.getUser().getId(), orderId);
        return new CommonResponse();
    }

    @ApiOperation(value = "上传凭证，普通用户和兑换商都会使用该接口")
    @PostMapping("upload-voucher")
    public CommonResponse uploadVoucher(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId,
                                        @ApiParam(value = "凭证内容", required = true) @RequestParam String credentialComment,
                                        @ApiParam(value = "凭证图片链接", required = true) @RequestParam String credentialUrls) {
        superMerchantOrderService.uploadVoucher(ShiroUtils.getUser().getId(), orderId, credentialComment, credentialUrls);
        return new CommonResponse();
    }

    @ApiOperation(value = "查看凭证")
    @GetMapping("look-voucher")
    public CommonResponse<VoucherVo> lookVoucher(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        SuperMerchantOrder order = superMerchantOrderService.getOrder(orderId);
        int userId = ShiroUtils.getUser().getId();
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        Assert.check(merchant == null, ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(merchant.getId() != order.getMerchantId() && merchant.getId() != order.getSuperMerchantId(), ErrorCode.ERR_MER_NO_ORDER);
        Assert.check(order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT, ErrorCode.ERR_MER_STATUS_WRONG);
        // 获取兑换商
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        VoucherVo vo = new VoucherVo();
        vo.setMerchantName(superMerchant.getName());
        vo.setContent(order.getCredentialComment());
        vo.setTime(order.getProofTime());
        vo.setUrl(order.getCredentialUrls());
        vo.setUserName(ShiroUtils.getUser().getRealName());
        return new CommonResponse(vo);
    }

    @ApiOperation(value = "兑换商确认收到充值的款，只有该订单的兑换商能调用该接口")
    @PostMapping("merchant-confirm-received")
    public CommonResponse merchantConfirmReceived(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        superMerchantOrderService.superMerchantConfirmReceived(ShiroUtils.getUser().getId(), orderId);
        return new CommonResponse();
    }

    @ApiOperation(value = "用户确认收到提现的款，只有改订单的发起用户能调用该接口")
    @PostMapping("user-confirm-received")
    public CommonResponse userConfirmReceived(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        superMerchantOrderService.userConfirmReceived(ShiroUtils.getUser().getId(), orderId);
        return new CommonResponse();
    }

    /*@ApiOperation(value = "申诉")
    @PostMapping("appeal")
    public  CommonResponse appeal(@ApiParam(value = "订单id", required = true)  @RequestParam Integer orderId,
                                  @ApiParam(value = "申诉原因", required = true)  String credentialComment,
                                  @ApiParam(value = "凭证URL", required = true)  String credentialUrls) {
        superMerchantOrderService.appeal(ShiroUtils.getUser().getId(), orderId, credentialComment, credentialUrls);
        return new CommonResponse();
    }*/

    @ApiOperation(value = "订单详情")
    @GetMapping("order-detail")
    public CommonResponse<OrderDetailVo> orderDetail(@ApiParam(value = "订单id", required = true) @RequestParam int orderId) {
        SuperMerchantOrder order = superMerchantOrderService.getOrder(orderId);
        Assert.check(order == null, ErrorCode.ERR_MER_NO_ORDER);
        int userId = ShiroUtils.getUser().getId();
        Merchant merchant = merchantService.getMerchantByUserId(userId);
        Assert.check(order.getMerchantId() != merchant.getId() && order.getSuperMerchantId() != merchant.getId(), ErrorCode.ERR_MER_NO_ORDER);
        OrderDetailVo vo = new OrderDetailVo();
        BeanUtils.copyProperties(order, vo);
        Merchant superMerchant = merchantService.getMerchant(order.getSuperMerchantId());
        vo.setMerchantName(superMerchant.getName());
        merchant = merchantService.getMerchant(order.getMerchantId());
        vo.setUserName(merchant.getName());
        if (order.getStatus() == MerchantOrderStatus.WAIT_ACCEPT) {
            int timeout = configService.getConfigValue(ConfigKey.MERCHANT_ORDER_TIMEOUT, 3600 * 24);  // 默认一天
            vo.setTimeoutSecond(timeout);
            vo.setTimeoutTimestamp(vo.getAddTime() + timeout);
        } else if (order.getStatus() == MerchantOrderStatus.WAIT_PAY && vo.getAcceptTime() != null) {
            int timeout = configService.getConfigValue(ConfigKey.MERCHANT_ORDER_TIMEOUT, 3600 * 24);  // 默认一天
            vo.setTimeoutSecond(timeout);
            vo.setTimeoutTimestamp(vo.getAcceptTime() + timeout);
        }
        if (order.getAcceptTime() != null && order.getAcceptTime() != 0) {
            vo.setMerchantPhone(merchant.getPhone());
            vo.setSuperMerchantPhone(superMerchant.getPhone());
        }
        return new CommonResponse(vo);
    }

    @ApiOperation(value = "我的云商账户")
    @GetMapping("merchant-account")
    public CommonResponse<Merchant> merchantAccount() {
        Merchant merchant = merchantService.getUserMerchant(ShiroUtils.getUser().getId());
        return new CommonResponse(merchant);
    }

    @ApiOperation(value = "云商账户明细")
    @GetMapping("merchant-bill")
    public CommonListResponse<MerchantBill> merchantBill(
            @ApiParam(value = "币种名称", required = false) @RequestParam(required = false) String coinName,
            @ApiParam(value = "类型", required = false) @RequestParam(required = false) String type,
            @ApiParam(value = "起始时间(unix时间戳)", required = false) @RequestParam(required = false) Integer startTime,
            @ApiParam(value = "结束时间(unix时间戳)", required = false) @RequestParam(required = false) Integer endTime,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Assert.check(size >= 100, ErrorCode.ERR_PAGE_SIZE_TOO_LARGE);
        Merchant merchant = merchantService.getUserMerchant(ShiroUtils.getUser().getId());
        if (merchant == null) return new CommonListResponse();
        Page<MerchantBill> ls = merchantService.getMerchantBill(merchant.getId(), coinName, type, startTime, endTime, page, size);
        return CommonListResponse.fromPage(ls);
    }

    /*@ApiOperation(value = "修改云商信息，修改的地方输入，没提供表示不修改")
    @PostMapping("update-merchant")
    public  CommonResponse updateMerchant( @ApiParam(value = "要修改的云商信息", required = true) @RequestBody(required = true) Merchant merchant) {
        merchantService.userUpdateMerchant(ShiroUtils.getUser().getId(), merchant);
        return new CommonResponse();
    }*/

    @ApiOperation(value = "修改兑换商工作状态")
    @PostMapping("update-merchant-work-status")
    public CommonResponse updateMerchant(@ApiParam(value = "新的工作状态，1:正常工作,0：不接单", required = true) @RequestParam(required = true) Integer status) {
        Merchant merchant = new Merchant();
        merchant.setWorkingStatus(status);
        merchantService.userUpdateMerchant(ShiroUtils.getUser().getId(), merchant);
        return new CommonResponse();
    }
}

