package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.excel.ExcelGenerator;
import com.cmd.exchange.common.excel.ReportColumn;
import com.cmd.exchange.common.excel.ReportData;
import com.cmd.exchange.common.excel.ReportDefinition;
import com.cmd.exchange.common.model.OtcMarket;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.service.C2cService;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Otc;
import com.cmd.exchange.common.model.OtcOrder;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.service.OtcMarketService;
import com.cmd.exchange.service.OtcService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Api(tags = "OTC设置信息")
@RestController
@RequestMapping("/otc")
public class OtcController {

    @Autowired
    OtcMarketService otcMarketService;
    @Autowired
    OtcService otcService;
    @Autowired
    C2cService c2cService;

    @ApiOperation("增加配置")
    @PostMapping("/add-config")
    public CommonResponse addOtcMarket(@ApiParam(value = "Otc参数") @RequestBody OtcMarketVO otcMarketVO) {
        otcMarketService.addOtcMarket(otcMarketVO);
        return new CommonResponse();
    }

    @ApiOperation("删除配置")
    @PostMapping("/del-config")
    public CommonResponse delOtcMarket(@ApiParam(value = "Otc参数") @RequestParam int id) {
        otcMarketService.delOtcMarket(id);
        return new CommonResponse();
    }


    @ApiOperation("设置参数")
    @PutMapping("/config")
    public CommonResponse setOtcMarket(@ApiParam(value = "Otc参数") @RequestBody OtcMarketVO otcMarketVO) {
        otcMarketService.updateOtcMarket(otcMarketVO);
        return new CommonResponse();
    }

    @ApiOperation("otc获取配置列表")
    @GetMapping("/config-list")
    public CommonResponse config_list() {
        List<OtcMarket> otcMarketList = otcMarketService.otcMarketList();
        return new CommonResponse(otcMarketList);
    }

    @ApiOperation("otc获取配置详情")
    @GetMapping("config-detail")
    public CommonResponse config_detail(@ApiParam("币种名称") @RequestParam(name = "coinName", required = true) String coinName) {
        OtcMarket otcMarket = otcMarketService.otcMarketDetail(coinName);
        return new CommonResponse(otcMarket);
    }

    @ApiOperation("挂单列表")
    @GetMapping("/applications")
    public CommonListResponse<C2cApplicationVO> getApplications(
            @ApiParam(value = "type,类型 1：买，2：卖, 100:获取所有", required = true) @RequestParam(value = "type") int type,
            @ApiParam(value = "status,状态 1：完成，2：匹配中，100：取消，10：获取所有", required = true) @RequestParam(value = "status") int status,
            @ApiParam(value = "coinName,如btc,usdt") @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(value = "订单号") @RequestParam(value = "orderNo", required = false) String orderNo,
            @ApiParam(value = "用户") @RequestParam(value = "userName", required = false) String userName,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
            @ApiParam(value = "结束时间", required = false) @RequestParam(name = "endTime", required = false) String endTime,
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize
    ) {
        Page<C2cApplicationVO> result = c2cService.getApplications(type, status, coinName, orderNo, userName, startTime, endTime, pageNo, pageSize);
        return new CommonListResponse().fromPage(result);
    }

    @ApiOperation("撤销下单")
    @PutMapping("/application-cancel")
    public CommonResponse cancelApplication(@ApiParam(value = "申请id", required = true) @RequestParam(value = "id") Long id) {
        Otc otc = otcService.getApplicationInfo(id);
        Assert.check(otc == null, ErrorCode.ERR_PARAM_ERROR);
        otcService.cancelApplication(otc.getUserId(), id);
        return new CommonResponse<>();
    }

    @ApiOperation("订单列表")
    @GetMapping("/orders")
    public CommonListResponse<C2cOrderVO> getOrders(
            @ApiParam(value = "买家姓名") @RequestParam(value = "buyName", required = false) String buyName,
            @ApiParam(value = "卖家姓名") @RequestParam(value = "sellName", required = false) String sellName,
            @ApiParam(value = "币名") @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(value = "status,状态 1:交易成功，2：匹配中，3：匹配成功, 等待接单,4:已经接单,5:已经付款,6:申诉中,100:已经取消,10：获取所有", required = true)
            @RequestParam(value = "status") int status,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
            @ApiParam(value = "结束时间", required = false) @RequestParam(name = "endTime", required = false) String endTime,
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize
    ) {
        Page<C2cOrderVO> orders = c2cService.getOrders(buyName, sellName, coinName, status, startTime, endTime, pageNo, pageSize);
        return new CommonListResponse().fromPage(orders);
    }

    @ApiOperation("取消订单")
    @PutMapping("/order-cancel")
    public CommonResponse cancelOrder(@ApiParam(value = "订单id", required = true) @RequestParam(value = "id") Long id) {
        OtcOrder vo = otcService.getOtcOrderInfo2(id);
        Assert.check(vo == null, ErrorCode.ERR_PARAM_ERROR);

        otcService.cancelOrder(vo.getId(), "canceled by system");
        return new CommonResponse<>();
    }

    @ApiOperation("完成订单")
    @PostMapping("/order-deal")
    public CommonResponse dealOrder(@ApiParam(value = "订单id", required = true) @RequestParam(value = "id") Long id) {
        OtcOrder vo = otcService.getOtcOrderInfo2(id);
        Assert.check(vo == null, ErrorCode.ERR_PARAM_ERROR);

        otcService.dealOrder(null, id, "deal by system");
        return new CommonResponse<>();
    }

