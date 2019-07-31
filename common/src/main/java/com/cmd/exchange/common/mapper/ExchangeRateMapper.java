package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ExchangeRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ExchangeRateMapper {
    void add(ExchangeRate exchangeRate);

    int updateExchangeRate(ExchangeRate exchangeRate);

    ExchangeRate getExchangeRate(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    /**
     * 猜测两个币的兑换率
     * 是通过中间货币来计算的，不会通过倒数来计算
     *
     * @param coinName           要兑换的币种
     * @param settlementCurrency 结算币种
     * @return 猜测价格
     */
    BigDecimal guestExchangeRate(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    BigDecimal guestHourAgoExchangeRate(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    int deleteExchangeRate(@Param("id") int id);

    ExchangeRate getExchangeRateById(@Param("id") int id);

    /**
     * 获取某个币种相关的所有兑换率
     *
     * @param coinName 币种
     * @return 兑换率列表
     */
    List<ExchangeRate> getCoinExchangeRate(@Param("coinName") String coinName);
}
