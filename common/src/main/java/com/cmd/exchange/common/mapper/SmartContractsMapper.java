package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.SmartContracts;
import com.cmd.exchange.common.model.SmartContractsExample;
import java.util.List;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
@Mapper
public interface SmartContractsMapper {
    int countByExample(SmartContractsExample example);

    int deleteByExample(SmartContractsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmartContracts record);

    int insertSelective(SmartContracts record);

    List<SmartContracts> selectByExample(SmartContractsExample example);

    SmartContracts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmartContracts record, @Param("example") SmartContractsExample example);

    int updateByExample(@Param("record") SmartContracts record, @Param("example") SmartContractsExample example);

    int updateByPrimaryKeySelective(SmartContracts record);

    int updateByPrimaryKey(SmartContracts record);

    Page<SmartContracts> selectByExample(SmartContractsExample example, RowBounds rowBounds);
}