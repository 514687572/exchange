package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.enums.MerchantOrderStatus;
import com.cmd.exchange.common.enums.MerchantOrderType;
import com.cmd.exchange.common.model.Merchant;
import com.cmd.exchange.common.model.OrderComplaint;
import com.cmd.exchange.common.model.SuperMerchantOrder;
import com.cmd.exchange.common.model.UserMerchantOrder;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.MerchantService;
import com.cmd.exchange.service.SuperMerchantOrderService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "超级兑换商订单接口")
@RestController
@RequestMapping("/super-merchant-order")
public class SuperMerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SuperMerchantOrderService userMerchantOrderService;

    @ApiOperation("搜索兑换商，如果是null，那么忽略该条件")
    @GetMapping("/list")
    public CommonListResponse<Merchant> searchMerchant(
            @ApiParam(name = "userId", value = "用户ID") @RequestParam(value = "userId", required = false) Integer userId,
            @ApiParam(name = "type", value = "类型:1:普通兑换商2:超级兑换商") @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "merchantName", value = "兑换商名称") @RequestParam(value = "merchantName", required = false) String merchantName,
            @ApiParam(name = "status", value = "状态，0：正常，1：已经禁用，2：申请中") @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Page<Merchant> ls = merchantService.searchMerchant(type, userId, merchantName, status, page, size);
        return CommonListResponse.fromPage(ls);
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
    public CommonListResponse<SuperMerchantOrder> searchUserOrder(
            @ApiParam(name = "merchantId", value = "兑换商ID") @RequestParam(required = false) Integer merchantId,
            @ApiParam(name = "superMerchantId", value = "超级兑换商ID") @RequestParam(required = false) Integer superMerchantId,
            @ApiParam(name = "status", value = "状态") @RequestParam(required = false) MerchantOrderStatus status,
            @ApiParam(name = "type", value = "订单类型") @RequestParam(required = false) MerchantOrderType type,
            @ApiParam(value = "订单号", required = false) @RequestParam(required = false) String orderNo,
            @ApiParam(name = "page", value = "当前页") @RequestParam(value = "page", required = true) Integer page,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(value = "size", required = true) Integer size) {
        Page<SuperMerchantOrder> ls = userMerchantOrderService.getOrders(merchantId, superMerchantId, status, type, orderNo, page, size);
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

}
