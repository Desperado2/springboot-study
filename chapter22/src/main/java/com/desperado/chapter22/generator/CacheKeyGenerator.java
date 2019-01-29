package com.desperado.chapter22.generator;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成接口
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数，生成指定缓存的key
     * @param pjp
     * @return  缓存key
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
