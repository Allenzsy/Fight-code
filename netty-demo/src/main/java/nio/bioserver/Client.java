package nio.bioserver;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 18:16
 * @Description:
 */
public class Client {
    public static void main(String[] args) throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("waiting...");
    }

}
