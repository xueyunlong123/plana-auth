package com.scaff.common.web.controller;



import com.scaff.common.web.result.JSONResult;
import com.scaff.utils.exception.BaseException;
import com.scaff.utils.exception.WebBasicCodeEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by cw on 15-11-24.
 */
@ControllerAdvice
@RequestMapping("/")
@Slf4j
public class GlobalController {
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult health() {
        return JSONResult.ok();
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(UnauthorizedException e) {
        log.error("AuthorizationException异常被截获", e);
        return JSONResult.error(WebBasicCodeEnum.PERMISSION_ERROR).buildModelAndView();
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(IncorrectCredentialsException e) {
        log.error("IncorrectCredentialsException异常被截获", e);
        /**
         * 获取密码错误次数
         */
        return JSONResult.error(WebBasicCodeEnum.USERNAME_OR_PASSWORD_ERROR).buildModelAndView();
    }


    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(AuthorizationException e) {
        log.error("AuthorizationException异常被截获", e);
        if (e.getCause() instanceof BaseException) {
            BaseException baseException = (BaseException) e.getCause();
            return JSONResult.error(baseException.getiErrorCode()).buildModelAndView();
        }
        return JSONResult.error(WebBasicCodeEnum.PERMISSION_ERROR).buildModelAndView();
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ExcessiveAttemptsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(ExcessiveAttemptsException e) {
        log.error("ExcessiveAttemptsException异常被截获", e);
        return JSONResult.error(WebBasicCodeEnum.PASSWORD_LIMIT_ERROR.getCode(),e.getMessage()).buildModelAndView();
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(BaseException e) {
        log.error("BaseException异常被截获", e);
        return JSONResult.error(e.getCode(), e.getMessage()).buildModelAndView();
    }

    /**
     * 参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException异常被截获", e);
        return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR).buildModelAndView();
    }

    /**
     * 参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException异常被截获", e);
        if(StringUtils.isNotEmpty(e.getMessage())){
            return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR.getCode(),e.getMessage()).buildModelAndView();
        }
        return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR).buildModelAndView();
    }

    /**
     * 参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(HttpMessageNotReadableException e) {
        log.error("IllegalArgumentException异常被截获", e);
        return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR).buildModelAndView();
    }

    /**
     * 参数错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView baseExceptionHandler(MethodArgumentNotValidException e) {
        log.error("IllegalArgumentException异常被截获", e);
        return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR.getCode(), String.format("%s -> %s", e.getBindingResult().getFieldError().getField(), e.getBindingResult().getFieldError().getDefaultMessage())).buildModelAndView();
    }

    /**
     * 未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView exceptionHandler(Exception e) {
        log.error("Exception异常被截获", e);
        if (e.getCause() instanceof BaseException) {
            BaseException baseException = (BaseException) e.getCause();
            return JSONResult.error(baseException.getiErrorCode()).buildModelAndView();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return JSONResult.error(WebBasicCodeEnum.NOT_SUPPORTED_METHOD_ERROR).buildModelAndView();
        }
        if (e instanceof BeansException) {
            return JSONResult.error(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR).buildModelAndView();
        }
        return JSONResult.error().buildModelAndView();
    }
}
