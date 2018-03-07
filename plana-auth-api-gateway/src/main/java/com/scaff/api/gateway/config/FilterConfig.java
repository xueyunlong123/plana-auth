package com.scaff.api.gateway.config;
import com.scaff.api.gateway.filter.PreRequestLogFilter;
import com.scaff.api.gateway.filter.planna.AuthRequestFilter;
import com.scaff.api.gateway.filter.planna.JwtFilter;
import com.scaff.api.gateway.filter.planna.LoginRequestFilter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xyl on 12/13/17.
 */
@Configuration
@EnableAutoConfiguration
public class FilterConfig {

    @Bean
    public PreRequestLogFilter preRequestLogFilter() {
        return new PreRequestLogFilter();
    }

    @Bean
    public LoginRequestFilter loginRequestFilter(){
        return new LoginRequestFilter();
    }

    @Bean
    public AuthRequestFilter authRequestFilter(){
        return new AuthRequestFilter();
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

}