    @ApiOperation("获取订单详细信息")
    @GetMapping("/order-detail")
    public CommonResponse<C2cOrderDetailVO> OrderDetail(@ApiParam(value = "订单id", required = true) @RequestParam(value = "id") Long id) {
        return new CommonResponse(otcService.getOtcOrderDetailVO(id));
    }

    @ApiOperation("查看聊天记录")
    @GetMapping("/chat-info")
    public CommonListResponse<ChatLogVo> chatLog(
            @ApiParam(value = "订单id", required = true) @RequestParam(value = "id") Long id,
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize
    ) {
        Page<ChatLogVo> chatLogVos = c2cService.getChatLogByOrderId(id, pageNo, pageSize);

        return CommonListResponse.fromPage(chatLogVos);
    }

    @ApiOperation("导出挂单")
    @GetMapping("/applications-download")
    public void applicationsDownload(
            @ApiParam(value = "type,类型 1：买，2：卖, 100:获取所有", required = true) @RequestParam(value = "type") int type,
            @ApiParam(value = "status,状态 1：完成，2：匹配中，100：取消，10：获取所有", required = true) @RequestParam(value = "status") int status,
            @ApiParam(value = "coinName,如btc,usdt") @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(value = "订单号") @RequestParam(value = "orderNo", required = false) String orderNo,
            @ApiParam(value = "用户") @RequestParam(value = "userName", required = false) String userName,
            @ApiParam(value = "开始时间，精度为day", required = true) @RequestParam(value = "beginTime") Date beginTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam(value = "endTime") Date endTime, HttpServletResponse response
    ) throws IllegalAccessException, NoSuchFieldException, IOException {

        List<C2cApplicationVO> Applications = c2cService.downloadApplications(type, status, coinName, orderNo, userName, beginTime, endTime);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户列表", "utf-8") + ".xls");

        ExcelGenerator generator = new ExcelGenerator();
        ReportDefinition definition = new ReportDefinition("挂单数据");

        definition.addColumn(new ReportColumn("ID", "id"));
        definition.addColumn(new ReportColumn("币名", "coinName"));
        definition.addColumn(new ReportColumn("单号", "orderNo"));
        definition.addColumn(new ReportColumn("用户", "createUser"));
        definition.addColumn(new ReportColumn("类型 1：买，2：卖", "type"));
        definition.addColumn(new ReportColumn("总数量", "amount"));
        definition.addColumn(new ReportColumn("价格", "price"));
        definition.addColumn(new ReportColumn("剩余数量", "rAmount"));
        definition.addColumn(new ReportColumn("匹配数量", "mAmount"));
        definition.addColumn(new ReportColumn("交易成功数量", "sAmount"));
        definition.addColumn(new ReportColumn("状态1：完成，2：匹配中，100：取消", "status"));
        definition.addColumn(new ReportColumn("挂单时间", "createTime"));

        ReportData reportData = new ReportData(definition, Applications);
        generator.addSheet(reportData);
        generator.write(response.getOutputStream());

        response.getOutputStream().flush();

    }

    @ApiOperation("导出订单")
    @GetMapping("/orders-download")
    public void ordersDownload(
            @ApiParam(value = "买家姓名") @RequestParam(value = "buyName", required = false) String buyName,
            @ApiParam(value = "卖家姓名") @RequestParam(value = "sellName", required = false) String sellName,
            @ApiParam(value = "币名") @RequestParam(value = "coinName", required = false) String coinName,
            @ApiParam(value = "status,状态 1:交易成功，2：匹配中，3：匹配成功, 等待接单,4:已经接单,5:已经付款,6:申诉中,100:已经取消,10：获取所有", required = true)
            @RequestParam(value = "status") int status,
            @ApiParam(value = "开始时间，精度为day", required = true) @RequestParam(value = "beginTime") Date beginTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam(value = "endTime") Date endTime, HttpServletResponse response
    ) throws IllegalAccessException, NoSuchFieldException, IOException {

        List<C2cOrderVO> orders = c2cService.downloadOrders(buyName, sellName, coinName, status, beginTime, endTime);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户列表", "utf-8") + ".xls");

        ExcelGenerator generator = new ExcelGenerator();
        ReportDefinition definition = new ReportDefinition("订单数据");

        definition.addColumn(new ReportColumn("ID", "id"));
        definition.addColumn(new ReportColumn("币名", "coinName"));
        definition.addColumn(new ReportColumn("单号", "orderNo"));
        definition.addColumn(new ReportColumn("买家账号", "buyUser"));
        definition.addColumn(new ReportColumn("卖家账号", "sellUser"));
        definition.addColumn(new ReportColumn("价格", "price"));
        definition.addColumn(new ReportColumn("数量", "number"));
        definition.addColumn(new ReportColumn("总额", "amount"));
        definition.addColumn(new ReportColumn("手续费", "fee"));
        definition.addColumn(new ReportColumn("成交时间", "finishTime"));
        definition.addColumn(new ReportColumn("交易货币", "legalName"));
        definition.addColumn(new ReportColumn("状态", "status"));
        definition.addColumn(new ReportColumn("凭证上传时间", "uploadCredentialTime"));
        definition.addColumn(new ReportColumn("订单完成标识0：未完成，无需标识，1：用户确认，2：后台确认", "tag"));
        definition.addColumn(new ReportColumn("撮合时间", "createTime"));

        ReportData reportData = new ReportData(definition, orders);
        generator.addSheet(reportData);
        generator.write(response.getOutputStream());

        response.getOutputStream().flush();
    }


}
