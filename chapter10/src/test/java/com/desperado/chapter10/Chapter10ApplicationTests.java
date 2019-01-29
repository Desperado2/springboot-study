package com.desperado.chapter10;

import com.desperado.chapter10.entity.User;
import com.desperado.chapter10.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter10ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(Chapter10ApplicationTests.class);


    @Autowired
    private UserService userService;

    /**
     * 查询是没有日志输出的，因为它直接从缓存中获取的数据，而添加、修改、删除都是会进入方法内执行具体的业务代码，
     * 然后通过切面去删除掉Redis中的缓存数据。其中 # 号代表这是一个 SpEL 表达式，此表达式可以遍历方法的参数对象，
     * 具体语法可以参考 Spring 的相关文档手册。
     */

    @Test
    public void get() {
        final User user = userService.saveOrUpdate(new User(5L, "u5", "p5"));
        log.info("[saveOrUpdate] - [{}]", user);
        final User user1 = userService.get(5L);
        log.info("[get] - [{}]", user1);
        userService.delete(5L);
    }
}

