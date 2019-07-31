package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.*;
import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.enums.MarketStatus;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 处理交易市场
 */
@Service
public class TradeService {
    private static Logger logger = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    TradeMapper tradeMapper;
    @Autowired
    TradeLogMapper tradeLogMapper;
    @Autowired
    UserCoinMapper userCoinMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MarketMapper marketMapper;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    TradeService me;   // 自己，用来启用事务的
    @Autowired
    ConfigService configService;
    @Autowired
    TradeBonusLogMapper tradeBonusLogMapper;

    @Autowired
    RedisTemplate redisTemplate;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    TradeWarningMapper tradeWarningMapper;
    // 记录上次用户的下单时间
    private Hashtable<Integer, Long> userLastTradeTime = new Hashtable<Integer, Long>();

    //所有的交易对对应的警告Id
    private static Map<Integer, BigDecimal> warningMap = new HashMap<>();

    // 用户2次交易之间的最短时间间隔
    @Value("${cmd.trade.trade-timet-limit:0}")
    private long tradeTimeLimi;

    private void checkTradeLimit(Integer userId) {
        if (tradeTimeLimi > 0) {
            Long lastTradeTime = userLastTradeTime.get(userId);
            if (lastTradeTime != null) {
                Assert.check(System.currentTimeMillis() - lastTradeTime < tradeTimeLimi, ErrorCode.ERR_TRADE_TOO_FREQUENT);
            }
        }
    }

    private void updateTradeTime(Integer userId) {
        if (tradeTimeLimi > 0) {
            userLastTradeTime.put(userId, System.currentTimeMillis());
        }
    }

    // 只允许4位小数
    private void checkTradeDecimalPoint(BigDecimal value) {
        if (value.scale() <= 4) return;
        BigDecimal newValue = value.setScale(4, RoundingMode.HALF_UP);
        Assert.check(value.compareTo(newValue) != 0, ErrorCode.ERR_TRADE_DECIMAL_POINT_4);
    }

