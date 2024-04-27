package com.xindijia.starter.annotation;

import com.xindijia.rpc.constant.RpcConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DJ
 * @since 2024/4/27/14:28
 * 服务提供者注解(用于注册服务)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RpcService {

    /**
     * 服务接口类
     *
     * @return
     */
    Class<?> interfaceClass() default void.class;


    /**
     * 服务版本
     *
     * @return
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
