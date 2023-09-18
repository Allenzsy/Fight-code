package netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.chat.message.*;
import netty.chat.protocol.MessageCodecSharable;
import netty.chat.protocol.ProtocolFrameDecoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Allenzsy
 * @Date 2022/7/23 21:31
 * @Description:
 */
@Slf4j
public class ChatClient {
    /**
     * 两个位置可以发送登录请求:
     * 1. 建立链接之后 sync() 那里
     * 2. 建立连接之后 handler 触发 active 事件那里
     */

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
        AtomicBoolean LOGIN = new AtomicBoolean(false);
        AtomicBoolean EXIT = new AtomicBoolean(false);
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            // 7s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                            ch.pipeline().addLast(new IdleStateHandler(0, 7, 0));
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了写空闲事件
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                }
                            });

                            // 添加一个 handler 用于处理连接成功之后触发的请求
                            ch.pipeline().addLast("client handler", new MyChannelInboundHandlerAdapter(LOGIN, EXIT, WAIT_FOR_LOGIN));
                        }
                    });
            Channel channel = bootstrap.connect("localhost", 8888).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error: ", e);
        } finally {
            group.shutdownGracefully();
        }


    }

    private static class MyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
        private final AtomicBoolean IS_LOGIN;
        private final AtomicBoolean IS_EXIT;
        private final CountDownLatch WAIT_LOGIN;

        public MyChannelInboundHandlerAdapter(AtomicBoolean LOGIN, AtomicBoolean EXIT, CountDownLatch WAIT_LOGIN) {
            this.IS_LOGIN = LOGIN;
            this.IS_EXIT = EXIT;
            this.WAIT_LOGIN = WAIT_LOGIN;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("{}", msg);
            // 是登录响应时, 根据返回值设置是否登录成功
            if (msg instanceof LoginResponseMessage) {
                LoginResponseMessage responseMessage = (LoginResponseMessage) msg;
                if(responseMessage.isSuccess()) {
                    IS_LOGIN.set(true);
                }
                WAIT_LOGIN.countDown();
            }
        }

        /**
         * 在连接建立后触发 active 事件, 开始监听用户输入
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 需要接收用户输入, 为了不阻塞处理请求的线程, 这里单独启用一个线程监听用户输入
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入用户名: ");
                String username = scanner.nextLine();
                if(IS_EXIT.get()){
                    return;
                }
                System.out.println("请输入密码: ");
                String password = scanner.nextLine();
                if(IS_EXIT.get()){
                    return;
                }
                LoginRequestMessage message = new LoginRequestMessage(username, password);
                ctx.writeAndFlush(message);

                try {
                    WAIT_LOGIN.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!IS_LOGIN.get()) {
                    ctx.channel().close();
                    return;
                }
                while (true) {
                    System.out.println("==================================");
                    System.out.println("send [username] [content]");
                    System.out.println("gsend [group name] [content]");
                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                    System.out.println("gmembers [group name]");
                    System.out.println("gjoin [group name]");
                    System.out.println("gquit [group name]");
                    System.out.println("quit");
                    System.out.println("==================================");
                    String command = scanner.nextLine();
                    if(IS_EXIT.get()){
                        return;
                    }
                    String[] s = command.split(" ");
                    switch (s[0]) {
                        case "send":
                            ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                            break;
                        case "gsend":
                            ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                            break;
                        case "gcreate":
                            Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                            set.add(username); // 加入自己
                            ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], set));
                            break;
                        case "gmembers":
                            ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                            break;
                        case "gjoin":
                            ctx.writeAndFlush(new GroupJoinRequestMessage(username, s[1]));
                            break;
                        case "gquit":
                            ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                            break;
                        case "quit":
                            ctx.channel().close();
                            return;
                    }
                }

            }, "用户输入线程").start();
        }

        // 在连接断开时触发
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.debug("连接已经断开，按任意键退出..");
            IS_EXIT.set(true);
        }

        // 在出现异常时触发
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.debug("连接已经断开，按任意键退出..{}", cause.getMessage());
            IS_EXIT.set(true);
        }
    }


}
