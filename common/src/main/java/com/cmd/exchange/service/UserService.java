package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.*;
import com.cmd.exchange.common.enums.SmsCaptchaType;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.oauth2.OAuth2Token;
import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.utils.*;
import com.cmd.exchange.common.vo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${token.expireTime:1800}")
    private int TOKENEXPIRETIME;

    @Value("${cmd.user.mustInvite:true}")
    private boolean mustInvite = true;   // 是否必须要邀请才能注册

    @Value("${cmd.user.maxSameCard:3}")
    private int maxSameCard = 3;   // 同一个身份证号最多被使用的次数

    @Autowired
    UserMapper userMapper;
    @Autowired
    SmsService smsService;
    @Autowired
    UserCoinService userCoinService;
    @Autowired
    ConfigService configService;
    @Autowired
    UserBillMapper userBillMapper;
    @Autowired
    UserLogService userLogService;
    @Autowired
    UserRewardLogMapper userRewardLogMapper;
    @Autowired
    NationalCodeMapper nationalCodeMapper;
    @Autowired
    GoogleAuthService googleAuthService;
    @Autowired
    RewardLogService rewardLogService;
    @Autowired
    CoinConfigMapper coinConfigMapper;
    @Autowired
    AppVersionMapper appVersionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCoinMapper userCoinMapper;


    @Autowired
    private RedisTemplate<String, String> redUsersTemplate;

    @Autowired
    private UserMachineService userMachineService;

    @Autowired
    private UserIntegrationMapper userIntegrationMapper;
    @Autowired
    private UserRecommendMapper userRecommendMapper;
    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private UserRecommendService userRecommendService;


    //获取用户token
    //public UserToken getUserTokenByUserId(int userId) {
    //    return userMapper.getUserTokenByUserId(userId);
    //}

    //public UserToken getUserTokenByToken(String token) {
    //    return userMapper.getUserTokenByToken(token);
    //}

    //token切换到redis中去, type=1web端登录，type=2手机端登录
    public Integer getRedisTokenByToken(String token) {
        String userID = this.redUsersTemplate.opsForValue().get("token:" + token);
        return userID != null ? Integer.parseInt(userID) : null;
    }

    public void setRedisTokenByToken(int type, String token, Integer userId) {
        //清除老的token
        this.disRedisTokenByUserId(type, userId);

        if (type == 1) {
            this.redUsersTemplate.opsForValue().set("token:" + token, String.valueOf(userId), TOKENEXPIRETIME, TimeUnit.SECONDS);
            this.redUsersTemplate.opsForValue().set("token:" + userId, token, TOKENEXPIRETIME, TimeUnit.SECONDS);
        } else {
            this.redUsersTemplate.opsForValue().set("token:" + token, String.valueOf(userId), TOKENEXPIRETIME, TimeUnit.SECONDS);
            this.redUsersTemplate.opsForValue().set("mobile:" + userId, token, TOKENEXPIRETIME, TimeUnit.SECONDS);
        }
    }

    public void disRedisTokenByUserId(int type, Integer userId) {
        String token = null;
        if (type == 1) {
            token = this.redUsersTemplate.opsForValue().get("token:" + userId);
            this.redUsersTemplate.delete("token:" + userId);
        } else {
            token = this.redUsersTemplate.opsForValue().get("mobile:" + userId);
            this.redUsersTemplate.delete("mobile:" + userId);
        }
        this.redUsersTemplate.delete("token:" + token);
    }

    //获取用户证件号码
    public User getUserByIDcard(String idcard) {
        return userMapper.getUserByIDcard(idcard);
    }

    //获取用户
    public User getUserByMobile(String mobile) {
        return userMapper.getUserByMobile(mobile);
    }

    public List<User> adminGetUserByUserName(List<String> userName) {
        return userMapper.adminGetUserByUserName(userName);
    }

    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    public User getUserByUserId(int userId) {
        return userMapper.getUserByUserId(userId);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //获取图像验证码
    public String getCaptchaImgWord(String areaCode, String mobile) {
        return smsService.getCaptchaImgWord(areaCode, mobile);
    }

    //获取短信验证码
    public void getSmsCode(String areaCode, String mobile, String type, String captchaImgCode) {
        Assert.check(mobile == null || mobile.length() == 0, ErrorCode.ERR_USER_MOBILE_ERROR);
        smsService.sendSmsCaptcha(areaCode, mobile, type, captchaImgCode);
    }

    //验证短信验证码
    public void checkSmsCode(String areaCode, String mobile, String type, String captchaImgCode) {
        Assert.check(!smsService.checkCaptchaFirst(areaCode, mobile, type, captchaImgCode), ErrorCode.ERR_USER_SMSCODE_ERROR);
    }

    //用户注册
    @Transactional
    public void register(User user, String inviteCode, String code, String mobile) {
        Assert.check(user.getMobile() == null && user.getEmail() == null, ErrorCode.ERR_USER_ACCOUNT_ERROR);
        Assert.check(user.getPassword() == null, ErrorCode.ERR_USER_PASSWORD_ERROR);
        if (user.getUserName() == null) {
            user.setUserName(user.getMobile());
        }
        if (mobile.contains("@")) {
            Assert.check(userMapper.getUserByEmail(user.getEmail()) != null, ErrorCode.ERR_USER_EMAIL_EXIST);
            //Assert.check(!smsService.checkCaptcha(user.getAreaCode(), user.getEmail(), SmsCaptchaType.REGISTER.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);
        } else {
            Assert.check(userMapper.getUserByMobile(user.getMobile()) != null, ErrorCode.ERR_USER_MOBILE_EXIST);
            //Assert.check(!smsService.checkCaptcha(user.getAreaCode(), user.getMobile(), SmsCaptchaType.REGISTER.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);
        }

        //验证图形验证码
//        smsService.CheckCaptchaImgWord(areaCode, mobile, imgCode);

        //生成用户
        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(user.getPassword());

        user.setPassword(encryptedPassword[0]);
        user.setSalt(encryptedPassword[1]);

        //生成邀请码
        //if (mustInvite) {
          //  Assert.check(StringUtils.isBlank(inviteCode), ErrorCode.ERR_REFERRER_NOT_EXIST);
        //}
        user.setInviteCode(CageUtil.getWords(10));
        if (StringUtils.isNotBlank(inviteCode)) {
            Integer referrerUserId = userMapper.getUserIdByInviteCode(inviteCode);
            Assert.check(referrerUserId == null, ErrorCode.ERR_REFERRER_NOT_EXIST);
            user.setReferrer(referrerUserId);
        }
        //如果填了邀请码，则查询邀请人id
        userMapper.addUser(user);

        //生成默认token记录
        User _user = null;
        if (mobile.contains("@")) {
            _user = userMapper.getUserByEmail(mobile);
        } else {
            _user = userMapper.getUserByMobile(mobile);
        }

        userMachineService.userAddMachine(_user.getId(), MachineType.REGISTRATION_RECOMMENDATION.getValue());


        //UserToken token = new UserToken().setUserId(_user.getId()).setToken(TokenGeneratorUtil.generateValue())
        //        .setExpireTime(new Date(new Date().getTime() + TOKENEXPIRETIME * 1000));
        //userMapper.addUserToken(token);

        //生成默认平台币
        //String coinName = configService.getPlatformCoinName();
        //if (coinName != null) {
        //    userCoinService.addUserCoin(_user.getId(), coinName);
        //}

        //给上级推荐奖励记录，状态未发放
        if (_user.getReferrer() != null) {
            //保留8位小数
            BigDecimal conf_value = new BigDecimal(1);
            // String groupType = _user.getGroupType();
            //if(!groupType.equals("0")){
            //  conf_value = new BigDecimal(configService.getConfigValue(groupType,100)).subtract(new BigDecimal(100));
            //}

            User referrer = userMapper.getUserByUserId(_user.getReferrer());
            if (referrer != null) {
                userRecommendService.userRecUser(_user.getReferrer(),_user.getId());
                int reward = configService.getUserReferrerReward();
                if (reward > 0) {
                    String coinName1 = configService.getPlatformCoinName();
                    if (coinName1 != null) {
                        userRewardLogMapper.add(new UserRewardLog()
                                .setUserId(_user.getReferrer())
                                .setFrom(_user.getId())
                                .setCoinName(coinName1)
                                .setAmount(new BigDecimal(reward).multiply(conf_value))
                                .setReason(UserBillReason.REFERRER_REWARD)
                                .setStatus(0)   //0未发放,生成记录
                                .setComment("推荐奖励"));
                    }
                }

                //注册成功后赠送积分--added in 2019/06/18
                //准备批量数据插入积分表
                List<UserIntegration> list = new ArrayList<UserIntegration>();
                //查配置表数据
                List<BigDecimal> configlist = new ArrayList<BigDecimal>();
                List<String> configKey = new ArrayList<String>(Arrays.asList(
                        ConfigKey.REGISTER_REWARD_INTEGRATION_A,ConfigKey.REGISTER_REWARD_INTEGRATION_B,
                        ConfigKey.REGISTER_REWARD_INTEGRATION_C,ConfigKey.REGISTER_REWARD_INTEGRATION_D));
                for(int i=0;i<=3;i++){
                    configlist.add(configService.getConfigValue(configKey.get(i),BigDecimal.ZERO,false));
                }
                if(configlist.get(0).compareTo(BigDecimal.ZERO)==1){
                    list.add(newObj(_user.getId(),IntegrationType.REGISTER_REWARD,configlist.get(0)));//赠送积分给自己
                }
                //用户上级关系(还需要赠送积分给上级：上1级，上2-3级，上4-10级)
                UserRecommend ur = userRecommendMapper.selectByPrimaryKey(_user.getId());
                if(ur != null){
                    List<Integer> pidList = new ArrayList<Integer>(Arrays.asList(ur.getPid1(),
                            ur.getPid2(),ur.getPid3(),ur.getPid4(),ur.getPid5(),ur.getPid6(),
                            ur.getPid7(),ur.getPid8(),ur.getPid9(),ur.getPid10()));
                    for(int i=0;i<pidList.size();i++){
                        if(pidList.get(i)==null||pidList.get(i)==0){//没有上级
                            break;
                        }
                        if(i==0&&configlist.get(1).compareTo(BigDecimal.ZERO)==1
                                &&sendIntegration(pidList.get(i))){//赠送积分给上1级
                            list.add(newObj(pidList.get(i),IntegrationType.REGISTER_REFERRER_REWARD,configlist.get(1)));
                        }
                        if(i>0&&i<=2&&configlist.get(2).compareTo(BigDecimal.ZERO)==1
                                &&sendIntegration(pidList.get(i))){//赠送积分给上2-3级
                            list.add(newObj(pidList.get(i),IntegrationType.REGISTER_REFERRER_REWARD,configlist.get(2)));
                        }
                        if(i>2&&i<=9&&configlist.get(3).compareTo(BigDecimal.ZERO)==1
                                &&sendIntegration(pidList.get(i))){//赠送积分给上4-10级
                            list.add(newObj(pidList.get(i),IntegrationType.REGISTER_REFERRER_REWARD,configlist.get(3)));
                        }
                    }
                }
                userIntegrationMapper.insertByBatch(list);
            }
        }
    }
    //判断是否可获得注册积分赠送
    private boolean sendIntegration(Integer userId){
        BigDecimal integrationTotal = userIntegrationMapper.getUserIntegrationByUserId(userId);
        if(integrationTotal == null){
            integrationTotal = BigDecimal.ZERO;
        }
        BigDecimal investTotal = BigDecimal.ZERO;
        Invest invest = investMapper.selectByPrimaryKey(userId);
        if(invest != null){
            investTotal = invest.getCoinAmount();
        }
        //积分总额在100-999.9，投资总额未到达10USDT
        if(integrationTotal.compareTo(new BigDecimal(100))>-1
                && integrationTotal.compareTo(new BigDecimal(1000))==-1
                && investTotal.compareTo(new BigDecimal(10))==-1){
                return false;
        }
        //积分总额在1000-9999.9，投资总额未到达100USDT
        if(integrationTotal.compareTo(new BigDecimal(1000))>-1
                && integrationTotal.compareTo(new BigDecimal(10000))==-1
                && investTotal.compareTo(new BigDecimal(100))==-1){
                return false;
        }
        //积分总额在10000-，投资总额未到达0.5*积分总额
        if(integrationTotal.compareTo(new BigDecimal(10000))>-1
                && investTotal.compareTo(integrationTotal.multiply(new BigDecimal("0.5")))==-1){
                return false;
        }
        return true;//默认积分总额在0-99.9，可获得；或其他情况达到要求，可获得。
    }
    //生成一个用户积分对象
    private UserIntegration newObj(Integer userId,Integer integrationType,BigDecimal integration){
        UserIntegration ui = new UserIntegration();
        ui.setUserId(userId);
        ui.setAddTime(new Date());
        ui.setIntegrationType(integrationType);
        ui.setUserIntegration(integration);
        return ui;
    }

    //设置交易密码
    public void setPayPassword(int userId, String areaCode, String mobile, String code, String payPassword) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        if (mobile.contains("@")) {
            Assert.check(!mobile.equals(user.getEmail()), ErrorCode.ERR_PARAM_ERROR);
        } else {
            Assert.check(!mobile.equals(user.getMobile()), ErrorCode.ERR_PARAM_ERROR);
        }
        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.TRANSACTION_PASSWORD.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);

        //生成用户
        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(payPassword);
        User tmp = new User().setId(userId).setPayPassword(encryptedPassword[0]).setPaySalt(encryptedPassword[1]);
        userMapper.updateUserByUserId(tmp);
    }

    //重置交易密码
    public void resetPayPassword(String areaCode, String mobile, String code, String payPassword) {
        Assert.check(mobile == null, ErrorCode.ERR_USER_MOBILE_EXIST);
        Assert.check(payPassword == null, ErrorCode.ERR_USER_PASSWORD_ERROR);

        User user = null;
        if (mobile.contains("@")) {
            Assert.check(!ToolsUtil.checkEmail(mobile), ErrorCode.ERR_USER_ACCOUNT_ERROR);
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobile(mobile);
        }
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.TRANSACTION_FORGET.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);
        googleAuthService.checkGoogleAuthSecond(user.getId(), areaCode, mobile, SmsCaptchaType.TRANSACTION_FORGET.getValue());

        //生成用户
        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(payPassword);
        User tmp = new User().setId(user.getId()).setPayPassword(encryptedPassword[0]).setPaySalt(encryptedPassword[1]);
        userMapper.updateUserByUserId(tmp);
    }

    // 检查用户是否能使用提供的身份证号码
    private void checkUseIdCard(int userId, User user) {
        List<User> idcards = userMapper.getUsersByIDcard(user.getIdCard(), this.maxSameCard + 1);
        if (idcards == null || idcards.size() < this.maxSameCard) {
            return;
        }
        // 如果使用用户超过最大个数，不能注册
        Assert.check(idcards.size() > this.maxSameCard, ErrorCode.ERR_USER_IDCARD_ERROR);
        // 使用次数刚好等于最大次数，看看里面是否有自己使用的（这种情况是修改自己其它信息，不修改身份证号码）
        for (User idUser : idcards) {
            if (idUser.getId() == userId) {
                // 用户修改自己的信息，id卡不变
                return;
            }
        }
        Assert.failed(ErrorCode.ERR_USER_IDCARD_ERROR);
    }

    //用户实名认证
    public void userIdCard(int userId, User user) {
        User tmp = userMapper.getUserByUserId(userId);
        Assert.check(tmp == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(tmp.getIdCardStatus() == 1, ErrorCode.ERR_USER_CERTIFICATED);

        //User Idcard = userMapper.getUserByIDcard(user.getIdCard());
        //Assert.check(Idcard != null && Idcard.getId() != userId, ErrorCode.ERR_USER_IDCARD_ERROR);
        checkUseIdCard(userId, user);
        user.setIdCardTime(new Date());
        int cnt = userMapper.updateUserIdCard(user);
    }

    //获取用户认证信息
    public RegisterVo getUserAuthInfo(int userId) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        return new RegisterVo().setCertType(String.valueOf(user.getIdCardType())).setIdcard(user.getIdCard())
                .setRealName(user.getRealName()).setPhone(user.getMobile()).setIdCardImg1(user.getIdCardImg1())
                .setIdCardImg2(user.getIdCardImg2()).setIdCardImg3(user.getIdCardImg3()).setIdCardStatus(user.getIdCardStatus())
                .setIdCardTime(user.getIdCardTime()).setNational(user.getAreaCode());
    }

    //用户登录
    @Transactional
    public TokenVo login(int type, String areaCode, String mobile, String password, String captchaImgCode, String loginIp) {
        User user = null;
        log.info("user mobile={},start login captchaImgCode={}", mobile, captchaImgCode);
        if (mobile.contains("@")) {
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobileAndAreaCode(areaCode, mobile);
        }

        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getStatus() == 1, ErrorCode.ERR_USER_DISABLE);

        log.info("viterfy image_code===");
