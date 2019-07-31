package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserContractsHistory;
import com.cmd.exchange.common.model.UserContractsHistoryExample;
import java.util.List;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface UserContractsHistoryMapper {
    int countByExample(UserContractsHistoryExample example);

    int deleteByExample(UserContractsHistoryExample example);

    int insert(UserContractsHistory record);

    int insertSelective(UserContractsHistory record);

    List<UserContractsHistory> selectByExample(UserContractsHistoryExample example);
    Page<UserContractsHistory> selectByExample(UserContractsHistoryExample example, RowBounds rowBounds);

    int updateByExampleSelective(@Param("record") UserContractsHistory record, @Param("example") UserContractsHistoryExample example);

    int updateByExample(@Param("record") UserContractsHistory record, @Param("example") UserContractsHistoryExample example);
}