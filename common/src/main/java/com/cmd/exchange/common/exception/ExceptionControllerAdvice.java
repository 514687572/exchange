package com.cmd.exchange.common.exception;

import com.cmd.exchange.common.response.CommonResponse;
import net.bytebuddy.implementation.FieldAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.ServerException;

/**
 * 全局异常处理
 * Created by jerry on 2017/6/26.
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionControllerAdvice {
    private static Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    private static final String UNAUTHORIZED_ERROR = "暂无操作权限";
    private static final String APP_ERROR = "程序错误，我们会尽快处理";
    private static final String SERVER_ERROR = "服务器错误，我们会尽快处理";

    //多语言此处可以统一处理

    /**
     * 未授权异常
     * 导致原因: 业务错误
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public CommonResponse unauthorizedException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.info("未授权错误，错误原因：" + e.getMessage());
        return new CommonResponse(403, UNAUTHORIZED_ERROR);
    }

    /**
     * 请求错误异常
     * 导致原因: 请求参数错误
     */
    @ExceptionHandler(value = {BadRequestException.class, ServletException.class})
    public CommonResponse badRequestException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.info("请求错误，错误原因：", e);
        return new CommonResponse(400, APP_ERROR);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public CommonResponse missingParameterError(HttpServletRequest request, HttpServletResponse response, MissingServletRequestParameterException e) {
        logger.info("请求错误，错误原因：", e);
        String errorMessage = "参数[" + e.getParameterName() + "]不能为空";

        return new CommonResponse(400, errorMessage);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public CommonResponse httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        logger.info("请求错误，错误原因：", e);
        String errorMessage = "请求方式[" + e.getMethod() + "]不支持";

        return new CommonResponse(400, errorMessage);
    }


    /**
     * 系统级异常
     * 导致原因: 程序错误，或系统环境问题
     */
    @ExceptionHandler(value = {Exception.class, ServerException.class})
    //@ResponseStatus(HttpStatus.OK)
    public CommonResponse serverError(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("系统错误，错误原因：" + e.getMessage(), e);
        return new CommonResponse(500, SERVER_ERROR);
    }

    //在参数上加了@Valid注解后，不满足条件的参数异常
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse argumentError(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        logger.error("参数错误", e);
        String errorMessage = "";
        for (FieldError error : e.getBindingResult().getFieldErrors()) {

            errorMessage += error.getField() + ":" + error.getDefaultMessage() + ",";
        }

        if (errorMessage.length() > 0) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        return new CommonResponse(400, errorMessage);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public CommonResponse argumentTypeError(HttpServletRequest request, HttpServletResponse response, MethodArgumentTypeMismatchException e) {
        logger.error("参数错误", e);
        String errorMessage = "参数[" + e.getName() + "] 无法转换成指定类型，值:" + e.getValue();


        return new CommonResponse(400, errorMessage);
    }


    //在参数上加了@Valid注解后，不满足条件的参数异常
    @ExceptionHandler(value = {BindException.class})
    public CommonResponse argumentError(HttpServletRequest request, HttpServletResponse response, BindException e) {
        logger.error("参数错误", e);
        String errorMessage = "";
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMessage += "字段[" + error.getField() + "]" + error.getDefaultMessage() + ",";
        }

        if (errorMessage.length() > 0) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        return new CommonResponse(400, errorMessage);
    }


    /**
     * 业务级异常
     * 导致原因: 业务错误
     */
    @ExceptionHandler(value = {BusinessException.class})
    //@ResponseStatus(HttpStatus.OK)
    public CommonResponse businessException(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        logger.warn("业务错误，错误原因：" + e.getMessage(), e);
        return new CommonResponse(e.getCode(), e.getMessage());
    }
}
