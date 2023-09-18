package concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2023/9/2 16:04
 * @Description:
 */
@Slf4j
public class SleepDemo {

    @Test
    public void test_sleep() throws Exception{
        Thread.sleep(100000);
    }

}
