package com.cmd.exchange.admin.service.componet;

import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.cmd.exchange.service.TimeMonitoringConfigService;
import com.cmd.exchange.service.TradeNoWarningUserService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class NoWarningUserComponet {

    @Autowired
    private TimeMonitoringConfigService timeMonitoringConfigService;

    @Autowired
    private TradeNoWarningUserService tradeNoWarningUserService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    //白名单用户id集合
    public static Map<String, Set<Integer>> userIdMap = new HashMap<String, Set<Integer>>();

    public void initNoWarningUser() {
        log.info("初始化白名单缓存数据---");
        Map<String, Set<Integer>> userIdMaps = new HashMap<String, Set<Integer>>();
        List<TradeNoWarningUser> tradeNoWarningUserList = tradeNoWarningUserService.getTradeNowarningAll();
        if (tradeNoWarningUserList != null && tradeNoWarningUserList.size() > 0) {
            for (TradeNoWarningUser dataUser : tradeNoWarningUserList) {
                if (userIdMaps.containsKey(dataUser.getNoWarningType())) {
                    userIdMaps.get(dataUser.getNoWarningType()).add(dataUser.getUserId());
                } else {
                    Set<Integer> idSets = new HashSet<Integer>();
                    idSets.add(dataUser.getUserId());
                    userIdMaps.put(dataUser.getNoWarningType(), idSets);
                }
            }
        }
        userIdMap = userIdMaps;
    }

    public static Map<String, Set<Integer>> getUserIdMap() {
        if (userIdMap != null && userIdMap.size() > 0) {
            return userIdMap;
        }
        return null;
    }
}
