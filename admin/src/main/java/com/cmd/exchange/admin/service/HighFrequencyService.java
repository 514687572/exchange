package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.service.componet.NoWarningUserComponet;
import com.cmd.exchange.admin.vo.HfConfigVO;
import com.cmd.exchange.admin.vo.MarketHFVO;
import com.cmd.exchange.common.constants.MonitorTradeType;
import com.cmd.exchange.common.model.TimeMonitoringConfig;
import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.vo.HFTradeVO;
import com.cmd.exchange.service.TimeMonitoringConfigService;
import com.cmd.exchange.service.TradeNoWarningUserService;
import com.cmd.exchange.service.TradeService;
import com.cmd.exchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 高频交易处理模块
 */
@Service
@Slf4j
public class HighFrequencyService {


    @Autowired
    private TimeMonitoringConfigService timeMonitoringConfigService;

    @Autowired
    private TradeNoWarningUserService tradeNoWarningUserService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoWarningUserComponet noWarningUserComponet;

    //白名单用户id集合
    public static Map<String, Set<Integer>> userIdMap = new HashMap<String, Set<Integer>>();

    //监控的交易对象
    //key:type , key:交易对 value:具体买入卖出信息
    public static Map<String, Map<String, HfConfigVO>> hfMap = new HashMap<String, Map<String, HfConfigVO>>();

    //各种时间的配置:key:监控的类型，时间间隔
    public static Map<String, Long> timeConfigMap = new HashMap<String, Long>();

    //各种交易对在设置时间范围类进行的交易对象信息
    public static boolean isMinitorBool = false;

    //设置过的交易对信息
    public static Map<String, Set<String>> tradeOnMap = new HashMap<String, Set<String>>();

    //各种交易对的交易对象
    public static Map<String, List<HFTradeVO>> hfTradeListMap = new HashMap<String, List<HFTradeVO>>();

    public void initHighFrequency() {
        //初始化高平交易数据到缓存
        List<TimeMonitoringConfig> timeMonitoringConfigList = timeMonitoringConfigService.getTimeMonitoringConfigListAll();
        isMinitorBool = false;
        Map<String, Set<String>> tradeOnMaps = new HashMap<String, Set<String>>();
        Map<String, Long> timeConfigMaps = new HashMap<String, Long>();
        Map<String, Map<String, HfConfigVO>> hfMaps = new HashMap<String, Map<String, HfConfigVO>>();
        if (timeMonitoringConfigList != null && timeMonitoringConfigList.size() > 0) {
            isMinitorBool = true;
            for (TimeMonitoringConfig data : timeMonitoringConfigList) {
                HfConfigVO hfConfigVO = new HfConfigVO();
                hfConfigVO.setBuyCount(data.getBuyNumber());
                hfConfigVO.setSellCount(data.getSellNumber());
                String marketKey = data.getCoinName() + "/" + data.getSettlementCurrency();
                //将交易对存储在map对象里
                log.info("初始化交易对到缓存-----");
                if (tradeOnMaps.containsKey(data.getCoinName())) {
                    tradeOnMaps.get(data.getCoinName()).add(data.getSettlementCurrency());

                } else {
                    Set<String> settlemSet = new HashSet<>();
                    settlemSet.add(data.getSettlementCurrency());
                    tradeOnMaps.put(data.getCoinName(), settlemSet);
                }

                log.info("初始化交易对到缓存完成-----");

                log.info("初始化每一个交易对具体的高频时间到缓存-----");

                timeConfigMaps.put(marketKey, Long.valueOf(data.getNumMinutes() * 60));

                log.info("初始化每一个交易对具体的高频时间到缓存完成-----");

                log.info("初始化每一个交易对具体的详情缓存开始-----");

                if (hfMaps.containsKey(data.getMonitoringType())) {
                    hfMaps.get(data.getMonitoringType()).put(marketKey, hfConfigVO);
                } else {
                    Map<String, HfConfigVO> hfConfigVOMap = new HashMap<>();
                    hfConfigVOMap.put(marketKey, hfConfigVO);
                    hfMaps.put(data.getMonitoringType(), hfConfigVOMap);
                }
                log.info("初始化每一个交易对具体的详情缓存完成-----");
            }
            hfMap = hfMaps;
            timeConfigMap = timeConfigMaps;
            tradeOnMap = tradeOnMaps;
        } else {
            log.info("高频交易配置表未配置具体相关事件中详细信息");
        }
    }

