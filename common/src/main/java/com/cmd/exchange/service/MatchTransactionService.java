package com.cmd.exchange.service;

import com.cmd.exchange.common.componet.DayAmountLimit;
import com.cmd.exchange.common.componet.DayCountLimit;
import com.cmd.exchange.common.componet.GlobalDataComponet;
import com.cmd.exchange.common.componet.MonthAmountLimit;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.TradePriceType;
import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.ReleasePolicyEnum;
import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.mapper.TradeLogMapper;
import com.cmd.exchange.common.mapper.TradeMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.Trade;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.vo.UserCoinVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 交易撮合服务，撮合2个交易
 */
@Service
public class MatchTransactionService {
    private static Logger log = LoggerFactory.getLogger(MatchTransactionService.class);
    // 这里自己注入自己，是一个比较变态的方式，主要原因是调用exchange的时候自动启动事务，要不函数自己内部调用自己是没有自动事务启动的
    @Autowired
    MatchTransactionService me;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private TradeLogMapper tradeLogMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ServerTaskService serverTaskService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private CoinService coinService;

    @Autowired
    private GlobalDataComponet globalDataComponet;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;
    // 保存所有市场信息
    private Hashtable<String, Market> markets = new Hashtable<String, Market>();
    // 保存所有币种信息
    private Hashtable<String, Coin> coins = new Hashtable<String, Coin>();

    // 最大价格设置
    private static final BigDecimal buyMaxPrice = new BigDecimal("999999999999999999999999");
    // 用于计算 交易对2个用户之间的交易次数
    private DayCountLimit dayCountLimit = new DayCountLimit();
    // 用于计算用户的奖励次数
    private DayCountLimit dayUserCountLimit = new DayCountLimit();
    // 用户按月最大释放币的数量
    private MonthAmountLimit monthAmountLimit;
    // 用户每天最大释放币的数据
    private DayAmountLimit dayAmountLimit;

    @Value("${trade.match.memory:true}")
    private boolean useMemoryMatch;
    // 每次读的的最大记录数
    @Value("${trade.match.load_num:100}")
    private int loadRecordNum = 100;
    // 上次加载数据市价
    private long lastLoadTradeTime;
    private long lastWorkTime;

    // 没有匹配的挂单
    static class WaitMatchTrades {
        String coinName;
        String settlementCurrency;
        TreeSet<Trade> buyTrades = new TreeSet<Trade>();
        TreeSet<Trade> sellTrades = new TreeSet<Trade>();
    }

    // 所有挂单统计,key是coinName/settlementCurrency，例如BTC/USDT
    private HashMap<String, WaitMatchTrades> waitTradesMap = new HashMap<String, WaitMatchTrades>();
    // 从数据库获取到的最新的还没有进行匹配的单
    private List<Trade> newMatchTrades = new ArrayList<Trade>();

    // 转入冻结金在交易的时候释放占交易总额的比例
    private BigDecimal receivedReleaseRate = BigDecimal.ZERO;

    // 转入冻结金在交易的时候释放占交易总额的比例（VIP）
    private BigDecimal receivedReleaseRateVip = BigDecimal.ZERO;


    /**
     * 一个交易计算结果
     */
    public static class TradeResult {
        BigDecimal price;                // 成交价格
        BigDecimal amount;               // 交易数字货币的数量(包括费用）
        BigDecimal totalCurrency;      // 结算货币数量(包括费用）
        BigDecimal buyFeeCoin;         // 扣除的数字货币的费用
        BigDecimal sellFeeCurrency;   // 结算货币的费用
        BigDecimal amountTran;        // 交易数字货币的数量(不包括费用）
        BigDecimal currencyTran;      // 结算货币数量(不包括费用）
    }

    private void addWaitMatchTrade(Trade trade) {
        if (!this.useMemoryMatch) return;
        String key = (trade.getCoinName() + "/" + trade.getSettlementCurrency()).toUpperCase();
        WaitMatchTrades trades = waitTradesMap.get(key);
        if (trades == null) {
            trades = new WaitMatchTrades();
            trades.coinName = trade.getCoinName();
            trades.settlementCurrency = trade.getSettlementCurrency();
            waitTradesMap.put(key, trades);
        }
        if (trade.getType() == TradeType.BUY.getValue()) {
            trades.buyTrades.add(trade);
        } else {
            trades.sellTrades.add(trade);
        }
    }

    private void delWaitMatchTrade(Trade trade) {
        if (!this.useMemoryMatch) return;
        String key = (trade.getCoinName() + "/" + trade.getSettlementCurrency()).toUpperCase();
        WaitMatchTrades trades = waitTradesMap.get(key);
        if (trades != null) {
            if (trade.getType() == TradeType.BUY.getValue()) {
                if (!trades.buyTrades.remove(trade)) {
                    log.error("remove buy trade failed:" + ReflectionToStringBuilder.toString(trade));
                }
            } else {
                if (!trades.sellTrades.remove(trade)) {
                    log.error("remove sell trade failed:" + ReflectionToStringBuilder.toString(trade));
                }
            }
        }
    }

    private Trade findMatchTradeInMem(Trade trade) {
        String key = (trade.getCoinName() + "/" + trade.getSettlementCurrency()).toUpperCase();
        WaitMatchTrades trades = waitTradesMap.get(key);
        if (trades == null) {
            return null;
        }
        if (trade.getType() == TradeType.BUY.getValue()) {
            if (trades.sellTrades.size() == 0) return null;
            // 新的下单是买入，取卖出中最小的进行匹配
            Trade matchTrade = trades.sellTrades.first();
            if (TradePriceType.TYPE_MARKET_TRANSACTION == trade.getPriceType() || matchTrade.getPrice().compareTo(trade.getPrice()) <= 0) {
                return matchTrade;
            }
            return null;
        } else {
            if (trades.buyTrades.size() == 0) return null;
            // 新的下单是卖出，取买入中最大的进行匹配
            Trade matchTrade = trades.buyTrades.last();
            if (TradePriceType.TYPE_MARKET_TRANSACTION == trade.getPriceType() || matchTrade.getPrice().compareTo(trade.getPrice()) >= 0) {
                return matchTrade;
            }
            return null;
        }
    }

