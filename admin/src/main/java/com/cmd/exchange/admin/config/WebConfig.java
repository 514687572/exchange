package com.cmd.exchange.admin.config;

import com.cmd.exchange.admin.filter.UserLogInterceptor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 用来渲染给前台的 json 映射器, 定义了一些自定义类的序列化规则, 然而并没有反序列化规则<br>
     * 所以使用此映射器序列化的 json, 想要反序列化回来调用(toObject toList)时将会不成功
     */
    public static final ObjectMapper RENDER = new ObjectMapper();

    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RENDER.setDateFormat(df);
    }

    public WebConfig() {
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的拦截器
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
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
