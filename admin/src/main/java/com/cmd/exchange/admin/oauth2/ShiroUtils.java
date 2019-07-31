package com.cmd.exchange.admin.oauth2;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.common.model.Admin;
import com.cmd.exchange.common.utils.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类 *
 *
 * @author lwx
 */
public class ShiroUtils {


    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    public static Session getSession() {
        return getSubject().getSession();
    }

    public static AdminEntity getUser() {
        return (AdminEntity) getSubject().getPrincipal();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return getSubject().getPrincipal() != null;
    }

    public static void logout() {
        getSubject().logout();
    }

}
