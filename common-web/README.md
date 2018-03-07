###common-web 通用web服务模块
一般引入整个项目都需要的一些web依赖。方便整个项目对web请求进行统一的处理

１．需要引入的ｐｏｍ有：
````
        <!-- 添加Eureka的依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        //引入必要的工具依赖
        <dependency>
            <groupId>scaff</groupId>
            <artifactId>common-utils</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
````
2.模块功能

    1.添加全局的httprequest转为指定对象的参数解析器：FastJsonArgumentResolver,可以自动将请求的json数据
    转为自己的DTO对象
    2.添加全局的FastJsonHttpMessageConverter，将返回的数据转为json。
    3.引入SpringSwagger2,可以自动提供api接口和测试数据,详细使用方式可以查看代码。
 