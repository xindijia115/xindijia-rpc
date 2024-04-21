package com.xindijia.rpc.registry;

import com.xindijia.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author xia
 * @since 2024/4/21/16:36
 * 注册中心本地缓存，服务消费者可以直接拿，使用一个服务列表来存储服务信息
 */
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读取缓存
     *
     * @return
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }


}
