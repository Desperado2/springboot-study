package com.desperado.chapter5;

import com.desperado.chapter5.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter5Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Chapter5ApplicationTests {

    private static  final Logger log = LoggerFactory.getLogger(Chapter5ApplicationTests.class);
    @Autowired
    private  TestRestTemplate template;
    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() {
        template.postForEntity("http://localhost:" + port + "/users", new User("user1", "pass1"), Integer.class);
        log.info("[添加用户成功]\n");
        // TODO 如果是返回的集合,要用 exchange 而不是 getForEntity ，后者需要自己强转类型
        ParameterizedTypeReference<List<User>> myBean = new ParameterizedTypeReference<List<User>>() {};
        ResponseEntity<List<User>> response2 = template.exchange("http://localhost:" + port + "/users",HttpMethod.GET, null,myBean);
        final List<User> body = response2.getBody();
        log.info("[查询所有] - [{}]\n", body);
        Long userId = body.get(0).getId();
        ResponseEntity<User> response3 = template.getForEntity("http://localhost:" + port + "/users/{id}", User.class, userId);
        log.info("[主键查询] - [{}]\n", response3.getBody());
        template.put("http://localhost:" + port + "/users/{id}", new User("user11", "pass11"), userId);
        log.info("[修改用户成功]\n");
        template.delete("http://localhost:" + port + "/users/{id}", userId);
        log.info("[删除用户成功]");
    }

}

