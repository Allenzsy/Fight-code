package concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintN {

    public static int maxNum = 100;
    public static int threadCount = 10;
    public static volatile int curNum = 0;
    public static Object LOCK = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            new Thread(() -> {
                printN(threadCount, threadNum);
            }, "print-"+(i+1)).start();
        }
    }

    public static void printN(int N, int threadNum) {

        while (true) {
            synchronized (LOCK) {
                if(curNum >= maxNum) {
                    break;
                }
                if(curNum % N == threadNum) {
                    curNum++;
                    log.info("{}, {}", Thread.currentThread().getName(), curNum);
//                    System.out.println("" + Thread.currentThread() + "-"+ curNum);
                }
            }
        }

    }


}
