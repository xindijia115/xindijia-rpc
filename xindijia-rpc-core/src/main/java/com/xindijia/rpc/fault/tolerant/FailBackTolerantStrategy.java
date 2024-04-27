package com.xindijia.rpc.fault.tolerant;

import com.xindijia.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author DJ
 * @since 2024/4/27/12:11
 * 降级到其他服务
 */
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //TODO 降级到其他的服务并调用
        return null;
    }
}
