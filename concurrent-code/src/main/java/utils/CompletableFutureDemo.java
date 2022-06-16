package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2022/5/22 17:02
 * @Description:
 */
public class CompletableFutureDemo {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Test
    public void test_applyAsync() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(()->{
            logger.info("异步线程1执行");
            sleep(3, TimeUnit.SECONDS);
            return "异步线程1执行完毕";
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(()->{
            logger.info("异步线程2执行");
            sleep(2, TimeUnit.SECONDS);
            return 2+2;
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (res1, res2) -> {
            logger.info("线程3开始执行");
            return String.format("线程1结果：%s,线程2结果：%s", res1, res2+"");
        });

        logger.info(f3.join());
    }


    void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        }catch(InterruptedException e){}
    }
}
