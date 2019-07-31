package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.ArticleStatus;
import com.cmd.exchange.common.enums.ArticleType;
import com.cmd.exchange.common.model.ProjectIntroduction;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.ProjectIntroductionVo;
import com.cmd.exchange.service.ProjectIntroductionService;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "项目介绍管理模块")
@RestController
@RequestMapping("/projectIntroduction")
public class ProjectIntroductionController {
    @Autowired
    private ProjectIntroductionService projectIntroductionService;

    @ApiOperation(value = "添加项目介绍")
    @PostMapping(value = "/addProjectIntroduction")
    public CommonResponse addProjectIntroduction(@Valid @RequestBody ProjectIntroductionVo projectIntroductionVo) {
        AdminEntity creator = ShiroUtils.getUser();
        projectIntroductionVo.setCreatorId(creator.getId());
        int i = projectIntroductionService.addProjectIntroduction(projectIntroductionVo);
        if (i > 0) {
            return new CommonResponse(ErrorCode.ERR_SUCCESS);
        }
        return new CommonResponse(ErrorCode.ERR_RECORD_UPDATE);
    }

    @ApiOperation(value = "删除项目介绍")
    @PostMapping(value = "/deleteProjectIntroduction")
    public CommonResponse deleteProjectIntroduction(@Valid @RequestParam Integer id) {
        projectIntroductionService.deleteProjectIntroduction(id);
        return new CommonResponse();
    }

    /*@ApiOperation(value = "修改项目介绍")
    @PutMapping(value = "/updateProjectIntroduction")
    public CommonResponse updateProjectIntroduction(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "文本内容", required = true) @RequestParam(required = true) String content,
            @ApiParam(value = "状态", required = true) @RequestParam(required = true) Integer status) {
        projectIntroductionService.updateProjectIntroduction(id, content, status);
        return new CommonResponse();
    }*/
    @ApiOperation(value = "修改项目介绍")
    @PutMapping(value = "/updateProjectIntroduction")
    public CommonResponse updateProjectIntroduction(@Valid @RequestBody ProjectIntroductionVo projectIntroductionVo) {
        projectIntroductionService.updateProjectIntroduction(projectIntroductionVo);
        return new CommonResponse();
    }

    @ApiOperation(value = "获取项目介绍列表")
    @GetMapping(value = "/getProjectIntroductionList")
    public CommonResponse getProjectIntroductionList(
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页码") @RequestParam Integer pageSize) {
        List<ProjectIntroduction> projectIntroductionList = projectIntroductionService.getProjectIntroductionList(null,pageNo,pageSize);
        return new CommonResponse(projectIntroductionList);
    }

}
