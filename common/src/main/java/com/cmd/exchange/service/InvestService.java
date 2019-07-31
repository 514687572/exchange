package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.*;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import com.cmd.exchange.common.vo.InvestPageVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class InvestService {
    private static Logger log = LoggerFactory.getLogger(InvestService.class);
    @Autowired
    private InvestMapper investMapper;
    @Autowired
    private InvestDetailMapper investDetailMapper;
    @Autowired
    private ProjectIntroductionMapper projectIntroductionMapper;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    ConfigService configService;
    @Autowired
    private UserRecommendMapper userRecommendMapper;
    @Autowired
    private UserIntegrationMapper userIntegrationMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private InvestIncomeConfigMapper investIncomeConfigMapper;

    public InvestPageVO getInvestPage(Integer userId) {
        InvestPageVO investPageVO = new InvestPageVO();
        //查可用USDT
        investPageVO.setAvailableBalance(queryAvailableBalance(userId));
        //查总投资额
        Invest invest = investMapper.selectByPrimaryKey(userId);
        if( null == invest ){
            investPageVO.setCoinAmount(BigDecimal.ZERO);
        }else{
            investPageVO.setCoinAmount(invest.getCoinAmount());
        }
        /*//查每种类型（共3种）的最新的一个开启状态的项目介绍
        List<ProjectIntroduction> contentInvestLits = projectIntroductionMapper
                .getProjectIntroductionList(new Integer(1),new Integer(1),new RowBounds(1, 1));
        if(null != contentInvestLits && contentInvestLits.size() == 1){
            investPageVO.setContentInvest(contentInvestLits.get(0).getContent());
        }
        List<ProjectIntroduction> contentIntegralLits = projectIntroductionMapper
                .getProjectIntroductionList(new Integer(2),new Integer(1),new RowBounds(1, 1));
        if(null != contentIntegralLits && contentIntegralLits.size() == 1){
            investPageVO.setContentIntegral(contentIntegralLits.get(0).getContent());
        }
        List<ProjectIntroduction> contentUnitLits = projectIntroductionMapper
                .getProjectIntroductionList(new Integer(3),new Integer(1), new RowBounds(1, 1));
        if(null != contentUnitLits && contentUnitLits.size() == 1){
            investPageVO.setContentUnit(contentUnitLits.get(0).getContent());
        }*/
        //查询项目介绍：查询最新的一条配置
        List<InvestIncomeConfigVO> configList = investIncomeConfigMapper.getInvestIncomeConfigList(
                null, new RowBounds(new Integer(0), new Integer(1)));
        if(configList!=null&&configList.size()==1){
            investPageVO.setContentInvest(configList.get(0).getDescription());
            investPageVO.setContentIntegral(configList.get(0).getDescription());
            investPageVO.setContentUnit(configList.get(0).getDescription());
        }
        return investPageVO;
    }

    @Transactional
    public void updateInvest(Integer userId, String payPassword, BigDecimal coinAmount) {
        //检查密码
        User u = userMapper.getUserByUserId(userId);
        Assert.check(StringUtils.isBlank(u.getPayPassword()), ErrorCode.ERR_USER_PAY_PASSWORD_NOT_FOUND);
        Assert.check(!EncryptionUtil.check(payPassword, u.getPayPassword(), u.getPaySalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);
        //如果当前可用USDT<想要投的额度,返回错误码
        Assert.check(queryAvailableBalance(userId).compareTo(coinAmount)==-1, ErrorCode.ERR_BALANCE_INSUFFICIENT);
        log.info("begin invest,userId=" + userId + ",coinAmount=" + coinAmount + ",createTime=" + new Date());
        Invest invest = investMapper.selectByPrimaryKey(userId);
        Invest investDefi = new Invest();
        investDefi.setUserId(userId);
        //是否有投过资
        if(invest == null){
            //没投资过，主表新增一条数据
            investDefi.setCreateTime(new Date());
            investDefi.setCoinAmount(coinAmount);
            investMapper.insertSelective(investDefi);
        }else{
            //有投资过，主表修改这条数据
            investDefi.setCoinAmount(coinAmount.add(invest.getCoinAmount()));
            investMapper.updateByPrimaryKeySelective(investDefi);
        }
        //明细表新增一条数据
        InvestDetail investDetail = new InvestDetail();
        investDetail.setInvestUserId(userId);
        investDetail.setCreateTime(new Date());
        investDetail.setAmount(coinAmount);
        investDetailMapper.insertSelective(investDetail);
        //更新可用USDT
        userCoinService.changeUserCoin(userId, CoinCategory.USDT,
                coinAmount.multiply(new BigDecimal(-1)),BigDecimal.ZERO,UserBillReason.INVEST_SUB,"投资USDT");

        //投资成功后赠送积分--added in 2019/06/19
        //准备批量数据插入积分表
        List<UserIntegration> list = new ArrayList<>();
        //查配置表数据
        List<BigDecimal> configlist = new ArrayList<BigDecimal>();
        List<String> configKey = new ArrayList<String>(Arrays.asList(
                ConfigKey.INVEST_REWARD_INTEGRATION_A,ConfigKey.INVEST_REWARD_INTEGRATION_B,
                ConfigKey.INVEST_REWARD_INTEGRATION_C,ConfigKey.INVEST_REWARD_INTEGRATION_D));
        for(int i=0;i<=3;i++){
            configlist.add(configService.getConfigValue(configKey.get(i),BigDecimal.ZERO,false));
        }
        //赠送积分给自己
        BigDecimal integA = coinAmount.multiply(configlist.get(0));
        if(integA!=null&&integA.compareTo(BigDecimal.ZERO)==1){
            list.add(newObj(userId,IntegrationType.INVEST_REWARD,integA));
        }
        //用户上级关系(还需要赠送积分给上级：上1级，上2-3级，上4-10级/上11-级)
        UserRecommend ur = userRecommendMapper.selectByPrimaryKey(userId);
        //总积分(=最大能够接受的积分)
        BigDecimal total;
        BigDecimal extra = BigDecimal.ZERO;
        if(ur != null){
            List<Integer> pidList = new ArrayList<Integer>(Arrays.asList(ur.getPid1(),
                    ur.getPid2(),ur.getPid3(),ur.getPid4(),ur.getPid5(),ur.getPid6(),
                    ur.getPid7(),ur.getPid8(),ur.getPid9(),ur.getPid10()));
            for(int i=0;i<pidList.size();i++){
                if((pidList.get(i)==null||pidList.get(i)==0)){//没有上级
                    break;
                }
                total = userIntegrationMapper.getUserIntegrationByUserId(pidList.get(i));
                //赠送积分给上1-10级
                if(total==null||BigDecimal.ZERO.compareTo(total)>=0){//没有积分，不赠送
                    continue;
                }
                //预赠积分(=投资额*系数)
                BigDecimal ready = BigDecimal.ZERO;
                if(i==0){//赠送积分给上1级
                    ready = coinAmount.multiply(configlist.get(1)).add(extra);
                }
                if(i>0&&i<=2){//赠送积分给上2-3级
                    ready = coinAmount.multiply(configlist.get(2)).add(extra);
                }
                if(i>2&&i<=8){//赠送积分给上4-9级
                    ready = coinAmount.multiply(configlist.get(3)).add(extra);
                }
                if(total.compareTo(ready)>=0){//总积分>=预赠积分
                    list.add(newObj(pidList.get(i),IntegrationType.INVEST_REFERRER_REWARD,ready));
                    extra = BigDecimal.ZERO;
                }else{
                    list.add(newObj(pidList.get(i),IntegrationType.INVEST_REFERRER_REWARD,total));
                    //剩余积分（继续往上级赠送，如果没有上级剩余积分作废）
                    extra = ready.subtract(total);
                }
            }
            //赠送积分给上10-级
            if(ur.getPid10() != null && ur.getPid10() != 0){
                boolean flag = false;
                Integer uId = ur.getPid10();
                do{
                    total = userIntegrationMapper.getUserIntegrationByUserId(uId);
                    //有积分，才赠送
                    if(total!=null&&total.compareTo(BigDecimal.ZERO)==1){
                        BigDecimal ready = coinAmount.multiply(configlist.get(3)).add(extra);
                        if(total.compareTo(ready)>=0){
                            list.add(newObj(uId,IntegrationType.INVEST_REFERRER_REWARD,ready));
                            extra = BigDecimal.ZERO;
                        }else{
                            list.add(newObj(uId,IntegrationType.INVEST_REFERRER_REWARD,total));
                            extra = ready.subtract(total);
                        }
                    }
                    User user = userMapper.getUserByUserId(uId);
                    if(user.getReferrer()== null || user.getReferrer()==0){
                        flag = false;
                    }else{
                        uId = user.getReferrer();
                        flag = true;//有上级就继续循环
                    }
                }while(flag);
            }
        }
        userIntegrationMapper.insertByBatch(list);
    }
    //生成一个用户积分对象
    private UserIntegration newObj(Integer userId,Integer integrationType,BigDecimal integration){
        UserIntegration ui = new UserIntegration();
        ui.setUserId(userId);
        ui.setAddTime(new Date());
        ui.setIntegrationType(integrationType);
        ui.setUserIntegration(integration);
        return ui;
    }
    //通过userId查询可用USDT
    private BigDecimal queryAvailableBalance(Integer userId) {
        UserCoinVO userCoinVO = userCoinService.getUserCoinByUserIdAndCoinName(userId,CoinCategory.USDT);
        if( null == userCoinVO ){
            return (BigDecimal.ZERO);
        }else {
            return userCoinVO.getAvailableBalance();
        }
    }
}
