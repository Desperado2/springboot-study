package com.desperado26.chapter26.annotation;

import com.desperado26.chapter26.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

    //资源名称
    String name() default "";

    //资源的key
    String key() default "";

    //key的prefix
    String prefix() default "";

    //给定的时间段
    int period();

    //最多的限制访问次数
    int count();

    //类型
    LimitType limitType() default LimitType.CUSTOMER;
}
