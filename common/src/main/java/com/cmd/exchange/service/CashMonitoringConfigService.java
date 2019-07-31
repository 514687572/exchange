package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.CashMonitoringConfigMapper;
import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashMonitoringConfigService {

    @Autowired
    private CashMonitoringConfigMapper cashMonitoringConfigMapper;

    public int addCashMonitoringConfig(CashMonitoringConfig cashMonitoringConfig) {
        return cashMonitoringConfigMapper.addCashMonitoringConfig(cashMonitoringConfig);
    }


    public int updateCashMonitoringConfig(CashMonitoringConfig cashMonitoringConfig) {
        return cashMonitoringConfigMapper.updateCashMonitoringConfig(cashMonitoringConfig);
    }

    public int delCashMonitoringConfigById(Integer id) {
        return cashMonitoringConfigMapper.delCashMonitoringConfigById(id);
    }

    public CashMonitoringConfig getCashMonitoringConfigById(Integer id) {
        return cashMonitoringConfigMapper.getCashMonitoringConfigById(id);
    }

    public CashMonitoringConfig getCashMonitoringConfigByName(String coinName) {
        return cashMonitoringConfigMapper.getCashMonitoringConfigByName(coinName);
    }

    public Page<CashMonitoringConfig> getCashMonitoringConfigPage(int pageNo, int pageSize) {
        return cashMonitoringConfigMapper.getCashMonitoringConfigPage(new PageRowBounds(pageNo, pageSize));
    }

    public List<CashMonitoringConfig> getCashMonitoringConfigList() {
        return cashMonitoringConfigMapper.getCashMonitoringConfigList();
    }


}
