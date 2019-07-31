package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Market;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface MarketMapper {
    void add(Market market);

    Page<Market> getMarketList(@Param("name") String name, RowBounds rowBounds);

    Market getMarket(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    List<Market> getMarketBySettlement(@Param("settlementCurrency") String settlementCurrency);

    int deleteMarket(@Param("marketId") Integer marketId);

    List<Market> getAllMarkets();

    @Select("select DISTINCT  `settlement_currency` as settlementCurrency from t_market")
    List<String> getAllDistinctSettlementCurrency();

    @Update("update t_market set last_day_price=#{price} where id=#{id}")
    void updateLastDayPrice(@Param("id") Integer id, @Param("price") BigDecimal price);

    Market getMarketById(Integer marketId);

    void updateMarket(Market market);
}
