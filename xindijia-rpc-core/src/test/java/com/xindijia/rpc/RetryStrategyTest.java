package com.xindijia.rpc;

import com.xindijia.rpc.fault.retry.NoRetryStrategy;
import com.xindijia.rpc.fault.retry.RetryStrategy;
import com.xindijia.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * @author DJ
 * @since 2024/4/25/21:56
 * 重试策略测试
 */
public class RetryStrategyTest {
    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
