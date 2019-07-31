package com.cmd.exchange.admin.controller;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserGroupConfig;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.UserInfoVO;
import com.cmd.exchange.service.UserGroupConfigService;
import com.cmd.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户分组配置模块")
@RestController
@RequestMapping("/userConfig")
public class UserGroupConfigController {

    @Autowired
    private UserGroupConfigService userGroupConfigService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取分组列表")
    @GetMapping("getUserGroupConfigList")
    public CommonResponse getUserGroupConfigList() {
        List<UserGroupConfig> list = userGroupConfigService.getUserGroupConfigList();
        return new CommonResponse(list);
    }

    @ApiOperation(value = "获取分组列表,不包含未分组的")
    @GetMapping("getUserGroups")
    public CommonResponse getUserGroups() {
        List<UserGroupConfig> list = userGroupConfigService.getUserGroupConfigList();
        List<UserGroupConfig> list2 = new ArrayList<UserGroupConfig>();
        if (list != null && list.size() > 0) {

            for (UserGroupConfig data : list) {
                if (!data.getGroupType().equals("GROUP_TYPE_BASE")) {
                    list2.add(data);
                }
            }
        }

        return new CommonResponse(list2);
    }

    @ApiOperation(value = "增加分组")
    @PostMapping("addUserGroupConfig")
    public CommonResponse addUserGroupConfigById(@RequestBody UserGroupConfig userGroupConfig) {

        if (userGroupConfig != null) {
            UserGroupConfig userGroupConfis = new UserGroupConfig();
            userGroupConfis.setGroupName(userGroupConfig.getGroupName());
            userGroupConfis.setGroupType("GROUP_TYPE_" + userGroupConfig.getGroupName());
            int i = userGroupConfigService.addUserGroupConfig(userGroupConfis);
            if (i > 0) {
                return new CommonResponse((ErrorCode.ERR_SUCCESS));
            }
            return new CommonResponse((ErrorCode.ERR_RECORD_UPDATE));
        } else {
            return new CommonResponse((ErrorCode.ERR_PARAM_ERROR));
        }

    }


    @ApiOperation(value = "修改分组名字")
    @PutMapping("updateUserGroupConfigNameById")
    public CommonResponse updateUserGroupConfigById(@RequestBody UserGroupConfig userGroupConfig) {

        int i = userGroupConfigService.updateUserGroupConfigNameById(userGroupConfig);
        if (i > 0) {
            return new CommonResponse((ErrorCode.ERR_SUCCESS));
        }
        return new CommonResponse((ErrorCode.ERR_RECORD_UPDATE));
    }

    @ApiOperation(value = "修改分组状态态")
    @PutMapping("updateUserGroupConfigStatusById")
    public CommonResponse updateUserGroupConfigStatusById(@RequestBody UserGroupConfig userGroupConfig) {

        if (userGroupConfig.getStatus() == 1) {
            UserGroupConfig userGroupConfig1 = userGroupConfigService.getUserGroupConfigById(userGroupConfig.getId());
            int count = userService.getUserCountByGroupType(userGroupConfig.getGroupType());
            if (count == 0) {
                int i = userGroupConfigService.updateUserGroupConfigStatusById(userGroupConfig);
                return i > 0 ? new CommonResponse((ErrorCode.ERR_SUCCESS)) : new CommonResponse((ErrorCode.ERE_GROUP_STATUS_NOT_WARNING));

            }
            return new CommonResponse((ErrorCode.ERE_GROUP_STATUS_NOT_WARNING), "存在用户使用，无法禁用");
        } else {
            int i = userGroupConfigService.updateUserGroupConfigStatusById(userGroupConfig);
            return i > 0 ? new CommonResponse((ErrorCode.ERR_SUCCESS)) : new CommonResponse((ErrorCode.ERE_GROUP_STATUS_NOT_WARNING));
        }
    }

    @ApiModelProperty(value = "获取分组详情")
    @GetMapping("getUserGroupConfigById")
    public CommonResponse updateUserGroupConfigStatusById(@ApiParam(value = "分组Id", required = true) @RequestParam(required = true) Integer id) {
        return new CommonResponse(userGroupConfigService.getUserGroupConfigById(id));
    }

    @ApiModelProperty(value = "删除分组")
    @GetMapping("delUserGroupConfigById")
    public CommonResponse setPayPassword(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "groupType", required = true) @RequestParam(required = true) String groupType) {
        int userCount = userService.getUserCountByGroupType(groupType);
        if (userCount > 0) {
            return new CommonResponse((ErrorCode.ERE_GROUP_USING), "有用户使用分组中,无法删除");
        }
        userGroupConfigService.delUserGroupConfig(id);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }
}
