package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.RedisKey;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.SmartContractsMapper;
import com.cmd.exchange.common.mapper.UserContractsMapper;
import com.cmd.exchange.common.model.SmartContracts;
import com.cmd.exchange.common.model.SmartContractsExample;
import com.cmd.exchange.common.model.UserContracts;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.SmartContractsVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SmartContractstService {
    private static final int TIMEOUT = 3 * 1000;
    //二十四小时
    private static final int SECONDS = 60 * 60 * 24;
    @Autowired
    private SmartContractsMapper smartContractsMapper;
    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private UserContractsMapper userContractsMapper;

    @Autowired
    private UserCoinService userCoinService;

    @Value("${smart.contractst.userDayBuyLimit:1}")
    private Integer userDayBuyLimit;



    public Page<SmartContractsVo> getSmartContractsList(String coinName, int pageNo, int pageSize){
        Page<SmartContractsVo> page = new Page<>();
        List<SmartContractsVo> smartContractsVos = new ArrayList<>();

        SmartContractsExample example = new SmartContractsExample();
        SmartContractsExample.Criteria criteria = example.createCriteria();
        criteria.andContractsCoinNameEqualTo(coinName);
        Page<SmartContracts> smartContractss = smartContractsMapper.selectByExample(example, new RowBounds(pageNo, pageSize));
        List<SmartContracts> result = smartContractss.getResult();
        BeanUtils.copyProperties(smartContractss,page);
        if(result.isEmpty()){
            return page;
        }
        SmartContractsVo smartContractsVo;
        for(SmartContracts smartContracts:result){
            smartContractsVo = new SmartContractsVo();
            BeanUtils.copyProperties(smartContracts,smartContractsVo);
            String key = DateUtil.getDateYMDStr(new Date())+smartContracts.getContractsCoinName()+smartContracts.getId();
            String dayLimit = redisLockService.getStringRedisTemplate().opsForValue().get(RedisKey.SMART_CONTRACTST_CONTRACTSLIMIT_ + key);
            if(null != dayLimit && Integer.parseInt(dayLimit) >= smartContracts.getContractsLimit().intValue())
            {
                smartContractsVo.setDeposit(false);
            }
            //按百分比展示
            smartContractsVo.setDailyOutput(smartContractsVo.getDailyOutput().multiply(new BigDecimal(100)));
            smartContractsVo.setConsume(smartContractsVo.getConsume().multiply(new BigDecimal(100)));
            smartContractsVos.add(smartContractsVo);
        }
        page.addAll(smartContractsVos);
        return page;
    }

    @Transactional
    public void contractstBuy(Integer id,Integer userId){

        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        Assert.check(!redisLockService.lock(RedisKey.SMART_CONTRACTST_LOCK_+id+""+userId,String.valueOf(time)),ErrorCode.ERR_USER_REPETITIVE_OPERATION);

        SmartContracts smartContracts = smartContractsMapper.selectByPrimaryKey(id);
        Assert.check(null == smartContracts, ErrorCode.ERR_RECORD_NOT_EXIST);

        //智能合约购买上限
        String dayKey = DateUtil.getDateYMDStr(new Date())+smartContracts.getContractsCoinName()+smartContracts.getId();
        String dayLimit = redisLockService.getStringRedisTemplate().opsForValue().get(RedisKey.SMART_CONTRACTST_CONTRACTSLIMIT_ + dayKey);
        if(null != dayLimit && Integer.parseInt(dayLimit) >= smartContracts.getContractsLimit().intValue())
        {
            Assert.check(true, ErrorCode.ERR_SMART_CONTRACTST_LIST);
        }

        //用户购买上限
        String userKey = DateUtil.getDateYMDStr(new Date())+smartContracts.getContractsCoinName()+userId;
        String userDayLimit = redisLockService.getStringRedisTemplate().opsForValue().get(RedisKey.SMART_CONTRACTST_USERDAYLIMIT_ + userKey);
        if(null != userDayLimit && Integer.parseInt(userDayLimit) >= userDayBuyLimit.intValue())
        {
            Assert.check(true, ErrorCode.ERR_USER_CONTRACTST_BUY);
        }


        UserContracts userContracts = new UserContracts();
        BeanUtils.copyProperties(smartContracts,userContracts);
        userContracts.setId(null);
        userContracts.setAddTime(new Date());
        userContracts.setUserId(userId);
        //累加2天作为第1天的不超过24小时不返还的计算
        int newTime = smartContracts.getCycle().intValue() + 2;
        userContracts.setRtvTime(DateUtil.plusDay(newTime));
        userContracts.setLastTime(System.currentTimeMillis());
        userContractsMapper.insert(userContracts);
        //下单扣款
        userCoinService.changeUserCoin(userId,userContracts.getContractsCoinName(),userContracts.getDepositNum().negate(),userContracts.getDepositNum(), UserBillReason.TRADE_CONTRACTST,"userContracts id:"+userContracts.getId());

        //每日当前合约购买累计
        int buyNum = 1;
        if(null != dayLimit){
            buyNum = Integer.parseInt(dayLimit)+buyNum;
        }
        redisLockService.getStringRedisTemplate().opsForValue().set(RedisKey.SMART_CONTRACTST_CONTRACTSLIMIT_ +dayKey,String.valueOf(buyNum),SECONDS, TimeUnit.SECONDS);


        int userBuyNum = 1;
        if(null != userDayLimit){
            userBuyNum = Integer.parseInt(userDayLimit)+userBuyNum;
        }
        redisLockService.getStringRedisTemplate().opsForValue().set(RedisKey.SMART_CONTRACTST_USERDAYLIMIT_ +userKey,String.valueOf(userBuyNum),SECONDS, TimeUnit.SECONDS);
        //解锁
        redisLockService.unlock(RedisKey.SMART_CONTRACTST_LOCK_+id+""+userId, String.valueOf(time));
    }
}
