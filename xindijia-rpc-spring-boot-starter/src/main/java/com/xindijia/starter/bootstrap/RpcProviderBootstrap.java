package com.xindijia.starter.bootstrap;

import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.config.RegistryConfig;
import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.proxy.RegistryFactory;
import com.xindijia.rpc.registry.LocalRegistry;
import com.xindijia.rpc.registry.Registry;
import com.xindijia.starter.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author DJ
 * @since 2024/4/27/14:57
 * 获取@RpcService注解的类，并获取属性值
 */
public class RpcProviderBootstrap implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            //需要注册服务
            //1. 获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            //默认值处理
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            //2. 注册服务
            //本地注册
            LocalRegistry.register(serviceName, beanClass);

            //全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "注册服务失败", e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