    @Transactional
    public int createTrade(TradeVo tradeVO) {
        checkTradeLimit(tradeVO.getUserId());
        User user = userMapper.getUserByUserId(tradeVO.getUserId());
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        // 暂时不需要实名验证也可以交易
        //Assert.check(user.getIdCardStatus() != 1, ErrorCode.ERR_USER_NOT_CERTIFICATED);

        Market market = marketMapper.getMarket(tradeVO.getCoinName(), tradeVO.getSettlementCurrency());
        Assert.check(market == null, ErrorCode.ERR_MARKET_NOT_EXIST);
        Assert.check(market.getClosed() == MarketStatus.HIDE, ErrorCode.ERR_MARKET_CLOSED);
        checkMarketTime(market);

        // 将价格和数量限制到最多4位小数
        checkTradeDecimalPoint(tradeVO.getAmount());
        if (tradeVO.getPriceType() != TradePriceType.TYPE_MARKET_TRANSACTION) {
            checkTradeDecimalPoint(tradeVO.getPrice());
            Assert.check(tradeVO.getPrice().compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_MARKET_TRADE_PRICE_LESS_THAN_ZERO);
        }
        Assert.check(tradeVO.getAmount().compareTo(BigDecimal.ZERO) <= 0, ErrorCode.ERR_TRADE_AMOUNT_TOO_LOW);

        BigDecimal totalCurrency = tradeVO.getPrice().multiply(tradeVO.getAmount());

        checkMarketLimitation(market, tradeVO);

        tradeVO.setTotalCurrency(totalCurrency);
        if (tradeVO.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION) {
            // 市价交易，并且是买单，amount是结算货币
            if (tradeVO.getType() == TradeType.BUY) {
                totalCurrency = tradeVO.getAmount();
                tradeVO.setTotalCurrency(tradeVO.getAmount());
                tradeVO.setAmount(BigDecimal.ZERO);
            } else {
                tradeVO.setTotalCurrency(BigDecimal.ZERO);
            }
            tradeVO.setPrice(BigDecimal.ZERO);
        }

        //取最近最低卖一价格，没有则为0
        tradeVO.setSellPrice(BigDecimal.ZERO);
        if(tradeVO.getType().getValue() == TradeType.SELL.getValue())
        {
            TradeVo openMinTradeOne = tradeMapper.getOpenMinTradeOne(TradeType.SELL.getValue(), tradeVO.getCoinName(), tradeVO.getSettlementCurrency());
            if(null != openMinTradeOne){
                tradeVO.setSellPrice(openMinTradeOne.getPrice());
            }
        }

        tradeMapper.addTrade(tradeVO);

        if (tradeVO.getType() == TradeType.BUY) {
            UserCoin userCoin = userCoinMapper.lockUserCoin(tradeVO.getUserId(), tradeVO.getSettlementCurrency());
            Assert.check(userCoin == null, ErrorCode.ERR_USER_COIN_NOT_EXIST);
            Assert.check(userCoin.getAvailableBalance().compareTo(totalCurrency) < 0, ErrorCode.ERR_BALANCE_INSUFFICIENT);
            //可用余额减少
            //userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getSettlementCurrency(),totalCurrency.negate(), BigDecimal.ZERO, UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
            //冻结余额增加
            //userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getSettlementCurrency(), BigDecimal.ZERO, totalCurrency, UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
            userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getSettlementCurrency(), totalCurrency.negate(), totalCurrency, UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
        } else {
            //可用余额减少
            //userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getCoinName(),tradeVO.getAmount().negate(), BigDecimal.ZERO, UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
            //冻结余额增加
            //userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getCoinName(), BigDecimal.ZERO, tradeVO.getAmount(), UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
            userCoinService.changeUserCoin(tradeVO.getUserId(), tradeVO.getCoinName(), tradeVO.getAmount().negate(), tradeVO.getAmount(), UserBillReason.TRADE_CREATE, "tradeId: " + tradeVO.getId());
        }
        updateTradeTime(user.getId());

        return tradeVO.getId();
    }

    // 检查市场交易时间，看看是否支持交易
    private void checkMarketTime(Market market) {
        Date date = new Date();
        String time = this.timeFormat.format(date);
        if (market.getDayExchangeBegin() != null && market.getDayExchangeBegin().length() > 0) {
            Assert.check(time.compareTo(market.getDayExchangeBegin()) < 0, ErrorCode.ERR_MARKET_CLOSED);
        }
        if (market.getDayExchangeEnd() != null && market.getDayExchangeEnd().length() > 0) {
            Assert.check(time.compareTo(market.getDayExchangeEnd()) > 0, ErrorCode.ERR_MARKET_CLOSED);
        }
    }

