package com.cmd.exchange.common.oauth2;

import com.cmd.exchange.common.constants.AdminStatus;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.exception.ServerException;
import com.cmd.exchange.common.mapper.UserMapper;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lwx 2017/09/01
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redUsersTemplate;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //TODO
        return null;
    }

    public Integer getRedisTokenByToken(String token) {
        String userID = this.redUsersTemplate.opsForValue().get("token:" + token);
        return userID != null ? Integer.parseInt(userID) : null;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String _token = (String) token.getPrincipal();
        Integer userId = getRedisTokenByToken(_token);
        if (userId == null) {
            throw new ServerException(ErrorCode.ERR_TOKEN_NOT_EXIST, "Token 失效");
        }
        /*UserToken userToken = userMapper.getUserTokenByToken(_token);
        if (userToken == null) {
            throw new ServerException(ErrorCode.ERR_TOKEN_NOT_EXIST, "Token 不存在");
        }
        if(userToken.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new ServerException(ErrorCode.ERR_TOKEN_EXPIRE_TIME, "Token 失效");
        }*/
        User user = userMapper.getUserByUserId(userId);
        if (user.getStatus() == AdminStatus.DISABLE.getValue()) {
            throw new ServerException(ErrorCode.ERR_USER_DISABLE, "用户被禁用");
        }
        user.setToken(_token);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, _token, getName());
        return info;
    }

}
