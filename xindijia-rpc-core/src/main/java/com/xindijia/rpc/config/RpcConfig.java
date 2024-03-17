package com.xindijia.rpc.config;

import com.xindijia.rpc.constant.SerializerKeys;
import lombok.Data;

/**
 * @author xia
 * @since 2024/3/14/16:54
 * RPC框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "xindijia-rpc";

    /**
     * 版本号
     */
    private String version ="1.0";

    /**
     * 服务主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private Boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
}