    //检查涨跌幅和数量
    private void checkMarketLimitation(Market market, TradeVo trade) {
        // 限价市场检查最大挂单总价
        if (trade.getPriceType() == TradePriceType.TYPE_LIMITED_TRANSACTION) {
            Assert.check(market.getMaxCurrency() != null && market.getMaxCurrency().compareTo(BigDecimal.ZERO) > 0
                    && trade.getPrice().multiply(trade.getAmount()).compareTo(market.getMaxCurrency()) > 0, ErrorCode.ERR_TRADE_CURRENCY_TOO_HIGH);
        }
        // 不是市价买入的时候才检查量
        if (trade.getType() == TradeType.BUY && trade.getPriceType() == TradePriceType.TYPE_MARKET_TRANSACTION) {
            // 这里是市价买入，不检查量的限制
        } else {
            if (market.getMinExchangeNum() != null) {
                if (trade.getAmount().compareTo(market.getMinExchangeNum()) < 0) {
                    logger.error("trade amount：{} is less than MinExchangeNum: {} on market: {}", trade.getAmount(), market.getMinExchangeNum(), market.getName());
                    Assert.check(true, ErrorCode.ERR_TRADE_AMOUNT_TOO_LOW);
                }
            }

            if (market.getMaxExchangeNum() != null) {
                if (trade.getAmount().compareTo(market.getMaxExchangeNum()) > 0) {
                    logger.error("trade amount：{} is less than MaxExchangeNum: {} on market: {}", trade.getAmount(), market.getMaxExchangeNum(), market.getName());
                    Assert.check(true, ErrorCode.ERR_TRADE_AMOUNT_TOO_HIGH);
                }
            }
        }

        if (trade.getPriceType() != TradePriceType.TYPE_MARKET_TRANSACTION) {
            if (market.getMinDecrease() != null) {
                if (market.getLastDayPrice() != null && market.getLastDayPrice().compareTo(BigDecimal.ZERO) > 0) {
                    Integer decrease = market.getLastDayPrice().subtract(trade.getPrice()).divide(market.getLastDayPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).intValue();
                    if (decrease > market.getMinDecrease()) {
                        logger.error("trade price: {}, decrease ratio: {} is less than min decrease: {} on market: ", trade.getPrice(), decrease, market.getMinDecrease(), market.getName());
                        Assert.check(true, ErrorCode.ERR_TRADE_PRICE_TOO_LOW);
                    }
                }
            }

            if (market.getMaxIncrease() != null) {
                if (market.getLastDayPrice() != null && market.getLastDayPrice().compareTo(BigDecimal.ZERO) > 0) {
                    Integer increase = trade.getPrice().subtract(market.getLastDayPrice()).divide(market.getLastDayPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).intValue();
                    if (increase > market.getMaxIncrease()) {
                        logger.error("trade price: {}, increase ratio: {} is larger than max increase: {} on market: ", trade.getPrice(), increase, market.getMaxIncrease(), market.getName());
                        Assert.check(true, ErrorCode.ERR_TRADE_PRICE_TOO_HIGH);
                    }
                }
            }
        }
    }

    /**
     * 取消一个交易
     *
     * @param tradeId      交易id
     * @param cancelReason 取消原因，用于记录账单，在UserBillReason中定义
     * @return 成功取消返回true（原来已经取消了也返回true），无法取消或者取消失败返回false
     */
    @Transactional
    public boolean cancelTrade(Integer userId, int tradeId, String cancelReason) {
        // 先锁定记录避免在取消过程中被修改
        Trade trade = tradeMapper.lockTrade(tradeId);
        if (trade.getStatus() == TradeStatus.CANCELED.getValue()) {
            logger.warn("trade already cancel,id=" + tradeId);
            return true;
        }
        if (trade.getStatus() != TradeStatus.OPEN.getValue()) {
            logger.warn("trade is not open,id=" + tradeId);
            return false;
        }

        if (!trade.getUserId().equals(userId)) {
            logger.warn("trade: {}  is not belong to user:{}, can not cancel it", tradeId, userId);
            return true;
        }


        // 检测完成，开始取消，除了修改状态，最重要的是要返回冻结的币种
        if (trade.getType() == TradeType.BUY.getValue()) {
            // 买，冻结的是结算货币，需要退还结算货币
            BigDecimal unlockCurrency = trade.getTotalCurrency().subtract(trade.getDealCurrency());
            userCoinService.changeUserCoin(trade.getUserId(), trade.getSettlementCurrency(), unlockCurrency, unlockCurrency.negate(),
                    cancelReason, "tradeId=" + trade.getId());
        } else {
            // 卖，冻结的是数字货币，返回给用户
            BigDecimal unlockCoin = trade.getAmount().subtract(trade.getDealCoin());
            userCoinService.changeUserCoin(trade.getUserId(), trade.getCoinName(), unlockCoin, unlockCoin.negate(),
                    cancelReason, "tradeId=" + trade.getId());
        }
        trade.setStatus(TradeStatus.CANCELED.getValue());
        tradeMapper.updateTrade(trade);

        return true;
    }

