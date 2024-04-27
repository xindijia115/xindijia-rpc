package com.xindijia.service;

import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.service.UserService;
import com.xindijia.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author DJ
 * @since 2024/4/27/15:59
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
