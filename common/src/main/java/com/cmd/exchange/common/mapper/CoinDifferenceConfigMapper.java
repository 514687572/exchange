package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinDifferenceConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CoinDifferenceConfigMapper {

    int addCoinDifferenceConfig(CoinDifferenceConfig coinDifferenceConfig);

    void delCoinDifferenceConfigById(@Param("id") Integer id);

    int updateCoinDifferenceConfig(CoinDifferenceConfig coinDifferenceConfig);

    CoinDifferenceConfig getCoinDifferenceConfigById(@Param("id") Integer id);

    List<CoinDifferenceConfig> getCoinDifferenceConfigAll();

    Page<CoinDifferenceConfig> getCoinDifferenceConfigPage(@Param("coinName") String coinName, RowBounds rowBounds);

    CoinDifferenceConfig getCoinDifferenceConfigByName(@Param("coinName") String coinName);
}