    /**
     * 开启一个方法去进行订单信息查询，
     * 根据交易对进行对应的币种配对查询
     */
    public void searchTarde() {
        Map<String, List<HFTradeVO>> hfTradeListDataMap = new HashMap<String, List<HFTradeVO>>();
        Date currentTime = new Date();
        Long endTime = currentTime.getTime() / 1000 - 5;
        //Long endTime = 15397438910L;
        log.info("startTime{}", endTime);
        if (isMinitorBool) {
            Map<String, List<HFTradeVO>> hfTradeListMaps = new HashMap<String, List<HFTradeVO>>();
            for (String coinNameKey : tradeOnMap.keySet()) {
                for (String settleCurrency : tradeOnMap.get(coinNameKey)) {
                    String tradeOnKey = coinNameKey + "/" + settleCurrency;
                    Long configTime = timeConfigMap.get(coinNameKey + "/" + settleCurrency);

                    if (configTime == null || configTime == 0) {
                        continue;
                    }
                    log.info("交易对{}的时间配:{}", tradeOnKey, endTime - 5);
                    Long startTime = endTime - configTime - 5;
                    // Long startTime=1537438610L;
                    List<HFTradeVO> hfConfigVOList = tradeService.getUserTradeByTimeConfig(startTime, endTime, coinNameKey, settleCurrency);
                    //判断该交易对的交易列表是否在map对象的缓存中存在
                    if (hfConfigVOList != null && hfConfigVOList.size() > 0) {


                        hfTradeListMaps.put(tradeOnKey, hfConfigVOList);

                    }
                }
            }
            hfTradeListMap = hfTradeListMaps;
        } else {
            log.info("not config HZ monitor ,not need monitor");
        }
    }

    /**
     * 判断该交易对的信息是否在高平交易查询列表中的对象map中
     */
    public String getExistBool(String coinName, String settlementCurrency) {

        if (hfTradeListMap == null || hfTradeListMap.size() == 0) {
            return null;
        } else {
            String marketKey1 = coinName + "/" + settlementCurrency;
            return marketKey1;
        }
    }

    /**
     * 根据交易对获取该交易对的配置基本信息
     *
     * @param coinName
     * @param settlementCurrency
     * @return
     */
    public HfConfigVO getHFConfigByCoinNameAndSettle(String coinName, String settlementCurrency) {
        HfConfigVO hfConfigVO = null;
        if (hfMap.containsKey(MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE)) {
            Map<String, HfConfigVO> hfConfigVOMap = hfMap.get(MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE);
            String key = coinName + "/" + settlementCurrency;
            if (hfConfigVOMap.containsKey(key)) {
                hfConfigVO = hfConfigVOMap.get(key);
                return hfConfigVO;
            }
        } else {
            return null;
        }
        return hfConfigVO;
    }


    /**
     * 根据交易对获取到报警的列表
     *
     * @param coinName
     * @param settlementCurrency
     * @return
     */
    public List<MarketHFVO> getMarketHFVOList(String coinName, String settlementCurrency) {
        List<MarketHFVO> list = new ArrayList<MarketHFVO>();
        List<HFTradeVO> hfTradeVOList = new ArrayList<HFTradeVO>();
        //满足报警的列表

        if (hfTradeListMap != null && hfTradeListMap.size() > 0) {
            //获取白名单
            userIdMap = noWarningUserComponet.getUserIdMap();
            Set<Integer> setIds = null;
            if (userIdMap != null && userIdMap.size() > 0) {
                setIds = userIdMap.get(MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE);
            }
            String marketKey = coinName + "/" + settlementCurrency;
            if (hfTradeListMap.containsKey(marketKey)) {
                hfTradeVOList = hfTradeListMap.get(marketKey);
            }
            HfConfigVO hfConfigVO = getHFConfigByCoinNameAndSettle(coinName, settlementCurrency);
            if (hfConfigVO == null) {
                return null;
            }
            //用户此段时间买入和卖出的数据量数据构造
            Map<Integer, Map<Integer, Integer>> userTradeMap = new HashMap<>();
            Date refreashTime = null;
            if (hfTradeVOList != null && hfTradeVOList.size() > 0) {
                refreashTime = hfTradeVOList.get(0).getRefreashTime();
                for (HFTradeVO hfTradeVO : hfTradeVOList) {
                    if (setIds != null) {
                        if (setIds.contains(hfTradeVO.getUserId())) {
                            continue;
                        }
                    }
                    if (userTradeMap.containsKey(hfTradeVO.getUserId())) {
                        userTradeMap.get(hfTradeVO.getUserId()).put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                    } else {
                        Map<Integer, Integer> typeNumMap = new HashMap<>();
                        typeNumMap.put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                        userTradeMap.put(hfTradeVO.getUserId(), typeNumMap);
                    }
                }

            }
            Integer buyCount = hfConfigVO.getBuyCount();
            Integer sellCount = hfConfigVO.getSellCount();
            List<Integer> monitorUserIds = new ArrayList<>();
            //判断该玩家是否为高频交易:1表示买入 2表示卖出

            if (userTradeMap != null && userTradeMap.size() > 0) {
                for (Integer userId : userTradeMap.keySet()) {
                    boolean bool = false;
                    if (buyCount > 0 && userTradeMap.get(userId).get(1) != null) {
                        if (userTradeMap.get(userId).get(1) >= buyCount) {
                            bool = true;
                        }
                    }
                    if (!bool) {
                        if (sellCount > 0 && userTradeMap.get(userId).get(2) != null) {
                            if (userTradeMap.get(userId).get(2) >= sellCount) {
                                bool = true;
                            }
                        }
                    }
                    if (bool) {
                        MarketHFVO marketHFVO = new MarketHFVO();
                        marketHFVO.setBuyCount(userTradeMap.get(userId).get(1));
                        marketHFVO.setSellCount(userTradeMap.get(userId).get(2));
                        marketHFVO.setCoinName(coinName);
                        marketHFVO.setSettlementCurrency(settlementCurrency);
                        marketHFVO.setFreshDate(refreashTime);
                        User user = userService.getUserByUserId(userId);
                        marketHFVO.setEmail(user.getEmail());
                        marketHFVO.setMobile(user.getMobile());
                        int totalCount = 0;
                        if (userTradeMap.get(userId).get(1) != null) {
                            totalCount = userTradeMap.get(userId).get(1);
                        }
                        if (userTradeMap.get(userId).get(2) != null) {
                            totalCount = totalCount + userTradeMap.get(userId).get(2);
                        }
                        marketHFVO.setTradeAllCount(totalCount);
                        //marketHFVO.setTradeAllCount(userTradeMap.get(userId).get(1)+userTradeMap.get(userId).get(2));
                        list.add(marketHFVO);
                        monitorUserIds.add(userId);
                    }
                }
            }
            if (list != null && list.size() > 1) {

                Collections.sort(list, new Comparator<MarketHFVO>() {
                    public int compare(MarketHFVO o1, MarketHFVO o2) {
                        //按照CityModel的city_code字段进行降序排列
                        if (o1.getTradeAllCount() < o2.getTradeAllCount()) {
                            return 1;
                        }
                        if (o1.getTradeAllCount() == o2.getTradeAllCount()) {
                            return 0;
                        }
                        return -1;
                    }
                });
            }
            return list;
        }
        return null;
    }


