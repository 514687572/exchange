package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.SendCoin;
import com.cmd.exchange.common.model.TimeMonitoring;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TimeMonitoringMapper {

    int add(TimeMonitoring timeMonitoring);

    int updateTimeMonitoring(TimeMonitoring timeMonitoring);

    int delTimeMonitoring(@Param("id") Integer id);

    TimeMonitoring getTimeMonitoringById(@Param("id") Integer id);

    TimeMonitoring getTimeMonitoringByType(@Param("monitoringType") String monitoringType);

    Page<TimeMonitoring> getTimeMonitoringPageList(@Param("monitoringName") String monitoringName, RowBounds rowBounds);

    List<TimeMonitoring> getTimeMonitoringList();
}
