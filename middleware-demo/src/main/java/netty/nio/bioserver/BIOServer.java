package netty.nio.bioserver;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static netty.util.ByteBufferUtil.debugRead;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 15:54
 * @Description:
 */
@Slf4j
public class BIOServer {

    /**
     * 使用nio理解阻塞模式
     * 非阻塞模式下，相关方法都会不会让线程暂停
     *    1. 在 ServerSocketChannel.accept 在没有连接建立时，会返回 null，继续运行
     *    2. SocketChannel.read 在没有数据可读时，会返回 0，但线程不必阻塞，可以去执行其它 SocketChannel 的 read 或是去执行 ServerSocketChannel.accept
     *       写数据时，线程只是等待数据写入 Channel 即可，无需等 Channel 通过网络把数据发送出去
     *    3. 但非阻塞模式下，即使没有连接建立，和可读数据，线程仍然在不断运行，白白浪费了 cpu
     * 数据复制过程中，线程实际还是阻塞的（AIO 改进的地方）
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接， SocketChannel 用来与客户端之间通信
            log.debug("connecting...");
            SocketChannel sc = ssc.accept(); // 阻塞方法，线程停止运行
            log.debug("connected... {}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                log.debug("before read... {}", channel);
                channel.read(buffer); // 阻塞方法，线程停止运行
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }

}
