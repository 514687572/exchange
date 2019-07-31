package com.cmd.exchange.external.utils;

import com.cmd.exchange.external.annotation.CheckSignature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class CheckSignatureInterceptor implements HandlerInterceptor {
    // 盐值
    final String SECRET = "ek93nkdkswWjfet";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        CheckSignature annotation = method.getAnnotation(CheckSignature.class);
        if (annotation != null) {
            // 执行认证
            Map<String, String[]> map = request.getParameterMap();
            // 转成treeMap 对 Map 的 key 的 ASCII 码从小到大排序
            TreeMap<String, String[]> stringTreeMap = new TreeMap<>(map);
            StringBuffer stringBuffer = new StringBuffer();
            stringTreeMap.forEach((k, v) -> {
                // 不能拼接签名字段
                if (!"sign".equals(k.toLowerCase())) {
                    stringBuffer.append(k + "=" + v[0]);
                }
            });
            String sign = request.getParameter("sign");
            String content = stringBuffer.insert(0, SECRET).append(SECRET).toString();
//            if (StringUtils.isBlank(sign) || !sign.equals(md5(content))) {
            if (StringUtils.isBlank(sign) || !sign.equals(Encrypt.toMd5(content))) {
                throw new RuntimeException("签名错误！");
            }
        }
        return true;
    }

    // MD5签名方法
    public static String md5(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            byte[] b = content.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                    outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "phone=18588414598";
        System.out.println(md5(str));    // fc7c53955c086a4486ae8ee9711ab28e
    }

}
