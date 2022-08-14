package netty.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.chat.message.LoginRequestMessage;
import netty.chat.message.LoginResponseMessage;
import netty.chat.server.service.UserServiceFactory;
import netty.chat.server.session.SessionFactory;

/**
 * @Author Allenzsy
 * @Date 2022/8/14 15:10
 * @Description: 没有共享信息, 因此可以指定为 @Shareable
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message = login ? new LoginResponseMessage(true, "登陆成功")
                                             : new LoginResponseMessage(false, "用户名或密码不正确");
        if(login) {
            SessionFactory.getSession().bind(ctx.channel(), username);
        }
        ctx.writeAndFlush(message);
    }
}
