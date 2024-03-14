package com.xindijia.rpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.WindowFocusListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xia
 * @since 2024/3/14/19:04
 * Mock服务代理
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据方法的类型返回，生成特定默认的对象
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(returnType);
    }

    /**
     * 生成指定类型的默认值对象
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
        //基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == short.class) {
                return (short)0;
            } else if (type == int.class) {
                return (int) 0;
            } else if (type == long.class) {
                return 0L;
            }
        }
        //对象
        return null;
    }
}
