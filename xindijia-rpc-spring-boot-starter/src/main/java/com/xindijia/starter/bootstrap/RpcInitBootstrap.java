package com.xindijia.starter.bootstrap;

import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.server.tcp.VertxTcpServer;
import com.xindijia.starter.annotation.EnableRpc;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author DJ
 * @since 2024/4/27/14:57
 * 获取@EnableRpc注解的属性,初始化框架
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取EnableRpc注解属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        //初始化
        RpcApplication.init();
        //全局配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //启动服务
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动服务");
        }
    }
}
