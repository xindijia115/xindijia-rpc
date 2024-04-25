package com.xindijia.rpc.constant;

/**
 * @author DJ
 * @since 2024/4/25/20:23
 * 负载均衡器键名常量
 */
public interface LoadBalancerKeys {
    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机
     */
    String RANDOM = "random";

    /**
     * 一致性hash
     */
    String CONSISTENT_HASH = "consistentHash";
}
