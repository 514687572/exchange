package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.AtomVO;
import com.cmd.exchange.common.model.Otc;
import com.cmd.exchange.common.model.OtcMarket;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.OtcOrderVO;
import com.cmd.exchange.common.vo.OtcVO;
import com.cmd.exchange.service.OtcService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "场外交易/C2C市场（用户使用真实货币在平台内买卖虚拟货币，真实货币在平台外部转账）")
@RestController
@RequestMapping("/otc-trade")
public class OtcTradeController {

    @Autowired
    OtcService otcService;

    //字符串转换成int数组
    private Integer[] stringToIntArray(String str) {
        String[] strArray = str.split(",");
        Integer[] ss = new Integer[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            ss[i] = Integer.parseInt(strArray[i]);
        }
        return ss;
    }

    @ApiOperation("订单申诉")
    @PutMapping("/appeal")
    public CommonResponse appeal(
            @ApiParam(value = "订单id", required = true) @RequestParam("orderId") Long orderId,
            @ApiParam(value = "申诉说明", required = true) @RequestParam("reason") String reason
    ) {
        int userId = ShiroUtils.getUser().getId();
        String appealCode = otcService.orderAppeal(userId, orderId, reason);
        return new CommonResponse(new AtomVO(appealCode));
    }

    @ApiOperation("撤销订单申诉")
    @PutMapping("/appeal-cancel")
    public CommonResponse appeal(
            @ApiParam(value = "订单id", required = true) @RequestParam("orderId") Long orderId
    ) {
        int userId = ShiroUtils.getUser().getId();
        otcService.cancelOrderAppeal(userId, orderId);
        return new CommonResponse();
    }

    @ApiOperation(value = "参考价格")
    @GetMapping("/market-price")
    public CommonResponse MarketPrice() {
        CommonResponse response = new CommonResponse(otcService.getMarketPrice());
        return response;
    }

    @ApiOperation(value = "获取OTC配置")
    @GetMapping("/otc-config")
    public CommonResponse<List<OtcMarket>> getOtcMarket() {
        return new CommonResponse(otcService.getOtcMarket());
    }

    @ApiOperation(value = "挂买单（充值）")
    @PostMapping("/deposit")
    public CommonResponse deposit(@RequestParam("coinName") String coinName,
                                  @RequestParam("legalName") String legalName,
                                  @RequestParam("amount") BigDecimal amount,
                                  @RequestParam("price") BigDecimal price,
                                  @RequestParam("comment") String comment,
                                  @RequestParam("minAmount") BigDecimal minAmount,
                                  @RequestParam("maxAmount") BigDecimal maxAmount) {
        if (legalName == null)
            legalName = "cny";

        //BigDecimal tAmount = amount.multiply(price);
        //Assert.check(minAmount.compareTo(new BigDecimal(0))<0||minAmount.compareTo(tAmount)>0||maxAmount.compareTo(tAmount)>0||price.compareTo(new BigDecimal(0.01))<0||minAmount.compareTo(maxAmount)>0,500,"非法操作");
        otcService.deposit(ShiroUtils.getUser().getId(), coinName, legalName, amount, price, minAmount, maxAmount);
        return new CommonResponse();
    }

    @ApiOperation(value = "挂卖单（提现）")
    @PostMapping("/withdraw")
    public CommonResponse withdraw(@RequestParam("coinName") String coinName,
                                   @RequestParam("legalName") String legalName,
                                   @RequestParam("amount") BigDecimal amount,
                                   @RequestParam("price") BigDecimal price,
                                   @RequestParam("comment") String comment,
                                   @RequestParam("minAmount") BigDecimal minAmount,
                                   @RequestParam("maxAmount") BigDecimal maxAmount) {
        if (legalName == null)
            legalName = "cny";

        //BigDecimal tAmount = amount.multiply(price);
        //Assert.check(minAmount.compareTo(new BigDecimal(0))<0||minAmount.compareTo(tAmount)>0||maxAmount.compareTo(tAmount)>0||price.compareTo(new BigDecimal(0.01))<0||minAmount.compareTo(maxAmount)>0,500,"非法操作");
        otcService.withdral(ShiroUtils.getUser().getId(), coinName, legalName, amount, price, minAmount, maxAmount);
        return new CommonResponse();
    }

    @ApiOperation(value = "卖市场购买")
    @PostMapping("/deposit-order")
    public CommonResponse depositOrder(@RequestParam("applicationId") Long id,
                                       @RequestParam("amount") BigDecimal amount,
                                       @RequestParam("comment") String comment) {

        long result = otcService.depositOrder(ShiroUtils.getUser().getId(), id, amount, comment);
        return new CommonResponse(new AtomVO(String.valueOf(result)));
    }

    @ApiOperation(value = "买市场购买")
    @PostMapping("/withdraw-order")
    public CommonResponse withdrawOrder(@RequestParam("applicationId") Long id,
                                        @RequestParam("amount") BigDecimal amount,
                                        @RequestParam("comment") String comment,
                                        @RequestParam(required = false) String payPassword) {
        long result = otcService.withdralOrder(ShiroUtils.getUser().getId(), id, amount, comment);
        return new CommonResponse(new AtomVO(String.valueOf(result)));
    }

