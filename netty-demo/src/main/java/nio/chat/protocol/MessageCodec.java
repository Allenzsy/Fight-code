package nio.chat.protocol;

/**
 * @Author Allenzsy
 * @Date 2022/7/22 1:10
 * @Description:
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import nio.chat.message.LoginRequestMessage;
import nio.chat.message.Message;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/7/22 1:10
 * @Description: 从Byte转化成自定义消息的编解码类
 * ### 2.4 自定义协议要素
 * * 魔数，用来在第一时间判定是否是无效数据包
 * * 版本号，可以支持协议的升级
 * * 序列化算法，消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如：json、protobuf、hessian、jdk
 * * 指令类型，是登录、注册、单聊、群聊... 跟业务相关
 * * 请求序号，为了双工通信，提供异步能力
 * * 正文长度
 * * 消息正文
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 1. 5 字节的魔数
        out.writeBytes("ZSYDD".getBytes());
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        out.writeByte(0);
        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充，一般是2的整数倍
        //out.writeByte(0xff);
        // 6. 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] magicNum = new byte[5];
        in.readBytes(magicNum);
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        ObjectInputStream ois = null;
        if (serializerType == 0) {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        }
        Message message = (Message) ois.readObject();
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);
        out.add(message);
    }


    @Test
    public void testMessageCodec() throws Exception {
        System.out.println("ZSYDD".getBytes().length);
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG), new MessageCodec());
        // encode
        LoginRequestMessage message = new LoginRequestMessage("zsy", "123", "张三");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        channel.writeInbound(buf);
    }

    @Test
    public void testMessageCodec_halfFrame() throws Exception {
        // 不加帧解码器测试半包
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec());
        // decode
        LoginRequestMessage message = new LoginRequestMessage("zsy", "123", "张三");
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        ByteBuf buf1 = buf.slice(0, 100);
        ByteBuf buf2 = buf.slice(100, buf.readableBytes()-100);
        buf.retain();//
        // 只写一半，如果没有帧解码器发生半包直接解析就会报错，如果有帧解码器发生半包则会因为数据不完整而等待后续的数据达到之后再解析
        channel.writeInbound(buf1); // 写一次会调用 release 释放，因此前面先用 retain 增加一次引用数
        channel.writeInbound(buf2);
    }

}

