package com.scaff.tools.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by chenwen on 16/12/29.
 */
@ComponentScan(basePackages = "com.scaff")
@SpringBootApplication
@EnableCaching
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class,args);
    }
}