//todo 本地测试暂时先放开
        smsService.CheckCaptchaImgWord(areaCode, mobile, captchaImgCode);

        googleAuthService.checkGoogleAuthSecond(user.getId(), areaCode, mobile, SmsCaptchaType.LOGIN.getValue());
//todo 本地测试暂时先放开
        Assert.check(!EncryptionUtil.check(password, user.getPassword(), user.getSalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);
        //生成token
        String token = TokenGeneratorUtil.generateValue();
        this.setRedisTokenByToken(type, token, user.getId());
        /*UserToken token = new UserToken().setUserId(user.getId()).setToken(TokenGeneratorUtil.generateValue())
                .setExpireTime(new Date(new Date().getTime() + TOKENEXPIRETIME * 1000));
        if (userMapper.getUserTokenByUserId(user.getId()) == null) {
            userMapper.addUserToken(token);
        } else {
            userMapper.updateUserToken(token);
        }*/

        //发送短信提醒:****0001的手机用户，您在2018年7月3日20:23:08登录了coin，若非您本人操作，请及时修改密码。
        //if (user.getLastLoginIp() == null || !user.getLastLoginIp().equals(loginIp)) {
        //  if (user.getMobile() != null) {
        //     String smsMsg = SmsTemplate.loginIpChangeSMS(user.getMobile());
        //    smsService.sendSms(user.getAreaCode(), user.getMobile(), smsMsg);
        // }
        //}

        //修改最后登录IP
        userMapper.updateUserLoginLogByUserId(user.getId(), loginIp);


        Subject subject = ShiroUtils.getSubject();
        try {
            subject.login(new OAuth2Token(token, true));
        } catch (IncorrectCredentialsException e1) {
            Assert.check(true, ErrorCode.ERR_TOKEN_NOT_EXIST);
        } catch (LockedAccountException e2) {
            Assert.check(true, ErrorCode.ERR_TOKEN_NOT_EXIST);
        }

        GoogleSecret googleSecret = googleAuthService.getGoogleSecret(user.getId());
        boolean isBindGoogleSecret = (googleSecret != null && googleSecret.getStatus() == 1) ? true : false;

        //返回的数据
        boolean isPayPassword = (user.getPayPassword() != null && user.getPayPassword().length() > 0) ? true : false;
        boolean isRealName = user.getIdCardStatus() == 1 ? true : false;
        boolean isMobile = user.getMobile() != null && user.getMobile().length() > 0 ? true : false;
        boolean isEmail = user.getEmail() != null && user.getEmail().length() > 0 ? true : false;
        log.info("======= login response successs ========  ");
        return new TokenVo().setUserId(user.getId()).setToken(token).setExpiretTime(new Date(new Date().getTime() + TOKENEXPIRETIME * 1000))
                .setBindPaypwd(isPayPassword).setRealNameAuth(isRealName).setBindEmail(isEmail).setBindMobile(isMobile).setBindGoogleSecret(isBindGoogleSecret);
    }

    public void logout(int type, Integer userId) {
        this.disRedisTokenByUserId(type, userId);
    }

    //忘记密码，重置密码
    @Transactional
    public void resetPassword(String areaCode, String mobile, String code, String password) {
        Assert.check(mobile == null, ErrorCode.ERR_USER_MOBILE_ERROR);
        Assert.check(password == null, ErrorCode.ERR_USER_PASSWORD_ERROR);

        User user = null;
        if (mobile.contains("@")) {
            user = userMapper.getUserByEmail(mobile);
        } else {
            user = userMapper.getUserByMobile(mobile);
        }

        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.PASSWORD_FORGET.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);
        googleAuthService.checkGoogleAuthSecond(user.getId(), areaCode, mobile, SmsCaptchaType.PASSWORD_FORGET.getValue());

        //生成用户
        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(password);
        User tmp = new User().setId(user.getId()).setPassword(encryptedPassword[0]).setSalt(encryptedPassword[1]);
        userMapper.updateUserByUserId(tmp);
    }

    @Transactional
    public void resetPassword(Integer userId, String newPassword) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(newPassword);
        User tmp = new User().setId(user.getId()).setPassword(encryptedPassword[0]).setSalt(encryptedPassword[1]);
        userMapper.updateUserByUserId(tmp);
    }

    @Transactional
    public void resetPayPassword(Integer userId, String newPassword) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(newPassword);
        User tmp = new User().setId(user.getId()).setPayPassword(encryptedPassword[0]).setPaySalt(encryptedPassword[1]);
        userMapper.updateUserByUserId(tmp);
    }

    public Page<UserInfoVO> getUserList(String referrerMobile,
                                        String mobile,
                                        Integer authStatus,
                                        Integer status,
                                        String groupType,
                                        String email,
                                        String realName,
                                        BigDecimal integrationMin,
                                        BigDecimal integrationMax,
                                        Integer pageNo,
                                        Integer pageSize) {
        Integer referrer = null;
        if (StringUtils.isNotBlank(referrerMobile)) {
            User oldUser = userMapper.getUserByMobile(referrerMobile);
            if (oldUser != null) {
                referrer = oldUser.getId();
            }
        }

        Page<UserInfoVO> userList = userMapper.getUserList(referrer, mobile, authStatus, status, groupType, email, realName, integrationMin, integrationMax, new RowBounds(pageNo, pageSize));
        if (!userList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            for (UserInfoVO u : userList) {
                if (u.getReferrer() != null) {
                    idList.add(u.getReferrer());
                }
            }

            if (idList.size() > 0) {
                List<UserInfoVO> referrerList = userMapper.getUserListByIdList(idList);
                Map<Integer, String> id2MobileMap = new HashMap<>();
                for (UserInfoVO u : referrerList) {
                    if (u.getMobile() != null) {
                        id2MobileMap.put(u.getId(), u.getMobile());
                    }
                }

                for (UserInfoVO u : userList) {
                    u.setReferrerMobile(id2MobileMap.get(u.getReferrer()));
                }
            }
        }

        return userList;
    }

    @Transactional
    public void updateUser(UpdateUserVO userToUpdate) {
        User user = userMapper.getUserByUserId(userToUpdate.getUserId());
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        User tmp = new User().setId(userToUpdate.getUserId());

        if (StringUtils.isNotBlank(userToUpdate.getAreaCode())) {
            tmp.setAreaCode(userToUpdate.getAreaCode());
        }

        if (StringUtils.isNotBlank(userToUpdate.getMobile())) {
            tmp.setMobile(userToUpdate.getMobile());
        }
        //if(StringUtils.isNotBlank(userToUpdate.getGroupType())){
        ////   tmp.setGroupType(userToUpdate.getGroupType());
        // }
        tmp.setStatus(userToUpdate.getStatus());

        if (userToUpdate.getRealNameStatus() != null && userToUpdate.getRealNameStatus() == 1) {
            Assert.check(StringUtils.isBlank(user.getIdCardImg1()), ErrorCode.ERR_USER_ID_CARD_NOT_UPLOAD);

            //分红处理
            updateUserIdCardStatus(Arrays.asList(userToUpdate.getUserId()), 1);
        }
        if (userToUpdate.getGroupType() != null) {
            tmp.setGroupType(userToUpdate.getGroupType());
        }
        if (userToUpdate.getUserType() != null) {
            tmp.setUserType(userToUpdate.getUserType());
        }
        if (userToUpdate.getIsPublishOtc() != null) {
            tmp.setIsPublishOtc(userToUpdate.getIsPublishOtc());
        }
        tmp.setIdCardStatus(userToUpdate.getRealNameStatus());
        userMapper.updateUserByUserId(tmp);
    }

    @Transactional
    public void updateUserStatus(List<Integer> userIds, Integer status) {
        for (Integer userId : userIds) {
            User tmp = new User().setId(userId);
            tmp.setStatus(status);
            int rows = userMapper.updateUserByUserId(tmp);
            Assert.check(rows == 0, ErrorCode.ERR_USER_NOT_EXIST);
        }
    }

    @Transactional
    public void updateUserIdCardStatus(List<Integer> userIds, Integer idCardStatus) {
        for (Integer userId : userIds) {
            User oldUser = userMapper.getUserByUserId(userId);
            Assert.check(oldUser == null, ErrorCode.ERR_USER_NOT_EXIST);

            Assert.check(StringUtils.isBlank(oldUser.getIdCardImg1()), ErrorCode.ERR_USER_ID_CARD_NOT_UPLOAD);

            //已经实名认证过滤掉
            if (oldUser.getIdCardStatus() == 1)
                continue;

            User tmp = new User().setId(userId);
            tmp.setIdCardStatus(idCardStatus);
            int rows = userMapper.updateUserByUserId(tmp);
            Assert.check(rows == 0, ErrorCode.ERR_USER_NOT_EXIST);

            //审核通过推荐奖励
            if (idCardStatus == 1) {
                //注册奖励
                int reward = configService.getUserRegisterReward();
                if (reward > 0) {
                    String coinName = configService.getPlatformCoinName();
                    if (coinName != null) {
                        //查找是否已经发送过注册奖励（场景：后台对同一个用户多次进行实名认证）
                        UserRewardLog userRewardLog = userRewardLogMapper.getUserRegisterRewardByUserId(userId);
                        if (userRewardLog == null) {
                            //userCoinService.addUserCoin(userId, coinName);
                            User user = userMapper.getUserByUserId(userId);
                            BigDecimal config_value = new BigDecimal(1);
                            //if(!user.getGroupType().equals("0")){
                            //    config_value = new BigDecimal(configService.getConfigValue(user.getGroupType())).subtract(new BigDecimal(100));
                            //  }

                            userCoinService.changeUserCoin(userId, coinName, new BigDecimal(reward).multiply(config_value), new BigDecimal(0), UserBillReason.REGISTER_REWARD, "注册赠送");


                            //注册赠送记录
                            userRewardLogMapper.add(new UserRewardLog().setUserId(userId).setCoinName(coinName).setReason(UserBillReason.REGISTER_REWARD)
                                    .setAmount(new BigDecimal(reward).multiply(config_value)).setComment("注册赠送").setStatus(1));
                        }
                    }
                }

                //推荐奖励发放
                if (oldUser.getReferrer() != null) {
                    User referrer = userMapper.getUserByUserId(oldUser.getReferrer());
                    if (referrer != null) {
                        //已经实名验证通过
                        UserRewardLog userRewardLog = userRewardLogMapper.getUserRewardByUserIdAndFrom(oldUser.getReferrer(), userId);
                        if (userRewardLog != null) {
                            userCoinService.changeUserCoin(oldUser.getReferrer(), userRewardLog.getCoinName(), userRewardLog.getAmount(), new BigDecimal(0), UserBillReason.REFERRER_REWARD, "推荐奖励");
                            userRewardLogMapper.updateUserRewardLogStatus(oldUser.getReferrer(), userId, 1);
                        }
                    }
                }
            }
        }
    }

    public void updateUserMobile(String areaCode, String oldPhone, String code, String newPhone) {
        Assert.check(StringUtils.isBlank(newPhone), ErrorCode.ERR_PARAM_ERROR);
        Assert.check(!smsService.checkCaptcha(areaCode, oldPhone, SmsCaptchaType.MOBILE_EDIT.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);

        User oldUser = userMapper.getUserByMobileAndAreaCode(areaCode, oldPhone);
        Assert.check(oldUser == null, ErrorCode.ERR_USER_NOT_EXIST);

        User tmp = new User().setId(oldUser.getId()).setMobile(newPhone);
        userMapper.updateUserByUserId(tmp);
    }

    public User getUserDetail(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    //获取用户奖励
    public Page<RewardLogVO> getUserRewardLog(Integer userId, Integer pageNo, Integer pageSize) {
        return rewardLogService.getUserRewardLog(userId, pageNo, pageSize);
    }

    //获取返佣奖励
    public Page<RewardLogVO> getTradeBonusLog(Integer userId, Integer pageNo, Integer pageSize) {
        return rewardLogService.getTradeBonusLog(userId, pageNo, pageSize);
    }

    //获取用户信息，包括推荐人，推荐码，推荐链接
    public UserInfoVO getUserInfo(Integer userId) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        String[] arrReasons = new String[]{UserBillReason.REGISTER_REWARD, UserBillReason.REFERRER_REWARD};
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        userInfoVO.setReferrerUrl(configService.getUserReferrerUrl());
        userInfoVO.setReferrerCount(userMapper.getUserReferrerCount(userId));
        userInfoVO.setReferrerKYCCount(userMapper.getUserReferrerKYCCount(userId));
        BigDecimal dReward = userRewardLogMapper.getTotalUserReward(userId, null, 1, arrReasons);
        userInfoVO.setReferrerReward(dReward == null ? BigDecimal.ZERO : dReward);
        BigDecimal dAllReward = userRewardLogMapper.getTotalUserReward(userId, null, 0, arrReasons);
        userInfoVO.setReferrerRewardAll(dAllReward == null ? BigDecimal.ZERO : dAllReward);
        BigDecimal integration = userIntegrationMapper.getUserIntegrationByUserId(userId);
        userInfoVO.setIntegration(integration == null ? BigDecimal.ZERO : integration);
        return userInfoVO;
    }

    //获取用户登录日志
    public Page<UserLog> getUserLoginLog(Integer userId, int pageNo, int pageSize) {
        return userLogService.getLoginLog(userId, pageNo, pageSize);
    }

    //获取国家区号配置
    public List<NationalCode> getNationalCode() {
        return nationalCodeMapper.getNationalCode();
    }

    public List<UserRewardVO> getUserRewardList() {
        String rewardListString = configService.getUserRewardRanking();
        List<UserRewardVO> rewardList = new ArrayList<>();

        try {
            rewardList.addAll(JSONUtil.parseToObject(rewardListString, new TypeReference<ArrayList<UserRewardVO>>() {
            }));
        } catch (Exception e) {
            logger.error("failed to parse reward ranking list: {}", rewardListString, e);
            Assert.check(true, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        }

        return rewardList;
    }

    //获取全局参数配置
    public List<Config> getConfig() {
        return configService.getConfigList();
    }

    //获取币种配置
    public CoinConfig getCoinConfig(String coinName) {
        return coinConfigMapper.getCoinConfigByName(coinName);
    }

    //绑定手机号
    public void bindMobile(int userId, String areaCode, String mobile, String code) {
        Assert.check(userMapper.getUserByMobile(mobile) != null, ErrorCode.ERR_USER_MOBILE_EXIST);

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getMobile() != null && user.getMobile().length() > 0, ErrorCode.ERR_USER_MOBILE_EXIST);

        Assert.check(!smsService.checkCaptcha(areaCode, mobile, SmsCaptchaType.MOBILE_BIND.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);

        userMapper.updateUserByUserId(new User().setId(user.getId()).setAreaCode(areaCode).setMobile(mobile));
    }

    //绑定邮件
    public void bindEmail(int userId, String areaCode, String email, String code) {
        Assert.check(userMapper.getUserByEmail(email) != null, ErrorCode.ERR_USER_EMAIL_EXIST);

        User user = userMapper.getUserByUserId(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);
        Assert.check(user.getEmail() != null && user.getEmail().length() > 0, ErrorCode.ERR_USER_EMAIL_EXIST);

        Assert.check(!smsService.checkCaptcha(areaCode, email, SmsCaptchaType.EMAIL_BIND.getValue(), code), ErrorCode.ERR_USER_SMSCODE_ERROR);

        userMapper.updateUserByUserId(new User().setId(user.getId()).setEmail(email));
    }

    //获取推荐人
    public Page<ReferrerVO> getUserReferrers(int userId, int pageNo, int pageSize) {
        return userMapper.getUserReferrer(userId, new RowBounds(pageNo, pageSize));
    }

    //获取最新版本号
    public AppVersion getAppVersion(String platform) {
        return appVersionMapper.getAppVersion(platform);
    }

    public Boolean isTokenValid(String token) {
        return this.getRedisTokenByToken(token) != null ? true : false;
    }


    public void updateUserGroupType(List<Integer> userIds, String groupType) {

        for (Integer userId : userIds) {
            userMapper.updateGroupType(userId, groupType);
        }

    }

    /**
     * 获取某个分组用户的数量
     */
    public int getUserCountByGroupType(String groupType) {
        return userMapper.getUserListByGroupTypeCount(groupType);
    }

    /**
     * 获取玩家拓扑图
     */

    public List<TopologyVO> getTopologyByUserId(Integer userId) {
        return userMapper.getTopologyByUserId(userId);
    }

    /**
     * 获取该玩家是否有下级
     *
     * @param userId
     * @return
     */
    public int getSubordinateBool(Integer userId) {
        return userMapper.getSubordinateBool(userId);
    }

    /***
     * 根据用户id查询出对应的分组配置
     */
    public UserGroupConfig getUserGroupConfigById(Integer userId) {
        return userMapper.getUserGroupTypeName(userId);
    }

    // 根据用户组类型获取所有用户，这里只返回用户ID和用户名，不返回其它字段
    public List<User> getAllUserIdNameByGroupType(String groupType) {
        return userMapper.getAllUserIdNameByGroupType(groupType);
    }

    // 获取用户所推荐的一级和二级用户的人数总和
    public int getRec1AndRec2UserCount(int userId) {
        User user = userMapper.getUserByUserId(userId);
        if (user == null) return 0;
        int rec1UserCount = userMapper.getRecUserCountByInviteCode(user.getId());
        // 没有一级用户，不会有二级用户
        if (rec1UserCount == 0) return 0;
        int rec2UserCount = userMapper.getRec2UserCountByInviteCode(user.getId());
        return rec1UserCount + rec2UserCount;
    }

    public BigDecimal getAvailableBalance(String mobile, String coinName) {
        Assert.check(!ToolsUtil.checkPhone(mobile), ErrorCode.ERR_USER_MOBILE_ERROR);

        User userByMobile = userService.getUserByMobile(mobile);
        Assert.check(null == userByMobile, ErrorCode.ERR_USER_NOT_EXIST);

        UserCoinVO userCoinVO = userCoinMapper.lockUserCoinByUserIdAndCoinName(userByMobile.getId(), coinName);
        Assert.check(null == userCoinVO, ErrorCode.ERR_COIN_NAME_IS_EXISTED);

        BigDecimal availableBalance = userMapper.getAvailableBalance(mobile, coinName);
        log.info("用户->{},币种->{},可用余额为：{}", mobile, mobile, availableBalance);
        return Optional.ofNullable(availableBalance).orElse(BigDecimal.ZERO);
    }

    public void modifyAvatar(Integer userId, String image) {
        User user = userMapper.getUserByUserId(userId);
        Assert.check(null == user, ErrorCode.ERR_USER_NOT_EXIST);
        int result = userMapper.modifyAvatar(userId, image);
        Assert.check(result == 0, ErrorCode.ERR_C2C_UPDATE_FAILURE);
    }

}