    /**
     * 获取过期的交易
     *
     * @param invalidBefore 指定过期的时间，在这个时间之前下的单还没有完成或取消的会找到
     * @param maxCount      返回的最大记录数
     * @return 过期的交易ID集合
     */
    public List<Integer> getExpiredTradeIds(int invalidBefore, int maxCount) {
        return tradeMapper.getExpiredTradeIds(invalidBefore, maxCount);
    }

    public OpenTradeListVo getOpenTradeList(String coinName, String settlementCurrency) {
        //定时任务计算结果，放到Redis中
        OpenTradeListVo openTradeList = (OpenTradeListVo) redisTemplate.opsForHash().get("OpenTrade", coinName + "_" + settlementCurrency);
        if (openTradeList == null) {
            openTradeList = getAndUpdateOpenTradeList(coinName, settlementCurrency);
        }

        return openTradeList;
    }

    private OpenTradeListVo getAndUpdateOpenTradeList(String coinName, String settlementCurrency) {
        List<TradeVo> buyTradeList = tradeMapper.getOpenTradeList(TradeType.BUY.getValue(), coinName, settlementCurrency, 10);
        List<TradeVo> sellTradeList = tradeMapper.getOpenTradeList(TradeType.SELL.getValue(), coinName, settlementCurrency, 10);

        //查出来的是从到高排序，但是页面需要从高往低排序
        sellTradeList.sort(new Comparator<TradeVo>() {
            @Override
            public int compare(TradeVo o1, TradeVo o2) {
                return o2.getPrice().compareTo(o1.getPrice());
            }
        });
        // 统计单个价格挂单的挂单总量
        if (buyTradeList == null) {
            buyTradeList = new ArrayList<TradeVo>();
        }
        if (sellTradeList == null) {
            sellTradeList = new ArrayList<TradeVo>();
        }
        /*for(TradeVo buyTrade : buyTradeList) {
            buyTrade.setTotalAmount(tradeMapper.getAmountByTypeAndPrice(TradeType.BUY.getValue(), buyTrade.getPrice()));
        }
        for(TradeVo sellTrade : sellTradeList) {
            sellTrade.setTotalAmount(tradeMapper.getAmountByTypeAndPrice(TradeType.SELL.getValue(), sellTrade.getPrice()));
        }*/

        OpenTradeListVo openTradeList = new OpenTradeListVo();
        openTradeList.setBuyTradeList(buyTradeList);
        openTradeList.setSellTradeList(sellTradeList);
        redisTemplate.opsForHash().put("OpenTrade", coinName + "_" + settlementCurrency, openTradeList);
        redisTemplate.expire("OpenTrade", 1, TimeUnit.SECONDS);
        return openTradeList;
    }

    public void updateOpenTradeList() {
        logger.debug("updateOpenTradeList");
        List<Market> markets = marketMapper.getAllMarkets();
        for (Market market : markets) {
            getAndUpdateOpenTradeList(market.getCoinName(), market.getSettlementCurrency());
        }
    }

    public TradeLogListVo getTradeLogList(String coinName, String settlementCurrency) {
        //定时任务计算结果，放到Redis中
        TradeLogListVo tradeLogListVo = (TradeLogListVo) redisTemplate.opsForHash().get("TradeLog", coinName + "_" + settlementCurrency);
        if (tradeLogListVo == null) {
            List<TradeLogVo> tradeLogList = tradeLogMapper.getTradeLogList(coinName, settlementCurrency, 20);

            tradeLogListVo = new TradeLogListVo();
            tradeLogListVo.setTradeLogList(tradeLogList);
        }

        return tradeLogListVo;
    }

    public Page<TradeVo> getOpenTradeListByUser(Integer userId, TradeType type, String coinName, String settlementCurrency, Integer pageNo, Integer pageSize) {
        return tradeMapper.getOpenTradeListByUser(userId, type != TradeType.ALL ? type.getValue() : null, coinName, settlementCurrency, new RowBounds(pageNo, pageSize));
    }

