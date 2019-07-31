package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.InvestDetail;
import com.cmd.exchange.common.model.InvestDetailExample;
import java.util.List;

import com.cmd.exchange.common.vo.InvestDetailVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface InvestDetailMapper {
    int countByExample(InvestDetailExample example);

    int deleteByExample(InvestDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvestDetail record);

    int insertSelective(InvestDetail record);

    List<InvestDetail> selectByExample(InvestDetailExample example);

    InvestDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvestDetail record, @Param("example") InvestDetailExample example);

    int updateByExample(@Param("record") InvestDetail record, @Param("example") InvestDetailExample example);

    int updateByPrimaryKeySelective(InvestDetail record);

    int updateByPrimaryKey(InvestDetail record);

    Page<InvestDetail> getInvestDetailList(@Param("investUserId") Integer investUserId, RowBounds rowBounds);
    Page<InvestDetailVO> getInvestDetailCurrentList(RowBounds rowBounds);
    List<InvestDetail> countInvestIncomeList();
}