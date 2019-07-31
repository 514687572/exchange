package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.MerchantService;
import com.cmd.exchange.service.UserMerchantOrderService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "兑换商接口")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserMerchantOrderService userMerchantOrderService;


    @ApiOperation(value = "支持兑换的币种列表")
    @GetMapping("/coins")
    public CommonResponse<List<MerchantCoinConf>> merchantCoinList() {
        List<MerchantCoinConf> coins = merchantService.getAllMerchantCoinConf();
        return new CommonResponse(coins);
    }

    @ApiOperation("增加支持兑换的币种")
    @PostMapping("/add-coin")
    public CommonResponse addMerchantCoinConf(@RequestBody(required = true) MerchantCoinConf conf) {
        merchantService.addMerchantCoinConf(conf);
        return new CommonResponse();
    }

    @ApiOperation("修改支持兑换的币种")
    @PutMapping("update-coin")
    public CommonResponse updateMerchantCoinConf(@RequestBody(required = true) MerchantCoinConf conf) {
        merchantService.updateMerchantCoinConf(conf);
        return new CommonResponse();
    }

    @ApiOperation("删除支持兑换的币种")
    @PostMapping("/del-coin")
    public CommonResponse delMerchantCoinConf(
            @ApiParam(value = "ID", required = true) @RequestParam(name = "id", required = true) int id) {
        merchantService.delMerchantCoinConf(id);
        return new CommonResponse();
    }

    @ApiOperation("获取支持兑换的币种")
    @GetMapping("/get-coin")
    public CommonResponse getMerchantCoinConf(
            @ApiParam(value = "ID", required = true) @RequestParam(name = "id", required = true) int id) {
        MerchantCoinConf merchantCoinConf = merchantService.getMerchantCoinConf(id);
        return new CommonResponse(merchantCoinConf);
    }

    @ApiOperation("搜索兑换商，如果是null，那么忽略该条件")
    @GetMapping("/list")
    public CommonListResponse<Merchant> searchMerchant(
            @ApiParam(name = "userId", value = "用户ID") @RequestParam(value = "userId", required = false) Integer userId,
            @ApiParam(name = "type", value = "类型:1:普通兑换商2:超级兑换商") @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "merchantName", value = "兑换商名称") @RequestParam(value = "merchantName", required = false) String merchantName,
            @ApiParam(name = "status", value = "状态，0：正常，1：已经禁用，2：申请中") @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(value = "pageNo", required = true) Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        Page<Merchant> ls = merchantService.searchMerchant(type, userId, merchantName, status, pageNo, pageSize);
        return CommonListResponse.fromPage(ls);
    }

    @ApiOperation("获取兑换商资产明细")
    @GetMapping("bill")
    public CommonResponse<MerchantBill> merchantBill(
            @ApiParam(value = "页数", required = true) @RequestParam(name = "pageNo", required =
                    true) Integer pageNo,
            @ApiParam(value = "每页显示条数", required = true) @RequestParam(name = "pageSize", required = true) Integer pageSize,
            @ApiParam(value = "兑换商id", required = true) @RequestParam(name = "merchantId", required = true) Integer merchantId,
            @ApiParam(value = "coinName", required = false) @RequestParam(name = "coinName", required = false) String coinName
    ) {
        Page<MerchantBill> page = merchantService.getMerchantBill(merchantId, coinName, null, null, null, pageNo, pageSize);
        return CommonListResponse.fromPage(page);
    }

    @ApiOperation("获取兑换商的详细信息")
    @GetMapping("merchant-detail")
    public CommonResponse merchantDetail(
            @ApiParam(name = "id", value = "兑换商id", required = true) @RequestParam(required = true) Integer id
    ) {
        Merchant merchant = merchantService.getMerchant(id);
        return new CommonResponse(merchant);
    }

    @ApiOperation("修改兑换商信息")
    @PutMapping("")
    public CommonResponse merchantUpdate(@RequestBody Merchant merchant) {
        merchantService.updateMerchant(merchant);
        return new CommonResponse();
    }

    @ApiModelProperty("超级兑换商拨币")
    @PostMapping("dispatch-balance")
    public CommonResponse dispatchBalance(
            @ApiParam(value = "超级兑换商id", required = true) @RequestParam(name = "merchant_id", required = true) Integer merchant_id,
            @ApiParam(value = "币种名称", required = true) @RequestParam(name = "coinName", required = true) String coinName,
            @ApiParam(value = "信用金", required = true) @RequestParam(name = "available", required = true) BigDecimal available,
            @ApiParam(value = "冻结金", required = true) @RequestParam(name = "freeze", required = true) BigDecimal freeze,
            @ApiParam(value = "拨币原因", required = true) @RequestParam(name = "reason", required = true) String reason
    ) {
        merchantService.changeMerchantBalance(merchant_id, coinName, available, freeze, reason, null);
        return new CommonResponse();
    }

    @ApiOperation(value = "查看兑换商资产")
    @GetMapping("merchant-balance")
    public CommonResponse<List<MerchantCoin>> getMerchantCoins(
            @ApiParam(name = "merchantId", value = "兑换商ID") @RequestParam(required = false) Integer merchantId) {
        List<MerchantCoin> ls = merchantService.getMerchantCoins(merchantId);
        return new CommonResponse<>(ls);
    }

    @ApiOperation("修改兑换商状态")
    @GetMapping("/change-status")
    public CommonResponse updateMerchantStatus(
            @ApiParam(name = "id", value = "兑换商ID") @RequestParam(value = "id", required = false) Integer id,
            @ApiParam(name = "status", value = "状态，0：正常，1：已经禁用，2：申请中") @RequestParam(value = "status", required = false) Integer status) {
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setStatus(status);
        merchantService.updateMerchant(merchant);
        return new CommonResponse();
    }

    @ApiOperation("查找用户跟普通兑换商之间的订单列表")
    @GetMapping("/user-merchant-order-list")
    public CommonListResponse<UserMerchantOrder> searchUserOrder(
            @ApiParam(name = "id", value = "用户ID") @RequestParam(required = false) Integer userId,
            @ApiParam(name = "merchantId", value = "兑换商ID") @RequestParam(required = false) Integer merchantId,
            @ApiParam(name = "status", value = "状态") @RequestParam(required = false) MerchantOrderStatus status,
            @ApiParam(name = "type", value = "订单类型") @RequestParam(required = false) MerchantOrderType type,
            @ApiParam(value = "订单号", required = false) @RequestParam(required = false) String orderNo,
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(value = "pageNo", required = true) Integer page,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(value = "pageSize", required = true) Integer size) {
        Page<UserMerchantOrder> ls = userMerchantOrderService.getOrders(userId, merchantId, status, type, orderNo, page, size);
        return CommonListResponse.fromPage(ls);
    }

    @ApiOperation("管理员强制完成订单")
    @GetMapping("/complete-order")
    public CommonResponse completeOrder(
            @ApiParam(name = "orderId", value = "订单ID") @RequestParam(required = true) Integer orderId) {
        userMerchantOrderService.mgrConfirmReceived(orderId);
        return new CommonResponse();
    }

    @ApiOperation("管理员强制取消订单")
    @GetMapping("/cancel-order")
    public CommonResponse cancelOrder(
            @ApiParam(name = "orderId", value = "订单ID") @RequestParam(required = true) Integer orderId,
            @ApiParam(name = "comment", value = "备注信息") @RequestParam(required = false) String comment) {
        userMerchantOrderService.mgrCancelOrder(orderId, comment);
        return new CommonResponse();
    }

    @ApiOperation("获取一个申诉单")
    @GetMapping("/complaint")
    public CommonResponse<OrderComplaint> getOrderComplaint(
            @ApiParam(name = "orderId", value = "申诉单ID") @RequestParam(name = "orderId", required = true) Integer orderId) {
        OrderComplaint oc = userMerchantOrderService.getOrderComplaintByOrderid(orderId);
        return new CommonResponse<OrderComplaint>(oc);
    }

    @ApiOperation("获取订单相关的申诉单")
    @GetMapping("/order-complaint")
    public CommonResponse<OrderComplaint> getOrderComplaintByOrder(
            @ApiParam(name = "orderId", value = "订单单ID") @RequestParam(required = true) Integer orderId) {
        OrderComplaint oc = userMerchantOrderService.getOrderComplaintByOrderid(orderId);
        return new CommonResponse<OrderComplaint>(oc);
    }

    @ApiOperation("获取申诉列表")
    @GetMapping("/complaint-list")
    public CommonListResponse<OrderComplaint> getOrderComplaint(
            @ApiParam(name = "userId", value = "申诉的用户ID") @RequestParam(required = true) Integer userId,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Page<OrderComplaint> ls = userMerchantOrderService.getOrderComplaintList(userId, page, size);
        return CommonListResponse.fromPage(ls);
    }

    @ApiOperation("管理员强制完成申诉")
    @GetMapping("/complete-appeal")
    public CommonResponse completeAppeal(
            @ApiParam(name = "orderId", value = "订单单ID") @RequestParam(required = true) Integer orderId,
            @ApiParam(name = "ocId", value = "申诉单ID") @RequestParam(required = true) Integer ocId,
            @ApiParam(name = "cancel", value = "true：把订单强制取消，否则强制将订单交易成功") @RequestParam(required = true) boolean cancel) {
        userMerchantOrderService.completeAppeal(ocId, orderId, !cancel);
        return new CommonResponse();
    }
}
