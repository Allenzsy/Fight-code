package nio.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author Allenzsy
 * @Date 2022/7/22 0:53
 * @Description:
 */
public class ChatServer {

    public static void main(String[] args) {

        // 1. 每个NioEventLoopGroup里有多个EventLoop，一个EventLoop持有一个Selector，可以处理多个Channel上的事件
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

        // 2. 创建服务端，并传入服务端ServerSocketChannel的实现类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class)
                       .group(boss, worker)
                        // 添加处理客户端事件的处理器
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                           /**
                            * 放入处理事件的处理器到pipeline中
                            * @param ch
                            * @throws Exception
                            */
                           @Override
                           protected void initChannel(SocketChannel ch) throws Exception {
                               ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,
                                       12, 4, 0, 0));
                               ch.pipeline().addLast(LOGGING_HANDLER);
                               //ch.pipeline(new MessageCodec)
                           }
                       });



    }

}
