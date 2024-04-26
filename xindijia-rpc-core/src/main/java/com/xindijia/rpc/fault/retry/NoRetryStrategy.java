package com.xindijia.rpc.fault.retry;

import com.xindijia.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author DJ
 * @since 2024/4/25/21:47
 * 不重试策略，不重试策略也是一种策略
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
