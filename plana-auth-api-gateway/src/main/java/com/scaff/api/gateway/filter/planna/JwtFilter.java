package com.scaff.api.gateway.filter.planna;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.scaff.api.gateway.service.JwtFeignService;
import com.scaff.common.web.result.JSONResult;
import com.scaff.enums.zuulfilter.ZuulFilterType;
import com.scaff.utils.exception.WebBasicCodeEnum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xyl on 1/9/18.
 */
public class JwtFilter extends ZuulFilter {

    private static final String LOGIN = "login";
    private static final String JWT = "jwt";

    @Autowired
    private JwtFeignService jwtFeignService;

    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return -3;
    }

    @Override
    public boolean shouldFilter() {
        return !RequestContext.getCurrentContext().getRequest().getRequestURI().contains(LOGIN);
    }

    @Override
    public Object run() {
        HttpServletRequest httpServletRequest = RequestContext.getCurrentContext().getRequest();
        String token = httpServletRequest.getHeader(JWT);
        if (StringUtils.isEmpty(token)){
            RequestContext.getCurrentContext().setSendZuulResponse(false);
            RequestContext.getCurrentContext().setResponseBody(
                    JSONResult.error(WebBasicCodeEnum.NOT_LOGIN_ERROR).buildJsonString()
            );
        }else {
            JSONResult jsonResult = jwtFeignService.check(token);
            if (!jsonResult.isSuccess()){
                RequestContext.getCurrentContext().setSendZuulResponse(false);
                RequestContext.getCurrentContext().setResponseBody(jsonResult.buildJsonString());
            }
        }

        return null;
    }


}

