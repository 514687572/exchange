package com.cmd.exchange.admin.oauth2;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.model.AdminToken;
import com.cmd.exchange.admin.service.AdminService;
import com.cmd.exchange.common.constants.AdminStatus;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.exception.ServerException;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserToken;
import com.cmd.exchange.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lwx 2017/09/01
 */
@Component(value = "Admin_OAuth2Realm")
public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Autowired
    private AdminService userService;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //TODO
        return null;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String _token = (String) token.getPrincipal();
        AdminToken userToken = userService.getAdminByToken(_token);
        if (userToken == null) {
            throw new ServerException(ErrorCode.ERR_TOKEN_NOT_EXIST, "Token 不存在");
        }
        if (userToken.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new ServerException(ErrorCode.ERR_TOKEN_EXPIRE_TIME, "Token 失效");
        }

        AdminEntity user = userService.getUserById(userToken.getUserId());
        if (user.getStatus() == AdminStatus.DISABLE) {
            throw new ServerException(ErrorCode.ERR_USER_DISABLE, "用户被禁用");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, _token, getName());
        return info;
    }

}
