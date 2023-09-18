package zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2022/12/15 14:22
 * @Description:
 */
@Slf4j
public class ZKService {

    private static ZooKeeper client;

    public static String ROOT_PATH = "/zsy";

    public static void main(String[] args) throws Exception{
        ZooKeeper zkService = getZKService();
        List<String> list = zkService.getChildren(ROOT_PATH, new ZKChildrenChangeEventHandler());
/*        for (String path: list) {
            log.info("子节点路径: {}", path);
        }*/

        TimeUnit.SECONDS.sleep(600);
    }

    public static ZooKeeper getZKService() {
        if (client != null) return client;
        String connectionString = "192.168.23.33:2183";
        try {
            client = new ZooKeeper(connectionString, 10*1000, new ZKConnectionEventHandler());
        } catch (IOException e) {
            log.error("{}", e);
        }
        return client;
    }

}
