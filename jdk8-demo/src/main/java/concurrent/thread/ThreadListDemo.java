package concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Allenzsy
 * @Date 2022/8/8 23:09
 * @Description:
 * 给定一个 List ，一个线程不断往往 List 中放入元素，一个线程需要监控当 List 中有10个元素时，发出一个通知
 * 这里还要看题意，说的不是很清晰：
 * 通知的时候需要保证 List 中就只有10个元素么？
 * 否的话：使用 CountDownLatch 和 CyclicBarrier 进行线程协作即可
 * 是的话：需要使用 Lock 和 Condition 进行线程协作和加锁
 */

@Slf4j
public class ThreadListDemo {

    @Test
    public void test_CountDownLatch() throws Exception{
        List shareList = new ArrayList<>();
        CountDownLatch count = new CountDownLatch(1);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                shareList.add(i);
                log.info("添加元素: {}", i);
                if (shareList.size() == 10) {
                    count.countDown();
                }
            }
        });

        Thread watcher = new Thread(() -> {
            try {
                if (shareList.size() != 10) {
                    count.await();
                    log.info("队列中已有 {} 个元素", shareList.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        watcher.start();
        producer.start();

        watcher.join();
        producer.join();

    }

    @Test
    public void test_CyclicBarrier() throws Exception{
        List shareList = new ArrayList<>();
        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    shareList.add(i);
                    log.info("添加元素: {}", i);
                    if (shareList.size() == 10) {
                        barrier.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });

        Thread watcher = new Thread(() -> {
            try {
                if (shareList.size() != 10) {
                    barrier.await();
                    log.info("队列中已有 10 个元素");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        watcher.start();
        producer.start();

        watcher.join();
        producer.join();

    }


    /**
     * 使用 Lock 配合 Condition 实现
     * @throws Exception
     */
    @Test
    public void test_lock_condition() throws Exception {
        List shareList = new ArrayList<>();
        Lock lock = new ReentrantLock();
        Condition cond = lock.newCondition();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    lock.lock();
                    log.info("添加元素: {}", i);
                    shareList.add(i);
                    if (shareList.size() == 10) {
                        cond.signalAll();
                        cond.await();
                    }
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread watch = new Thread(() -> {
            try {
                lock.lock();
                if (shareList.size() != 10) {
                    cond.await();
                    log.info("此时数组大小为: {}", shareList.size());
                    cond.signalAll();
                }
                lock.unlock();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        watch.start();
        producer.start();

        watch.join();
        producer.join();
    }

}
