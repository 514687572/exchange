package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.MarketGroupConfig;
import com.cmd.exchange.common.vo.MarketGroupConfigVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface MarketGroupConfigMapper {

    List<MarketGroupConfig> getMarketGroupConfigList(@Param("marketId") Integer marketId);


    int addMarketGroupConfig(MarketGroupConfig marketGroupConfig);

    int updateMarketGroupConfigById(MarketGroupConfig marketGroupConfig);

    Page<MarketGroupConfigVO> getMarketGroupConfigListByName(@Param("name") String name, RowBounds rowBounds);

    void delMarketGroupConfigById(@Param("id") Integer id);

    int countMarketGroupConfigByMarketNameAndGroupId(@Param("coinName") String coinName,
                                                     @Param("settlementCurrency") String settlementCurrency,
                                                     @Param("groupId") Integer groupId);

    MarketGroupConfigVO getMarketGroupConfigById(@Param("id") Integer id);

    List<MarketGroupConfig> getMarketGroupConfigListAll();

}
