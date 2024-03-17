package com.xindijia.rpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;

import java.io.IOException;

/**
 * @author xia
 * @since 2024/3/16/13:52
 * json序列化器
 */
public class JsonSerializer implements Serializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, type);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, type);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, type);
        }
        return obj;
    }

    /**
     * 由于Object的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法装换成原始对象，所以需要作特殊处理
     *
     * @param rpcRequest rpc请求
     * @param type       类型
     * @param <T>
     * @return
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        //循环处理每个参数的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            //如果类型不同，则重新处理一下类型
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        return type.cast(rpcRequest);
    }

    /**
     * 由于Object的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法装换成原始对象，所以需要作特殊处理
     *
     * @param rpcResponse rpc响应
     * @param type        类型
     * @param <T>
     * @return
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        //处理响应数据
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
