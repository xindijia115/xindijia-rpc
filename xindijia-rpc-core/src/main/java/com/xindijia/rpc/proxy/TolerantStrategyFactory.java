package com.xindijia.rpc.proxy;

import com.xindijia.rpc.fault.tolerant.FailFastTolerantStrategy;
import com.xindijia.rpc.fault.tolerant.TolerantStrategy;
import com.xindijia.rpc.spi.SpiLoader;

/**
 * @author DJ
 * @since 2024/4/27/12:20
 * 容错策略工厂
 */
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
