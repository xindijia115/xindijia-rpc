package com.xindijia.rpc.constant;

/**
 * @author xia
 * @since 2024/4/18/21:27
 * 注册中心键名常量, 支持多个注册中心，像序列化器一样，让开发者能够填写配置来指定使用的注册中心，并支持自定义中心，让框架易用，便于拓展
 */
public interface RegistryKeys {
    String ETCD = "etcd";

    String ZOOKEEPER = "zookeeper";
}