    private void loadOldOpenTrades(int lastMatchId) {
        if (!this.useMemoryMatch) return;
        log.info("loadOldOpenTrades,lastMatchId=" + lastMatchId);
        waitTradesMap.clear();
        List<Trade> trades = tradeMapper.getOpenTradesBeforeId(lastMatchId);
        for (Trade trade : trades) {
            addWaitMatchTrade(trade);
        }
        lastLoadTradeTime = System.currentTimeMillis();
    }

    /**
     * 撮合一个交易
     * 不一定能撮合成功
     *
     * @param trade 交易
     */
    private void matchTrade(Trade trade) {
        // 工作原理
        // 1.先找到可以跟该下单匹配的另外一个下单（找不到直接返回）
        // 2.对着2个订单进行交易
        // 3.查看该订单是否已经完成，已经完成直接返回
        // 4.如果没有完成，继续第一步
        log.info("begin match trade,id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice()
                + ",userId=" + trade.getUserId() + ",priceType=" + trade.getPriceType());
        Market market = this.markets.get(trade.getCoinName() + "/" + trade.getSettlementCurrency());
        if (market == null) {
            log.error("do not get market,ignore trade,id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice()
                    + ",userId=" + trade.getUserId() + ",market=" + trade.getCoinName() + "/" + trade.getSettlementCurrency());
            return;
        }
        while (true) {
            // 1.先找到可以跟该下单匹配的另外一个下单（找不到直接返回）
            Trade matchTrade = findMatchTrade(trade);
            if (matchTrade == null) {
                if (trade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION && trade.getStatus() != TradeStatus.DEAL.getValue()) {
                    // 取消掉这个单
                    tradeService.cancelTrade(trade.getUserId(), trade.getId(), UserBillReason.TRADE_BG_CANCEL);
                } else {
                    // 找不到匹配单，自己加到挂单列表
                    this.addWaitMatchTrade(trade);
                }
                return;
            }
            // 2.对着2个订单进行交易
            log.info("find match trade,org id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice() + ",userId=" + trade.getUserId() + ",amount=" + trade.getAmount()
                    + ",match id=" + matchTrade.getId() + ",type=" + matchTrade.getType() + ",price=" + matchTrade.getPrice() + ",userId=" + matchTrade.getUserId() + ",amount=" + matchTrade.getAmount()
                    + ",coinName=" + trade.getCoinName() + ",settlementCurrency=" + trade.getSettlementCurrency());
            try {
                if (me.exchange(trade, matchTrade, market)) {
                    // 完成了，如果是市价交易的买单，有可能没有办法交易完成（剩余的结算货币买不了东西）
                    if (trade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION && trade.getStatus() != TradeStatus.DEAL.getValue()) {
                        // 取消掉这个单
                        tradeService.cancelTrade(trade.getUserId(), trade.getId(), UserBillReason.TRADE_BG_CANCEL);
                    }
                    return;
                }
            } catch (Exception ex) {
                // 交易失败，
                log.error("exchange failed,check invalid trade", ex.getMessage(),ex);
                if (!me.checkTrade(trade)) return;
                me.checkTrade(matchTrade);
            }
        }
    }

    /**
     * 检测交易是否合法，如果不合法（在用户冻结金币用户剩余币少的时候，将会无法扣除冻结金，非法），把交易设置为交易失败状态
     *
     * @param checkTrade
     * @return
     */
    @Transactional
    public boolean checkTrade(Trade checkTrade) {
        // 先锁定记录避免在取消过程中被修改
        Trade trade = tradeMapper.lockTrade(checkTrade.getId());
        if (trade.getStatus() != TradeStatus.OPEN.getValue()) {
            // 订单已经不是交易状态了，只有在很短的空闲时间内被拿走控制权才会这样
            return false;
        }

        // 检测完成，开始取消，除了修改状态，最重要的是要返回冻结的币种
        if (trade.getType() == TradeType.BUY.getValue()) {
            // 买，冻结的是结算货币，需要退还结算货币
            BigDecimal unlockCurrency = trade.getTotalCurrency().subtract(trade.getDealCurrency());
            UserCoinVO uc = userCoinService.getUserCoinByUserIdAndCoinName(trade.getUserId(), trade.getSettlementCurrency());
            if ((uc == null) || (uc.getFreezeBalance().compareTo(unlockCurrency) < 0)) {
                trade.setStatus(TradeStatus.EXCEPTION.getValue());
                log.warn("buy trade error,id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice() + ",userId=" + trade.getUserId() + ",amount=" + trade.getAmount()
                        + ",totalCurrency=" + trade.getTotalCurrency() + ",coinName=" + trade.getCoinName() + ",settlementCurrency=" + trade.getSettlementCurrency());
                tradeMapper.updateTrade(trade);
                this.delWaitMatchTrade(trade);
                return false;
            }
            return true;
        } else {
            // 卖，冻结的是数字货币，返回给用户
            BigDecimal unlockCoin = trade.getAmount().subtract(trade.getDealCoin());
            UserCoinVO uc = userCoinService.getUserCoinByUserIdAndCoinName(trade.getUserId(), trade.getCoinName());
            if ((uc == null) || (uc.getFreezeBalance().compareTo(unlockCoin) < 0)) {
                trade.setStatus(TradeStatus.EXCEPTION.getValue());
                log.warn("sell trade error,id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice() + ",userId=" + trade.getUserId() + ",amount=" + trade.getAmount()
                        + ",totalCurrency=" + trade.getTotalCurrency() + ",coinName=" + trade.getCoinName() + ",settlementCurrency=" + trade.getSettlementCurrency());
                tradeMapper.updateTrade(trade);
                this.delWaitMatchTrade(trade);
                return false;
            }
            return true;
        }
    }

