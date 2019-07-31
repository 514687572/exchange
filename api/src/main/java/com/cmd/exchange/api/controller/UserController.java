package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.annotation.Auth;
import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.OpLogType;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonListResponse;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.CageUtil;
import com.cmd.exchange.common.utils.IPUtils;
import com.cmd.exchange.common.utils.ToolsUtil;
import com.cmd.exchange.common.vo.*;
import com.cmd.exchange.service.GoogleAuthService;
import com.cmd.exchange.service.SendCoinService;
import com.cmd.exchange.service.UserApiService;
import com.cmd.exchange.service.UserService;
import com.github.cage.GCage;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 登录与注册相关接口
 *
 * @author lwx 2017/08/30
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserApiService userApiService;
    @Autowired
    SendCoinService sendCoinService;
    @Autowired
    GoogleAuthService googleAuthService;

    @ApiOperation(value = "获取账户图形验证码", notes = "返回:图片文件")
    @ApiResponse(code = 200, message = "图片文件")
    @GetMapping("word.jpg")
    public ResponseEntity<byte[]> getNew(@ApiParam(value = "国际区号(86,852)", required = false) @RequestParam(required = false) String areaCode,
                                         @ApiParam(value = "手机号码", required = true) @RequestParam(required = true) String mobile,
                                         @RequestParam(value = "type", required = false, defaultValue = "G") @ApiIgnore String type) throws IOException {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(mobile);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        String words = userService.getCaptchaImgWord(areaCode, mobile);

        CageUtil.generate(new GCage(), 1, "cg1", ".jpg", "colding");

        return CageUtil.generate(type, words);
    }

    @ApiOperation("获取短信邮箱验证码")
    @PostMapping("/verify-code")
    public CommonResponse verificationCode(
            @ApiParam(value = "国际区号（86,852）", required = false) @RequestParam(required = false, name = "areaCode") String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true) String phone,
            @ApiParam(value = "图形验证码", required = true) @RequestParam(required = true) String captchaImgCode,
            @ApiParam(value = "类型：注册(REGISTER) 重置登录密码(PASSWORD_FORGET) 设置支付密码(TRANSACTION_PASSWORD)" +
                    " 重置支付密码(TRANSACTION_FORGET) 更换手机(MOBILE_EDIT) 转账(TRANSFER_OUT) " +
                    " 绑定手机号码(MOBILE_BIND) 绑定邮箱(EMAIL_BIND) Google验证器(GOOGLE_BIND)", required = true)
            @RequestParam(required = true, defaultValue = "REGISTER") String type) {
        log.info("获取短信邮箱验证码 :areaCode ->{},phone ->{},captchaImgCode ->{},type ->{}", areaCode, phone, captchaImgCode, type);
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        //不允许重复注册, EMAIL检查
        if (phone.contains("@")) {
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);
            if (type.equalsIgnoreCase("REGISTER")) {
                Assert.check(userService.getUserByEmail(phone) != null, ErrorCode.ERR_USER_EMAIL_EXIST);
            }
        } else {
            if (type.equalsIgnoreCase("REGISTER")) {
                Assert.check(userService.getUserByMobile(phone) != null, ErrorCode.ERR_USER_MOBILE_EXIST);
            }
        }

        userService.getSmsCode(areaCode, phone, type, captchaImgCode);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("验证短信邮件验证码")
    @PostMapping("/check-verify-code")
    public CommonResponse checkVerificationCode(
            @ApiParam(value = "国际区号（86,852）", required = false) @RequestParam(required = false, name = "areaCode") String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true) String phone,
            @ApiParam(value = "图形验证码", required = true) @RequestParam(required = true) String captchaImgCode,
            @ApiParam(value = "类型：注册(REGISTER) 重置登录密码(PASSWORD_FORGET) 设置支付密码(TRANSACTION_PASSWORD)" +
                    " 重置支付密码(TRANSACTION_FORGET) 更换手机(MOBILE_EDIT) 转账(TRANSFER_OUT) " +
                    " 绑定手机号码(MOBILE_BIND) 绑定邮箱(EMAIL_BIND) Google验证器(GOOGLE_BIND)", required = true)
            @RequestParam(required = true, defaultValue = "REGISTER") String type) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        userService.checkSmsCode(areaCode, phone, type, captchaImgCode);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("注册")
    @Auth(requireLogin = false)
    @PostMapping("/register")
    public CommonResponse register(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "国际区号（86,852）", required = false) @RequestParam(required = false, name = "areaCode") String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true, name = "phone") String mobile,
            @ApiParam(value = "验证码", required = true) @RequestParam(required = true) String verificationCode,
            @ApiParam(value = "密码", required = true) @RequestParam(required = true) String password,
            @ApiParam(value = "邀请码", required = false) @RequestParam(required = false) String invite
