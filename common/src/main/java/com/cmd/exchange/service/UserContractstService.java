package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.UserContractsHistoryMapper;
import com.cmd.exchange.common.mapper.UserContractsMapper;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class UserContractstService {
    private static final int LIMIT =  500;
    private static final int TIME = 1000 * 60 * 60 * 24;
    private static final String USDT = "USDT";
    @Autowired
    private UserContractsMapper userContractsMapper;
    @Autowired
    private UserContractsHistoryMapper userContractsHistoryMapper;

    @Autowired
    private UserCoinService userCoinService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private CoinService coinService;



    public Page<UserContracts> getUserContractsList(Integer userId, int pageNo, int pageSize){

        UserContractsExample example = new UserContractsExample();
        UserContractsExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("id desc");
        Page<UserContracts> userContracts = userContractsMapper.selectByExample(example, new RowBounds(pageNo, pageSize));

        userContracts.getResult().stream().forEach(a ->{
            //处理返还日期展示 减1天24 满足小时处理
            a.setRtvTime(DateUtil.plusDay(-2,a.getRtvTime()));
        });
        return userContracts;
    }
    public Page<UserContractsHistory> getUserContractsHisList(Integer userId, int pageNo, int pageSize){
        UserContractsHistoryExample example = new UserContractsHistoryExample();
        UserContractsHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("id desc");
        Page<UserContractsHistory> userContractsHistories = userContractsHistoryMapper.selectByExample(example, new RowBounds(pageNo, pageSize));
        userContractsHistories.getResult().stream().forEach(a ->{
            //处理返还日期展示 减1天 满足24小时处理
            a.setRtvTime(DateUtil.plusDay(-2,a.getRtvTime()));
        });
        return userContractsHistoryMapper.selectByExample(example, new RowBounds(pageNo, pageSize));
    }


    public void rtvDayContract(){

        List<UserContracts> userContracts;
        UserContractsExample userContractsExample = new UserContractsExample();
        UserContractsExample.Criteria criteria = userContractsExample.createCriteria();
        criteria.andLastTimeLessThan(System.currentTimeMillis());
        userContractsExample.setLimit(LIMIT);
        userContractsExample.setOrderByClause("id asc");
        while (true){
            userContracts = userContractsMapper.selectByExample(userContractsExample);

            if(userContracts.isEmpty()){
                break;
            }

            for(UserContracts contracts:userContracts)
            {
                doUserContacts(contracts);
            }

            if(userContracts.size() < LIMIT)
            {
                break;
            }

        }


    }

    @Transactional
    public void doUserContacts(UserContracts contracts) {
        long timeMillis = System.currentTimeMillis();
        long rtvTime = contracts.getRtvTime().getTime();
        //使用周期时间-当前时间，判断是否超时
        long endTime = rtvTime - timeMillis;

        UserCoinVO lockUserCoinByUserIdAndCoinName = userCoinService.lockUserCoinByUserIdAndCoinName(contracts.getUserId(), contracts.getContractsCoinName());

        if(endTime < 0 )
        {
            //超时数据迁移到历史列表
            insertUserContractsHis(contracts, "智能合约ID:{},超时数据迁移到历史列表,用户ID:{}");
            return;
        }

        //更新最后返还时间戳
        contracts.setLastTime(timeMillis);

        long addtime = contracts.getAddTime().getTime();
        //当前时间-合约添加时间，判断是否超过24小时
        long startTime = timeMillis - addtime;
        if(startTime < TIME){
            //未超过24小时数据，跳过合约处理
            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},未超过24小时数据，跳过合约处理,用户ID:{}",contracts.getId(),contracts.getUserId());
            return;
        }

        Coin coinByName = coinService.getCoinByName(contracts.getContractsCoinName());
        if(null == coinByName || coinByName.getSmartReleaseRate().compareTo(BigDecimal.ZERO) <= 0 ){
            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},币种{}为空或智能合约每日释放锁仓上限不大于零,用户ID:{}",contracts.getId(),contracts.getContractsCoinName(),contracts.getUserId());
            return;
        }

        UserCoinVO userCoinByUserIdAndCoinName = userCoinService.lockUserCoinByUserIdAndCoinName(contracts.getUserId(), USDT);
        if(null == userCoinByUserIdAndCoinName){

            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},USDT钱包不存在,用户ID:{}",contracts.getId(),contracts.getUserId());
            return;
        }

        Market marketByName = marketService.getMarketByName(contracts.getContractsCoinName(), USDT);
        if(null == marketByName || marketByName.getLastDayPrice().compareTo(BigDecimal.ZERO) <= 0)
        {
            //更新最后返还时间戳
            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},市场交易为空或昨日收盘价为零,用户ID:{}",contracts.getId(),contracts.getUserId());
            return;
        }

        if(null == lockUserCoinByUserIdAndCoinName || lockUserCoinByUserIdAndCoinName.getReceiveFreezeBalance().compareTo(BigDecimal.ZERO) <= 0)
        {
            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},{}钱包不存在或锁仓资产不足,用户ID:{}",contracts.getId(),contracts.getContractsCoinName(),contracts.getUserId());
            return;
        }

        //扣除usdt手续费 存入数量X手续费比例X该币种昨日收盘价
        BigDecimal usdtFee = contracts.getDepositNum().multiply(contracts.getConsume()).multiply(marketByName.getLastDayPrice());

        if(userCoinByUserIdAndCoinName.getAvailableBalance().compareTo(usdtFee) < 0 )
        {
            //更新最后返还时间戳
            userContractsMapper.updateByPrimaryKey(contracts);
            log.info("智能合约ID:{},手续费不足或锁仓资产不足,用户ID:{},USDT fee:{}",contracts.getId(),contracts.getUserId(),usdtFee);
            return;
        }



        //扣除usdt手续费
        userCoinService.changeUserCoin(contracts.getUserId(),USDT,usdtFee.negate(),BigDecimal.ZERO, UserBillReason.CONTRACTST_FEE,"智能合约每日返还扣手续费");

        //释放锁仓 存入数量 X 每日释放比例
        BigDecimal rtvAvailableBalance = contracts.getDepositNum().multiply(contracts.getDailyOutput());
        BigDecimal receiveFreezeBalanceByRate = lockUserCoinByUserIdAndCoinName.getReceiveFreezeBalance().multiply(coinByName.getSmartReleaseRate());
        if(receiveFreezeBalanceByRate.compareTo(rtvAvailableBalance) < 0)
        {
            rtvAvailableBalance = receiveFreezeBalanceByRate;
        }

        userCoinService.changeUserReceivedFreezeCoin(contracts.getUserId(),contracts.getContractsCoinName(),rtvAvailableBalance.negate(),UserBillReason.CONTRACTST_RECEIVE_FREEZE,"智能合约释放锁仓");
        userCoinService.changeUserCoin(contracts.getUserId(),contracts.getContractsCoinName(),rtvAvailableBalance,BigDecimal.ZERO,UserBillReason.CONTRACTST_RECEIVE_FREEZE,"智能合约释放锁仓");

        //争对即将过期数据进行处理,未超过24小时数据
        if(endTime < TIME )
        {
            //未超过24时数据迁移到历史列表
            insertUserContractsHis(contracts, "智能合约ID:{},未超过24时数据迁移到历史列表,用户ID:{}");
            return;
        }
        //更新最后返还时间戳
        userContractsMapper.updateByPrimaryKey(contracts);
    }

    public void insertUserContractsHis(UserContracts contracts, String s) {
        //交易周期完成后释放冻结资产
        userCoinService.changeUserCoin(contracts.getUserId(),contracts.getContractsCoinName(),contracts.getDepositNum(),contracts.getDepositNum().negate(),UserBillReason.TRADE_MATCH_RET,"交易完成后返回的冻结金");
        //迁移到历史数据
        UserContractsHistory contractsHistory = new UserContractsHistory();
        BeanUtils.copyProperties(contracts, contractsHistory);
        userContractsHistoryMapper.insert(contractsHistory);
        userContractsMapper.deleteByPrimaryKey(contracts.getId());
        log.info(s, contracts.getId(), contracts.getUserId());
    }

}
