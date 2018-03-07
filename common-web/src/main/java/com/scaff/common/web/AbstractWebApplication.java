package com.scaff.common.web;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 11/9/17.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.scaff"})
@Slf4j
public abstract class AbstractWebApplication implements ApplicationRunner {

    private void start(ApplicationArguments arguments) {
    }

    protected static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("---------------------------- {} start running web with args = {} ----------------------------", this.getClass().getName(), var1);

        start(var1);

        stopWatch.stop();
        log.info("---------------------------- end running web  costTime = {} (ms) ----------------------------", stopWatch.getTime());
    }
}
