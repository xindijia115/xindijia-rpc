package com.xindijia.rpc;

import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.utils.ConfigUtils;

/**
 * @author xia
 * @since 2024/3/14/17:36
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
