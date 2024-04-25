package com.xindijia.rpc.server.tcp;

import com.xindijia.rpc.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;

/**
 * @author xia
 * @since 2024/4/22/14:50
 */
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        //编写处理请求的逻辑，根据requestData构造响应数据并返回
        return "Hello, client".getBytes();
    }

    @Override
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();
        //创建tcp服务器
        NetServer server = vertx.createNetServer();

        //处理请求
        server.connectHandler(new TcpServerHandler());

        //启动tcp服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port:" + port);
            } else {
                System.err.println("Failed to start TCP server:" + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
