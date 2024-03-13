package com.xindijia.rpc.server;

import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;
import com.xindijia.rpc.registry.LocalRegistry;
import com.xindijia.rpc.serializer.JdkSerializer;
import com.xindijia.rpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author xia
 * @since 2024/3/12/21:59
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    /**
     * 业务流程
     * 1.反序列化请求为对象，从请求对象中获取参数
     * 2.根据服务名称从本地注册中获取对应服务实现类
     * 3.通过反射机制调用方法，返回处理结果
     * 4.对结果进行封装和序列化，并写入到响应中
     */

    @Override
    public void handle(HttpServerRequest request) {
        //指定序列化器
        final JdkSerializer jdkSerializer = new JdkSerializer();

        //记录日志
        System.out.println("receive request:" + request.method() + " " + request.uri());

        //异步处理Http请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = jdkSerializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            //如果请求为null直接返回
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, jdkSerializer);
                return;
            }

            try {
                //获取调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.getService(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                //封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //响应
            doResponse(request, rpcResponse, jdkSerializer);

        });
    }

    /**
     * 响应
     *
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse =
                request.response().putHeader("content-type", "application/json");
        try {
            //序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
