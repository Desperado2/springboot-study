package com.desperado.chapter5.json;

import com.desperado.chapter5.entity.User;
import com.desperado.chapter5.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * restful风格接口，通过测试类测试
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> queryUser(){
        return userService.queryUsers();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @DeleteMapping("{id}")
    public Integer delUser(@PathVariable Long id){
        return userService.delUser(id);
    }

    @PostMapping
    public Integer addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("{id}")
    public Integer editUser(@PathVariable Long id,@RequestBody User user){
        return userService.editUser(id,user);
    }
}
