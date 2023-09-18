package netty.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.chat.message.ChatRequestMessage;
import netty.chat.message.ChatResponseMessage;
import netty.chat.server.session.SessionFactory;

/**
 * @Author Allenzsy
 * @Date 2022/8/14 15:13
 * @Description:
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String toUser = msg.getTo();
        Channel channel = SessionFactory.getSession().getChannel(toUser);
        if(channel != null) {
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
        } else {
            ctx.channel().writeAndFlush(new ChatResponseMessage(false, "对方用户不存在或用户不在线"));
        }
    }
}
