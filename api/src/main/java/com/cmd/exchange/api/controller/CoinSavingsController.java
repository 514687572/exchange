package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.CoinSavingsVo;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.model.CoinSavings;
import com.cmd.exchange.common.model.UserDayProfit;
import com.cmd.exchange.common.model.UserMachine;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.UserBillVO;
import com.cmd.exchange.common.vo.UserDayProfitVo;
import com.cmd.exchange.common.vo.UserMachineVo;
import com.cmd.exchange.service.*;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Api(tags = "锁仓/存款/分润宝收益")
@RestController
@RequestMapping("/coin-savings")
public class CoinSavingsController {
    @Autowired
    private CoinSavingsService coinSavingsService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private ShareOutBonusService shareOutBonusService;

    @Autowired
    private UserMachineService userMachineService;

    @ApiOperation("锁仓/存储平台币")
    @PostMapping(value = "save-plat-coin")
    public CommonResponse savingPlatformCoin(@ApiParam(value = "存储的数量，必须是10000的倍数") @RequestParam BigDecimal savingBalance) {
        coinSavingsService.savingCoin(ShiroUtils.getUser().getId(), configService.getPlatformCoinName(), savingBalance);
        return new CommonResponse();
    }

    @ApiOperation("取出锁仓/存储的平台币")
    @PostMapping(value = "withdraw-plat-coin")
    public CommonResponse withdrawingPlatformCoin(@ApiParam(value = "取出的数量，必须是10000的倍数") @RequestParam BigDecimal withdrawBalance) {
        coinSavingsService.withdrawingCoin(ShiroUtils.getUser().getId(), configService.getPlatformCoinName(), withdrawBalance);
        return new CommonResponse();
    }

    /*@ApiOperation("获取锁仓/存储的平台币")
    @GetMapping(value = "get-plat-coin")
    public CommonResponse<CoinSavings> getPlatformCoin() {
        CoinSavings savings = coinSavingsService.getCoinSavings(ShiroUtils.getUser().getId(), configService.getPlatformCoinName());
        return new CommonResponse(savings);
    }*/

    /*@ApiOperation("获取总锁仓/存储的平台币")
    @GetMapping(value = "get-total-plat-coin")
    public CommonResponse<BigDecimal> getTotalPlatformCoinSavings() {
        BigDecimal total = coinSavingsService.getTotalCoinSavings(configService.getPlatformCoinName());
        return new CommonResponse(total);
    }*/

    /*@ApiOperation("获取当前锁仓/存储的基本信息（自己+平台）")
    @GetMapping(value = "current-saving-info")
    public CommonResponse<CoinSavingsVo> getCurrentSavingInfo() {
        CoinSavings savings = coinSavingsService.getCoinSavings(ShiroUtils.getUser().getId(), configService.getPlatformCoinName());
        BigDecimal total = coinSavingsService.getTotalCoinSavings(configService.getPlatformCoinName());
        CoinSavingsVo vo = new CoinSavingsVo();
        vo.setTotalBalance(total);
        if(savings != null) {
            vo.setCoinName(savings.getCoinName());
            vo.setUserId(savings.getUserId());
            vo.setUserBalance(savings.getBalance());
            BigDecimal mineQuotiety = configService.getConfigValue(ConfigKey.TRADE_MINING_QUOTIETY, new BigDecimal("200"), false);
            BigDecimal mineSaveUint = configService.getConfigValue(ConfigKey.TRADE_MINING_SAVE_UNIT, new BigDecimal("10000"), false);
            vo.setMaxCoinPerHour(mineQuotiety.multiply(savings.getBalance()).divide(mineSaveUint, 0, RoundingMode.HALF_UP));
        }

        return new CommonResponse(vo);
    }*/

    @ApiOperation(value = "获取用户的锁仓记录")
    @GetMapping(value = "add-records")
    public CommonListResponse<UserBillVO> getUserAddSavingBills(
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(required = true) Integer pageSize
    ) {
        Page<UserBillVO> bills = financeService.getUserBills(ShiroUtils.getUser().getId(), UserBillReason.SAVING_ADD, configService.getPlatformCoinName(), pageNo, pageSize);

        for (UserBillVO bill : bills) {
            if (bill.getChangeAmount() != null && bill.getChangeAmount().compareTo(BigDecimal.ZERO) < 0) {
                bill.setChangeAmount(bill.getChangeAmount().negate());
            }
        }
        return CommonListResponse.fromPage(bills);
    }

