package com.xindijia.rpc;

import com.xindijia.rpc.bootstrap.ProviderBootStrap;
import com.xindijia.rpc.model.ServiceRegisterInfo;
import com.xindijia.rpc.service.UserService;
import com.xindijia.rpc.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJ
 * @since 2024/4/27/14:07
 * 服务提供者示例
 */
public class ProviderExampleBootStrap {
    public static void main(String[] args) {
        //要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserServiceImpl> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        //服务提供者初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
}
