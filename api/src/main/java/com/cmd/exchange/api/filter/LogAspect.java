package com.cmd.exchange.api.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private static final Log log = LogFactory.getLog(LogAspect.class);

    @Before("within(com.cmd.exchange.api.controller.*)")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("请求=============" + method.getDeclaringClass().getName() + ", 方法名称：" + method.getName() + "，请求参数为： " + StringUtils.join(args, ";"));
        log.debug("请求=============" + method.getDeclaringClass().getName() + ", 方法名称：" + method.getName() + "，请求参数为： " + StringUtils.join(args, ";"));
    }

    @AfterReturning(value = "within(com.cmd.exchange.api.controller.*)", returning = "rvt")
    public void after(JoinPoint joinPoint, Object rvt) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("返回================" + method.getDeclaringClass().getName() + ", 方法名称：" + method.getName() + "，返回数据：" + JSONObject.toJSONString(rvt));
        log.debug("返回================" + method.getDeclaringClass().getName() + ", 方法名称：" + method.getName() + "，返回数据：" + JSONObject.toJSONString(rvt));
    }

}
