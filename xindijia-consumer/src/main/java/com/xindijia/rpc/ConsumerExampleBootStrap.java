package com.xindijia.rpc;

import com.xindijia.rpc.bootstrap.ConsumerBootStrap;
import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.proxy.ServiceProxyFactory;
import com.xindijia.rpc.service.UserService;

/**
 * @author DJ
 * @since 2024/4/27/14:18
 */
public class ConsumerExampleBootStrap {
    public static void main(String[] args) {
        //消费者初始化
        ConsumerBootStrap.init();

        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("DJ");
        //调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
