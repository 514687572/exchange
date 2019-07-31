package com.cmd.exchange.admin.componet;


import com.cmd.exchange.common.model.CoinDifferenceConfig;
import com.cmd.exchange.common.model.CoinDifference;
import com.cmd.exchange.service.CoinDifferenceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 数据差异监控key值
 */
@Component
public class MonitoringDataComponet {

    @Autowired
    private CoinDifferenceConfigService coinDifferenceConfigService;
    //key：coinName,value:具体详情
    public static Map<String, CoinDifference> coinDifferenceVOMap = new HashMap<>();

    public static Map<String, CoinDifferenceConfig> coinDifferenceConfigMap = new HashMap<>();

    public void initCoinName() {
        /**
         * 获取需要监控的币种信息
         */
        List<CoinDifferenceConfig> list = coinDifferenceConfigService.getCoinDifferenceConfigAll();
        if (list != null && list.size() > 0) {
            Map<String, CoinDifferenceConfig> configMap = new HashMap<>();
            for (CoinDifferenceConfig data : list) {
                configMap.put(data.getCoinName(), data);
            }
            coinDifferenceConfigMap = configMap;
        }
    }

    /**
     * 定时服务器去检测
     */
    public void refreshCoinData() {
        Date date = new Date();
        if (coinDifferenceConfigMap != null && coinDifferenceConfigMap.size() > 0) {

            Map<String, CoinDifference> differenceMap = new HashMap<>();
            for (String coinName : coinDifferenceConfigMap.keySet()) {
                CoinDifferenceConfig coinDifferenceConfig = coinDifferenceConfigMap.get(coinName);
                CoinDifference coinDifferenceVO = new CoinDifference();

                coinDifferenceVO.setCoinName(coinName);
                //历史该币种总数
                BigDecimal rollInTotal = new BigDecimal(0);

                //今日新增币种的数量
                BigDecimal todyAddRollInTotal = new BigDecimal(0);
                //用户该币种总余额
                BigDecimal userBalanceTotal = new BigDecimal(0);
                BigDecimal userValiableBalance = new BigDecimal(0);
                // BigDecimal user


                //是否需要报警
                Boolean bool = false;
                BigDecimal coinDifferNum = userBalanceTotal.subtract(rollInTotal).setScale(10);

                if (coinDifferNum.compareTo(new BigDecimal(coinDifferenceConfig.getMoreDifference()).setScale(10)) >= 1 ||
                        coinDifferNum.compareTo(new BigDecimal(coinDifferenceConfig.getLessDifference()).setScale(10)) == -1) {
                    bool = true;
                }

                //历史积分总数
                BigDecimal pointTotal = new BigDecimal(0);

                //今日新增的积分数量
                BigDecimal todayAddPointTotal = new BigDecimal(0);

                //服务器刷新监控时间点
                coinDifferenceVO.setRefreshTime(date);
                coinDifferenceVO.setWarning(bool);
                differenceMap.put(coinName, coinDifferenceVO);
            }

        }
    }

    /**
     * 根据币种名获取监控的数据
     */
    public List<CoinDifference> getCoinDifferenceVOByCoinName() {
        List<CoinDifference> list = new ArrayList<CoinDifference>();
        if (coinDifferenceVOMap != null && coinDifferenceVOMap.size() > 0) {
            for (String coinName : coinDifferenceVOMap.keySet()) {
                list.add(coinDifferenceVOMap.get(coinName));
            }
        }
        return list;
    }

}
