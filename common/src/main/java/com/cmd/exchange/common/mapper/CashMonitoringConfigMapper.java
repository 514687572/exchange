package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CashMonitoringConfigMapper {

    int addCashMonitoringConfig(CashMonitoringConfig cashMonitoringConfig);

    int updateCashMonitoringConfig(CashMonitoringConfig cashMonitoringConfig);

    int delCashMonitoringConfigById(@Param("id") Integer id);

    int updateRefreshTimeById(@Param("lastRefreshTime") Integer lastRefreshTime);

    CashMonitoringConfig getCashMonitoringConfigById(@Param("id") Integer id);

    CashMonitoringConfig getCashMonitoringConfigByName(@Param("coinName") String coinName);

    Page<CashMonitoringConfig> getCashMonitoringConfigPage(RowBounds rowBounds);

    List<CashMonitoringConfig> getCashMonitoringConfigList();
}
