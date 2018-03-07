package com.scaff.api.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.scaff.enums.zuulfilter.ZuulFilterType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xyl on 11/3/17.
 */
@Slf4j
public class PreRequestLogFilter extends ZuulFilter{
    //过滤器类型：pre、route、post、error,这里根据名称可以看出来各个类型的作用
    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    //同一类型过滤器的执行顺序，值越小越先执行
    @Override
    public int filterOrder() {
        return 1;
    }

    //该过滤器是否执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //这里主要定义，当你获取到一个外部的http请求时，你想做的操作，比如对请求的数据打印日志，或者根据请求的接口分配服务器（动态路由）
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
