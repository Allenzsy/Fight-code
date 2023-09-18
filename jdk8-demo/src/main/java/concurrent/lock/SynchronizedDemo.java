package concurrent.lock;

import java.util.PriorityQueue;

/**
 * @Author Allenzsy
 * @Date 2022/11/15 23:30
 * @Description:
 */
public class SynchronizedDemo {
    {
        final PriorityQueue<Integer> integers = new PriorityQueue<>((o1, o2) -> o2-o1);
    }
    public synchronized void method(){
        System.out.println("修饰实例方法");
    }

    public static synchronized void classMethod(){
        System.out.println("修饰静态方法");
    }

    public void frag() {
        synchronized(this) {
            System.out.println("对指定对象加锁");
        }
    }

}
