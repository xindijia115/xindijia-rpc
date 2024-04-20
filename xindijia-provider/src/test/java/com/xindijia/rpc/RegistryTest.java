package com.xindijia.rpc;
import com.xindijia.rpc.config.RegistryConfig;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.registry.EtcdRegistry;
import com.xindijia.rpc.registry.Registry;
import org.junit.Before;
import org.junit.Test;


/**
 * @author xia
 * @since 2024/4/20/22:29
 */

public class RegistryTest {

    final Registry registry = new EtcdRegistry();

    @Before
    public void init() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("http://localhost:2379");
        registry.init(registryConfig);
    }

    @Test
    public void register() throws Exception {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setAddress("localhost:1234");
        registry.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setAddress("localhost:1235");
        registry.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("2.0");
        serviceMetaInfo.setAddress("localhost:1234");
        registry.register(serviceMetaInfo);
    }

    @Test
    public void unRegister() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setAddress("localhost:1234");
        registry.unRegistry(serviceMetaInfo);
    }

//    @Test
//    public void serviceDiscovery() {
//        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
//        serviceMetaInfo.setServiceName("myService");
//        serviceMetaInfo.setServiceVersion("1.0");
//        String serviceKey = serviceMetaInfo.getServiceKey();
//        List&lt;ServiceMetaInfo&gt; serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
//        Assert.assertNotNull(serviceMetaInfoList);
//    }
}
