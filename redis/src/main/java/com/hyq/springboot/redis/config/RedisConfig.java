package com.hyq.springboot.redis.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-09 10:57
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(target.getClass().getName());
                stringBuffer.append(method.getName());
                for (Object param : params) {
                    stringBuffer.append(param.toString());
                }
                return stringBuffer.toString();
            }
        };
    }
}
