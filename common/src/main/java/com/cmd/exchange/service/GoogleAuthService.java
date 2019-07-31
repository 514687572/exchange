package com.cmd.exchange.service;


import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.enums.SmsCaptchaType;
import com.cmd.exchange.common.mapper.GoogleSecretMapper;
import com.cmd.exchange.common.mapper.UserMapper;
import com.cmd.exchange.common.model.GoogleAuth;
import com.cmd.exchange.common.model.GoogleSecret;
import com.cmd.exchange.common.model.SmsCaptcha;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.GoogleAuthenticatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GoogleAuthService {

    @Autowired
    GoogleSecretMapper googleSecretMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SmsService smsService;

    public GoogleSecret getGoogleSecret(int userId) {
        return googleSecretMapper.getGoogleSecret(userId);
    }

    public Boolean isBindGoogleSecret(String areaCode, String mobile) {
        User user = null;
        if (mobile.contains("@")) {
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobile(mobile);
        }
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(user.getId());
        if (googleSecret != null && googleSecret.getStatus() == 1) {
            return true;
        }
        return false;
    }

    public void bindGoogleSecret(int userId, String secret, String code, String areaCode, String mobile, String veryCode) {
        Assert.check(!GoogleAuthenticatorUtil.authcode(code, secret), ErrorCode.ERR_GOOGLE_SECRET_ERROR);

        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.GOOGLE_BIND.getValue(), veryCode), ErrorCode.ERR_USER_SMSCODE_ERROR);

        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(userId);
        if (googleSecret == null) {
            googleSecretMapper.add(new GoogleSecret().setUserId(userId).setSecret(secret).setStatus(1));
        } else {
            googleSecretMapper.updateGoogleSecret(new GoogleSecret().setId(googleSecret.getId()).setSecret(secret).setStatus(1));
        }
    }

    //只验证短信，邮件验证码，验证老的secretCode
    public void updateGoogleSecret(int userId, String newSecret, String newSecretCode, String oldSecretCode, String areaCode, String mobile, String veryCode) {
        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(userId);
        Assert.check(googleSecret == null, ErrorCode.ERR_GOOGLE_SECRET_ERROR);

        //Assert.check(!GoogleAuthenticatorUtil.authcode(googleSecret.getSecret(), oldSecretCode), ErrorCode.ERR_GOOGLE_SECRET_ERROR);
        Assert.check(!GoogleAuthenticatorUtil.authcode(newSecretCode, newSecret), ErrorCode.ERR_GOOGLE_SECRET_ERROR);
        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.GOOGLE_BIND.getValue(), veryCode), ErrorCode.ERR_USER_SMSCODE_ERROR);

        googleSecretMapper.updateGoogleSecret(new GoogleSecret().setId(googleSecret.getId()).setSecret(newSecret).setStatus(1));
    }

    //关闭google验证
    public void closeGoogleSecret(int userId, String secretCode, String areaCode, String mobile, String veryCode) {
        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(userId);
        Assert.check(googleSecret == null, ErrorCode.ERR_GOOGLE_SECRET_ERROR);

        Assert.check(!GoogleAuthenticatorUtil.authcode(secretCode, googleSecret.getSecret()), ErrorCode.ERR_GOOGLE_SECRET_ERROR);
        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.GOOGLE_BIND.getValue(), veryCode), ErrorCode.ERR_USER_SMSCODE_ERROR);
        googleSecretMapper.updateGoogleSecret(new GoogleSecret().setId(googleSecret.getId()).setStatus(0));
    }

    public void checkGoogleAuthFirst(String areaCode, String mobile, String type, String secretCode) {
        User user = null;
        if (mobile.contains("@")) {
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobile(mobile);
        }
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        SmsCaptchaType captchaType = Assert.assertEnumParam(SmsCaptchaType.class, type.trim(), ErrorCode.ERR_CODE_NOT_SUPPORT, "不支持的验证码类型:" + type);

        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(user.getId());
        if (googleSecret != null && googleSecret.getStatus() == 1) {
            Assert.check(!GoogleAuthenticatorUtil.authcode(secretCode, googleSecret.getSecret()), ErrorCode.ERR_GOOGLE_SECRET_ERROR);
            GoogleAuth googleAuth = googleSecretMapper.getGoogleAuthByMobileAndType(areaCode + mobile, captchaType.getValue());
            if (googleAuth == null) {
                googleSecretMapper.addGoogleAuth(new GoogleAuth().setCode(secretCode)
                        .setMobile(areaCode + mobile).setStatus(1).setType(captchaType.getValue())
                        .setLastTime(new Date(new Date().getTime() + 60 * 1000)));
            } else {
                googleSecretMapper.updateGoogleAuth(new GoogleAuth().setCode(secretCode)
                        .setMobile(areaCode + mobile).setStatus(1).setType(captchaType.getValue())
                        .setLastTime(new Date(new Date().getTime() + 60 * 1000)));
            }
        }
    }

    public void checkGoogleAuthSecond(String areaCode, String mobile, String type) {
        User user = null;
        if (mobile.contains("@")) {
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobile(mobile);
        }
        if (user == null) return;

        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(user.getId());
        if (googleSecret != null && googleSecret.getStatus() == 1) {
            //2分钟有效期
            GoogleAuth googleAuth = googleSecretMapper.getGoogleAuthByMobileAndType(areaCode + mobile, type);
            Assert.check(googleAuth == null, ErrorCode.ERR_GOOGLE_SECRET_ERROR);
            if (googleAuth.getStatus() == 1 && new Date().getTime() - googleAuth.getLastTime().getTime() < 60 * 1000) {
                return;
            }
            Assert.check(true, ErrorCode.ERR_GOOGLE_SECRET_ERROR);
        }
    }

    public void checkGoogleAuthSecond(int userId, String areaCode, String mobile, String type) {
        GoogleSecret googleSecret = googleSecretMapper.getGoogleSecret(userId);
        if (googleSecret != null && googleSecret.getStatus() == 1) {
            //2分钟有效期
            GoogleAuth googleAuth = googleSecretMapper.getGoogleAuthByMobileAndType(areaCode + mobile, type);
            Assert.check(googleAuth == null, ErrorCode.ERR_GOOGLE_SECRET_ERROR);
            if (googleAuth.getStatus() == 1 && new Date().getTime() - googleAuth.getLastTime().getTime() < 60 * 1000) {
                return;
            }
            Assert.check(true, ErrorCode.ERR_GOOGLE_SECRET_ERROR);
        }
    }

}
