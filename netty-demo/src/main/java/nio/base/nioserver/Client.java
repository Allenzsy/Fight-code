package nio.base.nioserver;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 18:16
 * @Description:
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8888));
        sc.write(Charset.defaultCharset().encode("12377888999000---\n"));
        System.out.println("waiting...");
        //Thread.sleep(1000*3);
        // 接收数据
        int count = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        while(true) {
            count += sc.read(buffer);
            buffer.clear();
            log.info("实际读取了: {} byte", count);
        }
        //sc.close();
    }

}
