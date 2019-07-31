package com.cmd.exchange.admin.controller;

import com.cmd.exchange.admin.vo.UserExportVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.excel.ExcelGenerator;
import com.cmd.exchange.common.excel.ReportColumn;
import com.cmd.exchange.common.excel.ReportData;
import com.cmd.exchange.common.excel.ReportDefinition;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.CageUtil;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.AddUserUpdateRecVO;
import com.cmd.exchange.common.vo.TopologyVO;
import com.cmd.exchange.common.vo.UpdateUserVO;
import com.cmd.exchange.common.vo.UserInfoVO;
import com.cmd.exchange.service.UserRecommendService;
import com.cmd.exchange.service.UserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录与注册相关接口
 *
 * @author lwx 2017/08/30
 */
@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRecommendService userRecommendService;

    @ApiOperation(value = "获取账户图形验证码", notes = "返回:图片文件")
    @ApiResponse(code = 200, message = "图片文件")
    @GetMapping("word.jpg")
    public ResponseEntity<byte[]> getNew(@ApiParam(value = "国际区号(86,852)", required = false) @RequestParam(required = false) String areaCode,
                                         @ApiParam(value = "手机号码", required = true) @RequestParam(required = true) String mobile,
                                         @RequestParam(value = "type", required = false, defaultValue = "G") @ApiIgnore String type) {
        if (areaCode == null)
            areaCode = "86";

        String words = userService.getCaptchaImgWord(areaCode, mobile);
        return CageUtil.generate(type, words);
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("")
    public CommonListResponse<UserInfoVO> getNew(@ApiParam(value = "推荐人手机号码", required = false) @RequestParam(required = false) String referrerMobile,
                                                 @ApiParam(value = "手机号码", required = false) @RequestParam(required = false) String mobile,
                                                 @ApiParam(value = "玩家分组 GROUP_TYPE_BASE:无分组， GROUP_TYPE_A：A组，GROUP_TYPE_B：B组 。。。", required = false) @RequestParam(required = false) String groupType,
                                                 @ApiParam(value = "实名状态 0：没有实名验证通过，1： 已经实名验证通过，2：已经提交实名验证，待验证，3：实名验证失败", required = false) @RequestParam(required = false) Integer authStatus,
                                                 @ApiParam(value = "用户状态 1:禁用 0：启用", required = false) @RequestParam(required = false) Integer status,
                                                 @ApiParam(value = "用户邮箱", required = false) @RequestParam(required = false) String email,
                                                 @ApiParam(value = "用户真实姓名", required = false) @RequestParam(required = false) String realName,
                                                 @ApiParam(value = "积分下限", required = false) @RequestParam(required = false) BigDecimal integrationMin,
                                                 @ApiParam(value = "积分上限", required = false) @RequestParam(required = false) BigDecimal integrationMax,
                                                 @ApiParam(value = "分页参数， 从1开始", required = true) @RequestParam(required = true) Integer pageNo,
                                                 @ApiParam(value = "每页记录数", required = true) @RequestParam(required = true) Integer pageSize
    ) {

        Page<UserInfoVO> userList = userService.getUserList(referrerMobile, mobile, authStatus, status, groupType, email, realName, integrationMin, integrationMax, pageNo, pageSize);

        return CommonListResponse.fromPage(userList);
    }

    @ApiOperation(value = "导出用户列表")
    @GetMapping("export")
    public void exportUser(@ApiParam(value = "推荐人手机号码", required = false) @RequestParam(required = false) String referrerMobile,
                           @ApiParam(value = "手机号码", required = false) @RequestParam(required = false) String mobile,
                           @ApiParam(value = "玩家分组 GROUP_TYPE_BASE:无分组， GROUP_TYPE_A：A组，GROUP_TYPE_B：B组 。。。", required = false) @RequestParam(required = false) String groupType,
                           @ApiParam(value = "实名状态", required = false) @RequestParam(required = false) Integer authStatus,
                           @ApiParam(value = "用户邮箱", required = false) @RequestParam(required = false) String email,
                           @ApiParam(value = "用户状态 1:禁用 0：启用", required = false) @RequestParam(required = false) Integer status, HttpServletResponse response
    ) throws IllegalAccessException, NoSuchFieldException, IOException {

        Page<UserInfoVO> userList = userService.getUserList(referrerMobile, mobile, authStatus, status, groupType, email, null,null,null, 1, Integer.MAX_VALUE);

        List<UserExportVO> voList = new ArrayList();
        for (UserInfoVO u : userList) {
            voList.add(UserExportVO.fromModel(u));
        }


        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户列表", "utf-8") + ".xls");

        ExcelGenerator generator = new ExcelGenerator();
        ReportDefinition definition = new ReportDefinition("用户列表");
        definition.addColumn(new ReportColumn("ID", "id"));
        definition.addColumn(new ReportColumn("区号", "areaCode"));
        definition.addColumn(new ReportColumn("手机号", "mobile"));
        definition.addColumn(new ReportColumn("分享码", "inviteCode"));
        definition.addColumn(new ReportColumn("身份证", "idCardUploadString"));
        definition.addColumn(new ReportColumn("真实姓名", "realName"));
        definition.addColumn(new ReportColumn("分组", "groupName"));
        definition.addColumn(new ReportColumn("实名认证", "authStatusString"));
        definition.addColumn(new ReportColumn("注册时间", "registerTime"));
        definition.addColumn(new ReportColumn("推荐用户手机", "referrer"));
        definition.addColumn(new ReportColumn("状态", "statusString"));

        ReportData reportData = new ReportData(definition, voList);
        generator.addSheet(reportData);
        generator.write(response.getOutputStream());

        response.getOutputStream().flush();
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("detail")
    public CommonResponse<User> getUserDetail(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId) {
        User user = userService.getUserDetail(userId);
        return new CommonResponse(user);
    }

    @ApiOperation("重置交易密码")
    // @OpLog(type = OpLogType.OP_USER_SET_PAY_PWD, comment = "")
    @PostMapping("/pay-password")
    public CommonResponse setPayPassword(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true) Integer userId) {
        userService.resetPayPassword(userId, EncryptionUtil.MD5("a123456"));
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("通过用户名获取用户")
    @PostMapping("get-user-by-user-name")
    public CommonResponse getUserByUserName(
            @ApiParam(value = "用户id", required = true) @RequestBody(required = true) List<String> userName
    ) {
        List<User> user = userService.adminGetUserByUserName(userName);
        return new CommonResponse(user);
    }

    @ApiOperation("重置登录密码")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PostMapping("/password")
    public CommonResponse setLoginPassword(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true) Integer userId) {
        userService.resetPassword(userId, EncryptionUtil.MD5("a123456" + "5466f6e5160010ce5d9d7e0b7dd3420d"));
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("更新用户信息")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PutMapping("")
    public CommonResponse updateUser(
            @RequestBody UpdateUserVO updateUserVO
    ) {
        userService.updateUser(updateUserVO);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }
    @ApiOperation("启用用户")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PostMapping("enable")
    public CommonResponse enableUser(
            @ApiParam(value = "用户id数组", required = true) @RequestBody(required = true) List<Integer> userIds
    ) {
        userService.updateUserStatus(userIds, 0);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("禁用用户")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PostMapping("disable")
    public CommonResponse disable(
            @ApiParam(value = "用户id数组", required = true) @RequestBody(required = true) List<Integer> userIds
    ) {
        userService.updateUserStatus(userIds, 1);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("审核通过")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PostMapping("id-card-pass")
    public CommonResponse idCardPass(
            @ApiParam(value = "用户id数组", required = true) @RequestBody(required = true) List<Integer> userIds
    ) {
        userService.updateUserIdCardStatus(userIds, 1);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("审核未通过")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @PostMapping("id-card-deny")
    public CommonResponse idCardDeny(
            @ApiParam(value = "用户id数组", required = true) @RequestBody(required = true) List<Integer> userIds
    ) {
        userService.updateUserIdCardStatus(userIds, 3);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("修改一个或者多个玩家玩家分组")
    //@OpLog(type = OpLogType.OP_USER_RESET_PWD, comment = "")
    @GetMapping("update-group-type")
    public CommonResponse updateGroupType(@ApiParam(value = "用户id数组", required = true) @RequestParam(required = true) List<Integer> userIds,
                                          @ApiParam(value = "用户分组Type:0无属性 ，A,B,C,D", required = true) @RequestParam(required = true) String groupType) {
        Assert.check(userIds == null || userIds.size() == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(groupType == null || groupType == "", ErrorCode.ERR_RECORD_NOT_EXIST);
        userService.updateUserGroupType(userIds, groupType);

        return new CommonResponse(ErrorCode.ERR_SUCCESS);


    }

    @ApiOperation(value = "玩家拓扑图")
    @GetMapping("/topology")
    public CommonResponse getTopology(@ApiParam(value = "用户Id", required = true) @RequestParam(required = true) Integer userId) {
        Assert.check(userId == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        TopologyVO topologyVO = new TopologyVO();
        List<TopologyVO> topologyVOList = new ArrayList<TopologyVO>();
        User user = userService.getUserByUserId(userId);
        topologyVO.setUserId(user.getId());
        topologyVO.setMobile(user.getMobile());
        topologyVO.setUserEmail(user.getEmail());
        topologyVO.setRegisterTime(user.getRegisterTime());
        int i = userService.getSubordinateBool(userId);
        if (i > 0) {
            topologyVO.setSubordinateBool(true);
            List<TopologyVO> topologyVOList1 = userService.getTopologyByUserId(userId);
            for (TopologyVO vo : topologyVOList1) {
                int k = userService.getSubordinateBool(vo.getUserId());
                if (k > 0) {
                    vo.setSubordinateBool(true);
                }
                topologyVOList.add(vo);
            }
        }
        topologyVO.setTopologyVOList(topologyVOList);
        return new CommonResponse(topologyVO);
    }

    @ApiOperation("新增用户信息，更新层级关系信息")
    @GetMapping("/addUserUpdateRec")
    public CommonResponse addUserUpdateRec(
          //  @RequestBody AddUserUpdateRecVO addUserUpdateRecVO
            @ApiParam(value = "插入电话号码", required = false) @RequestParam(required = false) String mobile,
            @ApiParam(value = "下级电话号码", required = false) @RequestParam(required = false) String recMobile,
            @ApiParam(value = "上级电话号码", required = false) @RequestParam(required = false) String oldMobile
            ) {

        userRecommendService.intertUserRecommend(recMobile,mobile,oldMobile);

        return new CommonResponse();
    }
}
