package com.xindijia.rpc.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xindijia.rpc.entities.User;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;
import com.xindijia.rpc.serializer.JdkSerializer;

import java.io.IOException;

/**
 * @author xia
 * @since 2024/3/13/19:53
 * 静态代理
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        //指定序列化器
        JdkSerializer jdkSerializer = new JdkSerializer();
        //发送请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            //序列化
            byte[] bodyBytes = jdkSerializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes).execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = jdkSerializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
