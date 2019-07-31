package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinGroupConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoinGroupConfigMapper {

    //获取某个分组币种对应的配置信息，groupId为null 表示查询所有
    List<CoinGroupConfig> getCionGroupConfigList(@Param("groupId") Integer groupId);

    //增加币种分组配置
    int addCoinGroupConfig(CoinGroupConfig coinGroupConfig);

    //修改币种分组配置
    int updateCoinGroupConfig(CoinGroupConfig coinGroupConfig);

    int CountCoinGroupConfigByCoinNameAndGroupId(CoinGroupConfig coinGroupConfig);

    List<CoinGroupConfig> getCoinGroupConfigListByCoinName(@Param("coinName") String coinName);

    int deleteCoinGroupConfigByCoinName(@Param("coinName") String coinName);

    CoinGroupConfig getCoinGroupConfigById(@Param("id") Integer id);

}
