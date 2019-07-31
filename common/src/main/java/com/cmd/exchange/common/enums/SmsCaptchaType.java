package com.cmd.exchange.common.enums;

import lombok.Getter;

@Getter
public enum SmsCaptchaType {
    REGISTER("01", "【ETEX】您正在注册新账号，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    PASSWORD_FORGET("02", "【ETEX】您正在进行忘记密码操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    TRANSACTION_PASSWORD("03", "【ETEX】您正在进行设置交易密码操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    TRANSACTION_FORGET("04", "【ETEX】您正在进行修改交易密码操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    MOBILE_EDIT("05", "【ETEX】您正在进行更换手机号码操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    TRANSFER_OUT("06", "【ETEX】您正在进行转账操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    MOBILE_BIND("07", "【ETEX】您正在进行绑定手机号码操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    EMAIL_BIND("08", "【ETEX】您正在进行绑定Email操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    GOOGLE_BIND("09", "【ETEX】您正在进行绑定google验证器操作，验证码%@%。妥善保管，请勿转发，5分钟内有效"),
    LOGIN("10", "【ETEX】您正在进行登录平台操作，验证码%@%。妥善保管，请勿转发，5分钟内有效");

    private String value;
    private String template;

    SmsCaptchaType(String value, String template) {
        this.value = value;
        this.template = template;
    }
}