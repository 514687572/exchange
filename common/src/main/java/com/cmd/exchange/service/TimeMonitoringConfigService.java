package com.cmd.exchange.service;

import com.cmd.exchange.common.enums.SendCoinStatus;
import com.cmd.exchange.common.mapper.TimeMonitoringConfigMapper;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeMonitoringConfigService {

    @Autowired
    private TimeMonitoringConfigMapper timeMonitoringConfigMapper;


    public int addTimeMonitoringConfig(TimeMonitoringConfig timeMonitoringConfig) {
        return timeMonitoringConfigMapper.add(timeMonitoringConfig);
    }

    public int updateTimeMonitoringConfigById(TimeMonitoringConfig timeMonitoringConfig) {
        return timeMonitoringConfigMapper.updateTimeMonitoringConfigById(timeMonitoringConfig);
    }

    public int delTimeMonitoringConfigById(Integer id) {
        return timeMonitoringConfigMapper.delTimeMonitoringConfigById(id);
    }

    public TimeMonitoringConfig getTimeMonitoringConfigById(Integer id) {
        return timeMonitoringConfigMapper.getTimeMonitoringConfigById(id);
    }


    public Page<TimeMonitoringConfig> getTimeMonitoringConfigList(String monitoringType, int pageNo, int pageSize) {
        return timeMonitoringConfigMapper.getTimeMonitoringConfigList(monitoringType, new RowBounds(pageNo, pageSize));
    }

    /*public TimeMonitoringConfig getTimeMonitoringConfigByType(String monitoringType){
       return timeMonitoringConfigMapper.getTimeMonitoringConfigByType(monitoringType);
    }*/
    public TimeMonitoringConfig getTimeMonitoringConfigByCoinNameAndSellName(String coinName, String settlementCurrency) {
        return timeMonitoringConfigMapper.getTimeMonitoringConfigByCoinNameAndSellName(coinName, settlementCurrency);
    }


    /**
     * * 获取所有交易对列表信息
     */
    public List<TimeMonitoringConfig> getTimeMonitoringConfigListAll() {

        return timeMonitoringConfigMapper.getTimeMonitoringConfigListAll();
    }


}
