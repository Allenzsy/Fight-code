package nio.base.nioserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Author Allenzsy
 * @Date 2022/7/15 19:00
 * @Description:
 */
@Slf4j
public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer e = iterator.next();
            if(e == 3) {
                iterator.remove();
            }
            log.info("{}", e);
        }
        iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer e = iterator.next();
            log.info("{}", e);
        }
        log.info("{}", 1 << 0);
    }

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
