package jvm;

/**
 * -Xss2M -Xms20m -Xmx20m
 */
public class JavaStackOOM {

    private void dontStop() {
        while(true) {
        }
    }
    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }
    public static void main(String[] args) {
/*        JavaStackOOM oom = new JavaStackOOM();
        oom.stackLeakByThread();*/
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {

        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
