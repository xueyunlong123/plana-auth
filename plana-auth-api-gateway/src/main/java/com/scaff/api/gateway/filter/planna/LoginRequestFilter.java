package com.scaff.api.gateway.filter.planna;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.scaff.api.gateway.service.JwtFeignService;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.LoginDTO;
import com.scaff.enums.zuulfilter.ZuulFilterType;
import com.scaff.utils.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xyl on 1/9/18.
 */
public class LoginRequestFilter extends ZuulFilter {

    @Autowired
    JwtFeignService jwtFeignService;

    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.getRequest().getRequestURI().contains("login");
    }

    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        LoginDTO dto = HttpUtils.getModelFromRequest(requestContext.getRequest(),LoginDTO.class);

        JSONResult jsonResult = jwtFeignService.login(dto);

        requestContext.setSendZuulResponse(false);
        requestContext.setResponseBody(jsonResult.buildJsonString());

        return null;
    }


}