    @ApiOperation(value = "挂单取消")
    @PostMapping("/cancel-application")
    public CommonResponse cancelApplication(@RequestParam("applicationId") Long id) {
        otcService.cancelApplication(ShiroUtils.getUser().getId(), id);
        return new CommonResponse();
    }

    @ApiOperation(value = "订单取消")
    @PostMapping("/cancel-order")
    public CommonResponse cancelOrder(@RequestParam("orderId") Long id,
                                      @RequestParam(value = "comment", required = false) String comment) {
        otcService.cancelOrder(ShiroUtils.getUser().getId(), id, comment);
        return new CommonResponse();
    }

    @ApiOperation(value = "上传凭证")
    @PostMapping("/upload-credential")
    public CommonResponse uploadCredential(@RequestParam("orderId") Long id,
                                           @RequestParam("credentialComment") String comment,
                                           @RequestParam("credentialUrls") String urls,
                                           @RequestParam("payId") Integer payId) {
        otcService.uploadCredential(ShiroUtils.getUser().getId(), id, comment, urls, payId);
        return new CommonResponse();
    }

    @ApiOperation(value = "确认订单")
    @PostMapping("/confirm-order")
    public CommonResponse confirmOrder(@RequestParam("orderId") Long id,
                                       @RequestParam(required = false) String payPassword) {
        otcService.confirmOrder(ShiroUtils.getUser().getId(), id, payPassword);
        return new CommonResponse();
    }

    //****
    @ApiOperation(value = "获取挂单")
    @GetMapping("/application-list")
    public CommonListResponse<OtcVO> getApplicationList(@ApiParam(value = "coinName", required = true) @RequestParam(value = "coinName", required = true) String coinName,
                                                        @ApiParam(value = "legalName", required = false) @RequestParam(value = "legalName", required = false) String legalName,
                                                        @ApiParam(value = "type", required = false) @RequestParam(value = "type", required = false) Integer type,
                                                        @ApiParam(value = "status:2匹配中,100取消,1成功(2,100,1)", required = false) @RequestParam(value = "status", required = false) String status,
                                                        @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                        @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (StringUtils.isBlank(coinName))
            coinName = null;

        Integer[] ss = null;
        if (StringUtils.isNoneBlank(status))
            ss = stringToIntArray(status);

        Page<OtcVO> rst = otcService.getApplicationList(coinName, type, ss, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation(value = "获取我的挂单")
    @GetMapping("/my-application-list")
    public CommonListResponse<OtcVO> getMyApplicatinList(@ApiParam(value = "coinName", required = false) @RequestParam(value = "coinName", required = false) String coinName,
                                                         @ApiParam(value = "legalName", required = false) @RequestParam(value = "legalName", required = false) String legalName,
                                                         @ApiParam(value = "type", required = false) @RequestParam(value = "type", required = false) Integer type,
                                                         @ApiParam(value = "status:2匹配中,100取消,1成功(2,100,1)", required = false) @RequestParam(value = "status", required = false) String status,
                                                         @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                         @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (coinName != null && coinName.trim().length() == 0)
            coinName = null;
        if (legalName != null && legalName.trim().length() == 0)
            legalName = null;

        Integer[] ss = null;
        if (status != null && status.trim().length() > 0)
            ss = stringToIntArray(status);

        Page<OtcVO> rst = otcService.getMyApplicationList(ShiroUtils.getUser().getId(), coinName, type, ss, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation(value = "获取我的订单")
    @GetMapping("/my-order-list")
    public CommonListResponse<OtcOrderVO> getMyApplicationOrderList(@ApiParam(value = "coinName", required = false) @RequestParam(value = "coinName", required = false) String coinName,
                                                                    @ApiParam(value = "legalName", required = false) @RequestParam(value = "legalName", required = false) String legalName,
                                                                    @ApiParam(value = "type:1买2卖", required = false) @RequestParam(value = "type", required = false) Integer type,
                                                                    @ApiParam(value = "status:2匹配中,4匹配待付款,5已付款待确认,6申诉,100取消,1成功(2,4,5,6,100,1)", required = false) @RequestParam(value = "status", required = false) String status,
                                                                    @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                                    @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (coinName != null && coinName.trim().length() == 0)
            coinName = null;
        if (legalName != null && legalName.trim().length() == 0)
            legalName = null;

        Integer[] ss = null;
        if (status != null && status.trim().length() > 0)
            ss = stringToIntArray(status);


        Page<OtcOrderVO> rst = otcService.getMyApplicationOrderList(ShiroUtils.getUser().getId(), coinName, type, ss, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation(value = "挂单详细")
    @GetMapping("/application-info")
    public CommonResponse<Otc> getApplicationInfo(@RequestParam(value = "id", required = true) Long id) {
        return new CommonResponse(otcService.getApplicationInfo(id));
    }

    @ApiOperation(value = "订单详细")
    @GetMapping("/otcOrder-info")
    public CommonResponse<OtcOrderVO> getOtcOrderInfo(@RequestParam(value = "id", required = true) Long id) {

        return new CommonResponse(otcService.getOtcOrderInfo(id, ShiroUtils.getUser().getId()));
    }

}
