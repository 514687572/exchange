package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinDifference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoinDifferenceMapper {

    int addCoinDifference(CoinDifference coinDifference);

    int updateCoinDifferenceByName(CoinDifference coinDifference);

    CoinDifference getCoinDifferenceByName(@Param("coinName") String coinName);

    List<CoinDifference> getCoinDifferenceList();


}
