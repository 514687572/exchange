package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.MarketGroupConfigMapper;
import com.cmd.exchange.common.model.Market;
import com.cmd.exchange.common.model.MarketGroupConfig;
import com.cmd.exchange.common.vo.MarketGroupConfigVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketGroupConfigService {

    @Autowired
    private MarketGroupConfigMapper marketGroupConfigMapper;

    public List<MarketGroupConfig> getMarketGroupConfigListByMarketId(Integer marketId) {
        return marketGroupConfigMapper.getMarketGroupConfigList(marketId);
    }

    public void addPatchMarketGroupConfig(MarketGroupConfig marketGroupConfig) {

        marketGroupConfigMapper.addMarketGroupConfig(marketGroupConfig);


    }

    public int updateMarketGroupConfigById(MarketGroupConfig marketGroupConfig) {
        return marketGroupConfigMapper.updateMarketGroupConfigById(marketGroupConfig);
    }

    public Page<MarketGroupConfigVO> getMarketGroupConfigListByName(String name, Integer pageNo, Integer pageSize) {
        return marketGroupConfigMapper.getMarketGroupConfigListByName(name, new RowBounds(pageNo, pageSize));
    }

    public void delMarketGroupConfigById(Integer id) {
        marketGroupConfigMapper.delMarketGroupConfigById(id);
    }

    public int countMarketGroupConfigByMarketIdAndGroupId(String coinName, String settlementCurrency, Integer groupId) {
        return marketGroupConfigMapper.countMarketGroupConfigByMarketNameAndGroupId(coinName, settlementCurrency, groupId);
    }

    public MarketGroupConfigVO getMarketGroupConfigById(Integer marketGroupConfigId) {
        return marketGroupConfigMapper.getMarketGroupConfigById(marketGroupConfigId);
    }
}
