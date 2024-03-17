package com.xindijia.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;
import com.xindijia.rpc.serializer.JdkSerializer;
import com.xindijia.rpc.serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xia
 * @since 2024/3/13/20:09
 * jdk动态服务代理
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        //JdkSerializer serializer = new JdkSerializer();
        //通过使用工厂+读取配置来获取实现类
        final Serializer serializer =
        SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        System.out.println(serializer.toString());
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            //序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            //发送请求
            //这里的地址被硬编码了，需要注册中心和服务发现来解决
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080").body(bodyBytes).execute()) {
                byte[] result = httpResponse.bodyBytes();
                //反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
