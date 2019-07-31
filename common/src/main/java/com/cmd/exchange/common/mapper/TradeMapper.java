package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.enums.TradeStatus;
import com.cmd.exchange.common.model.Trade;
import com.cmd.exchange.common.vo.HFTradeVO;
import com.cmd.exchange.common.vo.TradeVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TradeMapper {
    void addTrade(TradeVo trade);

    /**
     * 查找一个卖出的单，这个单的卖出价是当前所有卖出单中单价最低并且小于等于指定买入价的，并且入库比指定ID小的,如果是有多个价格一样的单，那么找最先进入数据库的单
     *
     * @return 匹配条件的卖出的单
     */
    // 自己不能跟自己匹配
    //@Select("select * from t_trade where `status`=0 and `type`=2 and `price`<=#{buyTradePrice}" +
    //    " and id < #{buyTradeId} and coin_name=#{coin} and settlement_currency=#{currency} and user_id<>#{userId} order by price asc,id asc limit 0,1")
    // 自己能跟自己匹配
    @Select("select * from t_trade where `status`=0 and `type`=2 and `price`<=#{buyTradePrice}" +
            " and id < #{buyTradeId} and coin_name=#{coin} and settlement_currency=#{currency} order by price asc,id asc limit 0,1")
    Trade getMatchSellTrade(@Param("buyTradeId") int buyTradeId, @Param("buyTradePrice") BigDecimal buyTradePrice,
                            @Param("coin") String coin, @Param("currency") String currency, @Param("userId") int userId);

    /**
     * 查找一个买入的单，这个单买入价比指定价格高，并且id币指定id小
     *
     * @return 匹配条件的买入的单
     */
    // 自己不能跟自己匹配
    //@Select("select * from t_trade where `status`=0 and `type`=1 and `price`>=#{sellTradePrice}" +
    //        " and id < #{sellTradeId} and coin_name=#{coin} and settlement_currency=#{currency} and user_id<>#{userId}  order by price desc, id asc limit 0,1")
    // 自己能跟自己匹配
    @Select("select * from t_trade where `status`=0 and `type`=1 and `price`>=#{sellTradePrice}" +
            " and id < #{sellTradeId} and coin_name=#{coin} and settlement_currency=#{currency} order by price desc, id asc limit 0,1")
    Trade getMatchBuyTrade(@Param("sellTradeId") int buyTradeId, @Param("sellTradePrice") BigDecimal buyTradePrice,
                           @Param("coin") String coin, @Param("currency") String currency, @Param("userId") int userId);

    /**
     * 获取比指定tradeId大的最先入库的没有完成的交易
     *
     * @param tradeId 指定的交易id
     * @return
     */
    @Select("select * from t_trade where `status`=0 and id > #{tradeId} order by id asc limit 0,1")
    Trade getNextOpenTrade(int tradeId);

    @Select("select * from t_trade where `status`=0 and id > #{tradeId} order by id asc limit 0,#{limit}")
    List<Trade> getNextOpenTrades(@Param("tradeId") int tradeId, @Param("limit") int limit);

    /**
     * 锁定记录，禁止外部修改
     *
     * @param id 记录id
     * @return Trade
     */
    @Select("select * from t_trade where id=#{id} for update")
    Trade lockTrade(int id);

    @Select("select * from t_trade where id=#{id}")
    Trade getTradeById(int id);

    /**
     * 更新交易信息，只会更新正常交易后的修改信息，不会更新不应该变化的信息，例如用户id
     *
     * @param trade 交易
     * @return 修改数量
     */
    @Update("update t_trade set deal_coin=#{dealCoin},deal_currency=#{dealCurrency},fee_coin=#{feeCoin},"
            + "fee_currency=#{feeCurrency},last_trade_time=unix_timestamp(),status=#{status} where id=#{id}")
    int updateTrade(Trade trade);

    /**
     * 获取过期的交易
     *
     * @param invalidBefore 指定过期的时间，在这个时间之前下的单还没有完成或取消的会找到
     * @param maxCount      返回的最大记录数
     * @return 过期的交易ID集合
     */
    @Select("select id from t_trade where `status`=0 and add_time<#{invalidBefore} limit 0,#{maxCount}")
    List<Integer> getExpiredTradeIds(@Param("invalidBefore") int invalidBefore, @Param("maxCount") int maxCount);

    @Select("select id,user_id,coin_name,settlement_currency,price,`type` from t_trade where `status`=0 and price_type=0 and id<=#{lastId}")
    List<Trade> getOpenTradesBeforeId(@Param("lastId") int lastId);

    /**
     * 获取市场订单深度（同样价格订单会被合并）, 价格从高到低排序
     *
     * @param coinName
     * @param settlementCurrency
     * @param limit              深度
     */
    List<TradeVo> getOpenTradeList(@Param("type") Integer type,
                                   @Param("coinName") String coinName,
                                   @Param("settlementCurrency") String settlementCurrency, @Param("limit") int limit);

    Page<TradeVo> getOpenTradeListByUser(@Param("userId") Integer userId,
                                         @Param("type") Integer type,
                                         @Param("coinName") String coinName,
                                         @Param("settlementCurrency") String settlementCurrency,
                                         RowBounds rowBounds);

    Page<TradeVo> getTradeList(@Param("coinName") String coinName,
                               @Param("settlementCurrency") String settlementCurrency,
                               @Param("type") Integer type, @Param("userName") String userName,
                               @Param("mobile") String mobile,
                               @Param("price") BigDecimal price,
                               @Param("amount") BigDecimal amount,
                               @Param("status") Integer status,
                               @Param("addStartTime") String addStartTime,
                               @Param("addEndTime") String addEndTime,
                               @Param("startLastTradeTime") String startLastTradeTime,
                               @Param("endLastTradeTime") String endLastTradeTime,
                               RowBounds rowBounds

    );

    //查找已经成交或者已经取消的订单
    Page<TradeVo> getTradeHistoryListByUser(@Param("userId") Integer userId,
                                            @Param("coinName") String coinName,
                                            @Param("settlementCurrency") String settlementCurrency,
                                            @Param("type") Integer type,
                                            RowBounds rowBounds);

    BigDecimal getAmountByTypeAndPrice(@Param("type") Integer type, @Param("price") BigDecimal price);

    BigDecimal getHighestPriceSellTrade(@Param("coinName") String coinName,
                                        @Param("settlementCurrency") String settlementCurrency);

    BigDecimal getLowestPriceBuyTrade(@Param("coinName") String coinName,
                                      @Param("settlementCurrency") String settlementCurrency);


    /**
     * 订单监控
     *
     * @param coinName
     * @param settlementCurrency
     * @param type
     * @param
     * @param mobile
     * @param rowBounds
     * @return
     */

    Page<TradeVo> getTadeListByTotalCurrency(@Param("coinName") String coinName,
                                             @Param("settlementCurrency") String settlementCurrency,
                                             @Param("type") Integer type, @Param("userName") String userName,
                                             @Param("mobile") String mobile, @Param("email") String email,
                                             @Param("amount") BigDecimal amount,
                                             RowBounds rowBounds);

    Page<TradeVo> getTadeListByTotalAllCurrency(@Param("coinName") String coinName,
                                                @Param("settlementCurrency") String settlementCurrency,
                                                @Param("userName") String userName,
                                                @Param("mobile") String mobile, @Param("email") String email,
                                                @Param("buyAmount") BigDecimal buyAmount,
                                                @Param("sellAmount") BigDecimal sellAmount,
                                                RowBounds rowBounds);


    List<HFTradeVO> getUserTradeByTimeConfig(@Param("startTime") Long startTime,
                                             @Param("endTime") Long endTime,
                                             @Param("coinName") String coinName,
                                             @Param("settlementCurrency") String settlementCurrency);


    /**
     * 订单全局监控
     *
     * @param coinName
     * @param settlementCurrency
     * @param
     * @return
     */
    int getTadeListByTotalCurrencyCount(@Param("coinName") String coinName,
                                        @Param("settlementCurrency") String settlementCurrency,
                                        @Param("amount") BigDecimal amount,
                                        @Param("type") Integer type
    );

    /**
     * 取一条最近最低卖一价格
     * @param type
     * @param coinName
     * @param settlementCurrency
     * @return
     */
    TradeVo getOpenMinTradeOne(@Param("type") Integer type,
                               @Param("coinName") String coinName,
                               @Param("settlementCurrency") String settlementCurrency);
}