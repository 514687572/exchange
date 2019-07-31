package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserTeam;
import com.cmd.exchange.common.model.UserTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTeamMapper {
    int countByExample(UserTeamExample example);

    int deleteByExample(UserTeamExample example);

    int insert(UserTeam record);

    int insertSelective(UserTeam record);

    List<UserTeam> selectByExample(UserTeamExample example);

    int updateByExampleSelective(@Param("record") UserTeam record, @Param("example") UserTeamExample example);

    int updateByExample(@Param("record") UserTeam record, @Param("example") UserTeamExample example);
}