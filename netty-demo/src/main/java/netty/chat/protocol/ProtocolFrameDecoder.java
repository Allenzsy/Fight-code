package netty.chat.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author Allenzsy
 * @Date 2022/7/23 21:54
 * @Description:
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder() {
        super(1024, 12, 4, 0, 0);
    }

}
