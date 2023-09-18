package jvm.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2023/2/25 10:14
 * @Description: -verbose:gc -Xmx200M -Xms200M -Xmn100M -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:PretenureSizeThreshold=4m
 */
@Slf4j
public class SoftReferenceDemo {

    private static final int MB = 1024*1024;

    //空跑 main 函数, eden 区用了 2.4M
    public static void main(String[] args) throws Exception{
        // Byte 引用类型4字节, 因此一共 4MB, 如果使用强引用就会直接 java.lang.OutOfMemoryinfo: Java heap space
        //Byte[] bytes1 = new Byte[MB];

        // 通过软引用就会直接回收这个软引用指向的对象
        final ReferenceQueue<Byte[]> queue = new ReferenceQueue<>();
        final SoftReference<Byte[]> softReference = new SoftReference<Byte[]>(new Byte[MB], queue);
        log.info("软引用: {}", softReference);

        // 把强引用置空, 否则观察不到现象的
        log.info("取消强引用");
        //bytes1 = null;
        final Thread thread = new Thread(() -> {
            while (!softReference.isEnqueued()) {
            }
            log.info("软引用被加入队列: {}", queue.poll());
        });
        thread.start();
        // 由于设置了 >=4MB 的对象直接进入老年代,因此会触发 Full GC
        byte[] bytes = new byte[56 * MB];
        //bytes = null;
        TimeUnit.SECONDS.sleep(60L);
        //byte[] bytes2 = new byte[6 * MB];
        thread.join();
    }
}
