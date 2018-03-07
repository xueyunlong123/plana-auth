package com.scaff.api.gateway.filter.planna;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.scaff.api.gateway.service.AuthCenterFeignService;
import com.scaff.api.gateway.service.JwtFeignService;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.AuthPermissionReqDTO;
import com.scaff.enums.zuulfilter.ZuulFilterType;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xyl on 1/9/18.
 */
public class AuthRequestFilter extends ZuulFilter {

    private static final String JWT = "jwt";
    private static final String USERNAME = "username";
    private static final String LOGIN = "login";

    @Autowired
    AuthCenterFeignService authCenterFeignService;

    @Autowired
    JwtFeignService jwtFeignService;

    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return !RequestContext.getCurrentContext().getRequest().getRequestURI().contains(LOGIN);
    }

    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();

        String url = requestContext.getRequest().getRequestURI().substring(1);
        String application = null;
        if (url.contains("/")){
            application = url.substring(0,url.indexOf("/"));
        }
        JSONResult jsonResult = authCenterFeignService.authPermission(
          AuthPermissionReqDTO.builder()
                  .application(application)
                  .path("")
                  .username(jwtFeignService.getUsername(requestContext.getRequest().getHeader(JWT)).getData().getString(USERNAME))
                  .build()
        );
        if (!jsonResult.isSuccess()){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody(jsonResult.buildJsonString());
        }
        return null;
    }


}