    public Page<TradeLogVo> getTradeLogListByUser(Integer userId, TradeType type, String coinName, String settlementCurrency, Integer pageNo, Integer pageSize) {
        return tradeLogMapper.getTradeLogListByUser(userId, type != TradeType.ALL ? type.getValue() : null, coinName, settlementCurrency, new RowBounds(pageNo, pageSize));
    }

    public Page<TradeVo> getTradeList(String coinName, String settlementCurrency, TradeType type, String userName, String mobile,
                                      BigDecimal price, BigDecimal amount, TradeStatus status, String addStartTime, String addEndTime, String startLastTradeTime, String endLastTradeTime, Integer pageNo, Integer pageSize) {
      /*  Integer  addStartTimes = null;
        if(addStartTime!=null){
            addStartTimes = (int)DateUtil.getDate(addStartTime).getTime();
        }
        Integer  addEndTimes = null;
        if(addEndTime!=null){
            addStartTimes = (int)DateUtil.getDate(addEndTime).getTime();
        }
        Integer startLastTradeTimes = null;
        if(startLastTradeTime!=null){
            startLastTradeTimes = (int)DateUtil.getDate(startLastTradeTime).getTime();
        }
        Integer endLastTradeTimes = null;
        if(endLastTradeTime!=null){
            endLastTradeTimes = (int)DateUtil.getDate(endLastTradeTime).getTime();
        }*/
        Integer lastStatus = null;
        if (status == TradeStatus.DEAL) {
            lastStatus = 1;
        }
        if (status == TradeStatus.OPEN) {
            lastStatus = 0;
        }
        if (status == TradeStatus.CANCELED) {
            lastStatus = 2;
        }
        if (status == TradeStatus.ALL) {
            lastStatus = null;
        }
        if (status == TradeStatus.EXCEPTION) {
            lastStatus = 100;
        }
        return tradeMapper.getTradeList(coinName, settlementCurrency, type != TradeType.ALL ? type.getValue() : null, userName, mobile, price, amount,
                lastStatus, addStartTime, addEndTime, startLastTradeTime, endLastTradeTime, new RowBounds(pageNo, pageSize));
    }

    public Page<TradeVo> getTradeHistoryListByUser(Integer userId, String coinName, String settlementCurrency, TradeType type, Integer pageNo, Integer pageSize) {
        return tradeMapper.getTradeHistoryListByUser(userId, coinName, settlementCurrency, type != TradeType.ALL ? type.getValue() : null, new RowBounds(pageNo, pageSize));
    }

    public Page<TradeLogVo> getTradeLogList(Integer tradeId, Integer pageNo, Integer pageSize) {
        Trade trade = tradeMapper.getTradeById(tradeId);
        Assert.check(trade == null, ErrorCode.ERR_TRADE_NOT_EXIST);
        return tradeLogMapper.getTradeLogListByTradeId(tradeId, trade.getType(), new RowBounds(pageNo, pageSize));
    }

    public Trade getTradeById(int id) {
        return tradeMapper.getTradeById(id);
    }

    //获取价格最高的卖单的价格
    public BigDecimal getHighestPriceSellTrade(String coinName, String settlementCurrency) {
        return tradeMapper.getHighestPriceSellTrade(coinName, settlementCurrency);
    }

    //获取价格最高的卖单的价格
    public BigDecimal getLowestPriceBuyTrade(String coinName, String settlementCurrency) {
        return tradeMapper.getLowestPriceBuyTrade(coinName, settlementCurrency);
    }

    public BigDecimal getLatestPrice(String coinName, String settlementCurrency) {
        return tradeLogMapper.getLatestPrice(coinName, settlementCurrency);
    }

