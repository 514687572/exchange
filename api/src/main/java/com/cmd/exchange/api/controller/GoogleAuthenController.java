package com.cmd.exchange.api.controller;

import com.cmd.exchange.api.vo.GoogleReqsVO;
;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.GoogleAuthenticatorUtil;
import com.cmd.exchange.common.utils.ToolsUtil;
import com.cmd.exchange.service.GoogleAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "谷歌验证")
@RestController
@RequestMapping("/google_verify")
public class GoogleAuthenController {

    @Autowired
    GoogleAuthService googleAuthService;

    @ApiOperation(value = "获取验证秘钥")
    @GetMapping(value = "getGoogleAuthenSecret")
    public CommonResponse<GoogleReqsVO> getGoogleAuthenSecret(@ApiParam(value = "用户名", required = false) @RequestParam("userName") String userName,
                                                              @ApiParam(value = "邮箱", required = false) @RequestParam("email") String email) {

        GoogleReqsVO vo = new GoogleReqsVO();
        String secret = GoogleAuthenticatorUtil.genSecret(userName, email);
        vo.setSecret(secret);
        vo.setUserName(userName);
        vo.setEmail(email);
        if (userName != "" || email != "" && userName != "") {
            String url = "otpauth://totp/" + userName + "?secret=" + secret;
            vo.setUrl(url);
            System.out.println(url);
            return new CommonResponse(vo);

        } else if (email != "") {
            String url = "otpauth://totp/" + email + "?secret=" + secret;
            vo.setUrl(url);
            System.out.println(url);
            return new CommonResponse(vo);
        }
        return new CommonResponse(ErrorCode.ERR_PARAM_ERROR);
    }

    @ApiOperation(value = "验证google码")
    @GetMapping(value = "verify-code")
    public CommonResponse verifyCode(@ApiParam(value = "areaCode", required = false) @RequestParam(required = false) String areacode,
                                     @ApiParam(value = "手机号/email", required = true) @RequestParam("phone") String phone,
                                     @ApiParam(value = "登录(LOGIN) 重置登录密码(PASSWORD_FORGET) 重置支付密码(TRANSACTION_FORGET) 转账(TRANSFER_OUT)", required = true)
                                     @RequestParam("type") String type,
                                     @ApiParam(value = "验证码", required = true) @RequestParam("code") String code) {
        if (areacode == null)
            areacode = "86";

        if (phone.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(phone), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        Assert.check(code == null || code.length() <= 0, ErrorCode.ERR_PARAM_ERROR);
        googleAuthService.checkGoogleAuthFirst(areacode, phone, type, code);
        return new CommonResponse();
    }

    @ApiOperation(value = "是否绑定google码")
    @PostMapping(value = "isbind-google-code")
    public CommonResponse<Boolean> isBindGoogleCode(@ApiParam(value = "国家区号", required = false) @RequestParam(required = false) String areacode,
                                                    @ApiParam(value = "手机号/email", required = true) @RequestParam(required = true) String mobile) {
        if (areacode == null)
            areacode = "86";

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        return new CommonResponse(googleAuthService.isBindGoogleSecret(areacode, mobile));
    }

    @ApiOperation(value = "绑定google码")
    @PostMapping(value = "bind-google-code")
    public CommonResponse bindGoogleCode(@ApiParam(value = "国家区号", required = false) @RequestParam(required = false) String areacode,
                                         @ApiParam(value = "手机号/email", required = true) @RequestParam(required = true) String mobile,
                                         @ApiParam(value = "短信验证码", required = true) @RequestParam(required = true) String verificationCode,
                                         @ApiParam(value = "google验证秘钥", required = true) @RequestParam(required = true) String secret,
                                         @ApiParam(value = "google验证码", required = true) @RequestParam(required = true) String code) {
        if (areacode == null)
            areacode = "86";

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        googleAuthService.bindGoogleSecret(ShiroUtils.getUser().getId(), secret, code, areacode, mobile, verificationCode);
        return new CommonResponse();
    }

    @ApiOperation(value = "修改google码")
    @PostMapping(value = "update-google-code")
    public CommonResponse updateGoogleCode(@ApiParam(value = "国家区号", required = false) @RequestParam(required = false) String areacode,
                                           @ApiParam(value = "手机号/email", required = true) @RequestParam(required = true) String mobile,
                                           @ApiParam(value = "短信验证码", required = true) @RequestParam(required = true) String verificationCode,
                                           @ApiParam(value = "google验证秘钥", required = true) @RequestParam(required = true) String newSecret,
                                           @ApiParam(value = "google验证码", required = true) @RequestParam(required = true) String newSecretCode) {
        if (areacode == null)
            areacode = "86";

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        googleAuthService.updateGoogleSecret(ShiroUtils.getUser().getId(), newSecret, newSecretCode, "", areacode, mobile, verificationCode);
        return new CommonResponse();
    }

    @ApiOperation(value = "关闭google码")
    @PostMapping(value = "close-google-code")
    public CommonResponse closeGoogleCode(@ApiParam(value = "国家区号", required = false) @RequestParam(required = false) String areacode,
                                          @ApiParam(value = "手机号/email", required = true) @RequestParam(required = true) String mobile,
                                          @ApiParam(value = "短信验证码", required = true) @RequestParam(required = true) String verificationCode,
                                          @ApiParam(value = "google验证码", required = true) @RequestParam(required = true) String secretCode) {
        if (areacode == null)
            areacode = "86";

        if (mobile.contains("@"))
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);

        googleAuthService.closeGoogleSecret(ShiroUtils.getUser().getId(), secretCode, areacode, mobile, verificationCode);
        return new CommonResponse();
    }

}
