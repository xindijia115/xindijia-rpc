package com.xindijia.rpc.fault.tolerant;

import com.xindijia.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author DJ
 * @since 2024/4/27/12:04
 * 容错策略
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e 异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
