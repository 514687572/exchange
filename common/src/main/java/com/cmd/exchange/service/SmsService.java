package com.cmd.exchange.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmd.exchange.common.enums.SmsCaptchaType;
import com.cmd.exchange.common.exception.ServerException;
import com.cmd.exchange.common.mapper.SmsMapper;
import com.cmd.exchange.common.model.SmsCaptcha;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.HttpUtil;
import com.cmd.exchange.common.utils.RandomUtil;
import com.cmd.exchange.common.utils.SimpleMailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 短信业务处理类
 * Created by Administrator on 2017/6/18.
 */
@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    private static final String SUCCESS = "0";

    private static final String PARAM = "%@%";

    @Value("${spring.profiles.active}")
    private String profiles;

    @Value("${sms.url}")
    private String url;
    @Value("${sms.account}")
    private String account;
    @Value("${sms.passwd}")
    private String passwd;
    @Value("${sms.from:}")
    private String from;

    @Value("${sms.urlGw:null}")
    private String urlGw = "http://yxsms.uninets.com.cn/Modules/Interface/http/Iservicesbsjy.aspx";
    @Value("${sms.accountGw:null}")
    private String accountGw = "szywgj";
    @Value("${sms.passwdGw:null}")
    private String passwdGw = "CML7OyrSpP";

    @Value("${sms.expire:60}")
    private int expire;
    @Value("${sms.ignore:false}")
    private boolean ignoreCheck; //是否校验短信验证码

    @Value("${email.serverHost:null}")
    private String serverHost;
    @Value("${email.serverPort:null}")
    private String serverPort;
    @Value("${email.userName:null}")
    private String userName;
    @Value("${email.userPassword:null}")
    private String userPassword;
    @Value("${email.fromAddress:null}")
    private String fromAddress;
    @Value("${email.validate:true}")
    private boolean validate;
    @Value("${email.starttls:false}")
    private boolean starttls;

    private static final int SMS_CAPTCHA_LENGTH = 6;

    @Autowired
    private CaptchaImgService captchaImgService;
    @Autowired
    private SmsMapper smsMapper;

    //获取图形验证码
    public String getCaptchaImgWord(String nationalCode, String mobile) {
        return captchaImgService.getWord(nationalCode, mobile);
    }

    //验证图形验证码
    public void CheckCaptchaImgWord(String nationalCode, String mobile, String words) {
        if (ignoreCheck) {
            logger.info("ignore captcha checking.");
            return;
        }
        captchaImgService.check(nationalCode, mobile, words);
    }

    //发送短信验证码
    public void sendSmsCaptcha(String nationalCode, String mobile, String captchaTypeCode, String captchaImgWord) {
        if (ignoreCheck) {
            logger.info("ignore sms message sending.");
            return;
        }
        // 1.检查图形验证码
        this.captchaImgService.check(nationalCode, mobile, captchaImgWord);

        SmsCaptchaType captchaType = Assert.assertEnumParam(SmsCaptchaType.class, captchaTypeCode.trim(), 500, "不支持的验证码类型:" + captchaTypeCode);

        // 2.检查是否连续发送短信验证码
        Assert.check(!isAllowCaptcha(nationalCode, mobile, captchaTypeCode), 500, "1分钟内只允许请求1条短信验证码");

//        // 开发环境使用
//        if (profiles.contains("dev") || profiles.contains("test")) {
//            SmsCaptcha devCaptcha = new SmsCaptcha().setMobile(nationalCode + mobile).setType(captchaType.getValue()).setCode("123456").setLastTime(new Date(new Date().getTime() + expire * 1000));
//            updateMobileCaptcha(devCaptcha); // 存储短信记录
//            return;
//        }

        // 3.生成新的短信验证码
        String captcha = RandomUtil.getCode(SMS_CAPTCHA_LENGTH);
        SmsCaptcha smsCaptcha = new SmsCaptcha().setMobile(nationalCode + mobile).setType(captchaType.getValue()).setCode(captcha).setLastTime(new Date(new Date().getTime() + expire * 1000));

        try {
            // 4.获得短信内容
            String msg = this.getCaptchaMsg(captchaType, captcha);

            // 5.调用邮件,短信服务
            if (mobile.contains("@") && captchaType == SmsCaptchaType.REGISTER) {
                //只有邮件注册单独发送
                sendEmailRegister(nationalCode, mobile, captcha);
            } else if (mobile.contains("@")) {
                sendEmail(nationalCode, mobile, msg);
            } else {
                sendSms(nationalCode, mobile, msg);
            }

            // 6.存储短信记录
            updateMobileCaptcha(smsCaptcha);
        } catch (Exception e) {
            throw new ServerException(500, "调用发送短信接口错误");
        }
    }

    private void updateMobileCaptcha(SmsCaptcha smsCaptcha) {
        SmsCaptcha sms = smsMapper.getSmsCaptchaByMobile(smsCaptcha.getMobile(), smsCaptcha.getType());
        if (sms == null) {
            smsMapper.addSmsCaptcha(smsCaptcha.getMobile(), smsCaptcha.getType(), smsCaptcha.getCode(), smsCaptcha.getLastTime());
        } else {
            smsMapper.updateSmsCaptcha(smsCaptcha.getMobile(), smsCaptcha.getType(), smsCaptcha.getCode(), smsCaptcha.getLastTime());
        }
    }

    /**
     * 获取验证码短信
     *
     * @param type    验证码类型：SmsCaptchaType
     * @param captcha 6位数字验证码
     * @return String 消息内容
     */
    private String getCaptchaMsg(SmsCaptchaType type, String captcha) {
        StringBuffer template = new StringBuffer(type.getTemplate());
        return replaceParam(template, captcha).toString();
    }

    private StringBuffer replaceParam(StringBuffer template, String paramVal) {
        int idx = template.indexOf(PARAM);
        return template.replace(idx, idx + PARAM.length(), paramVal);
    }

    //检查是否允许发送短信验证码(1分钟以内只能发送一条短信)
    private boolean isAllowCaptcha(String nationalCode, String mobile, String smsCaptchaType) {
        SmsCaptcha smsCaptcha = smsMapper.getSmsCaptchaByMobile(nationalCode + mobile, smsCaptchaType);
        if (smsCaptcha != null) {
            if (new Date().getTime() - smsCaptcha.getLastTime().getTime() < 60 * 1000) {
                return false;
            }
        }
        return true;
    }

    //检查手机短信验证码是否有效
    public boolean checkCaptcha(String nationalCode, String mobile, String smsCaptchaType, String captcha) {
        if (ignoreCheck) {
            logger.info("ignore sms message verify.");
            return true;
        }

        SmsCaptcha smsCaptcha = smsMapper.getSmsCaptcha(nationalCode + mobile, smsCaptchaType, captcha);
        if (smsCaptcha == null) {
            return false;
        }
        if (new Date().getTime() - smsCaptcha.getLastTime().getTime() < expire * 1000) {
            return true;
        }
        return false;
    }

    //两步验证（业务和验证分离）
    // 第一步OK，设置状态
    public boolean checkCaptchaFirst(String nationalCode, String mobile, String smsCaptchaType, String captcha) {
        SmsCaptcha smsCaptcha = smsMapper.getSmsCaptcha(nationalCode + mobile, smsCaptchaType, captcha);
        if (smsCaptcha == null) {
            return false;
        }
        if (new Date().getTime() - smsCaptcha.getLastTime().getTime() < expire * 1000) {
            smsMapper.updateSmsCaptchaStatus(nationalCode + mobile, smsCaptchaType);
            return true;
        }
        return false;
    }

    // 第二步，检查状态
    public boolean checkCaptchaSecond(String nationalCode, String mobile, String smsCaptchaType) {
        SmsCaptcha smsCaptcha = smsMapper.getSmsCaptchaByMobile(nationalCode + mobile, smsCaptchaType);
        if (smsCaptcha == null) {
            return false;
        }
        if (smsCaptcha.getStatus() == 1 && new Date().getTime() - smsCaptcha.getLastTime().getTime() < expire * 1000) {
            return true;
        }
        return false;
    }

    //发送paasoocn平台的短信
    public void sendPaasoocnSms(String nationalCode, String mobile, String msg) {
        if (nationalCode == null) nationalCode = "";
        // https://www.paasoocn.com/api.html
        String to = nationalCode.replaceFirst("^0*", "") + mobile.replaceFirst("^0*", "");
        Map<String, Object> m = new HashMap<>();
        m.put("key", this.account);
        m.put("secret", this.passwd);
        if (from != null && from.length() > 0) {
            m.put("from", this.from);
        } else {
            m.put("from", this.account);
        }
        m.put("to", to);
        m.put("text", msg);
        logger.info("请求参数为:to=" + m.get("to") + ",nationalCode=" + nationalCode + ",mobile=" + mobile + ",msg=" + m.get("text")
                + ",key=" + m.get("key") + ",secret=" + m.get("secret") + ",from=" + m.get("from"));
        try {
            String result = HttpUtil.HttpPost(url, m);
            logger.info("返回参数为:" + result);

            JSONObject jsonObject = JSON.parseObject(result);
            String code = jsonObject.get("status").toString();
            String msgid = jsonObject.get("messageid").toString();
            String error = jsonObject.get("status_code").toString();
            logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            logger.error("请求异常：" + e);
        }
    }

    //发送uninets平台的短信
    public void sendUninetsSms(String nationalCode, String mobile, String msg) {
        if (nationalCode == null) nationalCode = "";
        // http://sms.uninets.com.cn/Modules/Interface/http/IservicesBSJY.aspx
        String to = mobile;
        Map<String, Object> m = new HashMap<>();
        m.put("flag", "sendsms");
        m.put("loginname", this.account);
        m.put("password", this.passwd);
        m.put("p", to);
        m.put("c", msg);
        logger.info("短信请求参数为:mobile=" + mobile + ",msg=" + m.get("c") + ",loginname=" + m.get("loginname") + ",password=" + m.get("password"));
        try {
            String result = HttpUtil.HttpPost(url, m);
            logger.info("短信返回内容为:" + result);
        } catch (Exception e) {
            logger.error("请求异常：" + e);
        }
    }


    //发送短信
    public void sendSms(String nationalCode, String mobile, String msg) {
        String strUrl = null;
        Map<String, String> m = new HashMap<>();
//        if (this.url.contains("paasoo")) {
//            sendPaasoocnSms(nationalCode, mobile, msg);
//            return;
//        }
//        if (this.url.contains(".uninets.")) {
//            sendUninetsSms(nationalCode, mobile, msg);
//            return;
//        }
        if ("86".equals(nationalCode)) {
//            //国内短信
//            strUrl = this.url;
//            m.put("account", this.account);
//            m.put("password", this.passwd);
//            m.put("msg", msg);
//            m.put("phone", mobile);
//            m.put("report", "true");
//            String strJson = JSONUtil.toJSONString(m);
//            this.sendMsg(strUrl, strJson);
            sendDomesticMsg(mobile, msg);
        } else {
            sendInternationalMsg(nationalCode + mobile, msg);
        }
    }

    public void sendMsg(String url, String params) {
        logger.info("请求参数为:" + params);
        try {
            String result = HttpUtil.HttpPostJson(url, params);
            logger.info("返回参数为:" + result);

            JSONObject jsonObject = JSON.parseObject(result);
            String code = jsonObject.get("code").toString();
            String msgid = jsonObject.get("msgId").toString();
            String error = jsonObject.get("errorMsg").toString();
            logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            logger.error("请求异常：" + e);
        }
    }

    //发送邮件验证码
    public void sendEmail(String nationalCode, String email, String msg) {
        SimpleMailUtil.EmailProperty info = new SimpleMailUtil.EmailProperty()
                .setMailFromAddress(fromAddress).setValidate(validate)
                .setMailUserName(userName).setMailPassword(userPassword)
                .setMailServerHost(serverHost).setMailServerPort(serverPort)
                .setStarttls(starttls);
        logger.info("【ETEX】安全验证 send meail --- start ");
        String object = "【ETEX】安全验证";

        SimpleMailUtil.sendTextMailSSL(info, email, object, msg);
        logger.info("【ETEX】安全验证 send meail --- end ");
    }

    public void sendEmailRegister(String nationalCode, String email, String code) {
        SimpleMailUtil.EmailProperty info = new SimpleMailUtil.EmailProperty()
                .setMailFromAddress(fromAddress).setValidate(validate)
                .setMailUserName(userName).setMailPassword(userPassword)
                .setMailServerHost(serverHost).setMailServerPort(serverPort)
                .setStarttls(starttls);

        String object = "【ETEX】安全验证";

        String content = "您好，\n" +
                "您正在注册ETEX账号。\n" +
                "【ETEX】安全验证：" + code + "\n" +
                "妥善保管，请勿转发，5分钟内有效。\n\n";
        SimpleMailUtil.sendTextMailSSL(info, email, object, content);
    }

    /**
     * 发送国际短信
     *
     * @param mobile 手机号需要拼接区号
     * @param msg
     */
    public void sendInternationalMsg(String mobile, String msg) {
        logger.info("发送短信开始：" + mobile + " ------------" + msg);
        String mtUrl = this.urlGw;
        String smsParam = "?flag=sendsms&loginname=" + this.accountGw + "&p=" + mobile + "&password=" + this.passwdGw + "&c=" + msg + "&i=1";
        logger.info("发送短信开始URL:" + mtUrl + smsParam);
        String s = HttpUtil.httpGet(mtUrl + smsParam);
        logger.info("请求短信返回结果：---" + s);
    }

    /**
     * 发送国内短信
     *
     * @param mobile 手机号需要拼接区号
     * @param msg
     */
    public void sendDomesticMsg(String mobile, String msg) {
        logger.info("发送国内短信开始：" + mobile + " ------------" + msg);
        String mtUrl = this.url;
        String smsParam = "?flag=sendsms&loginname=" + this.account + "&p=" + mobile + "&password=" + this.passwd + "&c=" + msg;
        logger.info("发送国内短信开始URL:" + mtUrl + smsParam);
        String s = HttpUtil.httpGet(mtUrl + smsParam);
        logger.info("请求国内短信返回结果：---" + s);
    }

}
