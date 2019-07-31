package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.TimeMonitoringMapper;
import com.cmd.exchange.common.model.TimeMonitoring;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeMonitoringService {
    @Autowired
    private TimeMonitoringMapper timeMonitoringMapper;

    public int add(TimeMonitoring timeMonitoring) {
        return timeMonitoringMapper.add(timeMonitoring);
    }

    public int updateTimeMonitoring(TimeMonitoring timeMonitoring) {
        return timeMonitoringMapper.updateTimeMonitoring(timeMonitoring);
    }

    public int delTimeMonitoring(Integer id) {
        return timeMonitoringMapper.delTimeMonitoring(id);
    }

    public TimeMonitoring getTimeMonitoringById(Integer id) {
        return timeMonitoringMapper.getTimeMonitoringById(id);
    }

    public TimeMonitoring getTimeMonitoringByType(String monitoringType) {
        return timeMonitoringMapper.getTimeMonitoringByType(monitoringType);
    }

    public Page<TimeMonitoring> getTimeMonitoringPageList(String monitoringName, int pageNo, int pageSize) {
        return timeMonitoringMapper.getTimeMonitoringPageList(monitoringName, new RowBounds(pageNo, pageSize));
    }

    public List<TimeMonitoring> getTimeMonitoringList() {
        return timeMonitoringMapper.getTimeMonitoringList();
    }
}
