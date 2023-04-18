package concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParkDemo {

    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(() -> {
            log.info("{}", "执行park...");
            LockSupport.park();
            if (Thread.currentThread().isInterrupted()) {
                log.info("{}", "被中断...");
            }
            log.info("{}", "结束...");
        }, "thread-1");
        thread.start();
        TimeUnit.SECONDS.sleep(2L);
        thread.interrupt();
        thread.join();
    }

}
