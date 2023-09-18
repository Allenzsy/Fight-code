package concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author Allenzsy
 * @Date 2022/8/15 22:59
 * @Description: 创建线程的3种方式, 继承 Thread, 实现 Runnable 和 Callable 接口
 */
@Slf4j
public class CreateThreadDemo {

    @Test
    public void test_createThread() throws Exception{
        Thread runnable = new Thread(new RunnableDemo(), "使用 Runnable 实现线程,并传入 Thread 启动线程");
        ThreadDemo thread = new ThreadDemo("继承 Thread 实现线程, 直接创建线程");
        FutureTask<Integer> futureTaskCallable = new FutureTask<>(new CallableDemo());
        Thread threadFutureTaskCallable = new Thread(futureTaskCallable, "使用 Callable 实现线程, 并传入 FutureTask 进行包装, 再传入 Thread 启动线程");
        FutureTask<String> futureTaskRunnable = new FutureTask<>(new RunnableDemo(), "0000");
        Thread threadFutureTaskRunnable = new Thread(futureTaskRunnable, "使用 Runnable 实现线程, 并传入 FutureTask 进行包装, 再传入 Thread 启动线程");

        log.info("启动子线程");
        runnable.start();
        thread.start();
        threadFutureTaskRunnable.start();
        threadFutureTaskCallable.start();

        LockSupport.park();

        log.info("获取设置的执行完成的返回值: {}, 会阻塞调用线程", futureTaskRunnable.get());
        log.info("获取运算执行完成后的返回值: {}, 会阻塞调用线程", futureTaskCallable.get());
    }

    class RunnableDemo implements Runnable {
        @Override
        public void run() {
            log.info("实现 Runnable 接口并重写 run() 方法");
        }
    }

    class ThreadDemo extends Thread {
        public ThreadDemo(String name) {
            super(name);
        }

        @Override
        public void run() {
            log.info("继承 Thread 父类并重写 run() 方法");
        }
    }

    class CallableDemo implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            log.info("实现 Callable 接口并重写 call() 方法");
            return 10086;
        }
    }

}
