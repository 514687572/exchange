package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.constants.MonitorTradeType;
import com.cmd.exchange.common.model.SendCoin;
import com.cmd.exchange.common.model.TimeMonitoring;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TimeMonitoringConfigMapper {


    int add(TimeMonitoringConfig timeMonitoringConfig);

    int updateTimeMonitoringConfigById(TimeMonitoringConfig timeMonitoringConfig);

    int delTimeMonitoringConfigById(@Param("id") Integer id);

    TimeMonitoringConfig getTimeMonitoringConfigById(@Param("id") Integer id);


    TimeMonitoringConfig getTimeMonitoringConfigByCoinNameAndSellName(@Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency);

    Page<TimeMonitoringConfig> getTimeMonitoringConfigList(@Param("monitoringType") String monitoringType, RowBounds rowBounds);

    List<TimeMonitoringConfig> getTimeMonitoringConfigListAll();
}