    /**
     * 两个单进行货币交换
     *
     * @param _trade      原单
     * @param _matchTrade 查找到的匹配单
     * @return boolean      已经完成匹配返回true，需要继续匹配返回false
     */
    @Transactional
//    @CacheEvict(value = "stat" ,allEntries = true)
    public boolean exchange(Trade _trade, Trade _matchTrade, Market market) {
        // 交易前先锁定2列，然后重新判断是否符合交易，符合才进行交易
        Trade trade = tradeMapper.lockTrade(_trade.getId());
        if (trade.getStatus() != TradeStatus.OPEN.getValue()) {
            log.warn("trade status change,can not exchange,id=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice()
                    + ",userId=" + trade.getUserId());
            //BeanUtils.copyProperties(trade, _trade);
            this.delWaitMatchTrade(trade);
            return true;
        }
        Trade matchTrade = tradeMapper.lockTrade(_matchTrade.getId());
        if (matchTrade.getStatus() != TradeStatus.OPEN.getValue()) {
            log.warn("matchTrade status change,can not exchange,id=" + matchTrade.getId() + ",type=" + matchTrade.getType() + ",price=" + matchTrade.getPrice()
                    + ",userId=" + matchTrade.getUserId() + ", orgTradeId=" + trade.getId() + ",type=" + trade.getType() + ",price=" + trade.getPrice());
            //BeanUtils.copyProperties(matchTrade, _matchTrade);
            this.delWaitMatchTrade(matchTrade);
            return false;
        }
        Trade buyTrade, sellTrade;
        if (trade.getType() == TradeType.BUY.getValue()) {
            buyTrade = trade;
            sellTrade = matchTrade;
        } else {
            buyTrade = matchTrade;
            sellTrade = trade;
        }
        if (_trade.getPriceType() != TradePriceType.TYPE_MARKET_TRANSACTION) {
            Assert.isTrue(buyTrade.getPrice().compareTo(sellTrade.getPrice()) >= 0, "buyTrade price must >= sellTrade price");
        }

        // 验证数据完毕，开始交换
        // 计算各种交换结果数据
        TradeResult tradeResult = calTradeResult(buyTrade, sellTrade, market, _matchTrade, _trade);
        if (log.isDebugEnabled()) {
            log.debug("begin exchange, buy trade:" + ReflectionToStringBuilder.toString(buyTrade) + "\n sell trade:"
                    + ReflectionToStringBuilder.toString(sellTrade) + "\n result:" + ReflectionToStringBuilder.toString(tradeResult));
        }
        if (tradeResult.amount.compareTo(BigDecimal.ZERO) == 0) {
            // 成交数是0，是有可能出现的，在市价交易中可能户出现，终止交易
            log.warn("tradeResult.amount==0, over.buy trade:" + ReflectionToStringBuilder.toString(buyTrade) + "\n sell trade:"
                    + ReflectionToStringBuilder.toString(sellTrade) + "\n result:" + ReflectionToStringBuilder.toString(tradeResult));
            return true;
        }

        // 开始真正交易,需要做几个
        // 修改单据，修改资金(增加资金变化日志)，增加交易日志，费用转移（日志）
        // 修改单据
        buyTrade.setDealCoin(buyTrade.getDealCoin().add(tradeResult.amount));
        buyTrade.setDealCurrency(buyTrade.getDealCurrency().add(tradeResult.totalCurrency));
        buyTrade.setFeeCoin(buyTrade.getFeeCoin().add(tradeResult.buyFeeCoin));
        buyTrade.setFeeCurrency(buyTrade.getFeeCurrency().add(tradeResult.sellFeeCurrency));
        if (buyTrade.getAmount().compareTo(buyTrade.getDealCoin()) == 0) {
            buyTrade.setStatus(TradeStatus.DEAL.getValue());
            log.debug("buyTrade deal,id=" + buyTrade.getId());
            this.delWaitMatchTrade(buyTrade);
        } else if (buyTrade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION && buyTrade.getTotalCurrency().compareTo(buyTrade.getDealCurrency()) == 0) {
            buyTrade.setStatus(TradeStatus.DEAL.getValue());
            log.debug("market buyTrade deal,id=" + buyTrade.getId());
            this.delWaitMatchTrade(buyTrade);
        }
        tradeMapper.updateTrade(buyTrade);
        sellTrade.setDealCoin(sellTrade.getDealCoin().add(tradeResult.amount));
        sellTrade.setDealCurrency(sellTrade.getDealCurrency().add(tradeResult.totalCurrency));
        sellTrade.setFeeCoin(sellTrade.getFeeCoin().add(tradeResult.buyFeeCoin));
        sellTrade.setFeeCurrency(sellTrade.getFeeCurrency().add(tradeResult.sellFeeCurrency));
        if (sellTrade.getAmount().compareTo(sellTrade.getDealCoin()) == 0) {
            sellTrade.setStatus(TradeStatus.DEAL.getValue());
            log.debug("sellTrade deal,id=" + sellTrade.getId());
            this.delWaitMatchTrade(trade);
        }
        tradeMapper.updateTrade(sellTrade);

        // 修改资金(增加资金变化日志)
        // 买家增加数字货币，减少冻结的结算货币(扣除费用)
        userCoinService.changeUserCoin(buyTrade.getUserId(), buyTrade.getCoinName(), tradeResult.amountTran, UserCoinService.ZERO,
                UserBillReason.TRADE_MATCH, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId() + ",Fee:" + tradeResult.buyFeeCoin);
        userCoinService.changeUserCoin(buyTrade.getUserId(), buyTrade.getSettlementCurrency(), UserCoinService.ZERO, tradeResult.totalCurrency.negate(),
                UserBillReason.TRADE_MATCH, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId());
        // 卖家减少冻结的数字货币，增加结算货币(扣除费用)
        userCoinService.changeUserCoin(sellTrade.getUserId(), sellTrade.getCoinName(), UserCoinService.ZERO, tradeResult.amount.negate(),
                UserBillReason.TRADE_MATCH, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId());
        userCoinService.changeUserCoin(sellTrade.getUserId(), sellTrade.getSettlementCurrency(), tradeResult.currencyTran, UserCoinService.ZERO,
                UserBillReason.TRADE_MATCH, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId() + ",Fee:" + tradeResult.sellFeeCurrency);

        // 买方交易已经完成，如果有剩余的金额，返还冻结金给用户
        if (buyTrade.getStatus() == TradeStatus.DEAL.getValue() && buyTrade.getTotalCurrency().compareTo(buyTrade.getDealCurrency()) > 0) {
            BigDecimal retCur = buyTrade.getTotalCurrency().subtract(buyTrade.getDealCurrency());
            userCoinService.changeUserCoin(buyTrade.getUserId(), buyTrade.getSettlementCurrency(), retCur, retCur.negate(),
                    UserBillReason.TRADE_MATCH_RET, "BuyTrade:" + buyTrade.getId());
        }

        // 增加交易日志
        tradeLogMapper.addTradeLog(trade.getCoinName(), trade.getSettlementCurrency(), tradeResult.price, tradeResult.amount, buyTrade.getUserId(),
                sellTrade.getUserId(), tradeResult.buyFeeCoin, tradeResult.sellFeeCurrency, buyTrade.getId(), sellTrade.getId());

        // 费用转移到专人上面
        if (market.getFeeCoinUserId() == null || market.getFeeCoinUserId() == 0) {
            if (tradeResult.buyFeeCoin.compareTo(UserCoinService.ZERO) != 0) {
                log.warn("buyFeeCoin is not 0,but no fee coin user");
            }
        } else if (tradeResult.buyFeeCoin.compareTo(UserCoinService.ZERO) > 0) {
            userCoinService.changeUserCoin(market.getFeeCoinUserId(), buyTrade.getCoinName(), tradeResult.buyFeeCoin, UserCoinService.ZERO,
                    UserBillReason.TRADE_MATCH_FEE, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId() + ",Amount:" + tradeResult.amount);
        }
        if (market.getFeeCurrencyUserId() == null || market.getFeeCurrencyUserId() == 0) {
            if (tradeResult.sellFeeCurrency.compareTo(UserCoinService.ZERO) != 0) {
                log.warn("sellFeeCurrency is not 0,but no fee coin user");
            }
        } else if (tradeResult.sellFeeCurrency.compareTo(UserCoinService.ZERO) > 0) {
            userCoinService.changeUserCoin(market.getFeeCurrencyUserId(), sellTrade.getSettlementCurrency(), tradeResult.sellFeeCurrency, UserCoinService.ZERO,
                    UserBillReason.TRADE_MATCH_FEE, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId() + ",currency:" + tradeResult.totalCurrency);
        }

        doReleaseCoin(buyTrade, sellTrade, trade, tradeResult);

        return trade.getStatus() == TradeStatus.DEAL.getValue();
    }
    private void doReleaseCoin(Trade buyTrade, Trade sellTrade, Trade trade, TradeResult tradeResult){
        // 如果是冻结币种，并且不是自己买自己的，返还冻结金给买方用户
        if (buyTrade.getUserId().equals(sellTrade.getUserId())) {
            return;
        }

        Coin coin = this.coins.get(trade.getCoinName().toUpperCase());
        //是否释放锁仓
        if(coin.getReleasePolicy().intValue() == ReleasePolicyEnum.BUSHIFANG.getValue()){
            return;
        }

        //挂买释放锁仓
        if(coin.getReleasePolicy().intValue() == ReleasePolicyEnum.BUYSHIFANG.getValue()){
            doBuyReleaseCoin(buyTrade,sellTrade,trade,tradeResult,coin);
            return;
        }
        //挂卖释放锁仓
        if(coin.getReleasePolicy().intValue() == ReleasePolicyEnum.SELSHIFANG.getValue()){

            doSellReleaseCoin(buyTrade,sellTrade,trade,tradeResult,coin);
            return;
        }
        //挂买和挂卖同时释放锁仓
        doBuyReleaseCoin(buyTrade,sellTrade,trade,tradeResult,coin);
        doSellReleaseCoin(buyTrade,sellTrade,trade,tradeResult,coin);
    }

