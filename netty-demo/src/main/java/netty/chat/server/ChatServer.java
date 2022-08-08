package netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import netty.chat.protocol.MessageCodecSharable;
import netty.chat.protocol.ProtocolFrameDecoder;

/**
 * @Author Allenzsy
 * @Date 2022/7/22 0:53
 * @Description:
 */
@Slf4j
public class ChatServer {

    public static void main(String[] args) {

        // 1. 每个NioEventLoopGroup里有多个EventLoop，一个EventLoop持有一个Selector，可以处理多个Channel上的事件
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 2. 创建服务端，并传入服务端ServerSocketChannel的实现类
        try {
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
                                   ch.pipeline().addLast(new ProtocolFrameDecoder());
                                   ch.pipeline().addLast(LOGGING_HANDLER);
                                   ch.pipeline().addLast(messageCodecSharable);
                               }
                           });
            Channel channel = serverBootstrap.bind(8888).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error: ", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

}
