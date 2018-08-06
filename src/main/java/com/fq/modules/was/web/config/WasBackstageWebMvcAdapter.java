package com.fq.modules.was.web.config;

import com.fq.modules.was.web.interceptor.LoginInterceptor;
import com.fq.modules.was.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WasBackstageWebMvcAdapter extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());
        //排除的url
        registration.excludePathPatterns("/login" );
        registration.excludePathPatterns("/signIn" );
        registration.addPathPatterns("/**" );
    }
}