    // 释放释放锁仓
    private void doBuyReleaseCoin(Trade buyTrade, Trade sellTrade, Trade trade, TradeResult tradeResult,Coin coin) {

        //Coin coin = this.coins.get(trade.getCoinName().toUpperCase());
        if (coin != null && coin.getReceivedFreeze() != null && coin.getReceivedFreeze() == Coin.RECEIVED_FREEZE_YES) {
            BigDecimal releaseRate = coin.getReleaseRate();
            BigDecimal releaseRateVip = coin.getReleaseRateVip();
            if ((releaseRate != null && releaseRate.compareTo(BigDecimal.ZERO) > 0) || (releaseRateVip != null && releaseRateVip.compareTo(BigDecimal.ZERO) > 0)) {
                // 奖励次数满足要求才奖励(一个用户每天交易次数必须满足限制)
                String userCoinIdentity = buyTrade.getCoinName() + "-" + buyTrade.getUserId();

                //缓存用户当天第一笔交易的锁仓金额
                String receiveFreezeBalance = null;
                //用户当天释放锁仓金额上限
                BigDecimal releaseDayAmount = null;

                this.dayUserCountLimit.setDayLimitCount(coin.getMaxDayRelNum());
                if (this.dayUserCountLimit.isDayCountFinishUp(userCoinIdentity)) {
                    log.info("trade of " + userCoinIdentity + " is DayCountFinishUp, can not freeze coin");
                    return;
                }

                // 奖励次数满足要求才奖励(两个账号相互交易)
                String tradeIdentity = buyTrade.getCoinName() + "/" + buyTrade.getSettlementCurrency() + "-" + buyTrade.getUserId() + "/" + sellTrade.getUserId();
                if (this.dayCountLimit.isDayCountFinishUp(tradeIdentity)) {
                    log.info("trade of " + tradeIdentity + " is DayCountFinishUp, can not freeze coin");
                    return;
                }

                //用户每天释放统计
                if(this.dayAmountLimit == null)
                {
                    this.dayAmountLimit = new DayAmountLimit("DayReleaseLimit", this.redisTemplate,null,tradeResult.amountTran,coin.getReleaseRateVip(),coin.getReleaseRate());
                }
                //根据每天释放锁仓比例 进行统计
                if (coin.getReleaseDayLimitRate() != null && coin.getReleaseDayLimitRate().compareTo(BigDecimal.ZERO) > 0) {
                    dayAmountLimit.setAmountTran(tradeResult.amountTran);
                    dayAmountLimit.setUserRate(coin.getReleaseRate());
                    dayAmountLimit.setVipUserRate(coin.getReleaseRateVip());
                    log.info("DayReleaseLimit:用户Id:{},币种:{},释放锁仓上限比例{}",buyTrade.getUserId(),buyTrade.getCoinName(),coin.getReleaseDayLimitRate());
                    //缓存用户当天第一笔交易的锁仓金额
                    String dayUserCoinIdentity = userCoinIdentity+dayAmountLimit.getDayByNew(new Date());
                    receiveFreezeBalance = dayAmountLimit.getReceiveFreezeBalance(dayUserCoinIdentity);
                    if(null == receiveFreezeBalance)
                    {
                        UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(buyTrade.getUserId(), buyTrade.getCoinName());
                        if(null == userCoin || userCoin.getReceiveFreezeBalance().compareTo(BigDecimal.ZERO) == 0){
                            log.info("trade of " + userCoinIdentity + " is ReceiveFreezeBalance=0, can not freeze coin");
                            return;
                        }
                        this.dayAmountLimit.setVipType(userCoin.getVipType());
                        log.info("当天首次交易：用户Id:{},币种:{},释放锁仓上限比例{}",buyTrade.getUserId(),buyTrade.getCoinName(),coin.getReleaseDayLimitRate());
                        receiveFreezeBalance = userCoin.getReceiveFreezeBalance().toString();
                    }

                     releaseDayAmount =  new BigDecimal(receiveFreezeBalance).multiply(coin.getReleaseDayLimitRate()).setScale(8, RoundingMode.HALF_UP);

                    this.dayAmountLimit.setDayLimitAmount(releaseDayAmount.doubleValue());
                    log.info("当天交易：用户Id:{},币种:{},用户当前锁仓金额{}，释放锁仓上限比例{},上限释放锁仓金额{}:",buyTrade.getUserId(),buyTrade.getCoinName(),receiveFreezeBalance,coin.getReleaseDayLimitRate(),releaseDayAmount.doubleValue());
                    if (this.dayAmountLimit.isDayAmountFinishUp(userCoinIdentity)) {
                        log.info("trade of " + userCoinIdentity + " is isDayAmountFinishUp, can not freeze coin");
                        return;
                    }

                }
                // 用户月释放统计
                if (this.monthAmountLimit == null) {
                    this.monthAmountLimit = new MonthAmountLimit("MonthReleaseLimit", this.redisTemplate);
                }
                if (coin.getMaxMonthRel() != null && coin.getMaxMonthRel().compareTo(BigDecimal.ZERO) > 0) {
                    this.monthAmountLimit.setMonthLimitAmount(coin.getMaxMonthRel().doubleValue());
                    if (this.monthAmountLimit.isMonthAmountFinishUp(userCoinIdentity)) {
                        log.info("trade of " + userCoinIdentity + " is isMonthAmountFinishUp, can not freeze coin");
                        return;
                    }
                }

                BigDecimal amount = userCoinService.recFreezeToAvailBalance(buyTrade.getUserId(), buyTrade.getCoinName(), tradeResult.amountTran, coin.getReleaseRate(),
                        coin.getReleaseRateVip(), UserBillReason.TRADE_MATCH_RELEASE, "BuyTrade:" + buyTrade.getId() + ",SellTrade:" + sellTrade.getId());
                log.info("freeze to available user id=" + buyTrade.getUserId() + ",coin=" + buyTrade.getCoinName() + ", amount=" + tradeResult.amountTran
                        + ",return=" + amount);


                // 增加统计次数
                this.dayCountLimit.addDayCount(tradeIdentity);

                this.dayUserCountLimit.addDayCount(userCoinIdentity);

                //根据每天释放锁仓比例 累加统计
                if (coin.getReleaseDayLimitRate() != null && coin.getReleaseDayLimitRate().compareTo(BigDecimal.ZERO) > 0) {
                    this.dayAmountLimit.addDayAmount(userCoinIdentity, amount.doubleValue(),Double.parseDouble(receiveFreezeBalance));
                    log.info("当天累加统计：用户Id:{},币种:{},用户当前锁仓金额{},单次锁仓金额释放{}:",buyTrade.getUserId(),buyTrade.getCoinName(),receiveFreezeBalance,amount.doubleValue());
                }

                if (coin.getMaxMonthRel() != null && coin.getMaxMonthRel().compareTo(BigDecimal.ZERO) > 0) {
                    this.monthAmountLimit.addMonthAmount(userCoinIdentity, amount.doubleValue());
                }
            }
        }
    }
    // 释放释放锁仓
    private void doSellReleaseCoin(Trade buyTrade, Trade sellTrade, Trade trade, TradeResult tradeResult,Coin coin) {

        //判断卖价是否大于等于卖一价格指定百分比，小于则不释放
        if(null != coin.getSellPriceRate() && coin.getSellPriceRate().compareTo(BigDecimal.ZERO)>0){

            if(sellTrade.getSellPrice().compareTo(BigDecimal.ZERO) == 0){
                return;
            }
            BigDecimal sellPriceRate = (sellTrade.getPrice().subtract(sellTrade.getSellPrice())).divide(sellTrade.getSellPrice(),4, BigDecimal.ROUND_HALF_UP);
            if(sellPriceRate.compareTo(coin.getSellPriceRate()) < 0){
                log.debug("判断卖价是否大于等于卖一价格指定百分比，不释放,释放比例:{},计算后比例:{}",coin.getSellPriceRate(),sellPriceRate);
                return;
            }
            log.debug("判断卖价是否大于等于卖一价格指定百分比，释放,释放比例:{},计算后比例:{}",coin.getSellPriceRate(),sellPriceRate);
        }

        if (coin != null && coin.getReceivedFreeze() != null && coin.getReceivedFreeze() == Coin.RECEIVED_FREEZE_YES) {
            BigDecimal releaseRate = coin.getReleaseRate();
            BigDecimal releaseRateVip = coin.getReleaseRateVip();
            if ((releaseRate != null && releaseRate.compareTo(BigDecimal.ZERO) > 0) || (releaseRateVip != null && releaseRateVip.compareTo(BigDecimal.ZERO) > 0)) {
                // 奖励次数满足要求才奖励(一个用户每天交易次数必须满足限制)
                String userCoinIdentity = sellTrade.getCoinName() + "-" + sellTrade.getUserId();

                //缓存用户当天第一笔交易的锁仓金额
                String receiveFreezeBalance = null;
                //用户当天释放锁仓金额上限
                BigDecimal releaseDayAmount = null;

                this.dayUserCountLimit.setDayLimitCount(coin.getMaxDayRelNum());
                if (this.dayUserCountLimit.isDayCountFinishUp(userCoinIdentity)) {
                    log.info("trade of " + userCoinIdentity + " is DayCountFinishUp, can not freeze coin");
                    return;
                }

                // 奖励次数满足要求才奖励(两个账号相互交易)
                String tradeIdentity = buyTrade.getCoinName() + "/" + buyTrade.getSettlementCurrency() + "-" + buyTrade.getUserId() + "/" + sellTrade.getUserId();
                if (this.dayCountLimit.isDayCountFinishUp(tradeIdentity)) {
                    log.info("trade of " + tradeIdentity + " is DayCountFinishUp, can not freeze coin");
                    return;
                }

                //用户每天释放统计
                if(this.dayAmountLimit == null)
                {
                    this.dayAmountLimit = new DayAmountLimit("DayReleaseLimit", this.redisTemplate,null,tradeResult.amountTran,coin.getReleaseRateVip(),coin.getSellReleaseConf());
                }
                //根据每天释放锁仓比例 进行统计
                if (coin.getReleaseDayLimitRate() != null && coin.getReleaseDayLimitRate().compareTo(BigDecimal.ZERO) > 0) {
                    log.info("DayReleaseLimit:用户Id:{},币种:{},释放锁仓上限比例{}",sellTrade.getUserId(),sellTrade.getCoinName(),coin.getReleaseDayLimitRate());
                    //缓存用户当天第一笔交易的锁仓金额
                    String dayUserCoinIdentity = userCoinIdentity+dayAmountLimit.getDayByNew(new Date());
                    receiveFreezeBalance = dayAmountLimit.getReceiveFreezeBalance(dayUserCoinIdentity);
                    if(null == receiveFreezeBalance)
                    {
                        UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(sellTrade.getUserId(), sellTrade.getCoinName());
                        if(null == userCoin || userCoin.getReceiveFreezeBalance().compareTo(BigDecimal.ZERO) == 0){
                            log.info("trade of " + userCoinIdentity + " is ReceiveFreezeBalance=0, can not freeze coin");
                            return;
                        }
                        this.dayAmountLimit.setVipType(userCoin.getVipType());
                        log.info("当天首次交易：用户Id:{},币种:{},释放锁仓上限比例{}",sellTrade.getUserId(),sellTrade.getCoinName(),coin.getReleaseDayLimitRate());
                        receiveFreezeBalance = userCoin.getReceiveFreezeBalance().toString();
                    }

                     releaseDayAmount =  new BigDecimal(receiveFreezeBalance).multiply(coin.getReleaseDayLimitRate()).setScale(8, RoundingMode.HALF_UP);

                    this.dayAmountLimit.setDayLimitAmount(releaseDayAmount.doubleValue());
                    log.info("当天交易：用户Id:{},币种:{},用户当前锁仓金额{}，释放锁仓上限比例{},上限释放锁仓金额{}:",buyTrade.getUserId(),buyTrade.getCoinName(),receiveFreezeBalance,coin.getReleaseDayLimitRate(),releaseDayAmount.doubleValue());
                    if (this.dayAmountLimit.isDayAmountFinishUp(userCoinIdentity)) {
                        log.info("trade of " + userCoinIdentity + " is isDayAmountFinishUp, can not freeze coin");
                        return;
                    }

                }
                // 用户月释放统计
                if (this.monthAmountLimit == null) {
                    this.monthAmountLimit = new MonthAmountLimit("MonthReleaseLimit", this.redisTemplate);
                }
                if (coin.getMaxMonthRel() != null && coin.getMaxMonthRel().compareTo(BigDecimal.ZERO) > 0) {
                    this.monthAmountLimit.setMonthLimitAmount(coin.getMaxMonthRel().doubleValue());
                    if (this.monthAmountLimit.isMonthAmountFinishUp(userCoinIdentity)) {
                        log.info("trade of " + userCoinIdentity + " is isMonthAmountFinishUp, can not freeze coin");
                        return;
                    }
                }

                BigDecimal amount = userCoinService.recFreezeToAvailBalance(sellTrade.getUserId(), sellTrade.getCoinName(), tradeResult.amountTran, coin.getSellReleaseConf(),
                        coin.getReleaseRateVip(), UserBillReason.TRADE_MATCH_RELEASE,  ",SellTrade:" + sellTrade.getId()+"BuyTrade:" + buyTrade.getId());
                log.info("freeze to available user id=" + sellTrade.getUserId() + ",coin=" + sellTrade.getCoinName() + ", amount=" + tradeResult.amountTran
                        + ",return=" + amount);


                // 增加统计次数
                this.dayCountLimit.addDayCount(tradeIdentity);

                this.dayUserCountLimit.addDayCount(userCoinIdentity);

                //根据每天释放锁仓比例 累加统计
                if (coin.getReleaseDayLimitRate() != null && coin.getReleaseDayLimitRate().compareTo(BigDecimal.ZERO) > 0) {
                    this.dayAmountLimit.addDayAmount(userCoinIdentity, amount.doubleValue(),Double.parseDouble(receiveFreezeBalance));
                    log.info("当天累加统计：用户Id:{},币种:{},用户当前锁仓金额{},单次锁仓金额释放{}:",sellTrade.getUserId(),sellTrade.getCoinName(),receiveFreezeBalance,amount.doubleValue());
                }

                if (coin.getMaxMonthRel() != null && coin.getMaxMonthRel().compareTo(BigDecimal.ZERO) > 0) {
                    this.monthAmountLimit.addMonthAmount(userCoinIdentity, amount.doubleValue());
                }
            }
        }
    }

