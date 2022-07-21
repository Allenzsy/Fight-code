import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author Allenzsy
 * @Date 2022/7/9 8:41
 * @Description:
 */
public class Test0709 {

    @Test
    public void test() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] s = line.split(",");
        int[] nums = Arrays.stream(s).mapToInt(Integer::valueOf).toArray();
    }

    @Test
    public void test1() throws Exception{

        String[] arr = new String[]{"123", "456"};

        Thread t1 = new Thread(() -> {
            String s1 = arr[1];
            synchronized (s1) {
                System.out.println("线程1获取锁");
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread.sleep(500);

        Thread t2 = new Thread(() -> {
            synchronized (arr[0]) {
                System.out.println("线程2获取锁");
            }
        });
        t2.start();

        t2.join();



    }

}
