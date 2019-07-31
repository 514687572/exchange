package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.service.BitcoinService;
import com.cmd.exchange.service.EthService;
import com.cmd.exchange.service.UsdtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AdapterService {
    private static Logger logger = LoggerFactory.getLogger(AdapterService.class);
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


    @Transactional
    public String getAccountAddress(int userId, String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        try {
            switch (coin.getCategory()) {
                case CoinCategory.USDT:
                    return usdtService.getAccountAddress(userId, coinName);
                case CoinCategory.BTC:
                    return bitcoinService.getAccountAddress(userId, coinName);
                case CoinCategory.ETH:
                    return ethService.getAccountAddress(userId, coinName);
                case CoinCategory.TOKEN:
                    return ethService.getAccountAddress(userId, coinName);
                case CoinCategory.ETC:
                    return etcService.getAccountAddress(userId, coinName);
                case CoinCategory.REAL:
                    return "";
                default:
                    Assert.check(true, ErrorCode.ERR_NOT_SUPPORT_COIN);
            }
        } catch (Exception e) {
            logger.error("failed to get coin: {} address for user: {}", coinName, userId, e);
            Assert.check(true, ErrorCode.ERR_WALLET_ERROR);
        }

        return null;
    }

    public String sendToAddress(int userId, String coinName, String toAddress, double amount) {
        Coin coin = coinService.getCoinByName(coinName);
        switch (coin.getCategory()) {
            case CoinCategory.USDT:
                return usdtService.sendToAddress(userId, coinName, toAddress, amount);
            case CoinCategory.BTC:
                return bitcoinService.sendToAddress(userId, coinName, toAddress, amount);
            case CoinCategory.ETH:
                return ethService.sendToAddress(userId, coinName, toAddress, amount);
            case CoinCategory.TOKEN:
                return ethService.sendToAddress(userId, coinName, toAddress, amount);
            case CoinCategory.ETC:
                return etcService.sendToAddress(userId, coinName, toAddress, amount);
            default:
                Assert.check(true, ErrorCode.ERR_NOT_SUPPORT_COIN);
        }
        return null;
    }

    public int getTxConfirmCount(String coinName, String txid) {
        Coin coin = coinService.getCoinByName(coinName);
        switch (coin.getCategory()) {
            case CoinCategory.USDT:
                return usdtService.getTxConfirmCount(coinName, txid);
            case CoinCategory.BTC:
                return bitcoinService.getTxConfirmCount(coinName, txid);
            case CoinCategory.ETH:
                return ethService.getTxConfirmCount(coinName, txid);
            case CoinCategory.TOKEN:
                return ethService.getTxConfirmCount(coinName, txid);
            case CoinCategory.ETC:
                return etcService.getTxConfirmCount(coinName, txid);
            default:
                Assert.check(true, ErrorCode.ERR_NOT_SUPPORT_COIN);
        }
        return 0;
    }

    public boolean isAddressValid(String coinName, String address) {
        Coin coin = coinService.getCoinByName(coinName);
        switch (coin.getCategory()) {
            case CoinCategory.USDT:
                return usdtService.isAddressValid(coinName, address);
            case CoinCategory.BTC:
                return bitcoinService.isAddressValid(coinName, address);
            case CoinCategory.ETH:
                return ethService.isAddressValid(coinName, address);
            case CoinCategory.TOKEN:
                return ethService.isAddressValid(coinName, address);
            case CoinCategory.ETC:
                return etcService.isAddressValid(coinName, address);
            default:
                Assert.check(true, ErrorCode.ERR_NOT_SUPPORT_COIN);
        }
        return false;
    }
}
