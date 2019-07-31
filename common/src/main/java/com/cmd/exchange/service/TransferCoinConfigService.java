package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.TransferCoinConfigMapper;
import com.cmd.exchange.common.model.TransferCoinConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferCoinConfigService {

    @Autowired
    private TransferCoinConfigMapper sendCoinConfigMapper;

    public int addTransferCoinConfig(TransferCoinConfig sendCoinConfig) {
        return sendCoinConfigMapper.addTransferCoinConfig(sendCoinConfig);
    }

    public void delTransferCoinConfig(Integer id) {
        sendCoinConfigMapper.delTransferCoinConfig(id);
    }

    public TransferCoinConfig getTransferCoinConfigById(Integer id) {
        return sendCoinConfigMapper.getTransferCoinConfigById(id);
    }

    public TransferCoinConfig getTransferCoinConfigByCoinName(String coinName) {
        return sendCoinConfigMapper.getTransferCoinConfigByCoinName(coinName);
    }

    public List<TransferCoinConfig> getTransferCoinConfigList() {
        return sendCoinConfigMapper.getTransferCoinConfigList();
    }

    public Page<TransferCoinConfig> getTransferCoinConfigPage(String coinName, int pageNo, int pageSize) {
        return sendCoinConfigMapper.getTransferCoinConfigPage(coinName, new RowBounds(pageNo, pageSize));
    }

    public int updateTransferCoinConfig(TransferCoinConfig sendCoinConfig) {
        return sendCoinConfigMapper.updateTransferCoinConfig(sendCoinConfig);
    }


}
