package com.xindijia.rpc;

import cn.hutool.core.util.IdUtil;
import com.xindijia.rpc.constant.ProtocolConstant;
import com.xindijia.rpc.constant.RpcConstant;
import com.xindijia.rpc.model.RpcRequest;
import com.xindijia.rpc.myenum.ProtocolMessageSerializerEnum;
import com.xindijia.rpc.myenum.ProtocolMessageStatusEnum;
import com.xindijia.rpc.myenum.ProtocolMessageTypeEnum;
import com.xindijia.rpc.protocol.ProtocolMessage;
import com.xindijia.rpc.protocol.ProtocolMessageDecoder;
import com.xindijia.rpc.protocol.ProtocolMessageEncoder;
import io.vertx.core.buffer.Buffer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author xia
 * @since 2024/4/22/16:53
 */
public class ProtocolMessageTest {

    @Test
    public void testEncodeAndDecode() throws IOException {
        //构造消息
        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(10);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("myService");
        rpcRequest.setMethodName("myMethod");
        rpcRequest.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        rpcRequest.setParameterTypes(new Class[]{String.class});
        rpcRequest.setArgs(new Object[]{"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);

        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encodeBuffer);
        System.out.println(message);
        Assert.assertNotNull(message);
    }
}
