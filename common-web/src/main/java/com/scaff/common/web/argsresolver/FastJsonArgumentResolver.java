package com.scaff.common.web.argsresolver;


import com.scaff.utils.HttpUtils;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by xyl on 11/9/17.
 * 定义FastJson参数解析器
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        /*
        * 当参数加上FastJson注解时，则说明支持该参数解析器
        * */
        return methodParameter.getParameterAnnotation(FastJson.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        /*
        * 将httprequest中的流转为对应对象
        * */
        return HttpUtils.getModelFromRequest(nativeWebRequest.getNativeRequest(HttpServletRequest.class),methodParameter.getParameterType());
    }
}
