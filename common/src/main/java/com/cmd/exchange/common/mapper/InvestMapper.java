package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Invest;
import com.cmd.exchange.common.model.InvestExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvestMapper {
    int countByExample(InvestExample example);

    int deleteByExample(InvestExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(Invest record);

    int insertSelective(Invest record);

    List<Invest> selectByExample(InvestExample example);

    Invest selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") Invest record, @Param("example") InvestExample example);

    int updateByExample(@Param("record") Invest record, @Param("example") InvestExample example);

    int updateByPrimaryKeySelective(Invest record);

    int updateByPrimaryKey(Invest record);
}