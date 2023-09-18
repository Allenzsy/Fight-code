package zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Allenzsy
 * @Date 2022/12/15 16:04
 * @Description:
 */
@Slf4j
public class ZKChildrenChangeEventHandler implements Watcher {

    private ReentrantLock commandLock = new ReentrantLock();

    @Override
    public void process(WatchedEvent event) {
        log.info("等待释放锁...");
        commandLock.lock();
        try {
            // 再次添加节点
            List<String> childrens = ZKService.getZKService().getChildren(ZKService.ROOT_PATH, new ZKChildrenChangeEventHandler());
            log.info("事件名称: {}", event.getType());

            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                for (String s : childrens) {
                    TimeUnit.SECONDS.sleep(5);
                    String fullPath = String.format("%s/%s", ZKService.ROOT_PATH, s);
                    byte[] data = ZKService.getZKService().getData(fullPath, false, null);
                    log.info("子节点路径: {}, 子节点数据: {}", fullPath, new String(data, StandardCharsets.UTF_8));
                    if("xxx".equals(s) || "xxxx".equals(s)) {
                        // 无论版本号是多少都删除
                        ZKService.getZKService().delete(fullPath, -1);
                    }
                }
            }
        } catch (Exception e) {
            log.error("CommandListener listenCommand exception ",e);
        } finally {
            commandLock.unlock();

        }
    }

    @Test
    public void test_d() {
        System.out.println(Paths.get("foo", "bar", "baz.txt").toString());
        System.out.println(String.format("%s/%s", ZKService.ROOT_PATH, "asd"));
    }



}
