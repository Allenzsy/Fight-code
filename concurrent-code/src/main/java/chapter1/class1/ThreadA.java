package chapter1.class1;

public class ThreadA implements Runnable {

    VolatileExample ve;
    int flag = 0;

    ThreadA(VolatileExample ve, int flag) {
        this.ve = ve;
        this.flag = flag;
    }

    public void run() {
        if (flag == 0) {
            ve.writer();
        } else {
            ve.reader();
        }
    }
}
