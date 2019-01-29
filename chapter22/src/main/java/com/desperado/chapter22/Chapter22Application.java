package com.desperado.chapter22;

import com.desperado.chapter22.generator.CacheKeyGenerator;
import com.desperado.chapter22.interceptor.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chapter22Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter22Application.class, args);
    }

    /**
     * 注入接口实现
     * @return
     */
    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }
}

