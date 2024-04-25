package com.xindijia.rpc.proxy;

import com.xindijia.rpc.loadbalancer.LoadBalancer;
import com.xindijia.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.xindijia.rpc.spi.SpiLoader;

/**
 * @author DJ
 * @since 2024/4/25/20:26
 * 负载均衡器工厂(工厂模式，用于获取负载均衡器对象)
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