    public void logDayCountLimit() {
        HashMap<String, DayCountLimit.DayCount> stats = this.dayCountLimit.getAllStats();
        stats.forEach((String key, DayCountLimit.DayCount count) -> {
            log.info("key:" + key + ",id:" + count.identity + ",time:" + count.statTime + ",count:" + count.count);
        });

        stats = this.dayUserCountLimit.getAllStats();
        stats.forEach((String key, DayCountLimit.DayCount count) -> {
            log.info("key:" + key + ",id:" + count.identity + ",time:" + count.statTime + ",count:" + count.count);
        });
    }

    /**
     * 计算交易结果
     *
     * @param buyTrade   买单
     * @param sellTrade  卖单
     * @param market     市场
     * @param priceTrade 使用的成交价格方
     * @param newTrade   新下的单，主要用于做市价交易
     */
    private TradeResult calTradeResult(Trade buyTrade, Trade sellTrade, Market market, Trade priceTrade, Trade newTrade) {
        TradeResult tradeResult = new TradeResult();
        tradeResult.price = priceTrade.getPrice();
        if (newTrade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION && newTrade.getType() == TradeType.BUY.getValue()) {
            // 市价交易买的时候是不指定虚拟货币（货物）的数量的，只指定钱
            // 按照买单剩余金额计算可买的数量，最小单位是4位小数
            BigDecimal buyRemain = buyTrade.getTotalCurrency().subtract(buyTrade.getDealCurrency()).divide(tradeResult.price, 4, RoundingMode.FLOOR);
            BigDecimal sellRemain = sellTrade.getAmount().subtract(sellTrade.getDealCoin());
            if (buyRemain.compareTo(sellRemain) >= 0) {
                tradeResult.amount = sellRemain;
            } else {
                tradeResult.amount = buyRemain;
            }
        } else {
            // 交易的货币数量，使用数值小的
            BigDecimal buyRemain = buyTrade.getAmount().subtract(buyTrade.getDealCoin());
            BigDecimal sellRemain = sellTrade.getAmount().subtract(sellTrade.getDealCoin());
            if (buyRemain.compareTo(sellRemain) >= 0) {
                tradeResult.amount = sellRemain;
            } else {
                tradeResult.amount = buyRemain;
            }
        }
        tradeResult.totalCurrency = tradeResult.price.multiply(tradeResult.amount).setScale(tradeResult.price.scale(), RoundingMode.HALF_UP);


        tradeResult.buyFeeCoin = tradeResult.amount.multiply(market.getFeeCoin()).setScale(tradeResult.price.scale(), RoundingMode.HALF_UP);
        if (tradeResult.buyFeeCoin.compareTo(tradeResult.amount) >= 0) {
            log.error("fee of coin larger than amount, ignore fee");
            tradeResult.buyFeeCoin = new BigDecimal(0);
        }
        tradeResult.sellFeeCurrency = tradeResult.totalCurrency.multiply(market.getFeeCurrency()).setScale(tradeResult.price.scale(), RoundingMode.HALF_UP);
        if (tradeResult.sellFeeCurrency.compareTo(tradeResult.totalCurrency) >= 0) {
            log.error("fee of settlement currency larger than amount, ignore fee");
            tradeResult.sellFeeCurrency = new BigDecimal(0);
        }

        User buyUser = userService.getUserByUserId(buyTrade.getUserId());
        User selluser = userService.getUserByUserId(sellTrade.getUserId());

        BigDecimal buyConValue = globalDataComponet.getCobValue(buyUser.getGroupType(), market.getCoinName() + "/" + market.getSettlementCurrency(), 1);
        BigDecimal sellConvalue = globalDataComponet.getCobValue(selluser.getGroupType(), market.getCoinName() + "/" + market.getSettlementCurrency(), 2);
        log.info("======================= fee is ====================");
        log.info("buyUser.getGroupType is =====" + buyUser.getGroupType());
        log.info("selluser.getGroupType() is =====" + selluser.getGroupType());
        log.info("group buy fee is =====" + buyConValue);
        log.info("group sell fee is =====" + sellConvalue);
        log.info("======================= fee is ====================");
        if (buyConValue != null) {
            tradeResult.buyFeeCoin = tradeResult.amount.multiply(buyConValue).setScale(tradeResult.price.scale(), RoundingMode.HALF_UP);
            if (tradeResult.buyFeeCoin.compareTo(tradeResult.amount) >= 0) {
                log.error("fee of settlement currency larger than amount, ignore fee");
                tradeResult.buyFeeCoin = new BigDecimal(0);
            }
        }

        if (sellConvalue != null) {
            tradeResult.sellFeeCurrency = tradeResult.totalCurrency.multiply(sellConvalue).setScale(tradeResult.price.scale(), RoundingMode.HALF_UP);
            if (tradeResult.sellFeeCurrency.compareTo(tradeResult.totalCurrency) >= 0) {
                log.error("fee of settlement currency larger than amount, ignore fee");
                tradeResult.sellFeeCurrency = new BigDecimal(0);
            }
        }


        tradeResult.amountTran = tradeResult.amount.subtract(tradeResult.buyFeeCoin);
        tradeResult.currencyTran = tradeResult.totalCurrency.subtract(tradeResult.sellFeeCurrency);

        return tradeResult;
    }

