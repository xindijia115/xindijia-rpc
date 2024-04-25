package com.xindijia.rpc.server.tcp;

import com.xindijia.rpc.constant.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

/**
 * @author xia
 * @since 2024/4/24/16:55
 * 装饰着模式（使用recordParser对原有的buffer处理能力进行增强）
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }

    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        //构造parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH);
        parser.setOutput(new Handler<Buffer>() {
            //初始化
            int size = -1;
            //一次性完整读取(消息头 + 消息体)
            Buffer resultBuffer = Buffer.buffer();
            @Override
            public void handle(Buffer buffer) {
                if (-1 == size) {
                    //读取消息体长度
                    size = buffer.getInt(13);
                    parser.fixedSizeMode(size);
                    //写入头消息得到结果
                    resultBuffer.appendBuffer(buffer);
                } else {
                    //写入体消息得到结果
                    resultBuffer.appendBuffer(buffer);
                    // 已拼接为完整 Buffer，执行处理
                    bufferHandler.handle(resultBuffer);
                    //重置一轮
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }
            }
        });
        return parser;
    }

}
