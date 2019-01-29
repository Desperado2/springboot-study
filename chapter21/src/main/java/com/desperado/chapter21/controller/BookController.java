package com.desperado.chapter21.controller;

import com.desperado.chapter21.annotation.LocalLock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    /**
     * 在接口上添加 @LocalLock(key = "book:arg[0]")；意味着会将 arg[0] 替换成第一个参数的值，生成后的新 key 将被缓存起来；
     * @param token
     * @return
     */
    @LocalLock(key = "book:arg[0]")
    @GetMapping
    public String query(@RequestParam String token) {
        return "success - " + token;
    }
}
