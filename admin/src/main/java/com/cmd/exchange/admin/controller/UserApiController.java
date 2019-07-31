package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.UserApiStatus;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.common.vo.UserApiVOReqVO;
import com.cmd.exchange.service.UserApiService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户api管理模块")
@RestController
@RequestMapping("/user-api")
public class UserApiController {
    @Autowired
    private UserApiService userApiService;

    @ApiOperation(value = "查询用户API列表：")
    @GetMapping("")
    public CommonListResponse<UserApiVO> getUserApiList(
            @Valid UserApiVOReqVO reqVO
    ) {
        Page<UserApiVO> userList = userApiService.getUserApiList(reqVO);

        return CommonListResponse.fromPage(userList);
    }

    @ApiOperation(value = "禁用指定的api")
    @PostMapping("disable")
    public CommonResponse<UserApiVO> disableUserApi(
            @Valid @RequestBody List<Integer> ids
    ) {

        userApiService.updateUserApiStatus(ids, UserApiStatus.DISABLED);

        return new CommonResponse<>();
    }

    @ApiOperation(value = "禁用指定用户的api")
    @PostMapping("pass")
    public CommonResponse<UserApiVO> approveUserApi(
            @Valid @RequestBody List<Integer> ids
    ) {

        userApiService.updateUserApiStatus(ids, UserApiStatus.PASS);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "禁用指定用户的api")
    @PostMapping("deny")
    public CommonResponse<UserApiVO> denyApi(
            @Valid @RequestBody List<Integer> ids
    ) {

        userApiService.updateUserApiStatus(ids, UserApiStatus.DENY);
        return new CommonResponse<>();
    }

    @ApiOperation(value = "删除指定用户的Api")
    @PostMapping("del")
    public CommonResponse delApi(@Valid @RequestBody List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            return new CommonResponse<>(ErrorCode.ERR_RECORD_UPDATE, "未选择需要删除的用户");
        }
        userApiService.delUserApiByIds(ids);
        return new CommonResponse<>(ErrorCode.ERR_SUCCESS);
    }

}
