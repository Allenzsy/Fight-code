package concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2022/5/22 17:02
 * @Description:
 */
@Slf4j
public class CompletableFutureDemo {

    @Test
    public void test_applyAsync() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(()->{
            log.info("异步线程1执行");
            sleep(3, TimeUnit.SECONDS);
            return "异步线程1执行完毕";
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(()->{
            log.info("异步线程2执行");
            sleep(2, TimeUnit.SECONDS);
            return 2+2;
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (res1, res2) -> {
            log.info("线程3开始执行");
            return String.format("线程1结果：%s,线程2结果：%s", res1, res2+"");
        });

        log.info(f3.join());
    }


    void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        }catch(InterruptedException e){}
    }
}
