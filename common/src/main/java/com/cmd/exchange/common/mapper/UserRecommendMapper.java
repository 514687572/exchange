package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserRecommend;
import com.cmd.exchange.common.model.UserRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRecommendMapper {
    int countByExample(UserRecommendExample example);

    int deleteByExample(UserRecommendExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserRecommend record);

    int insertSelective(UserRecommend record);

    List<UserRecommend> selectByExample(UserRecommendExample example);

    UserRecommend selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByExample(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByPrimaryKeySelective(UserRecommend record);

    int updateByPrimaryKey(UserRecommend record);

    List<UserRecommend> getBatchUserRecommend(Integer pid);
}