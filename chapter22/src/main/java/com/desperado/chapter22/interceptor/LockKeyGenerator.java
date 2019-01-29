package com.desperado.chapter22.interceptor;

import com.desperado.chapter22.annotation.CacheLock;
import com.desperado.chapter22.annotation.CacheParam;
import com.desperado.chapter22.generator.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * key生成策略实现
 *
 * 解析过程虽然看上去优点绕，但认真阅读或者调试就会发现，主要是解析带 CacheLock 注解的属性，获取对应的属性值，生成一个全新的缓存 Key
 */
public class LockKeyGenerator implements CacheKeyGenerator {
    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        CacheLock cacheLock = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        // 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i=0; i<parameters.length; i++){
            CacheParam cacheParam = parameters[i].getAnnotation(CacheParam.class);
            if(cacheParam == null){
                continue;
            }
            builder.append(cacheLock.delimiter()).append(args[i]);
        }
        // 解析实体对象中的
        if(StringUtils.isEmpty(builder.toString())){
            final Annotation[][] annotations = method.getParameterAnnotations();
            for (int i=0; i<annotations.length; i++){
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields){
                    CacheParam cacheParam = field.getAnnotation(CacheParam.class);
                    if(cacheParam == null){
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(cacheLock.delimiter()).append(ReflectionUtils.getField(field,object));
                }
            }
        }
        return cacheLock.prefix() + builder.toString();
    }
}
