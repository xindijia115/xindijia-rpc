package com.xindijia.rpc.entities;

import java.io.Serializable;

/**
 * @author xia
 * @since 2024/3/12/20:31
 */

/**
 * 用户
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
