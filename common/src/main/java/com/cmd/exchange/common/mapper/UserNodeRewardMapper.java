package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.UserNodeReward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户 节点 奖励
 */
@Mapper
public interface UserNodeRewardMapper {

    UserNodeReward getOneInfo(UserNodeReward userNodeReward);

    List<UserNodeReward> getInfo(UserNodeReward userNodeReward);

    int add(UserNodeReward userNodeReward);

    int mod(UserNodeReward userNodeReward);


}
