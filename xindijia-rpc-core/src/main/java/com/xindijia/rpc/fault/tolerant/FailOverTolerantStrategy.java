package com.xindijia.rpc.fault.tolerant;

import com.xindijia.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author DJ
 * @since 2024/4/27/12:13
 * 故障转移
 */
public class FailOverTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // TODO 获取其他节点并调用
        return null;
    }
}
