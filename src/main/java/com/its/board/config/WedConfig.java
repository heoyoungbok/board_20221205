package com.its.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WedConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // html에서 사용할 경로
    private String savePath ="file:///D:/springboot_img/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){ // 등록
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
