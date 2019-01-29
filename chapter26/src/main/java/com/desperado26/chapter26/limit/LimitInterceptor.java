package com.desperado26.chapter26.limit;


import com.desperado26.chapter26.annotation.Limit;
import com.desperado26.chapter26.enums.LimitType;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class LimitInterceptor {
    public static final Logger log = LoggerFactory.getLogger(LimitInterceptor.class);

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    @Autowired
    public LimitInterceptor(RedisTemplate<String,Serializable> limitRedisTemplate){
        this.limitRedisTemplate = limitRedisTemplate;
    }

    @Around("execution(public * * (..)) && @annotation(com.desperado26.chapter26.annotation.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit annotation = method.getAnnotation(Limit.class);
        LimitType limitType = annotation.limitType();
        String name = annotation.name();
        String key;
        int period = annotation.period();
        int count = annotation.count();
        switch (limitType){
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                // TODO 如果此处想根据表达式或者一些规则生成 请看 一起来学Spring Boot | 第二十三篇：轻松搞定重复提交（分布式锁）
                key = annotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(annotation.prefix(), key));
        try {
            String luaScript = buildLuaScript();
            DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count1 = limitRedisTemplate.execute(redisScript, keys, count,period);
            log.info("Access try count is {} for name={} and key = {}", count, name, key);
            if(count1 != null || count1.intValue() <= count){
                return pjp.proceed();
            }else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }
        }catch (Throwable e){
            if(e instanceof RuntimeException){
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    /**
     * 限流脚本
     * @return lua脚本
     */
    public String buildLuaScript(){
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        //调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        //执行计算器自加
        lua.append("\nc=redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        //从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

    private static final String UNKONWN = "unknown";

    public String getIpAddress(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String ip =request.getHeader("x-forwarded-for");
        if(ip == null || ip.length()==0 || UNKONWN.equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKONWN.equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKONWN.equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
