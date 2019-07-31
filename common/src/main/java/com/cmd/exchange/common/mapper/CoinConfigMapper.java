package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoinConfigMapper {
    //和币种相关配置参数，不能放在t_config中
    @Select("select * from t_coin_config where coin_name=#{coinName}")
    CoinConfig getCoinConfigByName(@Param("coinName") String coinName);

    int add(CoinConfig coinConfig);

    int updateCoinConfig(CoinConfig coinConfig);

    List<CoinConfig> getCoinConfigList();
}
