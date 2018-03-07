package com.scaff.plana.auth.center;


import com.scaff.common.web.AbstractWebApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 11/17/17.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class AuthApplication extends AbstractWebApplication {

    public static void main(String[] args) {

        context = SpringApplication.run(AuthApplication.class, args);
        for (String name : context.getBeanDefinitionNames()) {
            log.info(name);
        }
    }

}
