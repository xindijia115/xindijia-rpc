package com.xindijia.rpc.server;

/**
 * @author xia
 * @since 2024/3/12/20:51
 * web服务器接口HttpServer，定义统一启动服务器的方法
 */
public interface HttpServer {

    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
