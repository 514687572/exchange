package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.cmd.exchange.common.model.InvestIncomeConfigExample;
import java.util.List;

import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface InvestIncomeConfigMapper {
    int countByExample(InvestIncomeConfigExample example);

    int deleteByExample(InvestIncomeConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvestIncomeConfig record);

    int insertSelective(InvestIncomeConfig record);

    List<InvestIncomeConfig> selectByExampleWithBLOBs(InvestIncomeConfigExample example);

    List<InvestIncomeConfig> selectByExample(InvestIncomeConfigExample example);

    InvestIncomeConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvestIncomeConfig record, @Param("example") InvestIncomeConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") InvestIncomeConfig record, @Param("example") InvestIncomeConfigExample example);

    int updateByExample(@Param("record") InvestIncomeConfig record, @Param("example") InvestIncomeConfigExample example);

    int updateByPrimaryKeySelective(InvestIncomeConfig record);

    int updateByPrimaryKeyWithBLOBs(InvestIncomeConfig record);

    int updateByPrimaryKey(InvestIncomeConfig record);

    //查询投资收益配置列表
    Page<InvestIncomeConfigVO> getInvestIncomeConfigList(@Param("type") Integer type, RowBounds rowBounds);
}