package com.desperado.chapter22.interceptor;

import com.desperado.chapter22.annotation.CacheLock;
import com.desperado.chapter22.generator.CacheKeyGenerator;
import com.desperado.chapter22.util.RedisLockHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * AOP拦截器（redis方案）
 *
 * 熟悉 Redis 的朋友都知道它是线程安全的，我们利用它的特性可以很轻松的实现一个分布式锁，如 opsForValue().setIfAbsent(key,value) 它的作用就是如果缓存中没有当前 Key 则进行缓存同时返回 true 反之亦然；当缓存后给 key 在设置个过期时间，防止因为系统崩溃而导致锁迟迟不释放形成死锁； 那么我们是不是可以这样认为当返回 true 我们认为它获取到锁了，在锁未释放的时候我们进行异常的抛出….
 */
@Aspect
@Configuration
public class LockMethodInterceptor {
    private RedisLockHelper redisLockHelper;
    private CacheKeyGenerator cacheKeyGenerator;

    public LockMethodInterceptor(RedisLockHelper redisLockHelper, CacheKeyGenerator cacheKeyGenerator){
        this.redisLockHelper = redisLockHelper;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    @Around("execution(public * *(..)) && @annotation(com.desperado.chapter22.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if(StringUtils.isEmpty(lock.prefix())){
            throw new RuntimeException("lock key is null....");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        String uuid = UUID.randomUUID().toString();

        try {
            boolean success = redisLockHelper.lock(lockKey, uuid, lock.expire(), lock.timeUnit());
            if(!success){
                throw new RuntimeException("重复提交");
            }
            try{
                return pjp.proceed();
            }catch (Throwable throwable){
                throw new RuntimeException("系统异常");
            }
        }finally {
            // TODO 如果演示的话需要注释该代码;实际应该放开
            //redisLockHelper.unlock(lockKey,uuid);
        }
    }

}
