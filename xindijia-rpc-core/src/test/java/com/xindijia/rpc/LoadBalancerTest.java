package com.xindijia.rpc;

import com.xindijia.rpc.loadbalancer.ConsistentHashLoadBalancer;
import com.xindijia.rpc.loadbalancer.LoadBalancer;
import com.xindijia.rpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author DJ
 * @since 2024/4/25/20:40
 * 负载均衡器测试
 */
public class LoadBalancerTest {

    final LoadBalancer loadBalancer = new ConsistentHashLoadBalancer();

    @Test
    public void select() {
        //请求参数
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", "zhanzhanbaobao");
        //服务列表
        ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setAddress("localhost:8000");

        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("2.0");
        serviceMetaInfo2.setAddress("xdj666.com:9999");

        List<ServiceMetaInfo> serviceMetaInfoList = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2);

        //连续调用三次
        ServiceMetaInfo serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);

        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);

        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
        Assert.assertNotNull(serviceMetaInfo);

    }
}
