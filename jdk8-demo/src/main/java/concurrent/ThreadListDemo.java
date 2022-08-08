package concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Allenzsy
 * @Date 2022/8/8 23:09
 * @Description:
 */

@Slf4j
public class ThreadListDemo {

    @Test
    public void test_lock_condition() throws Exception {
        List shareList = new ArrayList<>();
        Lock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        //Condition notYet = lock.newCondition();


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
