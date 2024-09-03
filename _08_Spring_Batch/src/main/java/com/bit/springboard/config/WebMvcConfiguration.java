package com.bit.springboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    // cors 에러 방지 설정
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        // spring boot 모든 api에 대한 교차출처 리소스 공유 허용
        corsRegistry.addMapping("/**")
                // 허용해줄 도메인 등록
                .allowedOrigins("http://localhost:3000")
                // 허용될 요청 방식 지정
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                // 허용될 헤더 지정
                .allowedHeaders("*")
                // 인증정보에 대한 요청 허용여부
                .allowCredentials(true)
                // 타임아웃 시간 설정
                .maxAge(3600);
    }
}
