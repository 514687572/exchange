package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserTradeStat;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.service.UserService;
import com.cmd.exchange.service.UserTradeStatService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "统计用户的交易盈亏情况")
@RestController
@RequestMapping("/user-trade-stat")
public class UserTradeStatController {
    @Autowired
    private UserTradeStatService userTradeStatService;
    @Autowired
    private UserService userService;

    Map<String, Integer> orderToType = new HashMap<>();

    public UserTradeStatController() {
        orderToType.put(("holdCoinPrice" + "/desc").toLowerCase(), 1);
        orderToType.put(("holdCoinPrice" + "/asc").toLowerCase(), 2);
        orderToType.put(("profit" + "/desc").toLowerCase(), 3);
        orderToType.put(("profit" + "/asc").toLowerCase(), 4);
    }

    private void updateUserTradeStat(Page<UserTradeStat> page) {
        if (page != null) {
            for (UserTradeStat uts : page) {
                User user = userService.getUserByUserId(uts.getUserId());
                if (user != null) {
                    uts.setUserName(user.getUserName());
                } else {
                    uts.setUserName("NoUserWithId:" + uts.getUserId());
                }
                if (uts.getHoldCoinPrice() != null) {
                    if (uts.getHoldCoinPrice().scale() > 8) {
                        uts.setHoldCoinPrice(uts.getHoldCoinPrice().setScale(8, RoundingMode.HALF_UP));
                    }
                }
            }
        }
    }


    @ApiOperation(value = "查询统计用户的交易盈亏情况")
    @GetMapping(value = "search-stat")
    public CommonListResponse<UserTradeStat> searchUserTradeStat(
            @ApiParam(required = false, name = "userId", value = "用户名") @RequestParam(required = false) String userName,
            @ApiParam(required = false, name = "coinName", value = "币种名称") @RequestParam(required = false) String coinName,
            @ApiParam(required = false, name = "settlementCurrency", value = "结算货币名称") @RequestParam(required = false) String settlementCurrency,
            @ApiParam(required = false, name = "orderType", value = "排序类型，0：用户id升序，1：coinName 升序，2：settlementCurrency升序，3，持仓成本降序") @RequestParam(required = false) Integer orderType,
            @ApiParam(required = false, name = "pageNo", value = "分页页码") @RequestParam(required = true) int pageNo,
            @ApiParam(required = false, name = "pageSize", value = "页码大小") @RequestParam(required = true) int pageSize,
            @ApiParam(required = false, name = "column", value = "排序字段名称") @RequestParam(required = false) String column,
            @ApiParam(required = false, name = "sort", value = "排序") @RequestParam(required = false) String sort) {
        if (orderType == null && column != null) {
            orderType = getOrderType(column, sort, orderToType);
        }
        Integer userId = null;
        if (userName != null && userName.trim().length() > 0) {
            User user = userService.getUserByUserName(userName.trim());
            if (user == null) {
                return new CommonListResponse<UserTradeStat>();
            }
            userId = user.getId();
        }
        Page<UserTradeStat> page = userTradeStatService.searchUserTradeStat(userId, coinName, settlementCurrency, orderType, pageNo, pageSize);
        updateUserTradeStat(page);
        return CommonListResponse.fromPage(page);
    }

    private Integer getOrderType(String column, String sort, Map<String, Integer> orderToType) {
        if (column == null) return null;
        if (sort == null) sort = "asc";
        Integer type = orderToType.get((column + "/" + sort).toLowerCase());
        return type;
    }


    @ApiOperation(value = "统计用户某个交易对的当前交易盈亏情况")
    @GetMapping(value = "stat-latest-trade")
    public CommonListResponse<UserTradeStat> statCurrentUserTrade(
            @ApiParam(required = true, name = "coinName", value = "币种名称") @RequestParam(required = true) String coinName,
            @ApiParam(required = true, name = "settlementCurrency", value = "结算货币名称") @RequestParam(required = true) String settlementCurrency,
            @ApiParam(required = false, name = "userId", value = "用户名") @RequestParam(required = false) String userName,
            @ApiParam(required = false, name = "orderType", value = "排序 1 持仓成本降序，2 持仓成本升序 3 盈利总额降序 4 盈利总额升序") @RequestParam(required = false) Integer orderType,
            @ApiParam(required = false, name = "pageNo", value = "分页页码") @RequestParam(required = true) int pageNo,
            @ApiParam(required = false, name = "pageSize", value = "页码大小") @RequestParam(required = true) int pageSize,
            @ApiParam(required = false, name = "column", value = "排序字段名称") @RequestParam(required = false) String column,
            @ApiParam(required = false, name = "sort", value = "排序") @RequestParam(required = false) String sort
    ) {
        if (orderType == null && column != null) {
            orderType = getOrderType(column, sort, this.orderToType);
        }
        Integer userId = null;
        if (userName != null && userName.trim().length() > 0) {
            User user = userService.getUserByUserName(userName.trim());
            if (user == null) {
                return new CommonListResponse<UserTradeStat>();
            }
            userId = user.getId();
        }
        Page<UserTradeStat> page = userTradeStatService.statCurrentUserTrade(coinName, settlementCurrency, userId, orderType, pageNo, pageSize, column, sort);
        updateUserTradeStat(page);
        return CommonListResponse.fromPage(page);
    }
}
