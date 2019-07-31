package com.cmd.exchange.admin.controller;


import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.admin.service.AdminService;
import com.cmd.exchange.admin.vo.AddAdminVO;
import com.cmd.exchange.admin.vo.LoginInfoVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户状态管理 Controller
 * Created by jerry on 2017/12/29.
 */
@Api(description = "用户-登录&登出", tags = "后台用户模块")
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private AdminService adminService;

    @Autowired
    public AdminUserController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "根据用户Id获取用户详细信息（比如角色等）", notes = "返回:用户信息")
    @GetMapping("{id}")
    public AdminEntity getUser(@PathVariable("id") Integer id) {
        return this.adminService.getUserById(id);
    }

    @ApiOperation(value = "登录", notes = "返回:登录信息")
    @PostMapping("/login")
    public CommonResponse<LoginInfoVO> login(
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true) String username,
            @ApiParam(value = "登录密码", required = true) @RequestParam(required = true) String password) {
        LoginInfoVO vo = this.adminService.login(username, password);
        return new CommonResponse<>(vo);
    }

    @ApiOperation(value = "检查token是否有效, statusCode, 100001:token不存在 100002:token已经过期")
    @GetMapping("/check-token")
    public CommonResponse login(
            @ApiParam(value = "token", required = true) @RequestParam(required = true) String token
    ) {

        return new CommonResponse(this.adminService.checkToken(token));
    }

    @ApiOperation(value = "修改指定用户的密码")
    @PostMapping("update-password")
    public CommonResponse editPassword(@RequestParam String userName, @RequestParam String newPassword) {
        Assert.check(ShiroUtils.getUser() == null, ErrorCode.ERR_USER_NOT_EXIST);
        this.adminService.updatePassword(ShiroUtils.getUser().getId(), userName, newPassword);
        return new CommonResponse();
    }


    @ApiOperation(value = "登出")
    @DeleteMapping("/logout")
    public void logout(@RequestHeader("token") @ApiIgnore String token) {
        this.adminService.logout(token);
    }

    @ApiOperation(value = "添加账号")
    @PostMapping("")
    public CommonResponse add(@RequestBody AddAdminVO adminVO) {
        adminService.add(adminVO);
        return new CommonResponse();
    }

    @ApiOperation(value = "删除一个或多个账号")
    @DeleteMapping("")
    public CommonResponse deleteUser(@RequestBody String[] userIds) {
        Assert.check(userIds == null || userIds.length == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
        adminService.deleteUser(userIds);
        return new CommonResponse();
    }


}
