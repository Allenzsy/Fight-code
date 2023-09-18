package concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2022/10/29 11:17
 * @Description:
 */
@Slf4j
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(10);
        ArrayBlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<String>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, blockingQueue);
        ThreadPoolExecutorDemo demo = new ThreadPoolExecutorDemo();
        Thread thread = new Thread(demo.new MyTask(blockingQueue2));
        thread.start();
        thread.interrupt();



    }

    class MyTask implements Runnable {

        ArrayBlockingQueue<String> blockingQueue;

        public MyTask(ArrayBlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            log.info("从阻塞队列中取元素");
            try {
                blockingQueue.take();
            } catch (InterruptedException e) {
                log.info("被中断");
            }
        }
    }
}


