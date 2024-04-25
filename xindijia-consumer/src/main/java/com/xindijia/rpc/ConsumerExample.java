package com.xindijia.rpc;

import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.proxy.ServiceProxyFactory;
import com.xindijia.rpc.service.UserService;
import com.xindijia.rpc.utils.ConfigUtils;

/**
 * @author xia
 * @since 2024/3/14/17:36
 */
public class ConsumerExample {
    public static void main(String[] args) {
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);
        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xdj666");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("------------------------");
            System.out.println("userName:" + newUser.getName());
            System.out.println("------------------------");
        } else {
            System.out.println("user == null");
        }
        System.out.println(userService.getNumber());
    }
}
