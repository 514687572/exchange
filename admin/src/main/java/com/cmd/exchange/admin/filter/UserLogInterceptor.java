package com.cmd.exchange.admin.filter;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.oauth2.ShiroUtils;
import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.mapper.UserLogMapper;
import com.cmd.exchange.common.model.Admin;
import com.cmd.exchange.common.model.AdminLog;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserLog;
import com.cmd.exchange.common.response.CommonResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.Map;

/**
 * 普通用户的日志操作
 * 对用户操作进行记录日志
 */
public class UserLogInterceptor implements HandlerInterceptor {
    private static final Log log = LogFactory.getLog(UserLogInterceptor.class);
    @Autowired
    private UserLogMapper userLogMapper;

    // 保存解析后的日志注释
    private Hashtable<HandlerMethod, Expression> methodExps = new Hashtable<HandlerMethod, Expression>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        httpServletRequest.setAttribute("RequestStartTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try {
            if (!(o instanceof HandlerMethod)) {
                log.warn("afterCompletion found object is not  HandlerMethod:" + String.valueOf(o));
                return;
            }
            HandlerMethod handlerMethod = (HandlerMethod) o;
            OpLog opLog = handlerMethod.getMethod().getAnnotation(OpLog.class);
            if (opLog == null) {
                return;
            }
            // 获取操作用户id
            AdminEntity user = ShiroUtils.getUser();
            if (user == null) {
                return;
            }
            long startTime = (Long) httpServletRequest.getAttribute("RequestStartTime");
            long usedTime = System.currentTimeMillis() - startTime;
            if (usedTime > 0x7fffffff) {
                usedTime = 0x7fffffff;
            }
            String comment = opLog.comment();
            if (comment.length() > 0) {
                Expression expression = methodExps.get(handlerMethod);
                if (expression == null) {
                    SpelExpressionParser parser = new SpelExpressionParser();
                    expression = parser.parseExpression(comment);
                    methodExps.put(handlerMethod, expression);
                }
                StandardEvaluationContext ctx = new StandardEvaluationContext();
                Map map = httpServletRequest.getParameterMap();
                ctx.setVariables(map);
                comment = expression.getValue(ctx, String.class);
            }
            AdminLog userLog = new AdminLog();
            userLog.setAdminId(user.getId());
            userLog.setComment(comment);
            userLog.setOperationIp(httpServletRequest.getRemoteAddr());
            userLog.setAddTime((int) (startTime / 1000));
            userLog.setType(opLog.type());
            // 暂时没有添加日志
            //this.LogMapper.insertUserLog(userLog);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}
