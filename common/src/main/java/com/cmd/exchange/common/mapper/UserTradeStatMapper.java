package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserTradeStat;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;

@Mapper
public interface UserTradeStatMapper {
    @Options(useGeneratedKeys = true)
    @Insert("insert into t_user_trade_stat(user_id,coin_name,settlement_currency,coin_buy_total,settlement_buy_total,coin_sell_total,settlement_sell_total,"
            + "hold_coin,settlement_cost_total,coin_fee,settlement_fee,first_trade_log_id,last_trade_log_id,create_time,update_time) values("
            + "#{userId},#{coinName},#{settlementCurrency},#{coinBuyTotal},#{settlementBuyTotal},#{coinSellTotal},#{settlementSellTotal},"
            + "#{holdCoin},#{settlementCostTotal},#{coinFee},#{settlementFee},#{firstTradeLogId},#{lastTradeLogId},NOW(),NOW())")
    void addUserTradeStat(UserTradeStat userTradeStat);

    @Select("select * from t_user_trade_stat where user_id=#{userId} and coin_name=#{coinName} and settlement_currency=#{settlementCurrency} limit 0,1 for update")
    UserTradeStat lockUserTradeStat(@Param("userId") int userId, @Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    @Update("update t_user_trade_stat set coin_buy_total=#{coinBuyTotal},settlement_buy_total=#{settlementBuyTotal},coin_sell_total=#{coinSellTotal},"
            + "settlement_sell_total=#{settlementSellTotal},hold_coin=#{holdCoin},settlement_cost_total=#{settlementCostTotal},coin_fee=#{coinFee},"
            + "settlement_fee=#{settlementFee},last_trade_log_id=#{lastTradeLogId},update_time=NOW() where id=#{id}")
    int updateUserTradeStatCoin(UserTradeStat userTradeStat);

    /**
     * 查找用户交易信息
     *
     * @param userId
     * @param coinName
     * @param settlementCurrency
     * @param orderType          排序，0：用户id升序，1 持仓成本降序，2 持仓成本升序  3：coinName 升序，4：settlementCurrency升序
     * @param rowBounds
     * @return
     */
    @Select({"<script>",
            "select uts.*, if(hold_coin!=0,settlement_cost_total/hold_coin,0) as hold_coin_price from t_user_trade_stat uts where 1=1",
            "<if test='userId != null'> and user_id=#{userId} </if>",
            "<if test='coinName != null and coinName.length > 0'> and coin_name=#{coinName} </if>",
            "<if test='settlementCurrency != null and settlementCurrency.length > 0'> and settlement_currency=#{settlementCurrency} </if>",
            "<if test='orderType != null'>",
            "<if test='orderType==0'> order by user_id asc </if>",
            "<if test='orderType==1'> order by if(hold_coin!=0,settlement_cost_total/hold_coin,0) desc </if>",
            "<if test='orderType==2'> order by if(hold_coin!=0,settlement_cost_total/hold_coin,0) asc</if>",
            "<if test='orderType==5'> order by coin_name asc </if>",
            "<if test='orderType==7'> order by settlement_currency asc </if>",
            "</if>",
            "</script>"})
    Page<UserTradeStat> searchUserTradeStat(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency, @Param("orderType") Integer orderType, RowBounds rowBounds);

    // 根据价格统计用户的交易盈亏信息
    // 排序 1 持仓成本降序，2 持仓成本升序 3 盈利总额降序 4 盈利总额升序
    @Select({"<script>",
            "select uts.*, if(hold_coin!=0,settlement_cost_total/hold_coin,0) as hold_coin_price,#{price}*hold_coin-settlement_cost_total as profit",
            " from t_user_trade_stat uts where 1=1",
            "<if test='userId != null'> and user_id=#{userId} </if>",
            "<if test='coinName != null and coinName.length > 0'> and coin_name=#{coinName} </if>",
            "<if test='settlementCurrency != null and settlementCurrency.length > 0'> and settlement_currency=#{settlementCurrency} </if>",
            "<if test='orderType != null'>",
            "<if test='orderType==1'> order by if(hold_coin!=0,settlement_cost_total/hold_coin,0) desc </if>",
            "<if test='orderType==2'> order by if(hold_coin!=0,settlement_cost_total/hold_coin,0) asc </if>",
            "<if test='orderType==3'> order by (#{price}*hold_coin-settlement_cost_total) desc </if>",
            "<if test='orderType==4'> order by (#{price}*hold_coin-settlement_cost_total) asc </if>",
            "</if>",
            "</script>"})
    Page<UserTradeStat> statUserTradeWithPrice(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency,
                                               @Param("userId") Integer userId, @Param("price") BigDecimal price,
                                               @Param("orderType") Integer orderType, @Param("column") String column, @Param("sort") String sort, RowBounds rowBounds);
}
