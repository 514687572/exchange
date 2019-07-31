package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoinStatMapper {

    int addCoinStat(CoinStat coinStat);

    int updateCoinStat(CoinStat coinStat);

    List<CoinStat> getCoinStatList();

    CoinStat getCoinStatByName(@Param("coinName") String coinName);

}
