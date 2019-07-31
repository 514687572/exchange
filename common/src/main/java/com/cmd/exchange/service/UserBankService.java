package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.UserBankMapper;
import com.cmd.exchange.common.model.UserBank;
import com.cmd.exchange.common.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBankService {

    @Autowired
    UserBankMapper userBankMapper;

    public int add(UserBank userBank) {
        Assert.check(userBank.getName() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank.getLegalName() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank.getBankType() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank.getBankName() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank.getBankUser() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank.getBankNo() == null, ErrorCode.ERR_PARAM_ERROR);

        List<UserBank> list = userBankMapper.getUserBankByUserId(userBank.getUserId(), userBank.getBankType(), null);
        Assert.check(list.size() != 0, ErrorCode.ERR_RECORD_EXIST);

        return userBankMapper.add(userBank);
    }

    public int updateUserBank(int userId, UserBank userBank) {
        UserBank userBank1 = userBankMapper.getUserBankById(userBank.getId());
        Assert.check(userBank1 == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank1.getUserId() != userId, ErrorCode.ERR_PARAM_ERROR);

        return userBankMapper.updateUserBank(userBank);
    }

    public int del(Integer id, Integer userId) {
        UserBank userBank1 = userBankMapper.getUserBankById(id);
        Assert.check(userBank1 == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank1.getUserId().intValue() != userId.intValue(), ErrorCode.ERR_PARAM_ERROR);

        return userBankMapper.del(id);
    }

    public int updateUserBankStatus(int userId, int id, int status) {
        UserBank userBank1 = userBankMapper.getUserBankById(id);
        Assert.check(userBank1 == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userBank1.getUserId() != userId, ErrorCode.ERR_PARAM_ERROR);

        return userBankMapper.updateUserBank(new UserBank().setId(id).setStatus(status));
    }

    public int updateUserBankStatusByBankType(int userId, Integer bankType, Integer status) {
        Assert.check(bankType == null || status == null, ErrorCode.ERR_PARAM_ERROR);
        return userBankMapper.updateUserBankByUserId(userId, bankType, status);
    }

    public UserBank getUserBankById(int id) {
        return userBankMapper.getUserBankById(id);
    }

    public List<UserBank> getUserBankByUserId(int userId, Integer bankType) {
        return userBankMapper.getUserBankByUserId(userId, bankType, null);
    }

    public int checkPay(int userId) {
        int result = 0;
        List<UserBank> userBanks = userBankMapper.checkUserBankById(userId);
        if (userBanks.size() > 0) {
            result = 1;
        }
        return result;
    }
}
