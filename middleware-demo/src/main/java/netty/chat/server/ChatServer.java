package netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.chat.protocol.MessageCodecSharable;
import netty.chat.protocol.ProtocolFrameDecoder;
import netty.chat.server.handler.*;

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
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 各种消息的 handler
        LoginRequestMessageHandler LOGON_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();

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
                                   ch.pipeline().addLast(MESSAGE_CODEC);
                                   //还需要处理一种连接假死的情况, 此时应该服务器端主动断开和客户端的连接, 5秒内未发生读事件就触发一个特殊事件 IdleStateEvent
                                   ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                                   ch.pipeline().addLast(new ChannelDuplexHandler() {
                                       @Override
                                       public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                           IdleStateEvent event = (IdleStateEvent) evt;
                                           // 触发了读空闲事件
                                           if (event.state() == IdleState.READER_IDLE) {
                                               log.debug("已经 10s 没有读取到数据了");
                                               ctx.channel().close();
                                           }
                                       }
                                   });
                                   // 添加接收请求的 handler 可以选择感兴趣的类型, 这里之后再重构
                                   ch.pipeline().addLast(LOGON_HANDLER);
                                   ch.pipeline().addLast(CHAT_HANDLER);
                                   ch.pipeline().addLast(GROUP_CREATE_HANDLER);
                                   ch.pipeline().addLast(GROUP_JOIN_HANDLER);
                                   ch.pipeline().addLast(GROUP_MEMBERS_HANDLER);
                                   ch.pipeline().addLast(GROUP_QUIT_HANDLER);
                                   ch.pipeline().addLast(GROUP_CHAT_HANDLER);
                                   ch.pipeline().addLast(QUIT_HANDLER);
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
