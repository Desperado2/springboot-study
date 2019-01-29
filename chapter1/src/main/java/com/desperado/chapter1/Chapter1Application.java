package com.desperado.chapter1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 主函数，启动类，运行它如果运行了 Tomcat、Jetty、Undertow 等容器
 */
@RestController
@SpringBootApplication
public class Chapter1Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter1Application.class, args);
    }


    @GetMapping("/demo1")
    public String demo1(){
        return "hello desperado";
    }

    /**
     * 打印springboot提供分bean
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args -> {
            System.out.println("springboot默认提供的bean");
            String[] names = ctx.getBeanDefinitionNames();
            Arrays.sort(names);
            Arrays.stream(names).forEach(System.out::println);
        };
    }
}

