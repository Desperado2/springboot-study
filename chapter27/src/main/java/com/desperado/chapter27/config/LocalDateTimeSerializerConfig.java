package com.desperado.chapter27.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 配置类
 * 方案一
 * 只需要定义一个配置类，在里面定义两个 Bean 即可完成全局日期格式化处理，这种方式也是本人现在使用的，
 * 同时还兼顾了 Date 和 LocalDateTime 并存
 */
@Configuration
public class LocalDateTimeSerializerConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String patter ;

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer(){
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(patter));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return bulider->bulider.serializerByType(LocalDateTime.class,localDateTimeSerializer());
    }
}
