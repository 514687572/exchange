package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.TradeReturnRewardVo;
import com.cmd.exchange.api.vo.TransferOutRequestVO;
import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.OpLogType;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserBill;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.enums.TransferType;
import com.cmd.exchange.common.model.SendCoin;
import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.common.vo.CoinTransferVo;
import com.cmd.exchange.service.*;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数字货币转入转出
 * 个人财务信息
 */
@Api(tags = "财务中心（数字货币转入转出，个人财务信息）")
@RestController
@RequestMapping("finance")
public class FinanceController {

    @Autowired
    FinanceService financeService;
    @Autowired
    AdapterService adapterService;
    @Autowired
    ConfigService configService;
    @Autowired
    UserService userService;
    @Autowired
    RewardTradeFeeService rewardTradeFeeService;
    @Autowired
    UserRecommendService userRecommendService;


    @ApiOperation(value = "数字币--->获取可用余额")
    @GetMapping("coin-balance")
    public CommonResponse<UserCoin> getCoinBalance(
            @ApiParam(required = true, name = "coinname", value = "币种名称") @RequestParam(value = "coinname") String coinname) {
        return new CommonResponse(financeService.getBalanceByCoinName(ShiroUtils.getUser().getId(), coinname));
    }

    @ApiOperation(value = "获取所有资产余额")
    @GetMapping("user-balance")
    public CommonResponse<List<UserCoinVO>> getAllCoinBalance() {
        List<UserCoinVO> userCoinList = financeService.getBalanceByUserId(ShiroUtils.getUser().getId());
        return new CommonResponse(userCoinList);
    }

    @ApiOperation(value = "获取所有基于基础货币计算出来的资产余额(返回中只有coinName和availableBalance有效)")
    @GetMapping("user-total-base-balance")
    public CommonResponse<UserCoin> getUserTotalBalance() {
        String coinName = configService.getMarketBaseCoinName();
        if (coinName == null) {
            coinName = configService.getPlatformCoinName();
        }
        UserCoin coin = new UserCoin();
        BigDecimal total = financeService.getUserTotalAssetConvertTo(ShiroUtils.getUser().getId(), coinName);
        coin.setAvailableBalance(total);
        coin.setCoinName(coinName);
        return new CommonResponse(coin);
    }

    @ApiOperation(value = "获取数字货币绑定的地址（在 数字币--->转入 中调用）")
    @PostMapping("bind-address")
    public CommonResponse<String> getBindAddress(
            @ApiParam(required = true, name = "coinname", value = "币种名称") @RequestParam(value = "coinname") String coinname) {
        String address = adapterService.getAccountAddress(ShiroUtils.getUser().getId(), coinname);
        return new CommonResponse(address);
    }