    /**
     * 查找跟指定交易单匹配的另外一个交易单
     *
     * @param trade 交易单
     * @return 匹配的另外一个交易单
     */
    private Trade findMatchTrade(Trade trade) {
        if (useMemoryMatch) {
            return findMatchTradeInMem(trade);
        }
        if (trade.getPriceType() == TradePriceType.TYPE_LIMITED_TRANSACTION) {
            if (trade.getType() == TradeType.BUY.getValue()) {
                // 买入的单，查找一个卖出的单，这个单的卖出价是当前所有卖出单中单价最低并且小于等于改单的买入价的，并且入库比该单先入库的
                // 如果是有多个价格一样的单，那么找最先进入数据库的单
                return tradeMapper.getMatchSellTrade(trade.getId(), trade.getPrice(), trade.getCoinName(), trade.getSettlementCurrency(), trade.getUserId());
            } else if (trade.getType() == TradeType.SELL.getValue()) {
                // 卖出的单，找一个买入的单，这个单买入价比该单高，并且在入库是最早的，并且入库比该单先入库的
                return tradeMapper.getMatchBuyTrade(trade.getId(), trade.getPrice(), trade.getCoinName(), trade.getSettlementCurrency(), trade.getUserId());
            }

        } else if (trade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION) {
            if (trade.getType() == TradeType.BUY.getValue()) {
                // 跟限价比较，不限制价格（暂时把价格设置为非常高，（有bug的）
                return tradeMapper.getMatchSellTrade(trade.getId(), buyMaxPrice, trade.getCoinName(), trade.getSettlementCurrency(), trade.getUserId());
            } else if (trade.getType() == TradeType.SELL.getValue()) {
                // 卖出的单，找一个买入的单，这个单买入价比该单高，并且在入库是最早的，并且入库比该单先入库的
                return tradeMapper.getMatchBuyTrade(trade.getId(), BigDecimal.ZERO, trade.getCoinName(), trade.getSettlementCurrency(), trade.getUserId());
            }
        }
        return null;
    }

