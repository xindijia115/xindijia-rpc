package com.xindijia.rpc.proxy;

import com.xindijia.rpc.constant.SerializerKeys;
import com.xindijia.rpc.serializer.*;
import com.xindijia.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xia
 * @since 2024/3/16/14:33
 * 序列化器工厂，用于获取序列化器对象
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

//    /**
//     * 序列化映射
//     */
//    private static final Map<String, Serializer> KEY_SERIALIZER_MAP =
//            new HashMap<String, Serializer>() {
//                {
//                    put(SerializerKeys.JDK, new JdkSerializer());
//                    put(SerializerKeys.JSON, new JsonSerializer());
//                    put(SerializerKeys.KRYO, new KryoSerializer());
//                    put(SerializerKeys.HESSIAN, new HessianSerializer());
//                }
//            };

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
