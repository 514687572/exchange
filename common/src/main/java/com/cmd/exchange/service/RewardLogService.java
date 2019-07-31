package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.UserRewardLogMapper;
import com.cmd.exchange.common.vo.RewardLogVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardLogService {
    @Autowired
    UserBonusService userBonusService;
    @Autowired
    UserRewardLogMapper userRewardLogMapper;

    //获取用户推荐的奖励
    Page<RewardLogVO> getUserRewardLog(int userId, int pageNo, int pageSize) {
        return userRewardLogMapper.getUserRewardLog(userId, new RowBounds(pageNo, pageSize));
    }

    //获取用户返佣的奖励
    Page<RewardLogVO> getTradeBonusLog(int userId, int pageNo, int pageSize) {
        return userBonusService.getTradeBonusLog(userId, pageNo, pageSize);
    }

}
