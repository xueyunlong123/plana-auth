package com.scaff.data;

import com.scaff.common.web.AbstractWebApplication;
import com.scaff.plana.auth.client.EnablePlanaAuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyl on 11/10/17.
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnablePlanaAuth
@EnableFeignClients
public class AnalyzeApplication extends AbstractWebApplication {
    public static void main(String[] args) {

        context = SpringApplication.run(AnalyzeApplication.class, args);
        for (String name : context.getBeanDefinitionNames()) {
            log.info(name);
        }
    }
}

