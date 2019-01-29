package com.desperado.chapter5.service;

import com.desperado.chapter5.entity.User;

import java.util.List;

public interface UserService {
     List<User> queryUsers();
     User getUser( Long id) ;
     int delUser( Long id) ;
     int addUser(User user);
     int editUser(Long id, User user);
}
