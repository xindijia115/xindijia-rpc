package com.xindijia.rpc.constant;

/**
 * @author DJ
 * @since 2024/4/26/21:00
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间重试
     */
    String FIXED_INTERVAL = "fixedInterval";
}
