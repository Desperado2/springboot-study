package com.desperado.chapter16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Chapter16Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16Application.class, args);
    }

    @Configuration
    static class MyEndpointConfiguration{
        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnEnabledEndpoint
        public MyEndPoint getMyEndPoint(){
            return new MyEndPoint();
        }
    }
}

