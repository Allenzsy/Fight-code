package chapter1.class1;

import org.junit.Test;

public class VolatileExample {
    /*
    * 变量 x 可能被 CPU 缓存而导致可见性问题，
    * 如果 Java 的版本在低于 1.5 版本上运行，x 可能是 42，也有可能是 0；
    * 如果在 1.5 及以上的版本上运行，x 就是等于 42
    * */
    int x = 0;
    boolean v = false; // volatile

    public void writer() {
        x = 42;
        v = true;
        System.out.println("写线程执行完成");
    }

    public void reader() {
        if (v == true) { // 这里x会是多少呢？
            System.out.println("x的值是：" + x);
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
        System.out.println("主线程执行完成");
    }
}
