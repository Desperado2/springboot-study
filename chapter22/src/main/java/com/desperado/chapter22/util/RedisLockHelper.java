package com.desperado.chapter22.util;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;
import sun.rmi.runtime.Log;

import javax.validation.constraints.DecimalMin;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 自定义bean
 * 通过封装成 API 方式调用，灵活度更加高
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisLockHelper {

    private static final String DELIMITER  = "|";
    //如果要求比较高可以通过注入的方式分配
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);
    private final StringRedisTemplate stringRedisTemplate;

    public RedisLockHelper(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取锁(存在死锁风险)
     * @param lockKey  key
     * @param value   value
     * @param time   过期时间
     * @param unit   时间单位
     * @return
     */
    public boolean tryLock(final String lockKey, final String value, final long time, final TimeUnit unit){
        return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.set(lockKey.getBytes(),
                value.getBytes(),Expiration.from(time,unit), RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    /**
     * 获取锁
     * @param lockKey  key
     * @param uuid  UUID
     * @param time  超时时间
     * @param unit  时间单位
     * @return      加锁是否成功
     */
    public boolean lock(final String lockKey, final String uuid, final long time, final TimeUnit unit){
        final long milliseconds = Expiration.from(time, unit).getExpirationTimeInMilliseconds();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, (System.currentTimeMillis() + milliseconds) + DELIMITER + uuid);
        if(success){
            stringRedisTemplate.expire(lockKey,time,TimeUnit.SECONDS);
        }else{
            String oldVal = stringRedisTemplate.opsForValue().getAndSet(lockKey, (System.currentTimeMillis() + milliseconds) + DELIMITER + uuid);
            final String[] oldValues = oldVal.split(Pattern.quote(DELIMITER));
            if(Long.parseLong(oldValues[0])+1 <= System.currentTimeMillis() ){
                return true;
            }
        }
        return success;
    }

    /**
     * 立即解锁
     * @param lockKey
     * @param value
     */
    public void unlock(String lockKey, String value) {
        unlock(lockKey, value, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟解锁
     * @param lockKey
     * @param uuid
     * @param delayTime
     * @param unit
     */
    public void unlock(final String lockKey, final String uuid, final long delayTime, TimeUnit unit){
        if(StringUtils.isEmpty(lockKey)){
            return;
        }
        if(delayTime <= 0){
            doUnlock(lockKey,uuid);
        }else{
            EXECUTOR_SERVICE.schedule(()->doUnlock(lockKey,uuid),delayTime,unit);
        }
    }


    /**
     * 解锁
     * @param lockkey
     * @param uuid
     */
    private void doUnlock(final String lockkey, final String uuid){
        String lock = stringRedisTemplate.opsForValue().get(lockkey);
        String[] values = lock.split(Pattern.quote(DELIMITER));
        if(values.length <= 0){
            return;
        }
        if(uuid.equals(values[1])){
            stringRedisTemplate.delete(lockkey);
        }
    }
}
