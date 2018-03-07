package com.scaff.api.gateway;

import com.scaff.common.web.AbstractWebApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 11/2/17.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
@Slf4j
@EnableDiscoveryClient
public class ZuulApiGatewayApplication extends AbstractWebApplication {

    public static void main(String[] args) {

        context = SpringApplication.run(ZuulApiGatewayApplication.class, args);
        for (String name : context.getBeanDefinitionNames()) {
            log.info(name);
        }
    }
}