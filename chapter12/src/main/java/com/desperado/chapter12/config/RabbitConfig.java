package com.desperado.chapter12.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 定义队列
 */
@Configuration
public class RabbitConfig {

    public static final String DEFAULT_BOOK_QUEUE = "dev.book.register.default.queue";
    public static final String MANUAL_BOOK_QUEUE = "dev.book.register.manual.queue";


    @Bean
    public Queue defaultBookQueue(){
        // 第一个参数   queue名称   第二个参数   是否需要持久化
        return new Queue(DEFAULT_BOOK_QUEUE,true);
    }

    @Bean
    public Queue manualBookQueue(){
        return new Queue(MANUAL_BOOK_QUEUE,true);
    }
}
