package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ProjectIntroduction;
import com.cmd.exchange.common.model.ProjectIntroductionExample;
import java.util.List;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ProjectIntroductionMapper {
    int countByExample(ProjectIntroductionExample example);

    int deleteByExample(ProjectIntroductionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectIntroduction record);

    int insertSelective(ProjectIntroduction record);

    List<ProjectIntroduction> selectByExampleWithBLOBs(ProjectIntroductionExample example);

    List<ProjectIntroduction> selectByExample(ProjectIntroductionExample example);

    ProjectIntroduction selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectIntroduction record, @Param("example") ProjectIntroductionExample example);

    int updateByExampleWithBLOBs(@Param("record") ProjectIntroduction record, @Param("example") ProjectIntroductionExample example);

    int updateByExample(@Param("record") ProjectIntroduction record, @Param("example") ProjectIntroductionExample example);

    int updateByPrimaryKeySelective(ProjectIntroduction record);

    int updateByPrimaryKeyWithBLOBs(ProjectIntroduction record);

    int updateByPrimaryKey(ProjectIntroduction record);

    Page<ProjectIntroduction> getProjectIntroductionList(@Param("type") Integer type, @Param("status") Integer status, RowBounds rowBounds);
}