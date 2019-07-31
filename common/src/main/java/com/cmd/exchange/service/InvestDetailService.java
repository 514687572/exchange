package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.TransactionPairs;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.vo.AirdropVO;
import com.cmd.exchange.common.vo.CandleVo;
import com.cmd.exchange.common.vo.InvestDetailVO;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class InvestDetailService {
    private static Logger log = LoggerFactory.getLogger(InvestDetailService.class);
    @Autowired
    private InvestDetailMapper investDetailMapper;
    @Autowired
    private InvestIncomeConfigMapper investIncomeConfigMapper;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private TradeStatService tradeStatService;
    @Autowired
    ConfigService configService;
    @Autowired
    private UserIntegrationMapper userIntegrationMapper;
    @Autowired
    private UserRecommendMapper userRecommendMapper;
    @Autowired
    private AirdropMapper airdropMapper;

    public List<InvestDetail> getInvestDetailList(Integer userId, Integer pageNo, Integer pageSize) {
        return investDetailMapper.getInvestDetailList(userId, new RowBounds(pageNo, pageSize));
    }

    public List<InvestDetailVO> getInvestDetailCurrentList() {
        return investDetailMapper.getInvestDetailCurrentList(new RowBounds(new Integer(1), new Integer(100)));
    }

    @Transactional
    public void countInvestStaticIncome() {
        try {
            List<InvestDetail> userList = investDetailMapper.countInvestIncomeList();
            if(userList != null && userList.size()>0){
                //查询最新的一条配置
                List<InvestIncomeConfigVO> configList = investIncomeConfigMapper.getInvestIncomeConfigList(
                        null, new RowBounds(new Integer(0), new Integer(1)));
                for (InvestDetail investUser : userList) {
                    Integer investUserId = investUser.getInvestUserId();
                    BigDecimal amount = investUser.getAmount();
                    if(configList!=null && configList.size()==1){
                        BigDecimal updAmount;
                        if(CoinCategory.USDT.equals(configList.get(0).getName().toLowerCase())){
                            updAmount = amount.multiply(configList.get(0).getStaticIncom());
                        }else{
                            updAmount = amount.multiply(configList.get(0).getStaticIncom()).divide(getLatestPrice(configList.get(0).getName().toLowerCase()), 8, RoundingMode.HALF_UP);
                        }
                        log.info("=========投资usdt静态收益"+configList.get(0).getName().toLowerCase()+"额updAmount==========="+updAmount);
                        if(updAmount!=null&&updAmount.compareTo(BigDecimal.ZERO)>0){
                            //更新可用资产
                            userCoinService.changeUserCoin(investUserId,CoinCategory.USDT,
                                    updAmount,BigDecimal.ZERO, UserBillReason.INVEST_ADD,"投资usdt静态收益"+configList.get(0).getName().toLowerCase());
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("=========投资静态收益返还出错===========");
        }
    }

    @Transactional
    public void countInvestDynamicIncome() {
        try {
                List<InvestDetail> investUserList = investDetailMapper.countInvestIncomeList();
                if(investUserList != null && investUserList.size()>0){
                    List<InvestIncomeConfigVO> configList = investIncomeConfigMapper.getInvestIncomeConfigList(
                            null, new RowBounds(new Integer(0), new Integer(1)));
                    if(configList==null || configList.size()==0){
                        return;
                    }
                    //算法1--start
                    //所有用户一天产生的动态分润--算法1要用到
                    BigDecimal totalCoinAmount = BigDecimal.ZERO;
                    for(InvestDetail investUser : investUserList){
                        if(configList!=null && configList.size()==1){
                            totalCoinAmount = investUser.getAmount().multiply(configList.get(0).getDynamicIncom()).add(totalCoinAmount);
                        }
                    }

                    List<BigDecimal> inlList = searchConfig("in");//上级：需要到达积分列表
                    List<BigDecimal> levelList = searchConfig("level");//上级：分润系数列表
                    //平台上每个人的积分*系数的总和
                    BigDecimal sigma = BigDecimal.ZERO;
                    //所有人id为key及可获得的分润value放在mapTotal
                    Map<Integer,BigDecimal> mapTotal = new HashMap<Integer,BigDecimal>();

                    //查每个人（即平台所有人）的积分，判断出每个人积分对应的系数；积分表没有的用户积分是0
                    List<UserIntegration> userInList= userIntegrationMapper.getBatchUserIntegration();
                    //每个人的积分*系数值放到mapInteLevel
                    Map<Integer,BigDecimal> mapInteLevel = new HashMap<Integer,BigDecimal>();
                    for(UserIntegration userIn : userInList){
                        BigDecimal integration = userIn.getUserIntegration()==null? BigDecimal.ZERO : userIn.getUserIntegration();
                        BigDecimal level;
                        if(integration.compareTo(inlList.get(0))>=0){//默认in1最大，in10最小
                            level = levelList.get(0);
                        }else if(integration.compareTo(inlList.get(1))>=0 && integration.compareTo(inlList.get(0))<0){
                            level = levelList.get(1);
                        }else if(integration.compareTo(inlList.get(2))>=0 && integration.compareTo(inlList.get(1))<0){
                            level = levelList.get(2);
                        }else if(integration.compareTo(inlList.get(3))>=0 && integration.compareTo(inlList.get(2))<0){
                            level = levelList.get(3);
                        }else if(integration.compareTo(inlList.get(4))>=0 && integration.compareTo(inlList.get(3))<0){
                            level = levelList.get(4);
                        }else if(integration.compareTo(inlList.get(5))>=0 && integration.compareTo(inlList.get(4))<0){
                            level = levelList.get(5);
                        }else if(integration.compareTo(inlList.get(6))>=0 && integration.compareTo(inlList.get(5))<0){
                            level = levelList.get(6);
                        }else if(integration.compareTo(inlList.get(7))>=0 && integration.compareTo(inlList.get(6))<0){
                            level = levelList.get(7);
                        }else if(integration.compareTo(inlList.get(8))>=0 && integration.compareTo(inlList.get(7))<0){
                            level = levelList.get(8);
                        }else if(integration.compareTo(inlList.get(9))>=0){
                            level = levelList.get(9);
                        }else{
                            level = BigDecimal.ZERO;
                        }
                        mapInteLevel.put(userIn.getUserId(),integration.multiply(level));
                        sigma = integration.multiply(level).add(sigma);
                    }
                    //每个人（即平台所有人）可获得的动态分润放到mapTotal
                    for(UserIntegration userIn : userInList){
                        BigDecimal inteLevel = mapInteLevel.get(userIn.getUserId());
                        if(inteLevel==null||inteLevel.compareTo(BigDecimal.ZERO)==0||sigma.compareTo(BigDecimal.ZERO)==0){
                            mapTotal.put(userIn.getUserId(),BigDecimal.ZERO);
                        }else{
                            log.info("用户ID=="+userIn.getUserId()+"===算法1获得的分润==="+inteLevel
                                    .divide(sigma,8,RoundingMode.HALF_UP).multiply(totalCoinAmount).multiply(new BigDecimal("0.25")));
                            mapTotal.put(userIn.getUserId(),inteLevel
                                    .divide(sigma,8,RoundingMode.HALF_UP).multiply(totalCoinAmount).multiply(new BigDecimal("0.25")));
                        }
                    }
                    /*for(Map.Entry<Integer,BigDecimal> entry: mapInteLevel.entrySet()) {
                        if(sigma.compareTo(BigDecimal.ZERO)==1){
                            mapTotal.put(entry.getKey(),entry.getValue()
                                    .divide(sigma,8,RoundingMode.HALF_UP).multiply(totalCoinAmount).multiply(new BigDecimal("0.25")));
                        }
                    }*/
                    //算法1--end


                    //把平台所有人的积分userInList转成userInMap(userId为key,积分为值)
                    Map<Integer, BigDecimal> userInMap = new HashMap<Integer, BigDecimal>();
                    for (UserIntegration userIn : userInList) {
                        userInMap.put(userIn.getUserId(),userIn.getUserIntegration()==null? BigDecimal.ZERO : userIn.getUserIntegration());
                    }

                    //每个投资人需要执行算法234
                    for (InvestDetail investUser : investUserList) {
                        Integer investUserId = investUser.getInvestUserId();
                        BigDecimal amount = investUser.getAmount();

                        //算法2
                        //动态收益额的1/4
                        BigDecimal coinAmount = amount.multiply(configList.get(0).getDynamicIncom()).multiply(new BigDecimal("0.25"));
                        //投资人investUserId的上1-10级
                        UserRecommend ur = userRecommendMapper.selectByPrimaryKey(investUserId);
                        List<Integer> pidList = urObjChangeToList(ur);
                        List<BigDecimal> refLevelList = searchConfig("refLevel");//分润系数列表
                        //十个人id为key及可获得的分润value更新到mapTotal
                        if(ur!=null){
                            for(int i=0;i<pidList.size();i++){
                                if(pidList.get(i)==null||pidList.get(i)==0){//没有上级
                                    break;
                                }
                                log.info("用户ID=="+pidList.get(i)+"===算法2获得的分润==="+coinAmount.multiply(refLevelList.get(i)));
                                mapTotal.put(pidList.get(i),mapTotal.get(pidList.get(i)).add(coinAmount.multiply(refLevelList.get(i))));
                            }
                        }

                        //算法3
                        List<BigDecimal> childList = searchConfig("child");//要求积分列表
                        //上1-10级和下1-10级人id为key及可获得的分润value更新到mapTotal
                        //上下1-10级可参与分润人的积分总和(需求的第二个sigma)
                        BigDecimal totalIn = BigDecimal.ZERO;
                        //上下1-10级可参与分润人ID
                        List<Integer> userIdList = new ArrayList<Integer>();
                        //下1级人id为key及可获得的分润value更新到mapTotal
                        //下1级可参与分润人的积分总和(需求的第三个sigma)
                        BigDecimal totalInOne = BigDecimal.ZERO;
                        List<Integer> userIdListOne = new ArrayList<Integer>();//下1级可参与分润人ID
                        //找出上1-10级中的可参与分润人，可参与分润人积分累加
                        if(ur!=null){
                            for(int i=0;i<pidList.size();i++){
                                if(pidList.get(i)==null||pidList.get(i)==0){//没有上级
                                    break;
                                }
                                if(userInMap.get(pidList.get(i)).compareTo(childList.get(i))>=0){//拥有的积分>=要求的积分,才可参与分润
                                    userIdList.add(pidList.get(i));
                                    totalIn = totalIn.add(userInMap.get(pidList.get(i)));
                                }
                            }
                        }
                        //找出investUserId的下1-10级中的可参与分润人，可参与分润人积分累加
                        //下1-10级人的推荐关系
                        List<UserRecommend> urList = userRecommendMapper.getBatchUserRecommend(investUserId);
                        if(urList!=null&&urList.size()>0){
                            for (UserRecommend userRe : urList){
                                List<Integer> pidListM = urObjChangeToList(userRe);
                                for(int i=0;i<pidListM.size();i++){
                                    if(pidListM.get(i)==null||pidListM.get(i)==0){//没有上级
                                        break;
                                    }
                                    if(investUserId.equals(pidListM.get(i))){//下i+1级(共1-10级)
                                        if(userInMap.get(userRe.getUserId()).compareTo(childList.get(i))>=0){//拥有的积分>=要求的积分,才可参与分润
                                            userIdList.add(userRe.getUserId());
                                            totalIn = totalIn.add(userInMap.get(userRe.getUserId()));
                                        }
                                        //算法4
                                        if(i==0&&userInMap.get(userRe.getUserId()).compareTo(BigDecimal.ZERO)>0){
                                            userIdListOne.add(userRe.getUserId());
                                            totalInOne = totalInOne.add(userInMap.get(userRe.getUserId()));
                                        }
                                    }
                                }
                            }
                        }
                        for(Integer userId : userIdList){
                            //算法3的上下1-10级每个人可获得的分润
                            log.info("用户ID=="+userId+"===算法3获得的分润==="+userInMap.get(userId).divide(totalIn,8,RoundingMode.HALF_UP).multiply(coinAmount));
                            mapTotal.put(userId,mapTotal.get(userId).add(userInMap.get(userId).divide(totalIn,8,RoundingMode.HALF_UP).multiply(coinAmount)));
                        }
                        for(Integer userId : userIdListOne){
                            //算法4的下1级每个人可获得的分润
                            log.info("用户ID=="+userId+"===算法4获得的分润==="+userInMap.get(userId).divide(totalInOne,8,RoundingMode.HALF_UP).multiply(coinAmount));
                            mapTotal.put(userId,mapTotal.get(userId).add(userInMap.get(userId).divide(totalInOne,8,RoundingMode.HALF_UP).multiply(coinAmount)));
                        }
                    }

                    //查询昨天的开启状态的空投任务列表（含有积分下限、积分上限、每人空投数量、币种）
                    List<AirdropVO> airdropList = airdropMapper.selectYesterdayAirdropList();
                    //把空投任务列表airdropList转成userAirdropMap(airdropId为key,实际空投到的人数为值)
                    Map<Integer, Integer> userAirdropMap = new HashMap<Integer, Integer>();
                    for (AirdropVO airdropVO : airdropList) {
                        userAirdropMap.put(airdropVO.getId(),airdropVO.getUserNum()==null? 0 : airdropVO.getUserNum());
                    }

                    //更新所有人的USDT可用资产
                    //分账比例
                    BigDecimal dividedSecond = configList.get(0).getDynamicScale();
                    //每个人将分账出的数量放到mapSendTo(分出账人ID为key,分账数为值)
                    Map<Integer,BigDecimal> mapSendTo = new HashMap<Integer,BigDecimal>();
                    //遍历mapTotal每人的分润*dividedSecond更新值
                    for(Map.Entry<Integer,BigDecimal> entry: mapTotal.entrySet()) {
                        if(entry.getValue().compareTo(BigDecimal.ZERO)>0){
                            BigDecimal  value = entry.getValue();
                            mapTotal.put(entry.getKey(),entry.getValue().multiply(dividedSecond));
                            mapSendTo.put(entry.getKey(),value.multiply(BigDecimal.ONE.subtract(dividedSecond)));//*(1-dividedSecond)
                        }
                    }
                    //将分账出的数量加到下1-10级上
                    for(Map.Entry<Integer,BigDecimal> entry: mapSendTo.entrySet()) {
                        if(entry.getValue().compareTo(BigDecimal.ZERO)>0){
                            //当前人entry.getKey()的下1-10级人的推荐关系(的人数)
                            List<UserRecommend> urList = userRecommendMapper.getBatchUserRecommend(entry.getKey());
                            if(urList!=null&&urList.size()>0){
                                //下1-10级人要更新的数=要分出去的数/下1-10级人数==>entry.getValue().divide(new BigDecimal(urList.size()), 8, RoundingMode.HALF_UP)
                                BigDecimal everyNum = entry.getValue().divide(new BigDecimal(urList.size()), 8, RoundingMode.HALF_UP);//均值
                                for (UserRecommend userRe : urList){
                                    mapTotal.put(userRe.getUserId(),mapTotal.get(userRe.getUserId()).add(everyNum));
                                }
                            }
                        }
                    }

                    for(Map.Entry<Integer,BigDecimal> entry: mapTotal.entrySet()) {
                        Integer currentUserId = entry.getKey();//当前人
                        BigDecimal airNumTotal = BigDecimal.ZERO;
                        for (AirdropVO airdropVO : airdropList){
                            if(airdropVO.getIntegrationMin().compareTo(userInMap.get(currentUserId))<=0
                                    &&airdropVO.getIntegrationMax().compareTo(userInMap.get(currentUserId))>=0){
                                airNumTotal = airNumTotal.add(airdropVO.getAirdropNum());
                                //须更新的空投任务id为key，value+1为value
                                userAirdropMap.put(airdropVO.getId(),userAirdropMap.get(airdropVO.getId())+1);
                            }
                        }


                        /*//当前人currentUserId的上1-10级
                        UserRecommend urSecond = userRecommendMapper.selectByPrimaryKey(currentUserId);
                        List<Integer> pidListSecond = urObjChangeToList(urSecond);
                        //查找十个人的动态收益，计算当前人currentUserId的上1-10级所给的二次分账
                        BigDecimal totalSecond = BigDecimal.ZERO;
                        for(int i=0;i<pidListSecond.size();i++){
                            if(pidListSecond.get(i)==null||pidListSecond.get(i)==0){//没有上级
                                break;
                            }
                            totalSecond = totalSecond.add(mapTotal.get(pidListSecond.get(i)).multiply(BigDecimal.ONE.subtract(dividedSecond)));//*(1-0.9)
                        }*/

                        //记下流水账entry.getKey()或currentUserId-->entry.getValue()+空投数量
                        BigDecimal updAmount;
                        if(CoinCategory.USDT.equals(configList.get(0).getName().toLowerCase())){
                            updAmount = entry.getValue().add(airNumTotal);
                        }else{
                            updAmount = entry.getValue().divide(getLatestPrice(configList.get(0).getName().toLowerCase()), 8, RoundingMode.HALF_UP).add(airNumTotal);
                        }
                        if(updAmount!=null&&updAmount.compareTo(BigDecimal.ZERO)>0){
                            log.info("用户ID=="+currentUserId+"===投资usdt动态分润"+configList.get(0).getName().toLowerCase()+"额updAmount==="+updAmount);
                            userCoinService.changeUserCoin(currentUserId,configList.get(0).getName().toLowerCase(),
                                    updAmount,BigDecimal.ZERO,UserBillReason.DYNAMIC_INCOME,"投资usdt动态分润"+configList.get(0).getName().toLowerCase());
                        }

                        for(Map.Entry<Integer,Integer> entryAir: userAirdropMap.entrySet()) {
                            Airdrop airdrop = new Airdrop();
                            airdrop.setId(entryAir.getKey());//空投任务
                            airdrop.setUserNum(entryAir.getValue());//空投的人数airdrop
                            airdrop.setStatus(3);//完成
                            airdrop.setType(configList.get(0).getType());//按照配置表的币种去空投
                            airdrop.setDoTime(new Date());
                            airdropMapper.updateByPrimaryKeySelective(airdrop);
                        }
                    }
                }
        }catch (Exception e){
            log.error("=========投资动态分润返还出错===========");
        }
    }
    /**
     * 查配置表数据
     * @param type
     * @return
     */
    private List<BigDecimal> searchConfig(String type){
        List<BigDecimal> list = new ArrayList<BigDecimal>();
        List<String> configKey = new ArrayList<String>();
        if("in".equals(type)){
            configKey = new ArrayList<String>(Arrays.asList(
                    ConfigKey.DIVIDED_NEED_INTEGRATION1,ConfigKey.DIVIDED_NEED_INTEGRATION2,ConfigKey.DIVIDED_NEED_INTEGRATION3,
                    ConfigKey.DIVIDED_NEED_INTEGRATION4,ConfigKey.DIVIDED_NEED_INTEGRATION5,ConfigKey.DIVIDED_NEED_INTEGRATION6,
                    ConfigKey.DIVIDED_NEED_INTEGRATION7,ConfigKey.DIVIDED_NEED_INTEGRATION8,ConfigKey.DIVIDED_NEED_INTEGRATION9,
                    ConfigKey.DIVIDED_NEED_INTEGRATION10));
        }
        if("level".equals(type)){
            configKey = new ArrayList<String>(Arrays.asList(
                    ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL1,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL2,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL3,
                    ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL4,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL5,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL6,
                    ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL7,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL8,ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL9,
                    ConfigKey.DIVIDED_NEED_INTEGRATION_LEVEL10));
        }
        if("refLevel".equals(type)){
            configKey = new ArrayList<String>(Arrays.asList(
                    ConfigKey.REFERRER_DIVIDED_LEVEL1,ConfigKey.REFERRER_DIVIDED_LEVEL2,ConfigKey.REFERRER_DIVIDED_LEVEL3,
                    ConfigKey.REFERRER_DIVIDED_LEVEL4,ConfigKey.REFERRER_DIVIDED_LEVEL5,ConfigKey.REFERRER_DIVIDED_LEVEL6,
                    ConfigKey.REFERRER_DIVIDED_LEVEL7,ConfigKey.REFERRER_DIVIDED_LEVEL8,ConfigKey.REFERRER_DIVIDED_LEVEL9,
                    ConfigKey.REFERRER_DIVIDED_LEVEL10));
        }
        if("child".equals(type)){
            configKey = new ArrayList<String>(Arrays.asList(
                    ConfigKey.NEED_INTEGRATION_CHILD1,ConfigKey.NEED_INTEGRATION_CHILD2,ConfigKey.NEED_INTEGRATION_CHILD3,
                    ConfigKey.NEED_INTEGRATION_CHILD4,ConfigKey.NEED_INTEGRATION_CHILD5,ConfigKey.NEED_INTEGRATION_CHILD6,
                    ConfigKey.NEED_INTEGRATION_CHILD7,ConfigKey.NEED_INTEGRATION_CHILD8,ConfigKey.NEED_INTEGRATION_CHILD9,
                    ConfigKey.NEED_INTEGRATION_CHILD10));
        }
        for(int i=0;i<=9;i++){
            list.add(configService.getConfigValue(configKey.get(i),BigDecimal.ZERO,false));
        }
        return list;
    }

    /**
     * UserRecommend对象属性转成List
     * @param ur
     * @return
     */
    private List<Integer> urObjChangeToList(UserRecommend ur){
        List<Integer> list = new ArrayList<Integer>();
        if(ur!=null){
            list = new ArrayList<Integer>(Arrays.asList(ur.getPid1(),ur.getPid2(),
                    ur.getPid3(),ur.getPid4(),ur.getPid5(),ur.getPid6(),ur.getPid7(),
                    ur.getPid8(),ur.getPid9(),ur.getPid10()));
        }
        return list;
    }
    /**
     * 外部接口查询币种的汇率
     * @return
     */
    public BigDecimal getLatestPrice(String name){
        /*List<CandleVo> candles = marketService.getCandlesFromHuobi("1",TransactionPairs.BTCUSDT.getValue());
        BigDecimal exchangeRate = new BigDecimal(1);
        for (CandleVo candle : candles){
            exchangeRate = candle.getVolume();
            //log.info("candle.getDate()="+candle.getDate()+"candle.getTime()="+candle.getTime()+"candle.getVolume()="+candle.getVolume());
        }
        return exchangeRate;*/
        return tradeStatService.getLast24HourTradeStat(name, "usdt").getLatestPrice();
    }

}
