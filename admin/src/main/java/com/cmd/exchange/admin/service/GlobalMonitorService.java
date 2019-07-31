package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.vo.GlobalMonitor;
import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.cmd.exchange.common.vo.CashMonitoringVO;
import com.cmd.exchange.common.vo.ServiceMontitorVO;
import com.cmd.exchange.service.CashMonitoringConfigService;
import com.cmd.exchange.service.CashMonitoringService;
import com.cmd.exchange.service.MonitorCacheService;
import com.cmd.exchange.service.ServerConfigService;
import com.cmd.exchange.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class GlobalMonitorService {

    @Autowired
    private CashMonitoringConfigService cashMonitoringConfigService;

    @Autowired
    public HighFrequencyService highFrequencyService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private CashMonitoringService cashMonitoringService;

    @Autowired
    private OnlineCountService onlineCountService;

    @Autowired
    private ServerConfigService serverConfigService;

    @Autowired
    private MonitorCacheService monitorCacheService;

    public GlobalMonitor getGolbalMonitor() {
        GlobalMonitor globalMonitor = new GlobalMonitor();
        boolean tradeBool = true;
        Set<String> tradeList = null;
        try {
            tradeList = tradeService.getGolbalTradeMonitor();
            if (tradeList != null && tradeList.size() > 0) {
                tradeBool = false;
            }
        } catch (Exception e) {
            tradeBool = true;
        }
        //
        globalMonitor.setTradeList(tradeList);
        globalMonitor.setTradeBool(tradeBool);

        //持币监控
        boolean cashBool = true;
        List<CashMonitoringConfig> cashMonitoringConfigs = cashMonitoringConfigService.getCashMonitoringConfigList();
        if (cashMonitoringConfigs != null && cashMonitoringConfigs.size() > 0) {
            List<CashMonitoringVO> list = cashMonitoringService.getCashMonitorVoList(cashMonitoringConfigs);
            if (list != null && list.size() > 0) {
                cashBool = false;
            }
        }
        globalMonitor.setCashBool(cashBool);

        //持仓监控
        boolean positionsBool = true;
        globalMonitor.setPositionsBool(positionsBool);

        boolean hfTradeBool = true;
        //高频交易
        Set<String> hfList = null;
        try {
            hfList = highFrequencyService.getHfMinitorList();
            if (hfList != null && hfList.size() > 0) {
                hfTradeBool = false;
            }
        } catch (Exception e) {
            hfTradeBool = true;
        }
        globalMonitor.setHfTradeList(hfList);
        globalMonitor.setHfTradeBool(hfTradeBool);

        Integer onlieNumber = 0;
        try {
            onlieNumber = onlineCountService.getOnlineCount();
        } catch (Exception e) {
            log.error("searc online user number exception:{}", e);
            onlieNumber = 0;
        }
        globalMonitor.setOnlieNumber(onlieNumber);

        boolean serviceBool = true;

        List<ServiceMontitorVO> list = serverConfigService.getServiceMototorList();
        if (list != null && list.size() > 0) {
            for (ServiceMontitorVO vo : list) {
                if (vo.isServiceStatus()) {
                    continue;
                } else {
                    serviceBool = false;
                    break;
                }
            }
        }

        globalMonitor.setServiceBool(serviceBool);
        return globalMonitor;


    }

}
