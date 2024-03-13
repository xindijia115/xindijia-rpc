package com.xindijia.rpc;

import com.xindijia.rpc.registry.LocalRegistry;
import com.xindijia.rpc.server.VertxHttpServer;
import com.xindijia.rpc.service.UserService;
import com.xindijia.rpc.service.impl.UserServiceImpl;

/**
 *服务提供者启动类
 */
public class EasyProviderExample {
    public static void main( String[] args ) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        //启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
