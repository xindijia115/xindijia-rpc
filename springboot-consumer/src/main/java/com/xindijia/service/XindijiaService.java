package com.xindijia.service;

import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.service.UserService;
import com.xindijia.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author DJ
 * @since 2024/4/27/19:23
 */
@Service
public class XindijiaService {

    @RpcReference
    private UserService userService;

    public String getUser() {
        User user = new User();
        user.setName("XDJ");
        User newUser = userService.getUser(user);
        return "name: " + newUser.getName();
    }
}
