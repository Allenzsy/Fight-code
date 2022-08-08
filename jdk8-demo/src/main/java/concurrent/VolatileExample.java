package concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class VolatileExample {

    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
        log.info("写线程执行完成");
    }

    /*
     * 变量 x 可能被 CPU 缓存而导致可见性问题，
     * 如果 Java 的版本在低于 1.5 版本上运行，x 可能是 42，也有可能是 0；
     * 如果在 1.5 及以上的版本上运行，x 就是等于 42
     * */
    public void reader() {
        if (v == true) { // 这里x会是多少呢？
            log.info("x的值是：" + x);
        }
    }

    class ThreadA implements Runnable {

        VolatileExample ve;
        int flag = 0;

        ThreadA(VolatileExample ve, int flag) {
            this.ve = ve;
            this.flag = flag;
        }

        public void run() {
            if (flag == 0) {
                ve.writer();
            } else {
                ve.reader();
            }
        }
    }

    @Test
    public void test() throws Exception {

        VolatileExample ve = new VolatileExample();
        Thread threadWriter = new Thread(new ThreadA(ve, 0));
        Thread threadReader = new Thread(new ThreadA(ve, 1));

        threadWriter.start();
        threadReader.start();

        threadReader.join();
        threadWriter.join();
        log.info("主线程执行完成");
    }
}
