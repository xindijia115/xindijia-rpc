package com.xindijia.rpc.server;


import io.vertx.core.Vertx;

/**
 * @author xia
 * @since 2024/3/12/20:54
 * 基于Vertx的Web服务器，能够监听指定的端口并处理请求
 */
public class VertxHttpServer implements HttpServer{
    /**
     * 启动服务器
     * @param port
     */
    @Override
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();
        //创建http服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();

        //监听端口并处理请求
        httpServer.requestHandler(new HttpServerHandler());

        //启动http服务器并监听指定的端口
        httpServer.listen(port, result -> {
            if(result.succeeded()) {
                System.out.println("server is now listening on port:" + port);
            } else {
                System.out.println("failed to start server:" + result.cause());
            }
        });
    }
}
