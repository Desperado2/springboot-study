package com.desperado26.chapter26.limit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * 默认情况下 spring-boot-data-redis 为我们提供了StringRedisTemplate 但是满足不了其它类型的转换，所以还是得自己去定义其它类型的模板
 */
@Configuration
public class RedisLimiterHelper {

    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
