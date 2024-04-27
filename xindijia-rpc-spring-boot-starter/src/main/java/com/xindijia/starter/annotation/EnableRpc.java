package com.xindijia.starter.annotation;

import com.xindijia.starter.bootstrap.RpcConsumerBootstrap;
import com.xindijia.starter.bootstrap.RpcInitBootstrap;
import com.xindijia.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DJ
 * @since 2024/4/27/14:28
 * 启用Rpc注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcConsumerBootstrap.class, RpcProviderBootstrap.class})
public @interface EnableRpc {

    /**
     * 是否需要启动server
     * @return
     */
    boolean needServer() default true;
}
