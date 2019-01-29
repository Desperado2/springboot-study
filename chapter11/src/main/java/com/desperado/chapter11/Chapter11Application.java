package com.desperado.chapter11;

import com.battcn.swagger.annotation.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 开启swagger
 *  访问http://localhost:8080/swagger-ui.html，
 */
@EnableSwagger2Doc
@SpringBootApplication
public class Chapter11Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter11Application.class, args);
    }

}