    private Trade getNextOpenTrade(int lastTradeId) {
        if (!useMemoryMatch) {
            return tradeMapper.getNextOpenTrade(lastTradeId);
        }
        if (this.newMatchTrades.size() > 0) {
            return this.newMatchTrades.remove(0);
        }
        // 没有了，获取一批
        this.newMatchTrades = tradeMapper.getNextOpenTrades(lastTradeId, this.loadRecordNum);
        if (this.newMatchTrades == null || this.newMatchTrades.size() == 0) {
            return null;
        }
        return this.newMatchTrades.remove(0);
    }

    /**
     * 撮合没有成交的记录
     */
    public void matchNewTrades() {
        if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
            log.info("is not trade task master, not work");
            return;
        }
        int count = 0;
        long startTime = System.currentTimeMillis();
        int lastTradeId = configService.getConfigValue(ConfigKey.LAST_MATCH_TRADE_ID, 0);
        int orgLastTradeId = lastTradeId;
        log.debug("begin matchNewTrades,lastTradeId=" + lastTradeId);
        // 如果距离上次工作时间超过1分钟，说明被切换了服务器，重新加载数据
        // 或者装载数据时间超过1个小时，也重新装载一次数据（这个是防止bug的，正常情况不需要）
        if (startTime - lastWorkTime >= 60000 || startTime - lastLoadTradeTime > 3600 * 1000) {
            loadOldOpenTrades(orgLastTradeId);
            try {
                this.dayCountLimit.removeOldStats();
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        this.receivedReleaseRate = configService.getConfigValue(ConfigKey.TRADE_RECEIVED_RELEASE_RATE, BigDecimal.ZERO, true);
        this.receivedReleaseRateVip = configService.getConfigValue(ConfigKey.TRADE_RECEIVED_RELEASE_RATE_VIP, BigDecimal.ZERO, true);
        int sameTradeRewardMaxTimes = configService.getConfigValue(ConfigKey.TRADE_SAME_REWARD_MAX_TIMES, 5);
        log.debug("receivedReleaseRate=" + receivedReleaseRate + ",receivedReleaseRateVip=" + receivedReleaseRateVip + ",sameTradeRewardMaxTimes=" + sameTradeRewardMaxTimes);
        this.dayCountLimit.setDayLimitCount(sameTradeRewardMaxTimes);
        lastWorkTime = startTime;
        List<Market> marketLs = marketService.getAllMarkets();
        for (Market market : marketLs) {
            this.markets.put(market.getCoinName() + "/" + market.getSettlementCurrency(), market);
        }
        List<Coin> coinLs = coinService.getCoin();
        for (Coin coin : coinLs) {
            this.coins.put(coin.getName().toUpperCase(), coin);
        }
        Trade trade;
        while (true) {
            trade = getNextOpenTrade(lastTradeId);
            if (trade == null) {
                log.debug("do not find any new open trade,lastTradeId=" + lastTradeId);
                break;
            }
            count++;
            if (count % 100 == 0) {
                log.info("now begin match trade num:" + count);
                if (!serverTaskService.isMasterServer(ConfigKey.TASK_NAME_TRADE_MATCH)) {
                    log.info("become trade task slaver, can not work");
                    break;
                }
            }
            matchTrade(trade);
            lastTradeId = trade.getId();
        }
        if (orgLastTradeId != lastTradeId) {
            configService.setConfigValue(ConfigKey.LAST_MATCH_TRADE_ID, Integer.toString(lastTradeId));
        }
        if (count > 0) {
            log.warn("match trade info: begin=" + startTime + ",use time=" + (System.currentTimeMillis() - startTime)
                    + ", match new trade count=" + count + ",lastTradeId=" + lastTradeId + ",now tradeId=" + lastTradeId);
        }
    }

    public String dumpMemory() {
        StringBuilder sb = new StringBuilder("waitTradesMap:\n");
        HashMap<String, WaitMatchTrades> tmpWaitTradesMap = (HashMap<String, WaitMatchTrades>) this.waitTradesMap.clone();
        for (Map.Entry<String, WaitMatchTrades> entry : tmpWaitTradesMap.entrySet()) {
            sb.append("\n" + entry.getKey() + "\n");
            WaitMatchTrades trades = entry.getValue();
            sb.append("  buy:\n");
            for (Trade trade : trades.buyTrades) {
                sb.append("    " + ReflectionToStringBuilder.toString(trade) + "\n");
            }
            sb.append("  sell:\n");
            for (Trade trade : trades.sellTrades) {
                sb.append("    " + ReflectionToStringBuilder.toString(trade) + "\n");
            }
        }
        sb.append("\n");

        String result = sb.toString();
        log.info(result);
        return result;
    }
}
