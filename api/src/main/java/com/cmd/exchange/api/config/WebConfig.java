package com.cmd.exchange.api.config;

import com.cmd.exchange.api.filter.UserLogInterceptor;
import com.cmd.exchange.api.filter.UserOperatorInterCeptor;
import com.cmd.exchange.common.utils.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final ObjectMapper RENDER = new ObjectMapper();

    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RENDER.setDateFormat(df);
    }

    public WebConfig() {
    }

    public static class String2DateConverter implements org.springframework.core.convert.converter.Converter<String, Date> {

        @Override
        public Date convert(String source) {

            return DateUtil.getDate(source);
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 从前台过来的数据转换成对应类型的转换器
        registry.addConverter(new String2DateConverter());
    }

    /**
     * 这里的addInterceptors调用默认比bean生成早
     * addInterceptor的时候直接new UserLogInterceptor会导致UserLogInterceptor里面的自动注入失效
     * 添加@Bean使得后续对返回的bean重新注入
     *
     * @return
     */
    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new UserLogInterceptor();
    }

    /**
     * 这里的addInterceptors调用默认比bean生成早
     * addInterceptor的时候直接new UserLogInterceptor会导致UserLogInterceptor里面的自动注入失效
     * 添加@Bean使得后续对返回的bean重新注入
     *
     * @return
     */
    @Bean
    public HandlerInterceptor getOperatorInterceptor() {
        return new UserOperatorInterCeptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的拦截器
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getOperatorInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (!converters.isEmpty()) {
            converters.removeIf(converter -> converter instanceof StringHttpMessageConverter
                    || converter instanceof MappingJackson2HttpMessageConverter);
        }

        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new CustomizeJacksonConverter());
    }

    private static class CustomizeJacksonConverter extends MappingJackson2HttpMessageConverter {
        private CustomizeJacksonConverter() {
            super(RENDER);
        }

        @Override
        protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
            super.writeSuffix(generator, object);
        }
    }
}
