package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.CreateTradeRequestVO;
import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.constants.*;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.PriceType;
import com.cmd.exchange.common.vo.TradeLogVo;
import com.cmd.exchange.common.vo.TradeVo;
import com.cmd.exchange.service.MarketService;
import com.cmd.exchange.service.TradeService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 交易接口
 */
@Api(tags = "交易接口，用于在交易市场买卖数字货币")
@RestController
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    TradeService tradeService;
    @Autowired
    MarketService marketService;

    private void updatePrice(CreateTradeRequestVO request) {
        if (request.getPriceType() == PriceType.LIMITED) {
            Assert.check(request.getPrice() == null, ErrorCode.ERR_PRICE_REQUIRED);
        } else {
            //市价单，自动补上价格
            BigDecimal price = null;
            if (request.getType() == TradeType.BUY) {
                //找出价格最高的卖单
                price = tradeService.getHighestPriceSellTrade(request.getCoinName(), request.getSettlementCurrency());
            } else {
                //找出价格最低的买单
                price = tradeService.getLowestPriceBuyTrade(request.getCoinName(), request.getSettlementCurrency());
            }

            if (price == null) {
                //如果没有委托单，则获取最后成交价格
                price = tradeService.getLatestPrice(request.getCoinName(), request.getSettlementCurrency());
            }

            //如果该市场从来没成交过，并且没有未成交的委托单， 那么就无法下市价单
            if (price == null) {
                Assert.check(true, ErrorCode.ERR_MARKET_TRADE_PRICE_NOT_FOUND);
            }

            request.setPrice(price);
        }

        Assert.check(request.getPrice().compareTo(BigDecimal.ZERO) < 0, ErrorCode.ERR_MARKET_TRADE_PRICE_LESS_THAN_ZERO);
    }

    @OpLog(type = OpLogType.OP_TRADE_CREATE)
    @ApiOperation("委托下单")
    @PostMapping(value = "")
    public CommonResponse<Integer> createTrade(@Valid CreateTradeRequestVO request) {
        updatePrice(request);

        TradeVo trade = new TradeVo();
        trade.setType(request.getType()).setCoinName(request.getCoinName()).setSettlementCurrency(request.getSettlementCurrency())
                .setPrice(request.getPrice()).setAmount(request.getAmount());
        trade.setPriceType(request.getPriceType() == PriceType.MARKET ? TradePriceType.TYPE_MARKET_TRANSACTION : TradePriceType.TYPE_LIMITED_TRANSACTION);
        trade.setUserId(ShiroUtils.getUser().getId());
        Integer tradeId = tradeService.createTrade(trade);
        return new CommonResponse<>(tradeId);
    }

    @OpLog(type = OpLogType.OP_TRADE_CANCEL, comment = "'tradeId=' + #id")
    @ApiOperation("撤销订单")
    @PostMapping(value = "/cancel/{id}")
    public CommonResponse cancelTrade(@PathVariable Integer id) {
        tradeService.cancelTrade(ShiroUtils.getUser().getId(), id, UserBillReason.TRADE_USER_CANCEL);
        return new CommonResponse<>();
    }


    @ApiOperation("获取指定用户未完全成交的订单")
    @GetMapping(value = "user-open-trade-list")
    public CommonListResponse<TradeVo> getOpenTradeListByUser(
            @ApiParam("交易类型") @RequestParam(required = true) TradeType type,
            @ApiParam("交易币种") @RequestParam(required = false) String coinName,
            @ApiParam("结算币种") @RequestParam(required = false) String settlementCurrency,
            @ApiParam("分页") @RequestParam Integer pageNo,
            @ApiParam("每页记录数量") @RequestParam Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        Page<TradeVo> tradeList = tradeService.getOpenTradeListByUser(user.getId(), type, coinName, settlementCurrency, pageNo, pageSize);
        return CommonListResponse.fromPage(tradeList);
    }

    @ApiOperation("获取指定用户的成交订单列表")
    @GetMapping(value = "user-trade-log-list")
    public CommonListResponse<TradeLogVo> getTradeLogListByUser(
            @ApiParam("交易类型") @RequestParam(required = true) TradeType type,
            @ApiParam("交易币种") @RequestParam(required = false) String coinName,
            @ApiParam("结算币种") @RequestParam(required = false) String settlementCurrency,
            @ApiParam("分页") @RequestParam(required = false) Integer pageNo,
            @ApiParam("每页记录数量") @RequestParam(required = false) Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        Page<TradeLogVo> tradeList = tradeService.getTradeLogListByUser(user.getId(), type, coinName, settlementCurrency, pageNo, pageSize);
        if (type != null && type == TradeType.ALL) {
            for (TradeLogVo vo : tradeList) {
                vo.setQueryType(vo.getType());
            }
        } else if (type != null) {
            for (TradeLogVo vo : tradeList) {
                vo.setQueryType(type);
                vo.setType(type);
            }
        }
        return CommonListResponse.fromPage(tradeList);
    }

    @ApiOperation("获取指定用户的委托历史(已经成交或者取消的订单)")
    @GetMapping(value = "user-trade-list")
    public CommonListResponse<TradeVo> getTradeListByUser(
            @ApiParam("交易类型") @RequestParam(required = false) TradeType type,
            @ApiParam("交易币种") @RequestParam(required = false) String coinName,
            @ApiParam("结算币种") @RequestParam(required = false) String settlementCurrency,
            @ApiParam("分页") @RequestParam(required = false) Integer pageNo,
            @ApiParam("每页记录数量") @RequestParam(required = false) Integer pageSize) {
        User user = ShiroUtils.getUser();
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        if (pageNo == null) {
            pageNo = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        Assert.check(pageSize >= 100, ErrorCode.ERR_PAGE_SIZE_TOO_LARGE);
        Page<TradeVo> tradeList = tradeService.getTradeHistoryListByUser(user.getId(), coinName, settlementCurrency, type, pageNo, pageSize);
        // 对于市价，把数量设置为成交数量
        for (TradeVo tv : tradeList) {
            if (tv.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION) {
                tv.setAmount(tv.getDealAmount());
            }
        }
        return CommonListResponse.fromPage(tradeList);
    }

    public static void main(String[] args) {

        Date date1 = new Date();

        //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd "); //加上时间
        //必须捕获异常
        try {
            String datestr = sDateFormat.format(date1);
            System.out.println(datestr);
        } catch (Exception e) {

        }
    }


}
