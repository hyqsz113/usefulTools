package com.hyq.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-28 10:51
 **/
@Intercepts(
        value = {
                @Signature(
                        type = StatementHandler.class,
                        method = "prepare",
                        args = {Connection.class}
                )
        }
)
public class mybatisPlugin implements Interceptor {
    /**
     * 这个方法是mybatis的核心方法
     * 要实现自定义逻辑，基本都是改造这个方法
     * 其中invocation参数 可以通过反射要获取  原始方法和  对应参数信息
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    /**
     * 它的作用是用来生成一个拦截对方，也就是代理对象，
     * 使得被代理的对象一定会经过intercept方法，
     * 通常都会使用mybatis提供的工具类Plugin来获取代理对象，
     * 如果有自己独特需求，可以自定义
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    /**
     * 用来设置插件的一些属性
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
    }
}
