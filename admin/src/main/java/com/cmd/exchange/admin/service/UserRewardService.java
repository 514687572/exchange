package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.dao.UserBillMapper;
import com.cmd.exchange.admin.vo.ReferralRewardVO;
import com.cmd.exchange.admin.vo.RegisterRewardReqVO;
import com.cmd.exchange.admin.vo.RegisterRewardVO;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserRewardService {
    @Autowired
    UserBillMapper userBillMapper;
    @Autowired
    UserService userService;

    public Page<RegisterRewardVO> getUserRegisterReward(RegisterRewardReqVO req) {
        return userBillMapper.getUserRegisterReward(req, new RowBounds(req.getPageNo(), req.getPageSize()));
    }

    public Page<ReferralRewardVO> getUserReferralRewardVO(RegisterRewardReqVO req) {
        if (StringUtils.isNotBlank(req.getReferrerMobile())) {
            User referrer = userService.getUserByMobile(req.getReferrerMobile());
            if (referrer == null) {
                return new Page<>();
            }
            req.setReferrer(referrer.getId());
        }

        if (StringUtils.isNotBlank(req.getMobile())) {
            User user = userService.getUserByMobile(req.getMobile());
            if (user.getReferrer() == null) {
                return new Page<>();
            }
            req.setUserId(user.getId());
        }

        return userBillMapper.getUserReferralRewardVO(req, new RowBounds(req.getPageNo(), req.getPageSize()));
    }

    // 获取交易返佣列表
    public Page<RegisterRewardVO> getTradeRewardRec(Integer userId, String coinName, Date beginTime, Date endTime, int pageNo, int pageSize) {
        return userBillMapper.getTradeRewardRec(userId, coinName, beginTime, endTime, new RowBounds(pageNo, pageSize));
    }
}
