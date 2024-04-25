package com.xindijia.rpc;

import com.xindijia.rpc.config.RegistryConfig;
import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.proxy.RegistryFactory;
import com.xindijia.rpc.registry.LocalRegistry;
import com.xindijia.rpc.registry.Registry;
import com.xindijia.rpc.server.VertxHttpServer;
import com.xindijia.rpc.server.tcp.VertxTcpServer;
import com.xindijia.rpc.service.UserService;
import com.xindijia.rpc.service.impl.UserServiceImpl;

/**
 * @author xia
 * @since 2024/4/20/21:50
 * 服务提供者启动类
 */
public class ProviderExample {
    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        //注册到服务中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        //启动web服务
//        VertxHttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        //启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