    /**
     * 获取高频交易的所有列表
     */
    public List<MarketHFVO> getMarketHFVOListAll() {
        List<MarketHFVO> list = new ArrayList<MarketHFVO>();
        //满足报警的列表
        if (hfTradeListMap != null && hfTradeListMap.size() > 0) {
            //获取白名单
            userIdMap = noWarningUserComponet.getUserIdMap();
            Set<Integer> setIds = null;
            if (userIdMap != null && userIdMap.size() > 0) {
                setIds = userIdMap.get(MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE);
            }
            for (String tradeKey : hfTradeListMap.keySet()) {
                List<HFTradeVO> hfTradeVOList = new ArrayList<>();
                String keys[] = tradeKey.split("/");
                String coinName = keys[0];
                String settlementCurrency = keys[1];

                String marketKey = coinName + "/" + settlementCurrency;
                if (hfTradeListMap.containsKey(marketKey)) {
                    hfTradeVOList = hfTradeListMap.get(marketKey);
                }
                HfConfigVO hfConfigVO = getHFConfigByCoinNameAndSettle(coinName, settlementCurrency);
                if (hfConfigVO == null) {
                    continue;
                }

                //用户此段时间买入和卖出的数据量数据构造
                Map<Integer, Map<Integer, Integer>> userTradeMap = new HashMap<>();
                Date refreashTime = null;
                if (hfTradeVOList != null && hfTradeVOList.size() > 0) {
                    refreashTime = hfTradeVOList.get(0).getRefreashTime();
                    for (HFTradeVO hfTradeVO : hfTradeVOList) {
                        if (setIds != null) {
                            if (setIds.contains(hfTradeVO.getUserId())) {
                                continue;
                            }
                        }
                        if (userTradeMap.containsKey(hfTradeVO.getUserId())) {
                            userTradeMap.get(hfTradeVO.getUserId()).put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                        } else {
                            Map<Integer, Integer> typeNumMap = new HashMap<>();
                            typeNumMap.put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                            userTradeMap.put(hfTradeVO.getUserId(), typeNumMap);
                        }
                    }

                }
                Integer buyCount = hfConfigVO.getBuyCount();
                Integer sellCount = hfConfigVO.getSellCount();
                List<Integer> monitorUserIds = new ArrayList<>();
                //判断该玩家是否为高频交易:1表示买入 2表示卖出

                if (userTradeMap != null && userTradeMap.size() > 0) {
                    for (Integer userId : userTradeMap.keySet()) {
                        boolean bool = false;
                        if (buyCount > 0 && userTradeMap.get(userId).get(1) != null) {
                            if (userTradeMap.get(userId).get(1) >= buyCount) {
                                bool = true;
                            }
                        }
                        if (!bool) {
                            if (sellCount > 0 && userTradeMap.get(userId).get(2) != null) {
                                if (userTradeMap.get(userId).get(2) >= sellCount) {
                                    bool = true;
                                }
                            }
                        }
                        if (bool) {
                            MarketHFVO marketHFVO = new MarketHFVO();
                            marketHFVO.setBuyCount(userTradeMap.get(userId).get(1));
                            marketHFVO.setSellCount(userTradeMap.get(userId).get(2));
                            marketHFVO.setCoinName(coinName);
                            marketHFVO.setSettlementCurrency(settlementCurrency);
                            marketHFVO.setFreshDate(refreashTime);
                            User user = userService.getUserByUserId(userId);
                            marketHFVO.setEmail(user.getEmail());
                            marketHFVO.setMobile(user.getMobile());
                            int totalCount = 0;
                            if (userTradeMap.get(userId).get(1) != null) {
                                totalCount = userTradeMap.get(userId).get(1);
                            }
                            if (userTradeMap.get(userId).get(2) != null) {
                                totalCount = totalCount + userTradeMap.get(userId).get(2);
                            }
                            marketHFVO.setTradeAllCount(totalCount);
                            list.add(marketHFVO);

                        }
                    }
                }
            }
            if (list != null && list.size() > 1) {

                Collections.sort(list, new Comparator<MarketHFVO>() {
                    public int compare(MarketHFVO o1, MarketHFVO o2) {
                        //按照CityModel的city_code字段进行降序排列
                        if (o1.getTradeAllCount() < o2.getTradeAllCount()) {
                            return 1;
                        }
                        if (o1.getTradeAllCount() == o2.getTradeAllCount()) {
                            return 0;
                        }
                        return -1;
                    }
                });
            }
            return list;

        }
        return null;
    }


