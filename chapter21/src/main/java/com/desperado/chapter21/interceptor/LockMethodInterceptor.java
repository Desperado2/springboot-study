package com.desperado.chapter21.interceptor;

import com.desperado.chapter21.annotation.LocalLock;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 本章先基于 本地缓存来做，后续讲解 redis 方案
 * 首先通过 CacheBuilder.newBuilder() 构建出缓存对象，设置好过期时间；其目的就是为了防止因程序崩溃锁得不到释放（当然如果单机这种方式程序都炸了，锁早没了；但这不妨碍我们写好点）
 *
 * 在具体的 interceptor() 方法上采用的是 Around（环绕增强） ，所有带 LocalLock 注解的都将被切面处理；
 *
 * 如果想更为灵活，key 的生成规则可以定义成接口形式（可以参考：org.springframework.cache.interceptor.KeyGenerator），这里就偷个懒了；
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    public static final Cache<String,Object> CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000) // 最大缓存数量
            .expireAfterWrite(5,TimeUnit.SECONDS) //过期时间
            .build();


    @Around("execution(public * *(..)) && @annotation(com.desperado.chapter21.annotation.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pjp.getArgs());
        if(!key.isEmpty()){
            if(CACHE.getIfPresent(key) != null){
                throw new RuntimeException("请勿重复提交");
            }
            //第一次调用，加入
            CACHE.put(key,key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        } finally {
            // 为了演示效果,这里就不调用 CACHES.invalidate(key); 代码了
        }
    }

    /**
     * key 的生成策略,如果想灵活可以写成接口与实现类的方式（TODO 后续讲解）
     *
     * @param keyExpress 表达式
     * @param args       参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
        }
        return keyExpress;
    }
}
