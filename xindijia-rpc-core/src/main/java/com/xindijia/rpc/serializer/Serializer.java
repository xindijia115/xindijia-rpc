package com.xindijia.rpc.serializer;

import java.io.IOException;

/**
 * @author xia
 * @since 2024/3/12/21:48
 * 序列化器接口
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
