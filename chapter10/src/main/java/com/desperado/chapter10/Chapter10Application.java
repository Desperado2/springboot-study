package com.desperado.chapter10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//开启注解支持
@SpringBootApplication
@EnableCaching
public class Chapter10Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter10Application.class, args);
    }

}

