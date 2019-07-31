package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.TradeStat;
import com.cmd.exchange.common.vo.CandleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface TradeStatMapper {

    /**
     * 统计指定时间内每分钟各种交易的统计
     * 该sql语句比较复杂，一个语句并且没有联合查询下统计出了各种交易每分钟内的数字币种，结束货币，总交易数字货币，总结算货币量，交易次数，最低价，最高价，第一单（开盘）价，最后一单（收盘）价
     * 如果要做不是一分钟的，比如10分钟，只要吧里面的mod 60修改成mod 600就可以
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @return 统计结果
     */
    @Select("select coin_name,settlement_currency,sum(amount) as sum_amount,sum(price*amount) as sum_currency,count(*) as deal_times," +
            " min(price) as min_price,max(price)as max_price, SUBSTRING(min(concat(100000000000000 + id,'-',price)), 17) as first_price," +
            " SUBSTRING(max(concat(100000000000000 + id,'-',price)), 17) as last_price, (add_time - add_time mod 60) as stat_time, 1 as stat_cycle" +
            " from t_trade_log where add_time >= #{beginTime} and add_time < #{endTime} " +
            "group by coin_name,settlement_currency,(add_time - add_time mod 60)")
    List<Map> getPerOneMinTradeStatInfoFromLog(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    /**
     * 统计指定时间内 货币量，交易次数，最低价，最高价，第一单（开盘）价，最后一单（收盘）价
     *
     * @param coinName           交易市场的数字货币
     * @param settlementCurrency 交易市场的结算货币
     * @param beginTime          统计开始时间
     * @param endTime            统计结束时间
     * @return 统计结果
     */
    @Select("select coin_name,settlement_currency,sum(amount) as sum_amount,sum(price*amount) as sum_currency,count(*) as deal_times," +
            " min(price) as min_price,max(price)as max_price, SUBSTRING(min(concat(100000000000000 + id,'-',price)), 17) as first_price," +
            " SUBSTRING(max(concat(100000000000000 + id,'-',price)), 17) as last_price" +
            " from t_trade_log where add_time >= #{beginTime} and add_time < #{endTime} and coin_name=#{coinName} and settlement_currency=#{settlementCurrency}")
    Map getStatInfoFromLog(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency,
                           @Param("beginTime") int beginTime, @Param("endTime") int endTime);

    /**
     * 从t_trade_log中统计指定时间内每分钟各种交易情况，并且插入到t_trade_stat表总
     * 该sql语句比较复杂，一个语句并且没有联合查询下统计出了各种交易每分钟内的数字币种，结束货币，总交易数字货币，总结算货币量，交易次数，最低价，最高价，第一单（开盘）价，最后一单（收盘）价
     * 如果要做不是一分钟的，比如10分钟，只要吧里面的mod 60修改成mod 600就可以
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @return 统计的条数
     */
    @Select("insert into t_trade_stat (coin_name, settlement_currency,deal_amount,deal_total_currency,fee_amount,fee_total_currency,deal_times,deal_min_price,deal_max_price,deal_first_price,deal_last_price,stat_time,stat_cycle)" +
            " select * from" +
            "(" +
            " select coin_name,settlement_currency,sum(amount) as sum_amount,sum(price*amount) as sum_currency,sum(buy_fee_coin) as fee_amount,sum(sell_fee_currency) as fee_total_currency,count(*) as deal_times," +
            " min(price) as min_price,max(price)as max_price, SUBSTRING(min(concat(100000000000000 + id,'-',price)), 17) as first_price," +
            " SUBSTRING(max(concat(100000000000000 + id,'-',price)), 17) as last_price, (add_time - add_time mod 60) as stat_time, 1 as stat_cycle " +
            " from t_trade_log where add_time >= #{beginTime} and add_time < #{endTime} " +
            " group by coin_name,settlement_currency,(add_time - add_time mod 60)" +
            ")" + "as t where not exists (select id from t_trade_stat where stat_time=t.stat_time and stat_cycle=t.stat_cycle and coin_name=t.coin_name and settlement_currency=t.settlement_currency )"
    )
    Integer statPerOneMinTrade(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    /**
     * 将t_trade_stat表中1分钟的统计汇总成其他周期的统计
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @param minute    统计间隔，请务必写成60的约数，这样统计才有规律
     * @return 插入的条数
     */
    @Select("insert into t_trade_stat (coin_name, settlement_currency,deal_amount,deal_total_currency,fee_amount,fee_total_currency,deal_times,deal_min_price,deal_max_price,deal_first_price,deal_last_price,stat_time,stat_cycle)" +
            " select * from" +
            "(" +
            " select coin_name,settlement_currency,sum(deal_amount) as sum_amount,sum(deal_total_currency) as sum_currency,sum(fee_amount),sum(fee_total_currency),sum(deal_times) as deal_times," +
            "  min(deal_min_price) as min_price,max(deal_max_price)as max_price, SUBSTRING(min(concat(100000000000000 + stat_time,'-',deal_first_price)), 17) as first_price," +
            "  SUBSTRING(max(concat(100000000000000 + id,'-',deal_last_price)), 17) as last_price, (stat_time - stat_time mod (60 * #{minute})) as stat_time, #{minute} as stat_cycle" +
            " from t_trade_stat where stat_time >= #{beginTime} and stat_time < #{endTime} and stat_cycle=1" +
            " group by coin_name,settlement_currency,(stat_time - stat_time mod (60 * #{minute}))" +
            ")" + "as t where not exists (select id from t_trade_stat where stat_time=t.stat_time and stat_cycle=t.stat_cycle and coin_name=t.coin_name and settlement_currency=t.settlement_currency )"
    )
    Integer statCycleTrade(@Param("beginTime") int beginTime, @Param("endTime") int endTime, @Param("minute") int minute);

    /**
     * 将t_trade_stat表中1分钟的统计汇总成周期是1天的统计
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @return 插入的条数
     */
    @Select("insert into t_trade_stat (coin_name, settlement_currency,deal_amount,deal_total_currency,fee_amount,fee_total_currency,deal_times,deal_min_price,deal_max_price,deal_first_price,deal_last_price,stat_time,stat_cycle)" +
            " select * from" +
            "(" +
            "select coin_name,settlement_currency,sum(deal_amount) as sum_amount,sum(deal_total_currency) as sum_currency,sum(fee_amount),sum(fee_total_currency),sum(deal_times) as deal_times,\n" +
            " min(deal_min_price) as min_price,max(deal_max_price)as max_price, SUBSTRING(min(concat(100000000000000 + stat_time,'-',deal_first_price)), 17) as first_price,\n" +
            " SUBSTRING(max(concat(100000000000000 + id,'-',deal_last_price)), 17) as last_price, min(unix_timestamp(FROM_UNIXTIME(stat_time, '%Y-%m-%d 00:00:00'))) as stat_time, 1440 as stat_cycle\n" +
            " from t_trade_stat where stat_time >= #{beginTime} and stat_time < #{endTime} and stat_cycle=1\n" +
            " group by coin_name,settlement_currency,unix_timestamp(FROM_UNIXTIME(stat_time, '%Y-%m-%d  00:00:00'))" +
            ")" + "as t where not exists (select id from t_trade_stat where stat_time=t.stat_time and stat_cycle=t.stat_cycle and coin_name=t.coin_name and settlement_currency=t.settlement_currency )"
    )
    Integer statDayTrade(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    /**
     * 将t_trade_stat表中1天的统计汇总成周期是多天的统计
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @param dayCycle  时间周期，天为单位
     * @return 插入的条数
     */
    @Select("insert into t_trade_stat (coin_name, settlement_currency,deal_amount,deal_total_currency,fee_amount,fee_total_currency,deal_times,deal_min_price,deal_max_price,deal_first_price,deal_last_price,stat_time,stat_cycle)" +
            " select * from" +
            "(" +
            "select coin_name,settlement_currency,sum(deal_amount) as sum_amount,sum(deal_total_currency) as sum_currency,sum(fee_amount),sum(fee_total_currency),sum(deal_times) as deal_times,\n" +
            " min(deal_min_price) as min_price,max(deal_max_price)as max_price, SUBSTRING(min(concat(100000000000000 + stat_time,'-',deal_first_price)), 17) as first_price,\n" +
            " SUBSTRING(max(concat(100000000000000 + id,'-',deal_last_price)), 17) as last_price, stat_time - (stat_time - unix_timestamp('2001-01-01')) mod (86400 * #{dayCycle}) as stat_time, 1440 * #{dayCycle} as stat_cycle\n" +
            " from t_trade_stat where stat_time >= #{beginTime} and stat_time < #{endTime} - (#{endTime} - unix_timestamp('2001-01-01')) mod (86400 * #{dayCycle}) and stat_cycle=1440\n" +
            " group by coin_name,settlement_currency,stat_time - (stat_time - unix_timestamp('2001-01-01')) mod (86400 * #{dayCycle})" +
            ")" + "as t where not exists (select id from t_trade_stat where stat_time=t.stat_time and stat_cycle=t.stat_cycle and coin_name=t.coin_name and settlement_currency=t.settlement_currency )"
    )
    Integer statNDayTrade(@Param("beginTime") int beginTime, @Param("endTime") int endTime, @Param("dayCycle") int dayCycle);

    /**
     * 将t_trade_stat表中1天的统计汇总成周期是1个月的统计
     *
     * @param beginTime 统计开始时间
     * @param endTime   统计结束时间
     * @return 插入的条数
     */
    @Select("insert into t_trade_stat (coin_name, settlement_currency,deal_amount,deal_total_currency,fee_amount,fee_total_currency,deal_times,deal_min_price,deal_max_price,deal_first_price,deal_last_price,stat_time,stat_cycle)" +
            " select * from" +
            "(" +
            "select coin_name,settlement_currency,sum(deal_amount) as sum_amount,sum(deal_total_currency) as sum_currency,sum(fee_amount),sum(fee_total_currency),sum(deal_times) as deal_times,\n" +
            " min(deal_min_price) as min_price,max(deal_max_price)as max_price, SUBSTRING(min(concat(100000000000000 + stat_time,'-',deal_first_price)), 17) as first_price,\n" +
            " SUBSTRING(max(concat(100000000000000 + id,'-',deal_last_price)), 17) as last_price, min(unix_timestamp(FROM_UNIXTIME(stat_time, '%Y-%m-01 00:00:00'))) as stat_time, 44640 as stat_cycle\n" +
            " from t_trade_stat where stat_time >= #{beginTime} and stat_time < #{endTime} and stat_cycle=1440\n" +
            " group by coin_name,settlement_currency,unix_timestamp(FROM_UNIXTIME(stat_time, '%Y-%m-01  00:00:00'))" +
            ")" + "as t where not exists (select id from t_trade_stat where stat_time=t.stat_time and stat_cycle=t.stat_cycle and coin_name=t.coin_name and settlement_currency=t.settlement_currency )"
    )
    Integer statMonthTrade(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    /**
     * 获取指定周期的最后一条记录
     *
     * @param cycle 周期
     * @return TradeStat
     */
    @Select("select * from t_trade_stat where stat_cycle=#{cycle} order by stat_time desc limit 0,1")
    TradeStat getLastTradeStat(int cycle);

    // 统计某一天的费用
    @Select("select * from t_trade_stat where stat_time=#{beginTime} and stat_cycle=#{cycle} and (fee_amount > 0 or fee_total_currency > 0)")
    List<TradeStat> getTradeStatOfTimeAndCycle(@Param("beginTime") int beginTime, @Param("cycle") int cycle);

    // 统计一段时间内的费用
    @Select("select sum(fee_amount) as fee_amount,sum(fee_total_currency) as fee_total_currency,coin_name,settlement_currency " +
            " from t_trade_stat where stat_time>=#{beginTime} and stat_time<#{endTime} and stat_cycle=1 and (fee_amount > 0 or fee_total_currency > 0)" +
            " group by coin_name,settlement_currency")
    List<TradeStat> getTradeFeeStatOfTime(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    List<CandleVo> getCandles(@Param("minutes") Integer minutes, @Param("coinName") String coinName,
                              @Param("settlementCurrency") String settlementCurrency,
                              @Param("startTime") long startTime,
                              @Param("endTime") long endTime);

    @Select("select coin_name,settlement_currency,sum(fee_amount) as fee_amount,sum(`fee_total_currency`) as fee_total_currency"
            + " from t_trade_stat where stat_time>=#{beginTime} and stat_time < #{endTime} and stat_cycle=1 group by coin_name,settlement_currency ")
    List<TradeStat> getSumFeeBetweenTime(@Param("beginTime") int beginTime, @Param("endTime") int endTime);


    /**
     * 获取指定交易市场指定之间之前的最后一小时交易的均价
     *
     * @param coinName           交易市场的数字货币
     * @param settlementCurrency 交易市场的结算货币
     * @param beforeTime         结束时间，一般是一小时的0时0点(不包括这个时间)
     * @return 结束时间之前的最后一次交易的单价，可能是NULL
     */
    @Select("select deal_total_currency/deal_amount from t_trade_stat where coin_name=#{coinName} and settlement_currency=#{settlementCurrency}" +
            " and stat_time<#{beforeTime} and stat_cycle=60 and deal_amount>0 order by id desc LIMIT 0,1")
    BigDecimal getAvgPriceOfHour(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency,
                                 @Param("beforeTime") int beforeTime);
}
