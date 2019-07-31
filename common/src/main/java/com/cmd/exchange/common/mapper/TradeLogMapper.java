package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.constants.TradeType;
import com.cmd.exchange.common.model.TradeFeeReturnDetail;
import com.cmd.exchange.common.model.TradeLog;
import com.cmd.exchange.common.model.UserDayProfit;
import com.cmd.exchange.common.model.UserTradeResult;
import com.cmd.exchange.common.vo.TradeLogRewardVo;
import com.cmd.exchange.common.vo.TradeLogVo;
import com.cmd.exchange.common.vo.TradeVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TradeLogMapper {
    @Insert("insert into t_trade_log(coin_name,settlement_currency,price,amount,buy_user_id,sell_user_id,add_time,buy_fee_coin,sell_fee_currency,buy_trade_id,sell_trade_id)"
            + "values(#{coinName},#{settlementCurrency},#{price},#{amount},#{buyUserId},#{sellUserId},unix_timestamp(),#{buyFeeCoin},#{sellFeeCurrency},#{buyTradeId},#{sellTradeId})")
    int addTradeLog(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency, @Param("price") BigDecimal price,
                    @Param("amount") BigDecimal amount, @Param("buyUserId") int buyUserId, @Param("sellUserId") int sellUserId,
                    @Param("buyFeeCoin") BigDecimal buyFeeCoin, @Param("sellFeeCurrency") BigDecimal sellFeeCurrency,
                    @Param("buyTradeId") int buyTradeId, @Param("sellTradeId") int sellTradeId);

    /**
     * 获取指定交易市场指定之间之前的最后一次交易的单价
     *
     * @param coinName           交易市场的数字货币
     * @param settlementCurrency 交易市场的结算货币
     * @param beforeTime         结束时间，一般是一天的0时0点(不包括这个时间)
     * @return 结束时间之前的最后一次交易的单价，可能是NULL
     */
    @Select("select price from t_trade_log where coin_name=#{coinName} and settlement_currency=#{settlementCurrency}" +
            " and add_time<#{beforeTime} order by id desc LIMIT 0,1")
    BigDecimal getMarketLastPrice(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency,
                                  @Param("beforeTime") int beforeTime);

    /**
     * 获取最新的交易价格
     *
     * @param coinName           交易市场的数字货币
     * @param settlementCurrency 交易市场的结算货币
     * @return 最后一次交易的单价，可能是NULL
     */
    @Select("select price from t_trade_log where coin_name=#{coinName} and settlement_currency=#{settlementCurrency} order by id desc LIMIT 0,1")
    BigDecimal getMarketLatestPrice(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    /**
     * 获取下一个比指定id大但是时间在endTime之前并且含有费用的交易记录
     *
     * @param lastId  指定的id，要求返回的id比这个大
     * @param endTime 最后时间（不包含）
     * @return 交易日志
     */
    TradeLog getNextHasFeeTradeLog(@Param("lastId") BigInteger lastId, @Param("endTime") int endTime);

    // 获取下一条USDT结算并且有USDT费用的日志，自己跟自己交易不会奖励
    @Select("SELECT *  FROM t_trade_log  WHERE id > #{lastId} and sell_fee_currency > 0 and settlement_currency='USDT' and buy_user_id != sell_user_id ORDER by id ASC limit 0,1")
    TradeLog getNextUsdtFeeTradeLog(@Param("lastId") BigInteger lastId);

    // 获取下一条交易日志
    @Select("select * from t_trade_log where id>#{lastId} order by id asc LIMIT 0,1")
    TradeLog getNextTradeLog(@Param("lastId") BigInteger lastId);

    @Select("select * from t_trade_log order by id desc limit 0,1")
    TradeLog getLastTrade();

    /**
     * 获取市场成交订单，时间从大到小排序
     *
     * @param coinName           交易市场的数字货币
     * @param settlementCurrency 交易市场的结算货币
     */
    List<TradeLogVo> getTradeLogList(
            @Param("coinName") String coinName,
            @Param("settlementCurrency") String settlementCurrency, @Param("limit") int limit);

    Page<TradeLogVo> getTradeLogListByUser(@Param("userId") Integer userId,
                                           @Param("type") Integer type,
                                           @Param("coinName") String coinName,
                                           @Param("settlementCurrency") String settlementCurrency,
                                           RowBounds rowBounds);

    Page<TradeLogVo> getTradeLogListByTradeId(@Param("tradeId") Integer tradeId, @Param("type") Integer type, RowBounds rowBounds);

    BigDecimal getLatestPrice(@Param("coinName") String coinName,
                              @Param("settlementCurrency") String settlementCurrency);

    // 计算一段时间内的用户交易排行
    List<UserTradeResult> getUserTradeResult(@Param("settlementCurrency") String settlementCurrency, @Param("limit") int limit,
                                             @Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd);

    // 就是你一段时间内所有用户的交易费用总和（只会返回费用大于0的）
    @Select("select user_id,coin_name,sum(total_fee) as total_fee from \n" +
            "(\n" +
            "select buy_user_id as user_id,coin_name,sum(buy_fee_coin) as total_fee from t_trade_log \n" +
            "where add_time >= #{timeBegin} and add_time < #{timeEnd} and buy_fee_coin>0 group by buy_user_id,coin_name\n" +
            "union\n" +
            "select sell_user_id as user_id,settlement_currency as coin_name,sum(sell_fee_currency) as total_fee from t_trade_log \n" +
            "where add_time >= #{timeBegin} and add_time < #{timeEnd} and sell_fee_currency>0 group by sell_user_id,settlement_currency\n" +
            ") as tmpTable group by user_id,coin_name")
    List<UserTradeResult> statUserTradeFee(@Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd);

    // 获取有费用的交易日志
    @Select({"<script>",
            "select id,coin_name,settlement_currency,buy_user_id,sell_user_id,buy_fee_coin,sell_fee_currency from t_trade_log ",
            " where (buy_fee_coin>0 or sell_fee_currency>0) and add_time >= #{timeBegin} and #{timeEnd} > add_time ",
            "<if test='lastId != null'> and id > #{lastId} </if>",
            " order by id limit 0,#{limit}",
            "</script>"})
    List<TradeLog> getTradeLogHasFee(@Param("lastId") BigInteger lastId, @Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd, @Param("limit") int limit);

    // 统计USDT的交易费用
    @Select("select sum(sell_fee_currency) from t_trade_log where settlement_currency='USDT' and add_time >= #{timeBegin} and #{timeEnd} > add_time")
    BigDecimal statUsdtFee(@Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd);

    // 统计用户卖出币种获取的所有usdt和费用
    @Select("select sell_user_id as user_id,sum(price*amount) as totalUsdt,sum(sell_fee_currency) as total_fee from t_trade_log " +
            "where settlement_currency='USDT' and add_time >= #{timeBegin} and add_time < #{timeEnd} group by sell_user_id")
    List<UserTradeResult> statUserUsdtSellTrade(@Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd);

    // 统计用户买入币种花费的所有usdt
    @Select("select buy_user_id as user_id,sum(price*amount) as totalUsdt from t_trade_log " +
            "where settlement_currency='USDT' and add_time >= #{timeBegin} and add_time < #{timeEnd} group by buy_user_id")
    List<UserTradeResult> statUserUsdtBuyTrade(@Param("timeBegin") int timeBegin, @Param("timeEnd") int timeEnd);


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 以下是处理交易返还手续费给一级和二级推荐用户的
    @Insert("INSERT INTO `t_trade_fee_return_detail` (`trade_log_id`,`ret_time`,`rec_user_rate`,`rec2_user_rate`," +
            "`buy_ret_coin`,`buy_rec_user_id`,`buy_rec_ret`,`buy_rec2_user_id`,`buy_rec2_ret`," +
            "`sell_ret_coin`,`sell_rec_user_id`,`sell_rec_ret`,`sell_rec2_user_id`,`sell_rec2_ret`,`sell_user_id`)" +
            "VALUES (#{tradeLogId},#{retTime},#{recUserRate},#{rec2UserRate}," +
            "#{buyRetCoin},#{buyRecUserId},#{buyRecRet},#{buyRec2UserId},#{buyRec2Ret}," +
            "#{sellRetCoin},#{sellRecUserId},#{sellRecRet},#{sellRec2UserId},#{sellRec2Ret},#{userId})")
    int addTradeFeeReturnDetail(TradeFeeReturnDetail tradeFeeReturnDetail);

    @Select("select * from t_trade_fee_return_detail where log_trade_id=#{logId} limit 1")
    TradeFeeReturnDetail getTradeFeeReturnDetailByLogId(@Param("logId") BigInteger logId);

    @Select({"<script>",
            "select t_trade_log.*,detail.ret_time,detail.rec_user_rate,detail.rec2_user_rate,detail.buy_rec_user_id,detail.buy_rec_ret,",
            " detail.buy_rec2_user_id,detail.buy_rec2_ret,detail.sell_rec_user_id,detail.sell_rec_ret,detail.sell_rec2_user_id,detail.sell_rec2_ret ",
            " from t_trade_log left join t_trade_fee_return_detail detail on detail.trade_log_id=t_trade_log.id ",
            " where 1=1 ",
            "<if test='coinName != null and coinName.length > 0'> and t_trade_log.coin_name = #{coinName} </if>",
            "<if test='settlementCurrency != null and settlementCurrency.length > 0'> and t_trade_log.settlement_currency = #{settlementCurrency} </if>",
            "<if test='buyUserId != null'> and t_trade_log.buy_user_id = #{buyUserId} </if>",
            "<if test='sellUserId != null'> and t_trade_log.sell_user_id = #{sellUserId} </if>",
            "<if test='startTime != null and startTime.length > 0'> and t_trade_log.add_time >= unix_timestamp(#{startTime}) </if>",
            "<if test='endTime != null and endTime.length > 0'> and unix_timestamp(#{endTime}) >= t_trade_log.add_time </if>",
            "<if test='onlyReward'> and detail.rec_user_rate is not NULL </if>",
            " order by id desc",
            "</script>"})
    Page<TradeLogRewardVo> getTradeLogRewardList(
            @Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency, @Param("buyUserId") Integer buyUserId,
            @Param("sellUserId") Integer sellUserId, @Param("onlyReward") boolean onlyReward, @Param("startTime") String startTime,
            @Param("endTime") String endTime, RowBounds rowBounds);
}
