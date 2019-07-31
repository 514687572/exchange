package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.CoinDifferenceMapper;
import com.cmd.exchange.common.model.CoinDifference;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinDifferenceService {
    @Autowired
    CoinDifferenceMapper coinDifferenceMapper;

    public int addCoinDifference(CoinDifference coinDifference) {
        return coinDifferenceMapper.addCoinDifference(coinDifference);
    }

    public int updateCoinDifferenceByName(CoinDifference coinDifference) {
        return coinDifferenceMapper.updateCoinDifferenceByName(coinDifference);
    }

    public CoinDifference getCoinDifferenceByName(String coinName) {
        return coinDifferenceMapper.getCoinDifferenceByName(coinName);
    }

    public List<CoinDifference> getCoinDifferenceList() {
        return coinDifferenceMapper.getCoinDifferenceList();
    }

}
