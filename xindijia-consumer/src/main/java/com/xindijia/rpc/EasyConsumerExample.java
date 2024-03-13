package com.xindijia.rpc;

import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.proxy.ServiceProxyFactory;
import com.xindijia.rpc.service.UserService;
import com.xindijia.rpc.service.UserServiceProxy;

/**
 * 服务消费者启动类
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        //代理对象
        //UserService userService = null;
        //静态代理
        //UserServiceProxy userService = new UserServiceProxy();
        //动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xindijia");
        //服务调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
