package concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Allenzsy
 * @Date 2022/10/28 20:00
 * @Description:
 */
@Slf4j
public class ThreadLocalDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new MyTask());
        }
        Thread.sleep(100);
        System.gc();
        //退出线程池  ---一定要shutdown掉线程池，不然主线程不会结束
        threadPool.shutdown();
    }

    @Slf4j
    static class MyTask implements Runnable {

        public static Random random = new Random();

        @Override
        public void run() {
            int i = random.nextInt();
            if (i % 2 == 0) {
                StringContextHolder.HOLDER2.set("修改为"+i);
            }
            log.info(StringContextHolder.HOLDER2.get());
            try {
                Thread.sleep(10000);
                log.info(StringContextHolder.HOLDER2.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class StringContextHolder {

        /**
         * 下面两种都是第一次 get 时,可以给赋值一个初始值
         */
        public static ThreadLocal<String> HOLDER1 = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "初始值";
            }
        };

        public static ThreadLocal<String> HOLDER2 = ThreadLocal.withInitial(
                () -> "初始值2"
        );
    }

}
