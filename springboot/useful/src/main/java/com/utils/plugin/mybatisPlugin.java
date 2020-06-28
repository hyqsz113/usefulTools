package com.utils.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-28 10:51
 **/

/**
 *  @Intercepts 注解就是用来标明拦截哪些类，哪些方法
 *
 * 总结，mybatis可以拦截的类分四大类
 *
 * 一、executor，executor类可以说是执行sql的全过程，如组装参数，sql改造，结果处理，比较广泛，但实际用的不多
 *
 * 二、StatementHandler，这个是执行sql的过程，可以获取到待执行的sql，可用来改造sql，如分页，分表，最常拦截的类
 *
 * 三、paremeterHandler，这个用来拦截sql的参数，可以自定义参数组装规则
 *
 * 四、resultHandler，这个用来处理结果
 */
@Intercepts(
        value = {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}
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
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");

        String sql = boundSql.getSql();
        System.out.println("===");
        System.out.println("===");
        System.out.println("拦截了"+sql);
        System.out.println("===");
        System.out.println("===");
        // 设置 sql 语句
        metaObject.setValue("",sql);

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
