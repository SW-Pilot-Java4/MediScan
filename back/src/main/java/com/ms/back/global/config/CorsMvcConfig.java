package com.ms.back.global.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")    // 프론트 주소
                .allowedMethods("*") // 허용할 HTTP 메서드 명시
                .allowedHeaders("*")                          // 모든 헤더 허용
                .allowCredentials(true);                      // 쿠키/인증 헤더 허용
    }}