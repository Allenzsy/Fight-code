package netty.chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import netty.chat.config.Config;
import netty.chat.message.Message;

import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/7/23 21:00
 * @Description: 此类需要和帧解码一起使用，保证到 Bytebuf 时已经是完整报文
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 1. 5 字节的魔数
        out.writeBytes("ZSYDD".getBytes());
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        out.writeByte(Config.getSerializerAlgorithm().ordinal()); //out.writeByte(0);
        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充，一般是2的整数倍
        //out.writeByte(0xff);
        // 6. 获取内容的字节数组
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //ObjectOutputStream oos = new ObjectOutputStream(bos);
        //oos.writeObject(msg);
        //byte[] bytes = bos.toByteArray();
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        // 向后传递结果
        outList.add(out);
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
        //ObjectInputStream ois = null;
        //if (serializerType == 0) {
        //    ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        //}
        //Message message = (Message) ois.readObject();

        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializerType];
        Message message = algorithm.deserialize(Message.getMessageClass(messageType), bytes);

        log.debug("{}, {}, {}, {}, {}, {}", new String(magicNum), version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);
        out.add(message);
    }
}
