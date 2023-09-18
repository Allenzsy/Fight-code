package concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Allenzsy
 * @Date 2023/3/22 22:47
 * @Description:
 */

@Slf4j
public class ReentrantLockDemo {

    public volatile int count;

    public static void main(String[] args) throws Exception{
        final ReentrantLockDemo resource = new ReentrantLockDemo();
        final ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread();
        for (int i = 0; i < 3; i++) {
            thread = new Thread(resource.new MyThread(lock, resource),
                    String.format("thread-%s", i));
            thread.start();
            TimeUnit.SECONDS.sleep(1L);
        }

        thread.join();
    }


    class MyThread implements Runnable {

        ReentrantLock lock;
        ReentrantLockDemo resource;

        public MyThread(ReentrantLock _lock, ReentrantLockDemo _resource) {
            lock = _lock;
            resource = _resource;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    resource.count++;
                }
                log.info("{} 执行完, 此时 count: {}", Thread.currentThread().getName(), resource.count);
            } finally {
                lock.unlock();
            }
        }
    }
}
