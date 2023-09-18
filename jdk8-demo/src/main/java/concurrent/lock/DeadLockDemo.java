package concurrent.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Allenzsy
 * @Date 2022/11/19 15:44
 * @Description:
 */
@Slf4j
public class DeadLockDemo {

    public static Object resource1 = new Object();
    public static Object resource2 = new Object();


    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            log.info("线程1启动");
            synchronized (resource1) {
                log.info("线程1成功获取 resource1");
                try {
                    Thread.sleep(3000);
                    log.info("线程1开始获取 resource2");
                    synchronized (resource2) {
                        log.info("线程1成功获取 resource2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程1");

        Thread thread2 = new Thread(() -> {
            log.info("线程2启动");
            synchronized (resource2) {
                log.info("线程2成功获取 resource2");
                try {
                    Thread.sleep(3000);
                    log.info("线程2开始获取 resource1");
                    synchronized (resource1) {
                        log.info("线程2成功获取 resource1");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程2");

        thread1.start();
        thread2.start();

    }

}
