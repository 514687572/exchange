package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.constants.UserBillType;
import com.cmd.exchange.common.enums.SendCoinStatus;
import com.cmd.exchange.common.enums.TransferType;
import com.cmd.exchange.common.excel.ExportToExcelUtil;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.common.vo.CoinTransferVo;
import com.cmd.exchange.service.*;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 数字货币转入转出
 * 个人财务信息
 */
@Api(tags = "财务中心（数字货币转入转出）")
@RestController
@Slf4j
@RequestMapping("finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;
    @Autowired
    SendCoinService sendCoinService;
    @Autowired
    UserService userService;
    @Autowired
    TransferService transferService;
    @Autowired
    CoinSavingsService coinSavingsService;
    @Autowired
    UserCoinService userCoinService;

    private static boolean isBool = false;


    @ApiOperation(value = "获取数字货币的转账历史记录")
    @GetMapping(value = "transfer-history")
    public CommonListResponse<CoinTransferVo> transferHistory(
            @ApiParam(required = false, value = "币种名称") @RequestParam(name = "coinName", required = false) String coinName,
            @ApiParam(value = "真实姓名", required = false) @RequestParam(name = "realName", required = false) String realName,

            @ApiParam(value = "类别,SEND:转出,RECEIVED:转入,ALL:所有", required = true) @RequestParam TransferType type,
            @ApiParam(value = "用户名", required = false) @RequestParam(name = "userName", required = false) String userName,
            @ApiParam(value = "当前页", required = true) @RequestParam Integer pageNo,
            @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(value = "开始时间, 格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 12:00:00") @RequestParam(required = false) String startTime,
            @ApiParam(value = "结束时间") @RequestParam(required = false) String endTime,
            @ApiParam(value = "用户电话") @RequestParam(required = false) String mobile,
            @ApiParam(value = "来源地址") @RequestParam(required = false) String sourceAddress,
            @ApiParam(value = "目标地址") @RequestParam(required = false) String targetAddress
    ) {

        CoinTransferReqVO reqVO = new CoinTransferReqVO();
        reqVO.setCoinName(coinName).setPageNo(pageNo).setPageSize(pageSize)
                .setStartTime(startTime).setEndTime(endTime);

        reqVO.setType(type).setMobile(mobile).setSourceAddress(sourceAddress).setTargetAddress(targetAddress).setUserName(userName);
        reqVO.setEndTime(endTime);
        reqVO.setStartTime(startTime);
        reqVO.setRealName(realName);

        Page<CoinTransferVo> transferList = financeService.transferHistory(reqVO);
        return CommonListResponse.fromPage(transferList);
    }

    @ApiOperation("获取转账列表")
    @GetMapping(value = "transfer-list")
    public CommonListResponse<SendCoin> getTransferList(@ApiParam("币种") @RequestParam(name = "coinName", required = false) String coinName,
                                                        @ApiParam(value = "用户名") @RequestParam(name = "userName", required = false) String userName,
                                                        @ApiParam("状态:APPLYING:申请，PASSED 通过，FAILED:未通过， ALL：所有") @RequestParam SendCoinStatus status,
                                                        @ApiParam(value = "用户分组") @RequestParam(name = "groupType", required = false) String groupType,
                                                        @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
                                                        @ApiParam(value = "结束时间", required = false) @RequestParam(name = "endTime", required = false) String endTime,
                                                        @ApiParam("pageNo") @RequestParam Integer pageNo,
                                                        @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                userId = user.getId();
            }
        }
        // Page<SendCoin> rst = sendCoinService.getTransferList(userId, coinName, status, pageNo, pageSize);
        Page<SendCoin> rst = sendCoinService.getTransferListByTime(userId, coinName, status, startTime, endTime, groupType, pageNo, pageSize);
        for (SendCoin sc : rst) {
            if (sc.getSecondStatus().equals("1") && sc.getStatus() == SendCoinStatus.APPLYING) {
                sc.setStatus(SendCoinStatus.PASSED);
            }
            if (sc.getUserId() != null) {
                User user = userService.getUserByUserId(sc.getUserId());
                if (user != null) {
                    sc.setUserName(user.getUserName());
                }
            }
        }
        return new CommonListResponse().fromPage(rst);
    }


    @ApiOperation("一次审核失败")
    @PostMapping(value = "transfer-check-fail")
    public CommonResponse transferCheckFail(@ApiParam("转账ID") @RequestParam Integer id) {
        sendCoinService.transferCheckFail(id, 0);
        return new CommonResponse();
    }

    @ApiOperation("一次审核通过")
    @PostMapping(value = "transfer-check-pass")
    public CommonResponse transferCheckPass(@ApiParam("转账ID") @RequestParam Integer id) {
        sendCoinService.transferCheckPass(id, 0);
        return new CommonResponse();
    }

    @ApiOperation("获取转账第二次审核转账列表")
    @GetMapping(value = "transfer-second-list")
    public CommonListResponse<SendCoin> getSecondTransferList(@ApiParam("币种") @RequestParam(name = "coinName", required = false) String coinName,
                                                              @ApiParam(value = "用户名") @RequestParam(name = "userName", required = false) String userName,
                                                              @ApiParam(value = "用户分组") @RequestParam(name = "groupType", required = false) String groupType,
                                                              @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
                                                              @ApiParam(value = "结束时间", required = false) @RequestParam(name = "endTime", required = false) String endTime,
                                                              @ApiParam("pageNo") @RequestParam Integer pageNo,
                                                              @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null) {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                userId = user.getId();
            }
        }
        // Page<SendCoin> rst = sendCoinService.getTransferList(userId, coinName, status, pageNo, pageSize);
        Page<SendCoin> rst = sendCoinService.getSecondTransferListByTime(userId, coinName, startTime, endTime, groupType, pageNo, pageSize);

        for (SendCoin sc : rst) {
            if (sc.getSecondStatus().equals("1") && sc.getStatus() == SendCoinStatus.PASSED) {
                sc.setSecondStatus("0");
            }
            if (sc.getUserId() != null) {
                User user = userService.getUserByUserId(sc.getUserId());
                if (user != null) {
                    sc.setUserName(user.getUserName());
                }
            }
        }
        return new CommonListResponse().fromPage(rst);
    }


    @ApiOperation("第二次审核失败")
    @PostMapping(value = "transfer-sencond-check-fail")
    public CommonResponse transferSecondCheckFail(@ApiParam("转账ID") @RequestParam Integer id) {
        sendCoinService.transferSecondCheckFail(id, 0);
        return new CommonResponse();
    }

    @ApiOperation("第二次审核通过")
    @PostMapping(value = "transfer-sencond-check-pass")
    public CommonResponse transferSencondCheckPass(@ApiParam("转账ID") @RequestParam Integer id) {

        sendCoinService.transferSecondCheckPass(id, 0);
        return new CommonResponse();

    }

    @ApiOperation("获取用户的资产流水列表")
    @GetMapping(value = "bill-list")
    public CommonListResponse<UserBillVO> getTransferList(
            @ApiParam(value = "用户名", required = false) @RequestParam(name = "userName", required = false) String userName,
            @ApiParam(value = "用户真实名", required = false) @RequestParam(name = "realName", required = false) String realName,
            @ApiParam(value = "用户分组") @RequestParam(name = "groupType", required = false) String groupType,
            @ApiParam(value = "格式 yyyy-mm-dd HH:mm:ss, 比如 2018-06-29 00:00:00", required = false) @RequestParam(name = "startTime", required = false) String startTime,
            @ApiParam(value = "结束时间", required = false) @RequestParam(name = "endTime", required = false) String endTime,
            @ApiParam("币种名称") @RequestParam(required = false) String coinName,
            @ApiParam(value = "账户特性", required = false) @RequestParam(name = "subType", required = false) Integer subType,
            @ApiParam(value = "变动类型", required = false) @RequestParam(name = "reason", required = false) String reason,
            @ApiParam("pageNo") @RequestParam Integer pageNo,
            @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null) {
            User userInfo = userService.getUserByUserName(userName);
            if (userInfo != null) {
                userId = userInfo.getId();
            }
        }
        //Page<UserBillVO> rst = financeService.getBillList(userId, coinName, pageNo, pageSize);
        Page<UserBillVO> rst = null;
        if (groupType != null && groupType != "" || userId != null || realName != null && realName != "") {
            rst = financeService.getUserBillsByTime(userId, coinName, startTime, endTime, groupType, subType, reason, realName, pageNo, pageSize);
            // 增加用户名称显示
        } else {
            rst = financeService.getUserBillsPage(userId, coinName, startTime, endTime, subType, reason, pageNo, pageSize);
        }

        for (UserBillVO vo : rst) {
            User user = userService.getUserByUserId(vo.getUserId());
            if (user != null) {
                vo.setUserName(user.getUserName());
            }
        }
        return CommonListResponse.fromPage(rst);
    }

    @ApiOperation("获取用户的资产信息")
    @GetMapping(value = "user-coin-info")
    public CommonListResponse<UserCoinInfoVO> getUserCoinInfo(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("分组类型") @RequestParam(required = false) String groupType,
            @ApiParam("币种名称") @RequestParam(required = false) String coinName,
            @ApiParam("用户真实姓名") @RequestParam(required = false) String realName,
            @ApiParam(required = false, name = "column", value = "排序字段名称(availableBalance,freezeBalance)") @RequestParam(required = false) String column,
            @ApiParam(required = false, name = "sort", value = "排序(asc,desc)") @RequestParam(required = false) String sort,
            @ApiParam("pageNo") @RequestParam Integer pageNo,
            @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null && userName.trim().length() > 0) {
            User user = userService.getUserByUserName(userName.trim());
            if (user == null) {
                return new CommonListResponse();
            }
            userId = user.getId();
        }

        Page<UserCoinInfoVO> rst = financeService.getUserCoinInfo(userId, coinName, groupType, realName, column, sort, pageNo, pageSize);
        return CommonListResponse.fromPage(rst);
    }


    @ApiOperation(value = "获取用户资产某个币种的总和")
    @GetMapping("/user-coin-total")
    public CommonResponse<BigDecimal> getUserCoinTotal(@ApiParam("币种名") @RequestParam(required = true) String coinName,
                                                       @ApiParam("组名") @RequestParam(required = false) String groupType) {
        BigDecimal total = new BigDecimal(0);
        total = financeService.getUserCoinAmountByCoinName(coinName, groupType);
        return new CommonResponse<>(total);
    }

    @ApiOperation(value = "统计所有收到的币")
    @GetMapping("/total-recv-coin")
    public CommonResponse<List<ReceivedCoin>> getTotalReceiveFromExternal() {
        List<ReceivedCoin> coin = transferService.getTotalReceiveFromExternal();
        return new CommonResponse<>(coin);
    }

    @ApiOperation(value = "统计今天收到的币")
    @GetMapping("/today-recv-coin")
    public CommonResponse<List<ReceivedCoin>> getTodayReceiveFromExternal() {
        List<ReceivedCoin> coin = transferService.getTodayReceiveFromExternal();


        return new CommonResponse<>(coin);
    }

    @ApiOperation(value = "统计所有发送的币")
    @GetMapping("/total-send-coin")
    public CommonResponse<List<SendCoin>> getTotalSendToExternal() {
        List<SendCoin> sends = sendCoinService.statTotalSendSuccessCoins();
        return new CommonResponse<>(sends);
    }

    @ApiOperation(value = "统计指定用户所有收到的币")
    @GetMapping("/user-total-recv-coin")
    public CommonResponse<List<ReceivedCoin>> getUserTotalReceiveFromExternal(
            @ApiParam("用户名") @RequestParam(required = true) String userName
    ) {
        User user = userService.getUserByUserName(userName);
        if (user == null) return new CommonResponse();
        List<ReceivedCoin> coin = transferService.getUserTodayReceiveFromExternal(user.getId());
        return new CommonResponse<>(coin);
    }

    @ApiOperation(value = "统计指定用户所有提走的币")
    @GetMapping("/user-total-send-coin")
    public CommonResponse<List<SendCoin>> getUserTotalSendCoin(
            @ApiParam("用户名") @RequestParam(required = true) String userName) {
        User user = userService.getUserByUserName(userName);
        if (user == null) return new CommonResponse();
        List<SendCoin> sends = sendCoinService.statUserTotalSendSuccessCoins(user.getId());
        return new CommonResponse<>(sends);
    }

    @ApiOperation("导出用户的资产信息")
    @GetMapping(value = "user-coin-info-export")
    public void exportUserCoinInfo(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("分组类型") @RequestParam(required = false) String groupType,
            @ApiParam("币种名称") @RequestParam(required = false) String coinName,
            @ApiParam("用户真实姓名") @RequestParam(required = false) String realName,
            @ApiParam(required = false, name = "column", value = "排序字段名称(availableBalance,freezeBalance)") @RequestParam(required = false) String column,
            @ApiParam(required = false, name = "sort", value = "排序(asc,desc)") @RequestParam(required = false) String sort,
            HttpServletResponse response, HttpServletRequest request) {
        log.info("exportUserCoinInfo userName=" + userName + ",groupType=" + groupType + ",coinName=" + coinName
                + ",realName=" + realName + ",column=" + column + ",sort=" + sort);
        if (isBool) {
            log.info("导出繁忙中，请稍后再试");
            return;
        }

        Integer userId = null;
        if (userName != null && userName.trim().length() > 0) {
            User user = userService.getUserByUserName(userName.trim());
            if (user == null) {
                return;
            }
            userId = user.getId();
        }

        List<UserCoinInfoVO> list = financeService.getUserCoinInfoAll(userId, coinName, groupType, realName, column, sort);
        if (list != null && list.size() > 0) {
            OutputStream out = null;
            ExportToExcelUtil<UserCoinInfoVO> excelUtil = new ExportToExcelUtil<UserCoinInfoVO>();
            try {
                out = response.getOutputStream();
                excelUtil.setResponseHeader(response, "用户资产信息表");
                //, "物流名称", "物流编号"
                String[] headers = {"用户Id", "用户名", "用户分组", "真实姓名", "币种名称", "钱包地址", "可用资产", "冻结资产"};
                String[] columns = {"userId", "userName", "groupName", "realName", "coinName", "bindAddress", "availableBalanceStr", "freezeBalanceStr"};

                excelUtil.exportExcel(headers, columns, list, out, request, "yyyy-MM-dd hh:mm:ss");

            } catch (Exception e) {
                log.warn("exprot exception is" + e, e);
            }
        }
    }


    @ApiOperation("获取用户的存钱/分润宝信息")
    @GetMapping(value = "user-coin-saving")
    public CommonListResponse<CoinSavings> getUserCoinSavingInfo(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("币种名称") @RequestParam(required = false) String coinName,
            //@ApiParam(required = false,name = "column",value = "排序字段名称(availableBalance,freezeBalance)") @RequestParam(required = false) String column,
            //@ApiParam(required = false,name = "sort",value = "排序(asc,desc)") @RequestParam(required = false) String sort,
            @ApiParam("pageNo") @RequestParam Integer pageNo,
            @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Integer userId = null;
        if (userName != null && userName.trim().length() > 0) {
            User user = userService.getUserByUserName(userName.trim());
            if (user == null) {
                return new CommonListResponse();
            }
            userId = user.getId();
        }

        Page<CoinSavings> rst = coinSavingsService.getCoinSavings(userId, coinName, pageNo, pageSize);
        return CommonListResponse.fromPage(rst);
    }

    @ApiOperation("设置用户币种vip类型")
    @PostMapping(value = "set-coin-vip-type")
    public CommonResponse setUserCoinVipType(
            @ApiParam("用户名") @RequestParam(required = true) Integer userId,
            @ApiParam("币种名称") @RequestParam(required = true) String coinName,
            @ApiParam("Vip类型，0：非vip，1：vip") @RequestParam Integer vipType) {
        int count = userCoinService.setUserCoinVipType(userId, coinName, vipType);
        Assert.check(count == 0, ErrorCode.ERR_USER_NOT_EXIST);
        return new CommonResponse();
    }


    @ApiOperation(value = "获取用户的冻结资产拨币记录")
    @GetMapping(value = "admin-change-rf-bill")
    public CommonListResponse<UserBill> getAdminChangeRfRecord(
            @ApiParam(name = "page", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(required = true) Integer pageSize
    ) {
        List<String> types = new ArrayList<String>();
        Page<UserBillVO> bills = financeService.getUserBills5(null, UserBillType.SUB_TYPE_RECEIVED_FREEZE, UserBillReason.DISPATCH_RELEASE, null, pageNo, pageSize);

        for (UserBillVO vo : bills) {
            User user = userService.getUserByUserId(vo.getUserId());
            if (user != null) {
                vo.setUserName(user.getUserName());
            }
        }

        return CommonListResponse.fromPage(bills);
    }


}