    @ApiOperation("获取转账出去列表")
    @GetMapping(value = "transfer-list")
    public CommonListResponse<SendCoin> getTransferList(@ApiParam("币种") @RequestParam String coinName,
                                                        @ApiParam("状态:0申请，1通过，2未通过") @RequestParam Integer status,
                                                        @ApiParam("分页：1开始") @RequestParam Integer pageNo,
                                                        @ApiParam("pageSize") @RequestParam Integer pageSize) {
        Page<SendCoin> rst = financeService.getTransfer(ShiroUtils.getUser().getId(), coinName, status, pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @OpLog(type = OpLogType.OP_FINANCE_TRANSFER_OUT, comment = "'coinName=' +  #coinName + ', toAddress=' +  #toAddress  + ', amount=' +  #amount")
    @ApiOperation("转账")
    @PostMapping(value = "transfer-out")
    public CommonResponse transferOut(@Valid TransferOutRequestVO transferOutReqVO) {

        financeService.transfer(ShiroUtils.getUser().getId(), transferOutReqVO.getCoinName(), transferOutReqVO.getToAddress(),
                transferOutReqVO.getAmount(), transferOutReqVO.getComment(), transferOutReqVO.getValidCode(), transferOutReqVO.getPaypassword());
        return new CommonResponse();
    }

    @OpLog(type = OpLogType.OP_FINANCE_TRANSFER_OUT, comment = "'SendReceivedFreeze:coinName=' +  #coinName + ', toAddress=' +  #toAddress  + ', amount=' +  #amount")
    @ApiOperation("转移冻结金的转入金")
    @PostMapping(value = "transfer-received-freeze")
    public CommonResponse transferReceivedFreeze(@Valid TransferOutRequestVO transferOutReqVO) {
        // 根据用户地址获取用户ID
        User user = userService.getUserByUserName(transferOutReqVO.getToAddress().trim());
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        financeService.transferReceivedFreeze(ShiroUtils.getUser().getId(), transferOutReqVO.getCoinName(), user.getId(),
                transferOutReqVO.getAmount(), transferOutReqVO.getComment(), transferOutReqVO.getValidCode(), transferOutReqVO.getPaypassword());
        return new CommonResponse();
    }

    @ApiOperation("转移冻结金的转入金手续费")
    @GetMapping(value = "feeUsdt")
    public CommonResponse feeUsdt() {
        // 冻结金扣除手续费 固定usdt作为手续费
        BigDecimal feeUsdt = configService.getConfigValue(ConfigKey.LOCK_WAREHOUSE_TRANSFER_FEE_FIXED, BigDecimal.ZERO, false);
        return new CommonResponse(feeUsdt);
    }

    @ApiOperation(value = "获取数字货币的转账历史记录")
    @GetMapping(value = "transfer-history")
    public CommonListResponse<CoinTransferVo> transferHistory(
            @ApiParam(required = false, value = "币种名称") @RequestParam(required = false) String coinName,
            @ApiParam(required = false, value = "SEND:转出,RECEIVED:转入,ALL:所有") @RequestParam(required = false) TransferType type,
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(required = true) Integer pageSize) {

        CoinTransferReqVO reqVO = new CoinTransferReqVO();

        reqVO.setCoinName(coinName).setPageNo(pageNo).setPageSize(pageSize);

        reqVO.setUserId(ShiroUtils.getUser().getId()).setType(type);

        Page<CoinTransferVo> transferList = financeService.transferHistory(reqVO);
        return CommonListResponse.fromPage(transferList);
    }

    @ApiOperation(value = "获取用户的充值记录（就是挖矿，转账，分红记录）")
    @GetMapping(value = "recharge-history")
    public CommonListResponse<UserBill> getRechargeRecord(
            @ApiParam(required = false, value = "充值类型,不填写返回所有类型") @RequestParam(required = false) String type,
            @ApiParam(required = false, value = "币种名称,不填写返回所有币种") @RequestParam(required = false) String coinName,
            @ApiParam(required = false, value = "开始时间") @RequestParam(required = false) String startDate,
            @ApiParam(required = false, value = "结束时间") @RequestParam(required = false) String endDate,
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(required = true) Integer pageSize
    ) {
        List<String> types = new ArrayList<String>();
        if (type == null || type.trim().length() == 0) {
//            types.add(UserBillReason.TRADE_BONUS);
//            types.add(UserBillReason.TRADE_REC_BONUS);
//            types.add(UserBillReason.TRADE_SHARE_BONUS);
//            types.add(UserBillReason.REGISTER_REWARD);
//            types.add(UserBillReason.REFERRER_REWARD);
            types.add(UserBillReason.BC_RECEIVED_COIN);
            types.add(UserBillReason.TRANSFER);
            types.add(UserBillReason.WITHDRAW);
            types.add(UserBillReason.INVEST_SUB);
            types.add(UserBillReason.INVEST_ADD);
            types.add(UserBillReason.DYNAMIC_INCOME);

        } else {
            types.add(type.trim());
        }
        if (coinName != null) {
            coinName = coinName.trim();
        }
        Page<UserBill> bills = financeService.getUserBillsByReasons(ShiroUtils.getUser().getId(), types, coinName, startDate, endDate, pageNo, pageSize);

        return CommonListResponse.fromPage(bills);
    }

    @ApiOperation(value = "获取用户的账单记录")
    @GetMapping(value = "bill")
    public CommonListResponse<UserBillVO> getUserBills(
            @ApiParam(required = false, value = "充值类型,不填写返回所有类型") @RequestParam(required = false) String type,
            @ApiParam(required = false, value = "币种名称,不填写返回所有币种") @RequestParam(required = false) String coinName,
            @ApiParam(name = "page", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "size", value = "每页条数") @RequestParam(required = true) Integer pageSize
    ) {
        if (type != null) type = type.trim();
        if (coinName != null) coinName = coinName.trim();
        Page<UserBillVO> bills = financeService.getUserBills(ShiroUtils.getUser().getId(), type, coinName, pageNo, pageSize);

        return CommonListResponse.fromPage(bills);
    }

    @ApiOperation(value = "获取用户直推列表")
    @GetMapping(value = "trade-ret-reward")
    public CommonResponse<UserRecIntegrVo> getUserTradeRewardInfo(@ApiParam(name = "userId", value = "获取用户直推列表") @RequestParam(required = false) Integer userId) {
//        TradeReturnRewardVo vo = new TradeReturnRewardVo();
//        int recUserCount = userService.getRec1AndRec2UserCount(ShiroUtils.getUser().getId());
//        vo.setRecUserCount(recUserCount);
        /*Map<String, BigDecimal> rewards = rewardTradeFeeService.getUserTradeFeeRewards(ShiroUtils.getUser().getId());
        if(rewards == null || rewards.size() == 0) return new CommonResponse(vo);
        for(Map.Entry<String, BigDecimal> entry : rewards.entrySet()) {
            TradeReturnRewardVo.RewardInfo info = new TradeReturnRewardVo.RewardInfo();
            info.coinName = entry.getKey();
            info.reward = entry.getValue();
            vo.getRewards().add(info);
        }*/

        User user = ShiroUtils.getUser();

        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        UserRecIntegrVo userRecIntegrVo = new UserRecIntegrVo();

        if(userId == null){

            userRecIntegrVo = userRecommendService.getUserChilByUserId(user);
        }else{
            userRecIntegrVo = userRecommendService.getUserChilByUserId(userId);
        }

        return new CommonResponse(userRecIntegrVo);
    }

}