    //订单监控列表
    public Page<TradeVo> getTadeListByTotalCurrency(String coinName, String settlementCurrency, TradeType type, String userName, String mobile, Integer pageNo, Integer pageSize) {

        Page<TradeVo> pageList = null;
        Market market = marketMapper.getMarket(coinName, settlementCurrency);
        if (market == null) {
            return pageList;
        }
        if (type != TradeType.ALL) {
            TradeWarning tradeWarning = tradeWarningMapper.getTradeWarningByMarketId(market.getId(), type.getValue());
            if (tradeWarning == null) {
                return pageList;
            }
            if (mobile != null && mobile.contains("@")) {
                logger.info("进入了邮箱查询列列表====@{}", mobile);
                pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, type != TradeType.ALL ? type.getValue() : null, userName, null, mobile, tradeWarning.getAmount(), new RowBounds(pageNo, pageSize));
            } else {
                pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, type != TradeType.ALL ? type.getValue() : null, userName, mobile, null, tradeWarning.getAmount(), new RowBounds(pageNo, pageSize));
            }
        } else if (type == TradeType.ALL) {

            BigDecimal buyAmount = new BigDecimal(0);
            BigDecimal sellAmount = new BigDecimal(0);
            TradeWarning tradeWarning = tradeWarningMapper.getTradeWarningByMarketId(market.getId(), TradeType.BUY.getValue());
            if (tradeWarning != null) {
                buyAmount = tradeWarning.getAmount();
            }
            tradeWarning = tradeWarningMapper.getTradeWarningByMarketId(market.getId(), TradeType.SELL.getValue());
            if (tradeWarning != null) {
                sellAmount = tradeWarning.getAmount();
            }
            //查询所有
            if (buyAmount.doubleValue() > 0 && sellAmount.doubleValue() == 0) {
                if (mobile != null && mobile.contains("@")) {
                    logger.info("进入了邮箱查询列列表====@{}", mobile);
                    pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, TradeType.BUY.getValue(), userName, null, mobile, buyAmount, new RowBounds(pageNo, pageSize));
                } else {
                    pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, TradeType.BUY.getValue(), userName, mobile, null, buyAmount, new RowBounds(pageNo, pageSize));
                }

            } else if (buyAmount.doubleValue() == 0 && sellAmount.doubleValue() > 0) {
                if (mobile != null && mobile.contains("@")) {
                    logger.info("进入了邮箱查询列列表====@{}", mobile);
                    pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, TradeType.SELL.getValue(), userName, null, mobile, sellAmount, new RowBounds(pageNo, pageSize));
                } else {
                    pageList = tradeMapper.getTadeListByTotalCurrency(coinName, settlementCurrency, TradeType.SELL.getValue(), userName, mobile, null, sellAmount, new RowBounds(pageNo, pageSize));
                }

            } else if (buyAmount.doubleValue() > 0 && sellAmount.doubleValue() > 0) {
                if (mobile != null && mobile.contains("@")) {
                    pageList = tradeMapper.getTadeListByTotalAllCurrency(coinName, settlementCurrency, userName, null, mobile, buyAmount, sellAmount, new RowBounds(pageNo, pageSize));
                } else {
                    pageList = tradeMapper.getTadeListByTotalAllCurrency(coinName, settlementCurrency, userName, mobile, null, buyAmount, sellAmount, new RowBounds(pageNo, pageSize));
                }
            }
        }
        List<TradeWarning> warningList = tradeWarningMapper.getTradeWarningListAll();
        if (warningList != null && warningList.size() > 0) {
            if (pageList != null && pageList.getResult().size() > 0) {
                List<TradeVo> tradeVos = pageList.getResult();
                for (int i = 0; i < tradeVos.size(); i++) {
                    TradeVo tradeVo = tradeVos.get(i);
                    tradeVo.setIsRed(1);
                    pageList.set(i, tradeVo);
                }
            }
        }
        return pageList;
    }


    public void conTructMap(List<TradeWarning> warningList) {

        for (TradeWarning data : warningList) {
            warningMap.put(data.getMarketId(), data.getAmount());
        }

    }

    public boolean getIsRed(Integer marketId, BigDecimal totalCurrency) {
        boolean bool = false;
        BigDecimal bigDecimalConfig = warningMap.get(marketId);
        if (bigDecimalConfig != null) {
            if (totalCurrency.compareTo(bigDecimalConfig) >= 0) {
                return true;
            }
        }
        return bool;
    }

    /**
     * 此方法用于高频交易的位置
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<HFTradeVO> getUserTradeByTimeConfig(Long startTime, Long endTime, String coinName, String settlementCurrency) {
        return tradeMapper.getUserTradeByTimeConfig(startTime, endTime, coinName, settlementCurrency);
    }


    /**
     * 获取挂单报警的长度，用于全局报警值判断
     */
    public Set<String> getGolbalTradeMonitor() {
        //boolean bool =true;
        Set<String> tradeList = new HashSet<>();
        List<Market> marketList = marketMapper.getAllMarkets();
        if (marketList == null) {
            return tradeList;
        }
        //int count = marketMapper.getTadeListByTotalCurrencyCount();
        for (Market market : marketList) {

            TradeWarning tradeWarning = tradeWarningMapper.getTradeWarningByMarketId(market.getId(), TradeType.BUY.getValue());
            if (tradeWarning != null) {
                int count = tradeMapper.getTadeListByTotalCurrencyCount(market.getCoinName(), market.getSettlementCurrency(), tradeWarning.getAmount(), TradeType.BUY.getValue());
                if (count > 0) {
                    tradeList.add(market.getCoinName() + "/" + market.getSettlementCurrency());
                }
            }
            tradeWarning = tradeWarningMapper.getTradeWarningByMarketId(market.getId(), TradeType.SELL.getValue());
            if (tradeWarning != null) {
                int count = tradeMapper.getTadeListByTotalCurrencyCount(market.getCoinName(), market.getSettlementCurrency(), tradeWarning.getAmount(), TradeType.SELL.getValue());
                if (count > 0) {
                    tradeList.add(market.getCoinName() + "/" + market.getSettlementCurrency());
                }
            }


        }
        return tradeList;
    }

    private String getUserName(Map<Integer, String> userNames, Integer userId) {
        if (userId == null || userId == 0) return null;
        // 从内存获取，内存不存在从数据库获取
        String name = userNames.get(userId);
        if (name != null) return name;
        User user = userMapper.getUserByUserId(userId);
        if (user != null) {
            userNames.put(userId, user.getUserName());
            return user.getUserName();
        }
        // 不应该发生不存在用户的情况，发生了使用空字符串，避免下次再到数据库获取
        userNames.put(userId, "");
        return "";
    }

    public Page<TradeLogRewardVo> getTradeLogRewardList(String coinName, String settlementCurrency, Integer buyUserId, Integer sellUserId, boolean onlyReward,
                                                        String startLastTradeTime, String endLastTradeTime, Integer pageNo, Integer pageSize) {
        Page<TradeLogRewardVo> result = tradeLogMapper.getTradeLogRewardList(coinName, settlementCurrency, buyUserId, sellUserId, onlyReward,
                startLastTradeTime, endLastTradeTime, new RowBounds(pageNo, pageSize));
        // 保存用户名
        Map<Integer, String> userNames = new HashMap<>();
        for (TradeLogRewardVo vo : result) {
            vo.setBuyUserName(getUserName(userNames, vo.getBuyUserId()));
            vo.setBuyRecUserName(getUserName(userNames, vo.getBuyRecUserId()));
            vo.setBuyRec2UserName(getUserName(userNames, vo.getBuyRec2UserId()));
            vo.setSellUserName(getUserName(userNames, vo.getSellUserId()));
            vo.setSellRecUserName(getUserName(userNames, vo.getSellRecUserId()));
            vo.setSellRec2UserName(getUserName(userNames, vo.getSellRec2UserId()));
        }
        return result;
    }
}
