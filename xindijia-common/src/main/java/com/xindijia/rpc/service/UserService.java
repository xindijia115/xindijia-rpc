package com.xindijia.rpc.service;

/**
 * @author xia
 * @since 2024/3/12/20:32
 */

import com.xindijia.rpc.entities.User;

/**
 * 用户接口
 */
public interface UserService {
    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);


    default short getNumber() {
        return 1;
    }
}
