package com.cmd.exchange.service;


import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.OtcMarketMapper;
import com.cmd.exchange.common.model.OtcMarket;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.OtcMarketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtcMarketService {

    @Autowired
    OtcMarketMapper otcMarketMapper;

    public void addOtcMarket(OtcMarketVO otcMarketVO) {
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(otcMarketVO.getCoinName());
        Assert.check(otcMarket != null, ErrorCode.ERR_RECORD_EXIST);

        otcMarketMapper.add(new OtcMarket().setCoinName(otcMarket.getCoinName())
                .setFeeRate(otcMarketVO.getFeeRate())
                .setExpiredTimeCancel(otcMarketVO.getExpiredTimeCancel())
                .setExpiredTimeFreeze(otcMarketVO.getExpiredTimeFreeze())
                .setMaxApplBuyCount(otcMarketVO.getMaxApplBuyCount())
                .setMaxApplSellCount(otcMarketVO.getMaxApplSellCount())
        );
    }

    public void delOtcMarket(int id) {
        int count = otcMarketMapper.del(id);
        Assert.check(count == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
    }

    public void updateOtcMarket(OtcMarketVO otcMarketVO) {
        OtcMarket otcMarket = otcMarketMapper.getOtcMarketByCoinName(otcMarketVO.getCoinName());
        Assert.check(otcMarket == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        otcMarketMapper.mod(new OtcMarket().setId(otcMarket.getId())
                .setFeeRate(otcMarketVO.getFeeRate())
                .setExpiredTimeCancel(otcMarketVO.getExpiredTimeCancel())
                .setExpiredTimeFreeze(otcMarketVO.getExpiredTimeFreeze())
                .setMaxApplBuyCount(otcMarketVO.getMaxApplBuyCount())
                .setMaxApplSellCount(otcMarketVO.getMaxApplSellCount())
        );
    }

    public List<OtcMarket> otcMarketList() {
        return otcMarketMapper.getOtcMarket();
    }

    public OtcMarket otcMarketDetail(String coinName) {
        return otcMarketMapper.getOtcMarketByCoinName(coinName);
    }

}
