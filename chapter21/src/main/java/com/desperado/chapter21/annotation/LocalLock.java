package com.desperado.chapter21.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {
    /**
     */
    String key() default "";

    /**
     * 过期时间 TODO 由于用的 guava 暂时就忽略这属性吧 集成 redis 需要用到
     *
     */
    int expire() default 5;
}
