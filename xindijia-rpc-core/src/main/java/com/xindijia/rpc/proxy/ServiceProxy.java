package com.xindijia.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.config.RpcConfig;
import com.xindijia.rpc.constant.ProtocolConstant;
import com.xindijia.rpc.constant.RpcConstant;
import com.xindijia.rpc.loadbalancer.LoadBalancer;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.myenum.ProtocolMessageSerializerEnum;
import com.xindijia.rpc.myenum.ProtocolMessageTypeEnum;
import com.xindijia.rpc.protocol.ProtocolMessage;
import com.xindijia.rpc.protocol.ProtocolMessageDecoder;
import com.xindijia.rpc.protocol.ProtocolMessageEncoder;
import com.xindijia.rpc.registry.Registry;
import com.xindijia.rpc.serializer.JdkSerializer;
import com.xindijia.rpc.serializer.Serializer;
import com.xindijia.rpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author xia
 * @since 2024/3/13/20:09
 * jdk动态服务代理
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        //JdkSerializer serializer = new JdkSerializer();
        //通过使用工厂+读取配置来获取实现类
        final Serializer serializer =
        SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        System.out.println(serializer.toString());
        //构造请求
        String serviceName = method.getDeclaringClass().getName();
        System.out.println("serviceName: " + serviceName);
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            //从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            //将调用方法名(请求路径)作为负载均衡参数
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            //ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
            System.out.println("address: " + selectedServiceMetaInfo.getServiceAddress());
            //发送TCP请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }
}
