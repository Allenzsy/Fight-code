package zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Author Allenzsy
 * @Date 2022/12/15 14:29
 * @Description:
 */
@Slf4j
public class ZKConnectionEventHandler implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("连接成功: {}", watchedEvent);
    }
}
