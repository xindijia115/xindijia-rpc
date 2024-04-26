package com.xindijia.rpc.fault.retry;

import com.xindijia.rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author DJ
 * @since 2024/4/25/21:42
 * 重试策略
 */
public interface RetryStrategy {
    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
