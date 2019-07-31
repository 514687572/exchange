package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.excel.ExportToExcelUtil;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.vo.TradeLogRewardVo;
import com.cmd.exchange.common.vo.TradeLogVo;
import com.cmd.exchange.common.vo.TradeVo;
import com.cmd.exchange.common.vo.UserCoinInfoVO;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 交易接口
 */

@Api(tags = "交易查询模块")
@RestController
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserService userService;

    private Log log = LogFactory.getLog(this.getClass());

    @ApiOperation(value = "委托列表-警告类型")
    @GetMapping("")
    public CommonListResponse<TradeVo> getTradeList(
            @ApiParam(value = "交易币种", required = true) @RequestParam() String coinName,
            @ApiParam(value = "结算币种", required = true) @RequestParam String settlementCurrency,
            @ApiParam(value = "交易类型： BUY:买入， SELL:卖出 ALL:所有", required = true) @RequestParam TradeType type,
            @ApiParam(value = "用户名", required = false) @RequestParam(required = false) String userName,
            @ApiParam(value = "手机号码", required = false) @RequestParam(required = false) String mobile,
            @ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
            @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize
    ) {

        Page<TradeVo> userList = tradeService.getTadeListByTotalCurrency(coinName, settlementCurrency, type, userName, mobile, pageNo, pageSize);
        if (userList != null) {
            return CommonListResponse.fromPage(userList);
        }
        return null;
    }

    @ApiOperation(value = "委托列表,查询所有")
    @GetMapping("/getTradeListAll")
    public CommonListResponse<TradeVo> getTradeListAll(
            @ApiParam(value = "交易币种", required = false) @RequestParam(name = "coinName", required = false) String coinName,
            @ApiParam(value = "结算币种", required = false) @RequestParam(name = "settlementCurrency", required = false) String settlementCurrency,
            @ApiParam(value = "交易类型： BUY:买入， SELL:卖出 ALL:所有", required = true) @RequestParam TradeType type,
            @ApiParam(value = "用户名", required = false) @RequestParam(required = false) String userName,
            @ApiParam(value = "手机号码", required = false) @RequestParam(required = false) String mobile,
            @ApiParam(value = "单价", required = false) @RequestParam(required = false) BigDecimal price,
            @ApiParam(value = "数量", required = false) @RequestParam(required = false) BigDecimal amount,
            @ApiParam(value = "成交状态", required = false) @RequestParam(required = false) TradeStatus status,
            @ApiParam(value = "委托开始时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "addStartTime", required = false) String addStartTime,
            @ApiParam(value = "委托结束时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "addEndTime", required = false) String addEndTime,

            @ApiParam(value = "成交开始时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "startLastTradeTime", required = false) String startLastTradeTime,
            @ApiParam(value = "成交结束时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "endLastTradeTime", required = false) String endLastTradeTime,


            @ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
            @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize
    ) {

        Page<TradeVo> userList = tradeService.getTradeList(coinName, settlementCurrency, type, userName, mobile, price, amount, status, addStartTime, addEndTime, startLastTradeTime, endLastTradeTime, pageNo, pageSize);

        return CommonListResponse.fromPage(userList);
    }

    @ApiOperation(value = "某条订单的成交列表")
    @GetMapping("deal")
    public CommonListResponse<TradeLogVo> getTradeLogList(
            @ApiParam(value = "订单id", required = true) @RequestParam Integer tradeId,
            @ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
            @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize
    ) {

        Page<TradeLogVo> userList = tradeService.getTradeLogList(tradeId, pageNo, pageSize);

        return CommonListResponse.fromPage(userList);
    }

    @ApiOperation(value = "获取交易日志和返佣详情列表")
    @GetMapping("trade-reward-ls")
    public CommonListResponse<TradeLogRewardVo> getTradeLogRewardList(
            @ApiParam(value = "交易币种", required = false) @RequestParam(name = "coinName", required = false) String coinName,
            @ApiParam(value = "结算币种", required = false) @RequestParam(name = "settlementCurrency", required = false) String settlementCurrency,
            @ApiParam(value = "买方用户名", required = false) @RequestParam(required = false) String buyUserName,
            @ApiParam(value = "卖方用户名", required = false) @RequestParam(required = false) String sellUserName,
            @ApiParam(value = "类型（1是只显示有返还奖励的），其它是所有", required = false) @RequestParam(required = false) Integer type,
            @ApiParam(value = "成交开始时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "startLastTradeTime", required = false) String startLastTradeTime,
            @ApiParam(value = "成交结束时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "endLastTradeTime", required = false) String endLastTradeTime,
            @ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
            @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize) {
        log.info("getTradeLogRewardList coinName=" + coinName + ",settlementCurrency=" + settlementCurrency + ",buyUserName=" + buyUserName
                + ",sellUserName=" + sellUserName + ",type=" + type + ",startLastTradeTime=" + startLastTradeTime + ",endLastTradeTime=" + endLastTradeTime
                + ",pageNo=" + pageNo + ",pageSize=" + pageSize);
        Integer buyUserId = null;
        if (buyUserName != null && buyUserName.trim().length() > 0) {
            User user = userService.getUserByUserName(buyUserName.trim());
            if (user == null) return new CommonListResponse<TradeLogRewardVo>();
            buyUserId = user.getId();
        }
        Integer sellUserId = null;
        if (sellUserName != null && sellUserName.trim().length() > 0) {
            User user = userService.getUserByUserName(sellUserName.trim());
            if (user == null) return new CommonListResponse<TradeLogRewardVo>();
            sellUserId = user.getId();
        }
        Page<TradeLogRewardVo> result = tradeService.getTradeLogRewardList(coinName, settlementCurrency, buyUserId, sellUserId,
                type != null && type == 1, startLastTradeTime, endLastTradeTime, pageNo, pageSize);
        return CommonListResponse.fromPage(result);
    }

    @ApiOperation(value = "导出交易日志和返佣详情列表")
    @GetMapping("export-trade-reward")
    public void exportTradeLogReward(
            @ApiParam(value = "交易币种", required = false) @RequestParam(name = "coinName", required = false) String coinName,
            @ApiParam(value = "结算币种", required = false) @RequestParam(name = "settlementCurrency", required = false) String settlementCurrency,
            @ApiParam(value = "买方用户名", required = false) @RequestParam(required = false) String buyUserName,
            @ApiParam(value = "卖方用户名", required = false) @RequestParam(required = false) String sellUserName,
            @ApiParam(value = "类型（1是只显示有返还奖励的），其它是所有", required = false) @RequestParam(required = false) Integer type,
            @ApiParam(value = "成交开始时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "startLastTradeTime", required = false) String startLastTradeTime,
            @ApiParam(value = "成交结束时间 yyyy-mm-dd HH:mm:ss", required = false) @RequestParam(name = "endLastTradeTime", required = false) String endLastTradeTime,
            HttpServletResponse response, HttpServletRequest request) {
        log.info("exportTradeLogReward coinName=" + coinName + ",settlementCurrency=" + settlementCurrency + ",buyUserName=" + buyUserName
                + ",sellUserName=" + sellUserName + ",type=" + type + ",startLastTradeTime=" + startLastTradeTime + ",endLastTradeTime=" + endLastTradeTime);
        Integer buyUserId = null;
        if (buyUserName != null && buyUserName.trim().length() > 0) {
            buyUserId = 0;
            User user = userService.getUserByUserName(buyUserName.trim());
            if (user != null) buyUserId = user.getId();
        }
        Integer sellUserId = null;
        if (sellUserName != null && sellUserName.trim().length() > 0) {
            sellUserId = 0;
            User user = userService.getUserByUserName(sellUserName.trim());
            if (user != null) sellUserId = user.getId();
        }
        Page<TradeLogRewardVo> result;
        if ((buyUserId != null && buyUserId == 0) || (sellUserId != null && sellUserId == 0)) {
            result = new Page<TradeLogRewardVo>();
        } else {
            result = tradeService.getTradeLogRewardList(coinName, settlementCurrency, buyUserId, sellUserId,
                    type != null && type == 1, startLastTradeTime, endLastTradeTime, 1, 60000);
        }
        // 开始导出
        OutputStream out = null;
        ExportToExcelUtil<TradeLogRewardVo> excelUtil = new ExportToExcelUtil<TradeLogRewardVo>();
        // 装换时间
        for (TradeLogRewardVo vo : result) {
            if (vo.getAddTime() != null) {
                vo.setAddTime1(new Date(1000L * vo.getAddTime()));
            }
        }
        try {
            out = response.getOutputStream();
            excelUtil.setResponseHeader(response, "交易日志和返佣详情");
            String[] headers = {"ID", "交易币种", "结算币种", "单价", "交易数量", "买方用户ID", "买方用户名", "卖方用户ID", "卖方用户名",
                    "交易时间", "买方支付的手续费（交易币种）", "卖方支付的手续费（结算货币）", "买方挂单ID", "卖方挂单ID",
                    "返还奖励的时间", "手续费返还给推荐用户的比例", "手续费返还给二级推荐用户的比例", "买方的推荐人ID", "买方的推荐人名称",
                    "买方推荐人返还的币个数", "买方的二级推荐人ID", "买方的二级推荐人名称", "买方二级推荐人返还的币个数",
                    "卖方的推荐人ID", "卖方的推荐人名称", "卖方推荐人返还的币个数", "卖方的二级推荐人ID", "卖方的二级推荐人名称", "卖方二级推荐人返还的币个数"};
            String[] columns = {"id", "coinName", "settlementCurrency", "price", "amount", "buyUserId", "buyUserName", "sellUserId", "sellUserName",
                    "addTime1", "buyFeeCoin", "sellFeeCurrency", "buyTradeId", "sellTradeId",
                    "retTime", "recUserRate", "rec2UserRate", "buyRecUserId", "buyRecUserName",
                    "buyRecRet", "buyRec2UserId", "buyRec2UserName", "buyRec2Ret",
                    "sellRecUserId", "sellRecUserName", "sellRecRet", "sellRec2UserId", "sellRec2UserName", "sellRec2Ret"};

            excelUtil.exportExcel(headers, columns, result, out, request, "yyyy-MM-dd hh:mm:ss");

        } catch (Exception e) {
            log.warn("exprot exception is" + e, e);
        }
    }

}
