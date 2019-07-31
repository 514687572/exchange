package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Otc;
import com.cmd.exchange.common.model.OtcMarket;
import com.cmd.exchange.common.vo.OtcMarketPriceVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OtcMarketMapper {

    int add(OtcMarket otcMarket);

    int del(int id);

    int mod(OtcMarket otcMarket);

    List<OtcMarket> getOtcMarket();

    OtcMarket getOtcMarketByCoinName(@Param("coinName") String coinName);
}