//            @ApiParam(value = "图形验证码"  ,required = true)  @RequestParam String imageCode
    ) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(mobile);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        mobile = mobile.replace(" ", "");

        User user = new User().setAreaCode(areaCode).setUserName(mobile).setPassword(password).setInviteCode(invite);
        if (mobile.contains("@")) {
            user.setEmail(mobile);
        } else {
            user.setMobile(mobile);

            //手机号检查
            Assert.check(!StringUtils.isNumeric(mobile), ErrorCode.ERR_USER_MOBILE_ERROR);
            if (areaCode.equals("86")) {
                Assert.check(mobile.length() < 11, ErrorCode.ERR_USER_MOBILE_ERROR);
            } else {
                Assert.check(mobile.length() < 5, ErrorCode.ERR_USER_MOBILE_ERROR);
            }
        }
        userService.register(user, invite, verificationCode, mobile);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("设置交易密码")
    @OpLog(type = OpLogType.OP_USER_SET_PAY_PWD, comment = "'payPassword=' + #payPassword")
    @PostMapping("/setting-pay-pwd")
    public CommonResponse setPayPassword(
            @ApiParam(value = "国际区号(86)", required = false) @RequestParam(required = false) String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true, name = "phone") String phone,
            @ApiParam(value = "验证码", required = true) @RequestParam(required = true) String verificationCode,
            @ApiParam(value = "交易密码", required = true) @RequestParam(required = true) String payPassword) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        userService.setPayPassword(ShiroUtils.getUser().getId(), areaCode, phone, verificationCode, payPassword);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("重置交易密码")
    @OpLog(type = OpLogType.OP_USER_EDIT_PAY_PWD)
    @PostMapping("/reset-pay-pwd")
    public CommonResponse resetPayPassword(
            @ApiParam(value = "国际区号", required = false) @RequestParam(required = false) String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true, name = "phone") String phone,
            @ApiParam(value = "验证码", required = true) @RequestParam(required = true) String verificationCode,
            @ApiParam(value = "新密码", required = true) @RequestParam(required = true) String payPassword) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        userService.resetPayPassword(areaCode, phone, verificationCode, payPassword);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("实名认证")
    @PostMapping("/id-card-auth")
    public CommonResponse idCardAuth(
            @ApiParam(value = "国家", required = false) @RequestParam(required = false) String national,
            @ApiParam(value = "证件类型(1-身份证，2-护照)", required = true) @RequestParam(required = true) String idcardType,
            @ApiParam(value = "真实姓名", required = true) @RequestParam(required = true) String realname,
            @ApiParam(value = "证件号码", required = true) @RequestParam(required = true) String idcard,
            @ApiParam(value = "证件手持正面照", required = true) @RequestParam(required = true) String idcardimg1,
            @ApiParam(value = "证件背面照", required = true) @RequestParam(required = true) String idcardimg2,
            @ApiParam(value = "证件背面照", required = false) @RequestParam(required = false) String idcardimg3) {
        User user = new User().setIdCardType(Integer.valueOf(idcardType).intValue()).setRealName(realname).setIdCard(idcard)
                .setIdCardImg1(idcardimg1).setIdCardImg2(idcardimg2).setIdCardImg3(idcardimg3).setId(ShiroUtils.getUser().getId());
        userService.userIdCard(ShiroUtils.getUser().getId(), user);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("获取用户认证信息")
    @GetMapping("/auth-info")
    public CommonResponse<RegisterVo> getUserAuthInfo() {
        return new CommonResponse(userService.getUserAuthInfo(ShiroUtils.getUser().getId()));
    }

    @ApiOperation("登录")
    @OpLog(type = OpLogType.OP_USER_LOGIN)
    @PostMapping("/login")
    public CommonResponse<TokenVo> Login(HttpServletRequest request,
                                         @ApiParam(value = "国家(86)", required = false) @RequestParam(required = false) String areaCode,
                                         @ApiParam(value = "用户名(手机、email)", required = true) @RequestParam(required = true) String phone,
                                         @ApiParam(value = "登录密码", required = true) @RequestParam(required = true) String password,
                                         @ApiParam(value = "图形验证码", required = true) @RequestParam(required = true) String captchaImgCode
    ) {
        //type=1web登录，2手机端登录
        int type = 1;
        String plat = request.getHeader("platform");
        if (plat != null && (plat.equalsIgnoreCase("ios") || plat.equalsIgnoreCase("android"))) {
            type = 2;
        }

        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        String loginIp = IPUtils.getIpAddr(request);

        return new CommonResponse(userService.login(type, areaCode, phone, password, captchaImgCode, loginIp));
    }

    @ApiOperation("退出登录")
    @OpLog(type = OpLogType.OP_USER_LOGOUT)
    @GetMapping("/logout")
    public CommonResponse logout(HttpServletRequest request) {
        //type=1web登录，2手机端登录
        int type = 1;
        String plat = request.getHeader("platform");
        if (plat != null && (plat.equalsIgnoreCase("ios") || plat.equalsIgnoreCase("android"))) {
            type = 2;
        }

        userService.logout(type, ShiroUtils.getUser().getId());
        ShiroUtils.logout();
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("重设登录密码")
    @PostMapping("/reset-pwd")
    public CommonResponse resetPassword(
            @ApiParam(value = "国际区号", required = false) @RequestParam(required = false) String areaCode,
            @ApiParam(value = "手机号码/email", required = true) @RequestParam(required = true, name = "phone") String phone,
            @ApiParam(value = "验证码", required = true) @RequestParam(required = true) String verificationCode,
            @ApiParam(value = "密码", required = true) @RequestParam(required = true) String password
    ) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(phone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        userService.resetPassword(areaCode, phone, verificationCode, password);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("更换手机号")
    @PostMapping("/reset-phone")
    public CommonResponse resetPhone(
            @ApiParam(value = "国际区号", required = false) @RequestParam(required = false) String areaCode,
            @ApiParam(value = "旧手机号码", required = true) @RequestParam(required = true, name = "oldPhone") String oldPhone,
            @ApiParam(value = "验证码", required = true) @RequestParam(required = true) String verificationCode,
            @ApiParam(value = "新手机号码", required = true) @RequestParam(required = true) String newPhone) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(oldPhone);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        userService.updateUserMobile(areaCode, oldPhone, verificationCode, newPhone);
        return new CommonResponse(ErrorCode.ERR_SUCCESS);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user-info")
    public CommonResponse<UserInfoVO> getUserInfo() {
        return new CommonResponse<>(userService.getUserInfo(ShiroUtils.getUser().getId()));
    }

    @ApiOperation("获取登录日志")
    @GetMapping("/login-log")
    public CommonListResponse<UserLog> getUserLoginLog(
            @ApiParam(value = "pageNo 从1开始", required = true) @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize
    ) {
        Page<UserLog> log = userService.getUserLoginLog(ShiroUtils.getUser().getId(), pageNo, pageSize);
        return new CommonListResponse().fromPage(log);
    }

    @ApiOperation("获取国家代码")
    @GetMapping("/national-code")
    public CommonResponse<List<NationalCode>> getNationalCode() {
        return new CommonResponse(userService.getNationalCode());
    }


    @ApiOperation("全局参数配置")
    @GetMapping("/config")
    public CommonResponse<List<Config>> getConfig() {
        return new CommonResponse(userService.getConfig());
    }

    @ApiOperation("根据币种获取转账手续费率")
    @GetMapping("/coin-config")
    public CommonResponse<CoinConfig> getCoinConfig(@RequestParam("coinName") String coinName) {
        return new CommonResponse(userService.getCoinConfig(coinName));
    }


    @ApiOperation("绑定邮箱")
    @PostMapping("/bind-mobile-email")
    public CommonResponse bindEmail(@RequestParam(value = "areaCode", required = false) String areaCode,
                                    @RequestParam("mobile") String mobile,
                                    @RequestParam("verificationCode") String verificationCode) {
        if (areaCode == null)
            areaCode = "86";

        // 如果是已经注册的用户从数据库获取国际区号
        User userByMobile = userService.getUserByMobile(mobile);
        if (null != userByMobile && StringUtils.isNotBlank(userByMobile.getAreaCode())) {
            areaCode = userByMobile.getAreaCode();
        }

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        if (mobile.contains("@")) {
            userService.bindEmail(ShiroUtils.getUser().getId(), areaCode, mobile, verificationCode);
        } else {
            userService.bindMobile(ShiroUtils.getUser().getId(), areaCode, mobile, verificationCode);
        }

        return new CommonResponse();
    }

    @ApiOperation("获取用户奖励记录")
    @GetMapping("/get-reward-log")
    public CommonListResponse<RewardLogVO> getRewardLog(
            @ApiParam(value = "pageNo 从1开始", required = true) @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize
    ) {
        Page<RewardLogVO> rst = userService.getUserRewardLog(ShiroUtils.getUser().getId(), pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation("获取用户挖矿返佣奖励")
    @GetMapping("/get-trade-bonus")
    public CommonListResponse<RewardLogVO> getTradeBonus(
            @ApiParam(value = "pageNo 从1开始", required = true) @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize
    ) {
        Page<RewardLogVO> rst = userService.getTradeBonusLog(ShiroUtils.getUser().getId(), pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation("获取推荐排名, 排名从第一到第三")
    @GetMapping("/reward-ranking")
    public CommonResponse<List<UserRewardVO>> getUserRewardList() {
        return new CommonResponse(userService.getUserRewardList());
    }

    @ApiOperation("获取推荐人列表")
    @GetMapping("/get-referrer")
    public CommonListResponse<ReferrerVO> getReferrers(
            @ApiParam(value = "pageNo 从1开始", required = true) @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        Page<ReferrerVO> rst = userService.getUserReferrers(ShiroUtils.getUser().getId(), pageNo, pageSize);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation("判断Token是否有效")
    @PostMapping("/is-token-valid")
    public CommonResponse<Boolean> isTokenValid(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return new CommonResponse<>(false);
        } else {
            return new CommonResponse<>(userService.isTokenValid(token));
        }
    }

    @ApiOperation("添加api")
    @PostMapping("/api")
    public CommonResponse<UserApiVO> addApi(
            @ApiParam(value = "ip白名单，不同名单用逗号隔开", required = true) @RequestParam(required = false) String whiteIpList,
            @ApiParam(value = "api说明", required = true) @RequestParam(required = false) String comment) {
        UserApi userApi = userApiService.addApi(ShiroUtils.getUser().getId(), whiteIpList, comment);
        UserApiVO vo = new UserApiVO();
        BeanUtils.copyProperties(userApi, vo);
        return new CommonResponse<UserApiVO>(vo);
    }

    @ApiOperation("删除api")
    @DeleteMapping("/api")
    public CommonResponse<String> deleteApi(
            @ApiParam(value = "api记录的id", required = true) @RequestParam Integer id) {
        userApiService.deleteApi(ShiroUtils.getUser().getId(), id);
        return new CommonResponse<>();
    }

    @ApiOperation("修改api，只能改白名单和说明")
    @PutMapping("/api")
    public CommonResponse<ReferrerVO> updateApiWhiteIpList(
            @ApiParam(value = "api记录id", required = true) @RequestParam Integer id,
            @ApiParam(value = "ip白名单，不同名单用逗号隔开", required = true) @RequestParam(required = false) String whiteIpList,
            @ApiParam(value = "api说明", required = true) @RequestParam(required = false) String comment) {
        userApiService.updateApiWhiteIpList(ShiroUtils.getUser().getId(), id, whiteIpList, comment);
        return new CommonResponse();
    }

    @ApiOperation("刷新api（重新生成key和secret）")
    @PostMapping("/api/refresh")
    public CommonResponse<String> refreshApi(
            @ApiParam(value = "api记录的id", required = true) @RequestParam Integer id) {
        userApiService.refreshApi(ShiroUtils.getUser().getId(), id);
        return new CommonResponse();
    }

    @ApiOperation("查询api列表")
    @GetMapping("/api")
    public CommonListResponse<ReferrerVO> getApiList(
            @ApiParam(value = "pageNo 从1开始", required = true) @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        UserApiVOReqVO req = new UserApiVOReqVO();

        req.setUserId(ShiroUtils.getUser().getId());
        req.setPageNo(pageNo).setPageSize(pageSize);

        Page<UserApiVO> rst = userApiService.getUserApiList(req);
        return new CommonListResponse().fromPage(rst);
    }

    @ApiOperation("查询用户邀请码")
    @GetMapping("/api/getInviteCode")
    public CommonResponse<String> getInviteCode(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId) {
        User user = userService.getUserByUserId(userId);
        return new CommonResponse(user.getInviteCode());
    }

    @ApiOperation("修改用户头像")
    @PostMapping("/image/modifyAvatar")
    public CommonResponse<String> modifyAvatar(@ApiParam(value = "用户头像地址", required = true) @RequestParam String image) {
        userService.modifyAvatar(ShiroUtils.getUser().getId(), image);
        return new CommonResponse();
    }



}
