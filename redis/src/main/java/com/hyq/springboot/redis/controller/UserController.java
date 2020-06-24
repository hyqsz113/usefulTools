package com.hyq.springboot.redis.controller;

import com.hyq.springboot.redis.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-09 12:25
 **/
@RestController
@RequestMapping("/info")
public class UserController {

    @GetMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser(){
        User user = new User("aa","bb");
        System.out.println("如果出现无缓存，说明没存进去，如果有返回");
        return user;
    }
}
