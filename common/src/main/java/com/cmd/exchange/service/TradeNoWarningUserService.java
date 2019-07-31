package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.TradeNoWarningUserMapper;
import com.cmd.exchange.common.model.TradeNoWarningUser;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeNoWarningUserService {
    @Autowired
    private TradeNoWarningUserMapper tradeNoWarningUserMapper;

    public Page<TradeNoWarningUser> getTadeNoWarningUserList(String userName, String noWarningType, Integer pageNo, Integer pageSize) {
        return tradeNoWarningUserMapper.getTradeNoWarningUserList(userName, noWarningType, new RowBounds(pageNo, pageSize));

    }

    public void delTradeNoWarningUserById(Integer id) {

        tradeNoWarningUserMapper.delTradeNoWarningUserById(id);
    }

    public int addTradeNoWarningUser(TradeNoWarningUser tradeNoWarningUser) {
        return tradeNoWarningUserMapper.addTradeNoWarningUser(tradeNoWarningUser);
    }

    public TradeNoWarningUser getTradeNoWarningByuserName(String userName, String noWarningType) {
        return tradeNoWarningUserMapper.getTradeNoWarningByuserName(userName, noWarningType);
    }

    public List<TradeNoWarningUser> getTradeNowarningAll() {
        return tradeNoWarningUserMapper.getTradeNoWarningUserAll();
    }
}
