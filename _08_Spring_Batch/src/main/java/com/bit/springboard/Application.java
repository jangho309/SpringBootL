package com.bit.springboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 스케줄링 기능 활성화
@EnableScheduling
// 1
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
