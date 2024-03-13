package com.xindijia.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xia
 * @since 2024/3/13/20:26
 * 动态代理工厂，根据指定类创建动态代理对象
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
