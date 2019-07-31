package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MonitorDataService {

    @Autowired
    BitcoinService bitcoinService;
    @Autowired
    EthService ethService;
    @Autowired
    UsdtService usdtService;
    @Autowired
    CoinService coinService;
    @Autowired
    EtcService etcService;

    public BigDecimal getCoinBanlance(String coinName) throws Exception {
        Coin coin = coinService.getCoinByName(coinName);
        switch (coin.getCategory()) {
            case CoinCategory.USDT:
                return usdtService.getAllUserBalanceInBlockChain(coinName);
            case CoinCategory.BTC:
                return bitcoinService.getAllUserBalanceInBlockChain(coinName);
            case CoinCategory.ETH:
                return ethService.getAllUserBalanceInBlockChain(coinName);
            //case CoinCategory.TOKEN: return ethService.getTxConfirmCount(coinName, txid);
            case CoinCategory.ETC:
                return etcService.getAllUserBalanceInBlockChain(coinName);
            default:
                Assert.check(true, ErrorCode.ERR_NOT_SUPPORT_COIN);
        }
        return null;
    }
}
