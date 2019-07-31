package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.CoinStatMapper;
import com.cmd.exchange.common.model.CoinStat;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinStatService {
    @Autowired
    private CoinStatMapper coinStatMapper;

    private int addCoinFeeStat(CoinStat coinStat) {
        return coinStatMapper.addCoinStat(coinStat);
    }

    private int updateCoinFeeStat(CoinStat coinStat) {
        return coinStatMapper.updateCoinStat(coinStat);
    }

    private List<CoinStat> getCoinStatList() {
        return coinStatMapper.getCoinStatList();
    }

    private CoinStat getCoinStatByName(String coinName) {
        return coinStatMapper.getCoinStatByName(coinName);
    }
}
