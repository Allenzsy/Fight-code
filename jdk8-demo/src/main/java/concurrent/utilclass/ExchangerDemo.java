package concurrent.utilclass;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Allenzsy
 * @Date 2022/11/19 23:41
 * @Description:
 */
@Slf4j
public class ExchangerDemo {

    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        pool.execute(() -> {
            String A = "银行流水A";
            try {
                log.info("线程A: {}", LocalTime.now());
                exchanger.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.execute(() -> {
            String B = "银行流水B";
            try {
                Thread.sleep(3000);
                String A = exchanger.exchange(B);
                log.info("线程B: {}", LocalTime.now());
                log.info("A和B数据是否一致: {}，A录入的是: {}，B录入是: {}", A.equals(B), A, B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }
}
