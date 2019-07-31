package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.CoinDifferenceConfigMapper;
import com.cmd.exchange.common.model.CoinDifferenceConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinDifferenceConfigService {

    @Autowired
    private CoinDifferenceConfigMapper coinDifferenceConfigMapper;

    public int addCoinDifferenceConfig(CoinDifferenceConfig coinDifferenceConfig) {
        return coinDifferenceConfigMapper.addCoinDifferenceConfig(coinDifferenceConfig);
    }

    public void delCoinDifferenceConfigById(Integer id) {
        coinDifferenceConfigMapper.delCoinDifferenceConfigById(id);
    }

    public int updateCoinDifferenceConfig(CoinDifferenceConfig coinDifferenceConfig) {
        return coinDifferenceConfigMapper.updateCoinDifferenceConfig(coinDifferenceConfig);
    }

    public CoinDifferenceConfig getCoinDifferenceConfigById(Integer id) {
        return coinDifferenceConfigMapper.getCoinDifferenceConfigById(id);
    }

    public List<CoinDifferenceConfig> getCoinDifferenceConfigAll() {
        return coinDifferenceConfigMapper.getCoinDifferenceConfigAll();
    }

    public Page<CoinDifferenceConfig> getCoinDifferenceConfigPage(String coinName, int pageNo, int pageSize) {
        return coinDifferenceConfigMapper.getCoinDifferenceConfigPage(coinName, new RowBounds(pageNo, pageSize));
    }

    public CoinDifferenceConfig getCoinDifferenceConfigByName(String coinName) {
        return coinDifferenceConfigMapper.getCoinDifferenceConfigByName(coinName);
    }
}
