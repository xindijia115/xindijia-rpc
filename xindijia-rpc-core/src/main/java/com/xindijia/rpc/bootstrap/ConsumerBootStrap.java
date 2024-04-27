package com.xindijia.rpc.bootstrap;

import com.xindijia.rpc.RpcApplication;

/**
 * @author DJ
 * @since 2024/4/27/14:15
 * 消费者启动类(初始化)
 */
public class ConsumerBootStrap {
    /**
     * 初始化
     */
    public static void init() {
        //RPC框架初始化(配置和注册中心)
        RpcApplication.init();
    }
}
