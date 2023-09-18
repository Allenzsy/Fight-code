package netty.nio.nioserver;

import lombok.extern.slf4j.Slf4j;
import netty.util.ByteBufferUtil;
import netty.util.Event;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 18:21
 * @Description:
 */
@Slf4j
public class NIOSelectorServer {

    /**
     * 多路复用IO模型
     * @param args
     */
    public static void main(String[] args) throws Exception{

        // 创建一个服务端
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // 设置非阻塞
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8888));
            // 创建一个 Select
            Selector selector = Selector.open();
            // 将ServerSocketChannel注册到selector上，并关注accept连接事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            /*// 也可以这样注册
            SelectionKey selectionKey = ssc.register(selector, 0);
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);*/
            while(true) {
                // 等待某一事件的发生，若没有未处理的事件则会阻塞，若还存在未处理的事件则阻塞
                log.info("等待事件...");
                selector.select();
                // 获取发生事件的集合
                Set<SelectionKey> keySet = selector.selectedKeys();
                // 然后通过迭代器遍历并处理事件
                Iterator<SelectionKey> keyIterator = keySet.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    // 处理key时要从selectedKeys中清除，因为selector并不会主动清理，如果不清除则下次遍历时还会处理：
                    // e.g. 上一次处理的时 accept 事件，没清除下次遍历还处理 accept 事件但此时可能并没有客户端连接，因此会返回 null
                    keyIterator.remove();
                    log.info("发生事件: {}, key对象为: {}", Event.eventName(key), key);
                    if(key.isAcceptable()) {
                        ServerSocketChannel c = (ServerSocketChannel) key.channel();
                        SocketChannel sc = c.accept();
                        sc.configureBlocking(false);
                        ByteBuffer readBuffer = ByteBuffer.allocate(10); // 创建一个读客户端连接用的ByteBuffer
                        List<ByteBuffer> buffers = new ArrayList<>(); // 由于要把读和写用的ByteBuffer分开因此创建了一个List
                        buffers.add(readBuffer); // 其实封装一个类，里面有两个ByteBuffer分别负责读写也挺好
                        // 将socketChannel注册到selector，并设置关注读事件，和设置attachment附件
                        SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ, buffers);
                        log.info("已建立连接: {}", sc);

                        // 如果此时想往客户端发送大量数据，比如
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 300; i++) {
                            sb.append("a");
                        }
                        ByteBuffer source = Charset.defaultCharset().encode(sb.toString());
                        // 此时一次性肯定是没法写完的，如果此时使用 while 循环持续的写，会出现两个问题：
                        // 1. 其他的客户端请求会阻塞无法处理
                        // 2. 由于存在send-Q发送缓冲区，当缓冲区满时无法将ByteBuffer中剩余的数据写入，此时只写入了0字节但是却发生了用户态到内核态的切换
                        // 因此，先写一次，若剩余由没写完的，就在selector上注册一个关注写事件，这样当发送缓冲区可写时会触发一个写事件，就可以写剩余数据了
                        int writed = sc.write(source);
                        // 模拟没写完的情况（在我本机多大都直接写过去。。。不懂为什么）
                        log.info("实际写入了: {} byte",writed);
                        source = Charset.defaultCharset().encode(sb.toString());
                        if(source.hasRemaining()) {
                            scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);
                            buffers.add(source); // 将写客户端用的ByteBuffer也加到，前边已经把buffers attach过了，这里直接加到buffers里就行
                        }
                    } else if(key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 客户端正常关闭或异常关闭都会发出读事件，只不过客户端正常关闭服务端读取时返回 -1,异常关闭服务端读取时抛异常
                        try {
                            List<ByteBuffer> buffers = (List<ByteBuffer>) key.attachment();
                            ByteBuffer oldBuffer = buffers.get(0);
                            // 若一次读不下，会再触发一次读事件
                            int read = sc.read(oldBuffer);
                            if(read == -1) { // 客户端正常关闭
                                log.info("客户端正常关闭: {}", sc);
                                key.cancel();
                                sc.close();
                            } else {
                                ByteBufferUtil.split(oldBuffer, '\n');
                                // 说明没找到分隔符，也就是出现了分包
                                if(oldBuffer.position() == oldBuffer.limit() && oldBuffer.get(oldBuffer.position()-1) != '\n') {
                                    // 扩容并下一次读取
                                    ByteBuffer newBuffer = ByteBuffer.allocate(oldBuffer.capacity() * 2);
                                    oldBuffer.flip();
                                    newBuffer.put(oldBuffer);
                                    buffers.remove(0);
                                    buffers.add(0, newBuffer);
                                    //key.attach(newBuffer);
                                }
                            }
                        } catch (IOException e) {
                            log.error("", e);
                            // cancel 会取消注册在 selector 上的 channel，并把 key 加入到 cancelKeys 中
                            key.cancel();
                        }
                    } else if(key.isWritable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        List<ByteBuffer> buffers = (List<ByteBuffer>) key.attachment();
                        ByteBuffer source = buffers.get(1);
                        int writed = sc.write(source);
                        log.info("实际写入了: {} byte",writed);
                        if(!source.hasRemaining()) {
                            key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
                            buffers.remove(1);
                        }
                    }

                }

            }

        }
    }

}
