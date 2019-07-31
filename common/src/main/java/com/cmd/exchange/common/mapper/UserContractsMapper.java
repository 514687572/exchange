package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserContracts;
import com.cmd.exchange.common.model.UserContractsExample;
import java.util.List;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface UserContractsMapper {
    int countByExample(UserContractsExample example);

    int deleteByExample(UserContractsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserContracts record);

    int insertSelective(UserContracts record);

    List<UserContracts> selectByExample(UserContractsExample example);

    Page<UserContracts> selectByExample(UserContractsExample example, RowBounds rowBounds);

    UserContracts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserContracts record, @Param("example") UserContractsExample example);

    int updateByExample(@Param("record") UserContracts record, @Param("example") UserContractsExample example);

    int updateByPrimaryKeySelective(UserContracts record);

    int updateByPrimaryKey(UserContracts record);

}