package com.xindijia.rpc.bootstrap;

import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.config.RegistryConfig;
import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.model.ServiceRegisterInfo;
import com.xindijia.rpc.proxy.RegistryFactory;
import com.xindijia.rpc.registry.LocalRegistry;
import com.xindijia.rpc.registry.Registry;
import com.xindijia.rpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author DJ
 * @since 2024/4/27/13:53
 * 服务提供者初始化
 */
public class ProviderBootStrap {

    /**
     * 初始化
     * @param serviceRegisterInfoList
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        //RPC框架初始化
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "注册服务失败", e);
            }
        }

        //启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
