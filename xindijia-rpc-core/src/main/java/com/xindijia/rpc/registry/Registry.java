package com.xindijia.rpc.registry;

import com.xindijia.rpc.config.RegistryConfig;
import com.xindijia.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author xia
 * @since 2024/4/18/20:51
 * 注册中心接口
 */
public interface Registry {

    /**
     * 监听(消费端)
     *
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);

    /**
     * 心跳检测(服务端)
     */
    void heartBeat();

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务(服务端)
     *
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务(服务端)
     *
     * @param serviceMetaInfo
     */
    void unRegistry(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现(获取某服务的所有节点, 消费端)
     *
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();
}
