package com.desperado.chapter6;

import com.desperado.chapter6.entity.User;
import com.desperado.chapter6.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter6ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(Chapter6ApplicationTests.class);

    @Autowired
    private UserRepository userRepository;
    @Test
    public void contextLoads() {
        final User user = userRepository.save(new User("u1","p1"));
        log.info("[添加成功]->[{}]",user);
        final List<User> users = userRepository.findAllByUsername("u1");
        log.info("[跟进用户名查询成功]->[{}]",users);
        //分页 排序
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("username")));
        final Page<User> userPage = userRepository.findAll(pageable);
        log.info("[分页+排序+查询所有]->[{}]",userPage.getContent());
        userRepository.findById(userPage.getContent().get(0).getId()).ifPresent(user1 -> log.info("[主键查询成功]->[{}]",user1));
        User user1 = userRepository.save(new User(user.getId(), "修改u1", "修改p1"));
        log.info("[修改成功] - [{}]", user1);
        userRepository.deleteById(user.getId());
        log.info("[删除主键为 {} 成功] - [{}]", user.getId());
    }

}

