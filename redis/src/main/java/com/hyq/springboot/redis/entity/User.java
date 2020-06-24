package com.hyq.springboot.redis.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-09 11:38
 **/
@Getter
@Setter
public class User implements Serializable {

    public String name;
    public String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
