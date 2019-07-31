package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CoinStatus;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.CoinConfigMapper;
import com.cmd.exchange.common.mapper.CoinGroupConfigMapper;
import com.cmd.exchange.common.mapper.CoinMapper;
import com.cmd.exchange.common.mapper.UserGroupConfigMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.CoinConfig;
import com.cmd.exchange.common.model.CoinGroupConfig;
import com.cmd.exchange.common.model.UserGroupConfig;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.CoinVO;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinConfigMapper coinConfigMapper;
    @Autowired
    private CoinGroupConfigMapper coinGroupConfigMapper;
    @Autowired
    private UserGroupConfigMapper userGroupConfigMapper;

    @Transactional
    public int add(CoinVO coinVO) {
        Coin tmp = coinMapper.getCoinByName(coinVO.getName());
        Assert.check(tmp != null, ErrorCode.ERR_RECORD_EXIST);

        CoinConfig coinConfig = new CoinConfig();
        coinConfig.setCoinName(coinVO.getName()).setTransferFeeRate(coinVO.getTransferFeeRate())
                .setTransferFeeSelect(coinVO.getTransferFeeSelect())
                .setTransferMinAmount(coinVO.getTransferMinAmount())
                .setTransferMaxAmount(coinVO.getTransferMaxAmount())
                .setTransferFeeStatic(coinVO.getTransferFeeStatic());
        coinConfigMapper.add(coinConfig);

        List<UserGroupConfig> userGroupConfigList = userGroupConfigMapper.getUserGroupConfigList();
        if (userGroupConfigList != null && userGroupConfigList.size() > 0) {
            List<CoinGroupConfig> coinGroupConfigList = new ArrayList<CoinGroupConfig>();
            for (UserGroupConfig data : userGroupConfigList) {
                CoinGroupConfig coinGroupConfig = new CoinGroupConfig();
                coinGroupConfig.setGroupId(data.getId());
                coinGroupConfig.setCoinName(coinVO.getName());
                coinGroupConfig.setStatus(0);
                int i = coinGroupConfigMapper.CountCoinGroupConfigByCoinNameAndGroupId(coinGroupConfig);
                if (i < 1) {
                    coinGroupConfigMapper.addCoinGroupConfig(coinGroupConfig);
                }

            }

        }


        //添加完币种后需要对币种分组配置表进行数据增加

        Coin coin = new Coin();
        BeanUtils.copyProperties(coinVO, coin);
        return coinMapper.add(coin);
    }

    @Transactional
    public int updateCoin(CoinVO coinVO) {
        Coin tmp = coinMapper.getCoinByName(coinVO.getName());
        Assert.check(tmp == null, ErrorCode.ERR_RECORD_NOT_EXIST, "");

        CoinConfig coinConfig = new CoinConfig();
        coinConfig.setCoinName(coinVO.getName()).setTransferFeeRate(coinVO.getTransferFeeRate())
                .setTransferMinAmount(coinVO.getTransferMinAmount())
                .setTransferMaxAmount(coinVO.getTransferMaxAmount())
                .setTransferFeeSelect(coinVO.getTransferFeeSelect())
                .setTransferFeeStatic(coinVO.getTransferFeeStatic());
        coinConfigMapper.updateCoinConfig(coinConfig);

        //这进行修改分组配置表的信息
        List<CoinGroupConfig> coinGroupConfigList = coinGroupConfigMapper.getCoinGroupConfigListByCoinName(coinVO.getName());
        if (coinGroupConfigList != null && coinGroupConfigList.size() > 0) {
            for (CoinGroupConfig coinGroupConfig : coinGroupConfigList) {
                coinGroupConfig.setStatus(CoinStatus.NORMAL.getValue());
                coinGroupConfigMapper.updateCoinGroupConfig(coinGroupConfig);
            }
        }


        Coin coin = new Coin();
        BeanUtils.copyProperties(coinVO, coin);
        coin.setId(tmp.getId()).setName(tmp.getName());
        return coinMapper.updateCoin(coin);
    }

    public void updateCoinStatus(String name, int status) {
        Coin coin = coinMapper.getCoinByName(name);
        Assert.check(coin == null, ErrorCode.ERR_RECORD_NOT_EXIST, "");

        if (status == CoinStatus.NORMAL.getValue()) {
            coinMapper.updateCoin(new Coin().setId(coin.getId()).setStatus(CoinStatus.NORMAL.getValue()));
            //修改币种分组配置的状态值
            List<CoinGroupConfig> coinGroupConfigList = coinGroupConfigMapper.getCoinGroupConfigListByCoinName(name);
            if (coinGroupConfigList != null && coinGroupConfigList.size() > 0) {
                for (CoinGroupConfig coinGroupConfig : coinGroupConfigList) {
                    coinGroupConfig.setStatus(CoinStatus.NORMAL.getValue());
                    coinGroupConfigMapper.updateCoinGroupConfig(coinGroupConfig);
                }
            }

        } else if (status == CoinStatus.DISABLE.getValue()) {
            coinMapper.updateCoin(new Coin().setId(coin.getId()).setStatus(CoinStatus.DISABLE.getValue()));
            //修改币种分组配置的状态值
            List<CoinGroupConfig> coinGroupConfigList = coinGroupConfigMapper.getCoinGroupConfigListByCoinName(name);
            if (coinGroupConfigList != null && coinGroupConfigList.size() > 0) {
                for (CoinGroupConfig coinGroupConfig : coinGroupConfigList) {
                    coinGroupConfig.setStatus(CoinStatus.DISABLE.getValue());
                    coinGroupConfigMapper.updateCoinGroupConfig(coinGroupConfig);
                }
            }
        } else {
            Assert.check(true, ErrorCode.ERR_PARAM_ERROR, "");
        }
    }

    public void deleteCoin(String name) {
        Coin coin = coinMapper.getCoinByName(name);


        Assert.check(coin == null, ErrorCode.ERR_RECORD_NOT_EXIST, "");

        coinMapper.deleteCoin(name);
        coinGroupConfigMapper.deleteCoinGroupConfigByCoinName(name);

    }

    public Coin getCoinByName(String coinName) {
        return coinMapper.getCoinByName(coinName);
    }

    public List<Coin> getCoin() {
        return coinMapper.getCoin();
    }

    public CoinVO getCoinInfo(String coinName) {
        Coin coin = coinMapper.getCoinByName(coinName);
        if (coin == null) {
            return null;
        }

        CoinVO vo = new CoinVO();
        BeanUtils.copyProperties(coin, vo);


        CoinConfig coinConfig = coinConfigMapper.getCoinConfigByName(coinName);
        if (coinConfig != null) {
            BeanUtils.copyProperties(coinConfig, vo);
        }

        return vo;
    }

    /**
     * 获取某个列表的所有币
     *
     * @param category 类别
     * @return 币种列表
     */
    public List<Coin> getCoinsByCategory(String category) {
        return coinMapper.getCoinsByCategory(category);
    }

    /**
     * 获取所有以太坊和以太坊代币
     *
     * @return
     */
    public List<Coin> getAllEthCoins() {
        return coinMapper.getAllEthCoins();
    }

}
