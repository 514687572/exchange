package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.ShareOutBonusLogMapper;
import com.cmd.exchange.common.mapper.ShareOutBonusMapper;
import com.cmd.exchange.common.mapper.TradeBonusLogMapper;
import com.cmd.exchange.common.mapper.UserMapper;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.vo.*;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//用户分红相关的查询接口
@Service
public class UserBonusService {
    @Autowired
    private TradeBonusLogMapper tradeBonusLogMapper;
    @Autowired
    private ShareOutBonusLogMapper shareOutBonusMapper;

    @Autowired
    private UserMapper userMapper;

    public Page<TradeBonusLogVO> getTradeBonusList(TradeBonusLogRequestVO request) {
        if (StringUtils.isNotBlank(request.getMobile())) {
            User user = userMapper.getUserByMobile(request.getMobile());
            if (user == null) {
                return new Page<>();
            }

            request.setUserId(user.getId());
        }

        if (StringUtils.isNotBlank(request.getReferrerMobile())) {
            User user = userMapper.getUserByMobile(request.getReferrerMobile());
            if (user == null) {
                return new Page<>();
            }

            request.setReferrerId(user.getId());
        }

        return tradeBonusLogMapper.getTradeBonusList(request, new RowBounds(request.getPageNo(), request.getPageSize()));
    }

    public Page<ShareOutBonusLogVO> getShareBonusList(ShareOutBonusLogRequestVO request) {
        if (StringUtils.isNotBlank(request.getMobile())) {
            User user = userMapper.getUserByMobile(request.getMobile());
            if (user == null) {
                return new Page<>();
            }

            request.setUserId(user.getId());
        }

        if (StringUtils.isNotBlank(request.getRealName())) {
            User user = userMapper.getUserByRealName(request.getRealName());
            if (user == null) {
                return new Page<>();
            }

            request.setUserId(user.getId());
        }

        return shareOutBonusMapper.getShareOutBonusList(request, new RowBounds(request.getPageNo(), request.getPageSize()));
    }

    public Page<RewardLogVO> getTradeBonusLog(int userId, int pageNo, int pageSize) {
        return tradeBonusLogMapper.getTradeBonusLog(userId, new RowBounds(pageNo, pageSize));
    }
}