    /**
     * 全局监控
     */
    public Set<String> getHfMinitorList() {
        Set<String> hfList = new HashSet<>();

        if (hfTradeListMap != null && hfTradeListMap.size() > 0) {
            //获取白名单
            userIdMap = noWarningUserComponet.getUserIdMap();
            Set<Integer> setIds = null;
            if (userIdMap != null && userIdMap.size() > 0) {
                setIds = userIdMap.get(MonitorTradeType.HIGH_HZ_TIME_MONITORING_H_F_TRADE);
            }
            for (String tradeKey : hfTradeListMap.keySet()) {
                List<HFTradeVO> hfTradeVOList = new ArrayList<>();
                String keys[] = tradeKey.split("/");
                String coinName = keys[0];
                String settlementCurrency = keys[1];

                String marketKey = coinName + "/" + settlementCurrency;
                if (hfTradeListMap.containsKey(marketKey)) {
                    hfTradeVOList = hfTradeListMap.get(marketKey);
                }
                HfConfigVO hfConfigVO = getHFConfigByCoinNameAndSettle(coinName, settlementCurrency);
                if (hfConfigVO == null) {
                    continue;
                }
                Map<Integer, Map<Integer, Integer>> userTradeMap = new HashMap<>();

                if (hfTradeVOList != null && hfTradeVOList.size() > 0) {

                    for (HFTradeVO hfTradeVO : hfTradeVOList) {
                        if (setIds != null) {
                            if (setIds.contains(hfTradeVO.getUserId())) {
                                continue;
                            }
                        }
                        if (userTradeMap.containsKey(hfTradeVO.getUserId())) {
                            userTradeMap.get(hfTradeVO.getUserId()).put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                        } else {
                            Map<Integer, Integer> typeNumMap = new HashMap<>();
                            typeNumMap.put(hfTradeVO.getType(), hfTradeVO.getTradeNum());
                            userTradeMap.put(hfTradeVO.getUserId(), typeNumMap);
                        }
                    }
                }
                Integer buyCount = hfConfigVO.getBuyCount();
                Integer sellCount = hfConfigVO.getSellCount();
                //判断该玩家是否为高频交易:1表示买入 2表示卖出

                if (userTradeMap != null && userTradeMap.size() > 0) {
                    for (Integer userId : userTradeMap.keySet()) {
                        boolean bool = false;
                        if (buyCount > 0 && userTradeMap.get(userId).get(1) != null) {
                            if (userTradeMap.get(userId).get(1) >= buyCount) {
                                bool = true;
                            }
                        }
                        if (!bool) {
                            if (sellCount > 0 && userTradeMap.get(userId).get(2) != null) {
                                if (userTradeMap.get(userId).get(2) >= sellCount) {
                                    bool = true;
                                }
                            }
                        }
                        if (bool) {
                            hfList.add(tradeKey);
                        }
                    }
                }
            }
        }
        return hfList;
    }
}
