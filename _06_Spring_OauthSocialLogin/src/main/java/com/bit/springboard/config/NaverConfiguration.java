package com.bit.springboard.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// @Configuraiont: 클래스 파일을 설정파일로 지정하는 어노테이션
//                 자동으로 빈 객체로 등록이 되며
//                 @Configuration이 추가된 클래스에선
//                 @Bean 어노테이션으로 빈 객체를 생성하는 메소드를 정의할 수 있다.
@Configuration
@PropertySource("classpath:/application.properties")
@Getter
public class NaverConfiguration {
    @Value("${ncp.accessKey}")
    private String accessKey;
    @Value("${ncp.secretKey}")
    private String secretKey;
    @Value("${ncp.regionName}")
    private String regionName;
    @Value("${ncp.endPoint}")
    private String endPoint;
}









