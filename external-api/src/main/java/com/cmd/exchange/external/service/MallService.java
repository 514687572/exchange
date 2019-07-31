package com.cmd.exchange.external.service;


import com.alibaba.fastjson.JSON;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.RedisKey;
import com.cmd.exchange.common.mapper.MallUserDeductionIntegralRecordMapper;
import com.cmd.exchange.common.model.MallUserDeductionIntegralRecord;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.external.request.UserDeductionIntegral;
import com.cmd.exchange.external.request.UserDeductionIntegralRequest;
import com.cmd.exchange.service.FinanceService;
import com.cmd.exchange.service.RedisLockService;
import com.cmd.exchange.service.TransferService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MallService {

    private static final int TIMEOUT = 3 * 1000;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private RedisLockService redisLockService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private MallUserDeductionIntegralRecordMapper mallUserDeductionIntegralRecordMapper;

    @Transactional
    public void userDeductionIntegrals(UserDeductionIntegralRequest request) {

        List<UserDeductionIntegral> userDeductionIntegralList = JSON.parseArray(request.getUserDeductionIntegrals(), UserDeductionIntegral.class);

        Assert.check(null == userDeductionIntegralList, ErrorCode.ERR_PARAM_ERROR);

        String orderNum = request.getOrderNum();

        String mobile = request.getMobile();

        Assert.check(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(orderNum), ErrorCode.ERR_PARAM_ERROR);


        long time = System.currentTimeMillis() + TIMEOUT;
        //加锁
        Assert.check(!redisLockService.lock(RedisKey.EXTERNAL_DEDUCTIONINTEGRAL + request.getOrderNum(), String.valueOf(time)), ErrorCode.ERR_USER_REPETITIVE_OPERATION);


        MallUserDeductionIntegralRecord byOrderNum = mallUserDeductionIntegralRecordMapper.getByOrderNum(orderNum);

        Assert.check(null != byOrderNum, ErrorCode.ERR_SUCCESS);

        for (UserDeductionIntegral integral : userDeductionIntegralList) {
            Assert.check(transferService.getConfigByName(integral.getCoinName(), ConfigKey.SYN_DEDUCTION_INTEGRALS_COIN_NAME), ErrorCode.ERR_USER_COINNAME_RESTRICT);

            financeService.userDeductionIntegral(integral.getCoinName(), request.getMobile(), integral.getChangeBalance(), request.getSign(), request.getTimestamp(), request.getOrderNum());
        }


        //解锁
        redisLockService.unlock(RedisKey.EXTERNAL_DEDUCTIONINTEGRAL + request.getOrderNum(), String.valueOf(time));
    }
}
