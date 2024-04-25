package com.xindijia.rpc.loadbalancer;

import com.xindijia.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author DJ
 * @since 2024/4/25/19:52
 * 负载均衡器(消费端使用)
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     *
     * @param requireParams
     * @param serviceMetaInfoList
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requireParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
