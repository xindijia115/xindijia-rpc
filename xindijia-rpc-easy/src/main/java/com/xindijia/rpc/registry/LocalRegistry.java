package com.xindijia.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xia
 * @since 2024/3/12/21:29
 * 本地注册中心
 */

public class LocalRegistry {

    /**
     * 注册信息存储
     * 使用线程安全的ConcurrentHashMap来存储服务注册的信息，key为服务名称，value为服务实现类，通过反射去调用方法
     */
    public static final Map<String, Class<?>> map =  new ConcurrentHashMap<>();

    /**
     * 注册服务
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> getService(String serviceName) {
        return map.get(serviceName);
    }

    public static void deleteService(String serviceName) {
        map.remove(serviceName);
    }
}