    @ApiOperation(value = "获取用户的解锁记录")
    @GetMapping(value = "sub-records")
    public CommonListResponse<UserBillVO> getUserSubSavingBills(
            @ApiParam(name = "pageNo", value = "当前页") @RequestParam(required = true) Integer pageNo,
            @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(required = true) Integer pageSize
    ) {
        Page<UserBillVO> bills = financeService.getUserBills(ShiroUtils.getUser().getId(), UserBillReason.SAVING_SUB, configService.getPlatformCoinName(), pageNo, pageSize);

        return CommonListResponse.fromPage(bills);
    }

    @ApiOperation(value = "获取用户的昨日收益")
    @GetMapping(value = "yesterday-profit")
    public CommonResponse<UserDayProfitVo> getUserYesterdayProfit() {
        UserDayProfit userDayProfit = shareOutBonusService.getUserLastProfit(ShiroUtils.getUser().getId());
        if (userDayProfit == null) {
            userDayProfit = new UserDayProfit();
            Date date = new Date();
            userDayProfit.setProfitTime(date);
            userDayProfit.setUserId(ShiroUtils.getUser().getId());
        }


        CoinSavings coinSavings = coinSavingsService.getCoinSavings(ShiroUtils.getUser().getId(), configService.getPlatformCoinName());
        if (coinSavings != null) {
            userDayProfit.setTotalBalance(coinSavings.getBalance());
        }

        UserDayProfitVo userDayProfitVo = new UserDayProfitVo();
        int dayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        Date todayBegin = new Date(1000L * dayBeginTimestamp);
        UserMachineVo searchVo = new UserMachineVo();
        searchVo.setYesterdayTime(todayBegin);
        searchVo.setUserId(ShiroUtils.getUser().getId());
        List<UserMachineVo> userMachines = userMachineService.getUserMachines(searchVo);
        UserMachineVo userMachineVo;
        if(userMachines.size() > 0 )
        {
            userMachineVo = userMachines.get(0);
            userDayProfitVo.setMachineName(userMachineVo.getMachineName());
            userDayProfitVo.setMachineCoin(userMachineVo.getMachineCoin());
            userDayProfitVo.setMachineOpen(userMachineVo.getIsOpen());
            userDayProfitVo.setMachineYesterdayIncome(userMachineVo.getYesterdayIncome());
        }else{
            searchVo.setYesterdayTime(null);
            userMachines = userMachineService.getUserMachines(searchVo);
            if(userMachines.size() > 0 )
            {
                userMachineVo = userMachines.get(0);
                userDayProfitVo.setMachineName(userMachineVo.getMachineName());
                userDayProfitVo.setMachineCoin(userMachineVo.getMachineCoin());
                userDayProfitVo.setMachineOpen(userMachineVo.getIsOpen());
                //userDayProfitVo.setMachineYesterdayIncome(userMachineVo.getYesterdayIncome());
            }
        }



        BeanUtils.copyProperties(userDayProfit,userDayProfitVo);

        userDayProfitVo.setSuperNodeDayProfit(userDayProfit.getSuperNodeProfit());
        userDayProfitVo.setSuperNodeTotalProfit(userDayProfit.getSuperNodeTotalProfit());
        userDayProfitVo.setUsdtDayTeamAward(userDayProfit.getUsdtTeamReward());
        userDayProfitVo.setUsdtTeamTotalProfit(userDayProfit.getUsdtTeamTotalReward());
        userDayProfitVo.setUsdtDayTeamTotaExchange(userDayProfit.getUsdtTeamGetAmount());


        return new CommonResponse(userDayProfitVo);
    }
    @ApiOperation(value = "用户矿机是否开启/关闭")
    @GetMapping(value = "machineOpen")
    public CommonResponse machineOpen(@ApiParam(required = true, name = "isOpen", value = "isOpen") @RequestParam(value = "isOpen", required = false) Integer isOpen) {


        int i = userMachineService.machineOpem(new UserMachine().setUserId(ShiroUtils.getUser().getId()).setIsOpen(isOpen));

        return new CommonResponse();
    }
}
