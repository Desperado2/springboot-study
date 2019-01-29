package com.desperado.chapter5.service;

import com.desperado.chapter5.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> queryUsers() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUser(Long id) {
        // 根据主键ID查询
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int delUser(Long id) {
        // 根据主键ID删除用户信息
        String sql = "DELETE FROM t_user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int addUser(User user) {
        // 添加用户
        String sql = "insert into t_user(username, password) values(?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    @Override
    public int editUser(Long id, User user) {
        // 根据主键ID修改用户信息
        String sql = "UPDATE t_user SET username = ? ,password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), id);
    }
}
