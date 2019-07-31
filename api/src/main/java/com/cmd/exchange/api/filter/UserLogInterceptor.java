package com.cmd.exchange.api.filter;

import com.cmd.exchange.common.oauth2.ShiroUtils;
import com.cmd.exchange.common.annotation.OpLog;
import com.cmd.exchange.common.mapper.UserLogMapper;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserLog;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.IPUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
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
                log.warn("afterCompletion found object is not HandlerMethod:" + String.valueOf(o));
                return;
            }
            HandlerMethod handlerMethod = (HandlerMethod) o;
            OpLog opLog = handlerMethod.getMethod().getAnnotation(OpLog.class);
            if (opLog == null) {
                return;
            }
            // 获取操作用户id -- 注销后就找不到用户了
            User user = ShiroUtils.getUser();
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

                Map variablesMap = new HashMap<String, Object>();
                variablesMap.putAll(map);

                //获取PathVariable注解里的参数
                Map pathVariables = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                if (pathVariables != null) {
                    variablesMap.putAll(pathVariables);
                }

                ctx.setVariables(variablesMap);
                comment = expression.getValue(ctx, String.class);
            }
            UserLog userLog = new UserLog();
            userLog.setUserId(user.getId());
            userLog.setComment(comment);
            //userLog.setOperationIp(httpServletRequest.getRemoteAddr());
            userLog.setOperationIp(IPUtils.getIpAddr(httpServletRequest));
            userLog.setOperationTime(new Date());
            if (e != null) {
                userLog.setSuccess(0);
            } else {
                // ReturnStatusCode在拦截器CommonResponseAdvice中设置的
                Object retObj = httpServletRequest.getAttribute("ReturnResponseObj");
                if (retObj != null && retObj instanceof CommonResponse) {
                    CommonResponse commResp = (CommonResponse) retObj;
                    userLog.setSuccess(commResp.getStatusCode() == 0 ? 1 : 0);
                } else {
                    userLog.setSuccess(1);
                }
            }
            userLog.setType(opLog.type());
            userLog.setUsedTime((int) usedTime);
            this.userLogMapper.insertUserLog(userLog);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public static void main(String[] args) {

        String comment = "'id' + #id + ' name=' + #name";
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        Expression expression = parser.parseExpression(comment);
        Map<String, Object> map = new HashMap<>();
        map.put("id", 3);
        ctx.setVariables(map);
        String value = expression.getValue(ctx, String.class);
        System.out.println(value);
    }
}
