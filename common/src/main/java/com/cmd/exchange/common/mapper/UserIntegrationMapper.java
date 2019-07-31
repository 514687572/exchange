package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserIntegration;
import com.cmd.exchange.common.model.UserIntegrationExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserIntegrationMapper {
    int countByExample(UserIntegrationExample example);

    int deleteByExample(UserIntegrationExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserIntegration record);

    int insertSelective(UserIntegration record);

    List<UserIntegration> selectByExample(UserIntegrationExample example);

    UserIntegration selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserIntegration record, @Param("example") UserIntegrationExample example);

    int updateByExample(@Param("record") UserIntegration record, @Param("example") UserIntegrationExample example);

    int updateByPrimaryKeySelective(UserIntegration record);

    int updateByPrimaryKey(UserIntegration record);

    /**
     * 获取用户总积分
     * @param userId
     * @return
     */
    BigDecimal getUserIntegrationByUserId(Integer userId);
    /**
     * 批量插入积分表
     * @param userIntegrationList
     * @return
     */
    void insertByBatch(List<UserIntegration> userIntegrationList);
    /**
     * 获取批量用户的总积分
     * @return
     */
    List<UserIntegration> getBatchUserIntegration();
}