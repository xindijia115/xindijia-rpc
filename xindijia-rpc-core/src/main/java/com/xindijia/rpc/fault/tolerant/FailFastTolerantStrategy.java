package com.xindijia.rpc.fault.tolerant;

import com.xindijia.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author DJ
 * @since 2024/4/27/12:06
 * 快速失败容错策略(立即通知外层调用方)
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
