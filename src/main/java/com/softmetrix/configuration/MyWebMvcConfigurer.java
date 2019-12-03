package com.softmetrix.configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class MyWebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }   
      
    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }
}