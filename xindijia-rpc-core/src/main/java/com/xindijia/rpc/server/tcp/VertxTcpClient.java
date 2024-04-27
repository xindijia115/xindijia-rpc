package com.xindijia.rpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.xindijia.rpc.RpcApplication;
import com.xindijia.rpc.constant.ProtocolConstant;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.model.RpcResponse;
import com.xindijia.rpc.model.ServiceMetaInfo;
import com.xindijia.rpc.myenum.ProtocolMessageSerializerEnum;
import com.xindijia.rpc.myenum.ProtocolMessageTypeEnum;
import com.xindijia.rpc.protocol.ProtocolMessage;
import com.xindijia.rpc.protocol.ProtocolMessageDecoder;
import com.xindijia.rpc.protocol.ProtocolMessageEncoder;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xia
 * @since 2024/4/22/15:03
 */
public class VertxTcpClient {

    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException {
        //int i = 10 / 0; //测试重试策略
        //发送TCP请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        String address = serviceMetaInfo.getAddress();
        String[] split = address.split(":");
        String host = split[0];
        Integer port = Integer.parseInt(split[1]);
        netClient.connect(port, host, result -> {
            if (!result.succeeded()) {
                System.err.println("Failed to connect to TCP server");
                return;
            }

            NetSocket socket = result.result();
            //发送数据
            //构造消息
            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
            ProtocolMessage.Header header = new ProtocolMessage.Header();
            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
            header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
            header.setRequestId(IdUtil.getSnowflakeNextId());
            protocolMessage.setHeader(header);
            protocolMessage.setBody(rpcRequest);
            //编码请求
            try {
                Buffer encode = ProtocolMessageEncoder.encode(protocolMessage);
                socket.write(encode);
                System.out.println("成功发送消息");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //接收响应
            TcpBufferHandlerWrapper tcpBufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
                try {
                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                } catch (IOException e) {
                    throw new RuntimeException("协议消息解码错误");
                }
            });
            socket.handler(tcpBufferHandlerWrapper);
        });

        RpcResponse rpcResponse = responseFuture.get();
        System.out.println("成功接收到响应");
        //关闭连接
        netClient.close();
        return rpcResponse;
    }

    public void start() {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();


        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("Connected to TCP server");
                NetSocket socket = result.result();
                for (int i = 0; i < 1000; i++) {//发送一千次消息，测试粘包问题
                    //发送数据
                    Buffer buffer = Buffer.buffer();
                    String str = "Hello, server!Hello, server!Hello, server!Hello, server!";
                    buffer.appendInt(0);
                    buffer.appendInt(str.getBytes().length);
                    buffer.appendBytes(str.getBytes());
                    socket.write(buffer);
                }
                //接收响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server:" + buffer.toString());
                });
            } else {
                System.err.println("Failed to connect to TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
