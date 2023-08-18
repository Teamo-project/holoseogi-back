package com.holoseogi.holoseogi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://www.holoseogi.co.kr/",
                        "http://www.holoseogi.co.kr",
                        "http://ec2-13-238-92-189.ap-southeast-2.compute.amazonaws.com:8000",
                        "http://ec2-13-238-92-189.ap-southeast-2.compute.amazonaws.com:8000/")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}