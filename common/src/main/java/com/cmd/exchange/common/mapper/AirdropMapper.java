package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Airdrop;
import com.cmd.exchange.common.model.AirdropExample;
import java.util.List;

import com.cmd.exchange.common.vo.AirdropVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AirdropMapper {
    int countByExample(AirdropExample example);

    int deleteByExample(AirdropExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Airdrop record);

    int insertSelective(Airdrop record);

    List<Airdrop> selectByExample(AirdropExample example);

    Airdrop selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Airdrop record, @Param("example") AirdropExample example);

    int updateByExample(@Param("record") Airdrop record, @Param("example") AirdropExample example);

    int updateByPrimaryKeySelective(Airdrop record);

    int updateByPrimaryKey(Airdrop record);

    //查询空投任务列表
    Page<Airdrop> getAirdropList(RowBounds rowBounds);
    //查询昨天的开启状态的空投任务列表
    List<AirdropVO> selectYesterdayAirdropList();
}