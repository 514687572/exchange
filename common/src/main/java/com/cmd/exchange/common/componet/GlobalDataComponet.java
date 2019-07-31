package com.cmd.exchange.common.componet;

import com.cmd.exchange.common.mapper.MarketGroupConfigMapper;
import com.cmd.exchange.common.mapper.UserGroupConfigMapper;
import com.cmd.exchange.common.model.MarketGroupConfig;
import com.cmd.exchange.common.model.UserGroupConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class GlobalDataComponet {

    /**
     * 全局数据配置
     **/

    @Autowired
    private MarketGroupConfigMapper marketGroupConfigMapper;

    @Autowired
    private UserGroupConfigMapper userGroupConfigMapper;


    /**
     * 交易手续费匹配的缓存数据,卖出的手费,key1：用户分组类型，key2：BTC_USDT
     **/
    private static Map<String, Map<String, BigDecimal>> userBuyConValeMap = new HashMap<>();

    private static Map<String, Map<String, BigDecimal>> userSellConValeMap = new HashMap<>();

    // private static List<MarketGroupConfig> marketGroupConfigList = new ArrayList<>();

    // private static List<UserGroupConfig> userGroupConfigList = new ArrayList<>();

    private static String no_type = "GROUP_TYPE_BASE";

    public void initConValueMap() {
        log.info("===输出初始化交易配对===");
        List<MarketGroupConfig> marketGroupConfigListTemp = marketGroupConfigMapper.getMarketGroupConfigListAll();

        List<UserGroupConfig> userGroupConfigListTemp = userGroupConfigMapper.getUserGroupConfigList();

        Map<String, Map<String, BigDecimal>> userBuyConValeTempMap = new HashMap<>();

        Map<String, Map<String, BigDecimal>> userSellConValeTempMap = new HashMap<>();


        if (marketGroupConfigListTemp != null && marketGroupConfigListTemp.size() > 0
                && userGroupConfigListTemp != null && userGroupConfigListTemp.size() > 0) {
            log.info("有数据");
            for (UserGroupConfig userGroupConfig : userGroupConfigListTemp) {
                if (userGroupConfig.getGroupType().equals(no_type)) {
                    continue;
                }
                Map<String, BigDecimal> buyValueMap = new HashMap<>();
                Map<String, BigDecimal> sellValueMap = new HashMap<>();

                for (MarketGroupConfig marketGroupConfig : marketGroupConfigListTemp) {
                    if (marketGroupConfig.getGroupId() == userGroupConfig.getId()) {
                        if (marketGroupConfig.getBuyConValue() != null && marketGroupConfig.getBuyConValue() != "") {

                            buyValueMap.put(marketGroupConfig.getCoinName() + "/" + marketGroupConfig.getSettlementCurrency(), new BigDecimal(marketGroupConfig.getBuyConValue()).divide(new BigDecimal(100)));
                        }
                        if (marketGroupConfig.getSellConValue() != null && marketGroupConfig.getSellConValue() != "") {
                            sellValueMap.put(marketGroupConfig.getCoinName() + "/" + marketGroupConfig.getSettlementCurrency(), new BigDecimal(marketGroupConfig.getSellConValue()).divide(new BigDecimal(100)));
                        }
                    }
                }
                log.info("---------" + userGroupConfig.getGroupType());
                userBuyConValeTempMap.put(userGroupConfig.getGroupType(), buyValueMap);
                userSellConValeTempMap.put(userGroupConfig.getGroupType(), sellValueMap);
            }
            userSellConValeMap = userSellConValeTempMap;
            userBuyConValeMap = userBuyConValeTempMap;

        }
        log.info("===输出初始化交易配对完成 finish===");
    }


    /**
     * @param groupType
     * @param marketTyName
     * @param type         1:表示买入 2.表示卖出
     * @return
     */
    public BigDecimal getCobValue(String groupType, String marketTyName, Integer type) {

        BigDecimal conValue = null;
        if (type == 1) {
            if (userBuyConValeMap != null && userBuyConValeMap.size() > 0) {
                log.info("userBuyConValeMap .size is  >0");
                if (userBuyConValeMap.get(groupType) != null && userBuyConValeMap.get(groupType).size() > 0) {
                    conValue = userBuyConValeMap.get(groupType).get(marketTyName);
                }
            }
        } else if (type == 2) {
            if (userSellConValeMap != null && userSellConValeMap.size() > 0) {
                if (userSellConValeMap != null && userSellConValeMap.size() > 0) {
                    log.info("userSellConValeMap .size is  >0");
                    if (userSellConValeMap.get(groupType) != null && userSellConValeMap.get(groupType).size() > 0) {
                        conValue = userSellConValeMap.get(groupType).get(marketTyName);
                    }
                }
            }
        }
        return conValue;
    }

    /**
     * 初始所有需要监控的币种
     */
    public void initMonitoringData() {
        //获取所需要监控的币种


    }


}
