###scaff-api-gateway
网关模块，所有微服务的入口

１．需要引入的ｐｏｍ有：
````
        <!--通用的工具引入-->
        <dependency>
            <groupId>scaff</groupId>
            <artifactId>common-utils</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--通用的枚举引入-->
        <dependency>
            <groupId>scaff</groupId>
            <artifactId>common-enum</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--springcloud的zuull支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zuul</artifactId>
        </dependency>
````
2.模块意义

    所有微服务的入口，现在比较流行的网关实现有：KONG，consul等等，这里有时间会一一实现
    网关提供的一些基本功能包括动态路由，监控，安全校验等等。
    
3.模块功能
    
    1.稍微介绍一下filter的使用方法,这里以打印日志的filter为例子：
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
    
    2.统一打印请求日志：PreRequestLogFilter