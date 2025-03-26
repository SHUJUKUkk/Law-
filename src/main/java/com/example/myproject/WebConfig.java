package com.example.myproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将浏览器请求 /img/** 映射到项目内 classpath 下的 templates/img 目录
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/templates/img/");
    }
}