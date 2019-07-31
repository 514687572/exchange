package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.ProjectIntroductionMapper;
import com.cmd.exchange.common.model.ProjectIntroduction;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.ProjectIntroductionVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ProjectIntroductionService {
    @Autowired
    private ProjectIntroductionMapper projectIntroductionMapper;

    @Transactional
    public int addProjectIntroduction(ProjectIntroductionVo projectIntroductionVo) {
        projectIntroductionVo.setCreateTime(new Date());
        projectIntroductionVo.setUpdateTime(new Date());
        return projectIntroductionMapper.insertSelective(projectIntroductionVo.toModel());
    }

    public void deleteProjectIntroduction(Integer id) {
        int count = projectIntroductionMapper.deleteByPrimaryKey(id);
        Assert.check(count == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
    }

    @Transactional
    public void updateProjectIntroduction(ProjectIntroductionVo projectIntroductionVo) {
        ProjectIntroduction projectIntroduction = projectIntroductionMapper.selectByPrimaryKey(projectIntroductionVo.getId());
        Assert.check(projectIntroduction == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        projectIntroduction.setContent(projectIntroductionVo.getContent());
        projectIntroduction.setStatus(projectIntroductionVo.getStatus());
        projectIntroduction.setType(projectIntroductionVo.getType());
        projectIntroduction.setUpdateTime(new Date());
        projectIntroductionMapper.updateByPrimaryKeySelective(projectIntroduction);
    }

    public List<ProjectIntroduction> getProjectIntroductionList(Integer type, Integer pageNo, Integer pageSize) {
        return projectIntroductionMapper.getProjectIntroductionList(type,null, new RowBounds(pageNo, pageSize));
    }
}
