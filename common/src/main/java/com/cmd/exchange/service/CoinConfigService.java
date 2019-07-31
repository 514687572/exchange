package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.CoinConfigMapper;
import com.cmd.exchange.common.model.CoinConfig;
import com.cmd.exchange.common.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinConfigService {

    @Autowired
    private CoinConfigMapper coinConfigMapper;

    public int addCoinConfig(CoinConfig coinConfig) {
        Assert.check(coinConfig == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(coinConfig.getCoinName() == null, ErrorCode.ERR_PARAM_ERROR);

        CoinConfig tmp = coinConfigMapper.getCoinConfigByName(coinConfig.getCoinName());
        Assert.check(tmp != null, ErrorCode.ERR_RECORD_EXIST);

        return coinConfigMapper.add(coinConfig);
    }

    public int updateCoinConfig(CoinConfig coinConfig) {
        Assert.check(coinConfig == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(coinConfig.getCoinName() == null, ErrorCode.ERR_PARAM_ERROR);

        CoinConfig tmp = coinConfigMapper.getCoinConfigByName(coinConfig.getCoinName());
        Assert.check(tmp == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        return coinConfigMapper.updateCoinConfig(coinConfig);
    }

    public List<CoinConfig> getCoinConfigList() {
        return coinConfigMapper.getCoinConfigList();
    }
}
