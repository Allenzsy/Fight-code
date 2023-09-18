package netty.nio.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import netty.util.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 19:00
 * @Description:
 */
@Slf4j
public class ByteBufferDemo {

    @Test
    public void test(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        log.info("写入前...");
        log.info("capacity: {}", buffer.capacity());
        log.info("position: {}", buffer.position());
        log.info("limit: {}", buffer.limit());
        buffer.put("123".getBytes());
        log.info("写入后...");
        log.info("capacity: {}", buffer.capacity());
        log.info("position: {}", buffer.position());
        log.info("limit: {}", buffer.limit());
        buffer.flip();
        log.info("切换读模式后...");
        log.info("capacity: {}", buffer.capacity());
        log.info("position: {}", buffer.position());
        log.info("limit: {}", buffer.limit());
        ByteBufferUtil.debugRead(buffer);
    }

    @Test
    public void test2() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("123\n88889".getBytes());
        ByteBufferUtil.split(buffer, '\n');
    }

}
