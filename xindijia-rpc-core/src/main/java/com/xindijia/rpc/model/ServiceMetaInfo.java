package com.xindijia.rpc.model;

import cn.hutool.core.util.StrUtil;
import com.xindijia.rpc.constant.RpcConstant;
import lombok.Data;

/**
 * @author xia
 * @since 2024/4/18/20:25
 * 服务注册信息
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务分组
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        //后续可以扩展服务分组
        // return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNoteKey() {
        return String.format("%s/%s", getServiceKey(), address);
    }

    /**
     * 获取完整服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(address, "http")) {
            return String.format("http://%s", address);
        }
        return String.format("%s", address);
    }
}
