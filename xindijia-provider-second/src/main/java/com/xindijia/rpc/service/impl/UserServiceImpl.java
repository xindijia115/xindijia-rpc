package com.xindijia.rpc.service.impl;

import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.service.UserService;

/**
 * @author xia
 * @since 2024/3/12/20:37
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
